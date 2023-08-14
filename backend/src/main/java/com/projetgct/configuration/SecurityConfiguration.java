package com.projetgct.configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;


@Configuration
public class SecurityConfiguration {
	
	@Value("${Ad.url}")
	private String adUrl;
	

	@Value("${Ad.domain}")
	private String adDomain;

    protected void configure(HttpSecurity http) throws Exception {
    	  http
    	   .authorizeRequests()
    	   .anyRequest().fullyAuthenticated()
    	   .and()
    	   .formLogin();
    	 }




    	@Bean
    	public AuthenticationProvider activeDirectoryLdapAuthenticationProvider() {
  
    	ActiveDirectoryLdapAuthenticationProvider activeDirectoryLdapAuthenticationProvider = new ActiveDirectoryLdapAuthenticationProvider( adDomain, adUrl);
 
    	activeDirectoryLdapAuthenticationProvider.setConvertSubErrorCodesToExceptions(true); 
  
    	return activeDirectoryLdapAuthenticationProvider;
    	}
 
 
    	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	auth.authenticationProvider(activeDirectoryLdapAuthenticationProvider());
    	
    	}
 
} 