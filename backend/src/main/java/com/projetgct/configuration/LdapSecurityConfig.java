package com.projetgct.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.security.ldap.authentication.BindAuthenticator;
import org.springframework.security.ldap.authentication.LdapAuthenticationProvider;
import org.springframework.security.ldap.search.FilterBasedLdapUserSearch;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class LdapSecurityConfig {
	@Value("${ldap.url}")
	private String ldapUrl;
	
	@Value("${ldap.userdn}")
	private String userDn;
	
	@Value("${ldap.usersearch}")
	private String userSearch;
	
	@Value("${ldap.useruid}")
	private String userUid;
	
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

      http
       .authorizeHttpRequests()
       .anyRequest().fullyAuthenticated()
       .and()
       .formLogin();
      http.authenticationProvider(ldapAuthenticationProvider());
      return http.build();
    }

    @Bean
    LdapAuthenticationProvider ldapAuthenticationProvider() {
       return new LdapAuthenticationProvider(authenticator());
    }

    @Bean
    BindAuthenticator authenticator() {

    	FilterBasedLdapUserSearch search = new FilterBasedLdapUserSearch(userSearch,userUid, contextSource());

       BindAuthenticator authenticator = new BindAuthenticator(contextSource());
       authenticator.setUserSearch(search);
       return authenticator;
    }

    @Bean
    public DefaultSpringSecurityContextSource contextSource() {
       DefaultSpringSecurityContextSource dsCtx = new DefaultSpringSecurityContextSource(ldapUrl);
       dsCtx.setUserDn(userDn);
       return dsCtx;
    }
}