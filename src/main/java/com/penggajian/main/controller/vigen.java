package com.penggajian.main.controller;

public class vigen {
	
	 /**
     * Encrypt using Vigenere cipher
     * @param s open text
     * @param key key phrase (only capital letters)
     * @return ciphertext (only capital letters)
     */
    public static String encipher(String s, String key){
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < s.length(); i ++){
            if(s.charAt(i) < 33 || s.charAt(i) > 90){ //ASCII character (capital letter)
                throw new IllegalArgumentException("" +
                        "Open text must contain only capital letters");
            }
            //add shift modularly
//            System.out.println(s.charAt(i));
//            System.out.println(getShift(key, i));
//            System.out.println(s.charAt(i) + getShift(key, i));
//            System.out.println(s.charAt(i) + getShift(key, i) - 26);
            char encyphered = s.charAt(i) + getShift(key, i) > 90 ? (char)((s.charAt(i) + getShift(key, i)) - 26) : (char)(s.charAt(i) + getShift(key, i));
            builder.append(encyphered);
        }
        return builder.toString();
    }
    /**
     * Decrypt using Vigenere cipher
     * @param s cipher text (only capital letters)
     * @param key key phrase (only capital letters)
     * @return open text
     */
    public static String decipher(String s, String key){
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < s.length(); i ++){
            if(s.charAt(i) < 33 || s.charAt(i) > 90){ //ASCII character (capital letter)
                throw new IllegalArgumentException("" +
                        "Ciphertext must contain only capital letters");
            }
            //subtract shift modularly
//            System.out.println(s.charAt(i));
//            System.out.println(getShift(key, i));
            char decyphered = s.charAt(i) - getShift(key, i) < 33 ? (char)((s.charAt(i) - getShift(key, i)) + 26) : (char)(s.charAt(i) - getShift(key, i));
            builder.append(decyphered);
        }
        return builder.toString();
    }
    /**
     * Get shift
     * @param key key phrase
     * @param i position in the text
     * @return shift
     */
    private static int getShift(String key, int i) {
            if(key.charAt(i % key.length()) < 33 || key.charAt(i % key.length()) > 90){
                throw new IllegalArgumentException("" +
                        "Key phrase must contain only capital letters");
            }
//            System.out.println(((int)key.charAt(i % key.length())) - 33);
        return ((int)key.charAt(i % key.length())) - 33;
    }
    
    
    
    
    public static void main(String[] args){

        String text = "4000000";
        String key = "!V@SHA";
        
        String enciphered = encipher(text, key);
        System.out.println(enciphered);
        System.out.println(decipher(enciphered, key));
    }

}
