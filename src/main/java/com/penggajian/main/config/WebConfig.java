/**
 * 
 */
package com.penggajian.main.config;

//import javax.servlet.Filter;

//import org.apache.catalina.Context;
//import org.apache.catalina.connector.Connector;
//import org.apache.tomcat.util.descriptor.web.SecurityCollection;
//import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
//import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;


/**
 * @author Fajar
 *
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter
{   
	
	
	@Autowired
    private MessageSource messageSource;

    @Override
    public Validator getValidator() {
        LocalValidatorFactoryBean factory = new LocalValidatorFactoryBean();
        factory.setValidationMessageSource(messageSource);
        return factory;
    }
    	
	//http://stackoverflow.com/questions/25957879/filter-order-in-spring-boot
//	@Bean
//	public FilterRegistrationBean securityFilterChain(@Qualifier(AbstractSecurityWebApplicationInitializer.DEFAULT_FILTER_NAME) Filter securityFilter) {
//	    FilterRegistrationBean registration = new FilterRegistrationBean(securityFilter);
//	    registration.setOrder(Integer.MAX_VALUE - 1);
//	    registration.setName(AbstractSecurityWebApplicationInitializer.DEFAULT_FILTER_NAME);
//	    return registration;
//	}

	
	
    @Override
	public void addViewControllers(ViewControllerRegistry registry)
	{
		super.addViewControllers(registry);
        registry.addViewController("/login").setViewName("public/login");
		registry.addRedirectViewController("/", "/home");
		
	}
	
	
	@Bean 
	public ClassLoaderTemplateResolver emailTemplateResolver(){ 
		ClassLoaderTemplateResolver TemplateResolver = new ClassLoaderTemplateResolver(); 
		TemplateResolver.setSuffix(".html"); 
		TemplateResolver.setTemplateMode("HTML5"); 
		TemplateResolver.setCharacterEncoding("UTF-8"); 
		TemplateResolver.setOrder(2);
		
		return TemplateResolver; 
	}
	
	
}
