package com.penggajian.main.controller;

import java.util.Arrays;

public class vigentest {
	
	 public static String encrypt(String key, String clear) {
		    
		    // Initialize varibles and arrays
		    int keyLength = key.length();
		    int clearLength = clear.length();
		    int[]  keyAscii = new int[keyLength];
		    int[]  clearAscii = new int[clearLength];
		    String[] cipherString = new String[clearLength];
		    int[]  cipherAscii = new int[clearLength];;
		    String cipher;
		    
		    // Store Ascii values of each character of the key
		    for(int i = 0; i < key.length(); i++) {
		      keyAscii[i] = (int) key.charAt(i);
		    }
		    
		    // Store Ascii values of each character of the cleartext
		    for (int i = 0; i < clear.length(); i++) {
		      clearAscii[i] = (int) clear.charAt(i);
		    }
		    
		    // Create ciphertext
		    int j = 0;
		    for (int i = 0; i < clear.length(); i++) {
		      cipherAscii[i] = (keyAscii[j] - 65) + clearAscii[i];
		      if (cipherAscii[i] > 90) cipherAscii[i] = cipherAscii[i] - 26;
		      cipherString[i] = Character.toString ((char) cipherAscii[i]);
		      j++;
		      if (j == key.length()) j = 0;
		    }
		    
		    // Link the strings from cipherString[] into one big string
		    cipher = Arrays.toString(cipherString);
		    
		    return cipher;
		  }
		  
		  public static String decrypt(String key, String cipher) {
		    
		    // Initialize varibles and arrays
		    int keyLength = key.length();
		    int cipherLength = cipher.length();
		    char[] keyChars = new char[keyLength];
		    int[]  keyAscii = new int[keyLength];
		    char[] cipherChars = new char[cipherLength];
		    int[]  cipherAscii = new int[cipherLength];
		    String[] clearString = new String[cipherLength];
		    int[]  clearAscii = new int[cipherLength];;
		    String clear;
		    
		    // Store the key as an array of chars and store the Ascii values
		    for(int i = 0; i < key.length(); i++) {
		      keyChars[i] = key.charAt(i);
		      keyAscii[i] = (int) keyChars[i];
		    }
		    
		    // Store the cipher as an array of chars and store the Ascii values
		    for (int i = 0; i < cipher.length(); i++) {
		      cipherChars[i] = cipher.charAt(i);
		      cipherAscii[i] = (int) cipherChars[i];
		    }
		    
		    // Create ciphertext
		    int j = 0;
		    for (int i = 0; i < cipher.length(); i++) {
		      clearAscii[i] = cipherAscii[i] - (keyAscii[j] - 65);
		      if (clearAscii[i] < 65) clearAscii[i] = clearAscii[i] + 26;
		      clearString[i] = Character.toString ((char) clearAscii[i]);
		      j++;
		      if (j == key.length()) j = 0;
		    }
		    
		    // Link the strings from clearString[] into one big string
		    clear = Arrays.toString(clearString);
		    
		    return clear;
		  }
		  
		  public static void crack(int keyspace, String cipher) {
		    // Initialize
		    String key = "";
		    String clear;
		    int[] keyAscii = new int[keyspace];
		    Arrays.fill(keyAscii, 33);
		    double iters = (Math.pow(26.0, (double) keyspace));
		    
		    // Form string from array of Ascii values
		    StringBuilder sb = new StringBuilder();
		    for (int j = 0; j < keyspace; j++) {
		      sb.append( (char) keyAscii[j] );
		    }
		    key = sb.toString();
		    
		    // Try every possible key
		    for (int i = 0; i < iters; i++) {
		      
		      // Decrypt that key
		      clear = decrypt(key, cipher);
		      
		      // Print
		      System.out.println(key + ": " + clear);
		      
		      // Get next key
		      key = nextKey(key);
		    }
		  }
		  
		  public static String nextKey(String key) {
		    int keyspace = key.length();
		    StringBuilder sb = new StringBuilder(key);
		    if ( (int) key.charAt(keyspace - 1) == 90 ) {
		      for (int i = 1; i < keyspace; i++) {
		        if ( (int) key.charAt(keyspace - i) == 90 ) {
		          sb.setCharAt(keyspace - i, 'A');
		          int current = (int) sb.charAt(keyspace - (i + 1));
		          char next = (char) (current + 1);
		          sb.setCharAt(keyspace - (i + 1), next);
		        }
		      }
		    key = sb.toString();
		    return key;
		    }
		    else {
		      int current = (int) sb.charAt(keyspace - 1);
		      char next = (char) (current + 1);
		      sb.setCharAt(keyspace - 1, next);
		      key = sb.toString();
		      return key;
		    }
		  }
		  
		  public static void main(String args[]) {
		    
		    // Initialize values
//		    String key = "LEMON";
//		    String clear = "ATTACKATDAWN";
		    String cipher = "LXFOPVEFRNHR";
		    int keyspace = 1;
		    
		    crack(keyspace, cipher);
		  }

}
