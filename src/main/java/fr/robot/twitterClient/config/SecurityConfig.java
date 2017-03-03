package fr.robot.twitterClient.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
@ComponentScan(basePackages = { "fr.robot.twitterClient.security" })
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired private @Qualifier("userDetailsService") UserDetailsService userDetailsService;
	@Autowired private DataSource dataSource;
	@Autowired private PersistentTokenRepository tokenRepository;
	@Autowired private LogoutSuccessHandler myLogoutSuccessHandler;
    @Autowired private AuthenticationFailureHandler authenticationFailureHandler;
	
	@Override
	public void configure(final WebSecurity web) throws Exception {
	       web.ignoring().antMatchers("/resources/**");
	    }
	
    @Override 
    protected void configure(HttpSecurity http) throws Exception {
        http
        	.csrf().disable()
        	.headers().disable()
	        .authorizeRequests()
	        .antMatchers("/","/login*", "/logout*","/user/registration*", "/registration*","/forgetPassword*", "/user/resetPassword*","/resources/**","/webjars/**").permitAll()
	        .antMatchers("/invalidSession*").anonymous()
	        //.antMatchers("/admin/**").access("hasRole('ADMIN')")
	        .anyRequest().authenticated()
	        .and()
	        
            .formLogin()
            	.successHandler(savedRequestAwareAuthenticationSuccessHandler())
               	.loginPage("/login")
                .defaultSuccessUrl("/home")
                .failureUrl("/login?exceptions")
                //.successHandler(myAuthenticationSuccessHandler)
                .successHandler(savedRequestAwareAuthenticationSuccessHandler())
                .failureHandler(authenticationFailureHandler)
                .permitAll().and()
			/*.rememberMe()
				.rememberMeParameter("remember-me")
				.tokenRepository(tokenRepository)
				.tokenValiditySeconds(86400).and() */
			.sessionManagement()
                .invalidSessionUrl("/invalidSession.html")
                .maximumSessions(1).sessionRegistry(sessionRegistry()).and()
                .sessionFixation().none().and()
             .headers()
             		.xssProtection().block(false).
             		and().defaultsDisabled().cacheControl().
             		and().contentTypeOptions().
             		and().httpStrictTransportSecurity().includeSubDomains(true).maxAgeInSeconds(31536000).
             		and().frameOptions().sameOrigin().and()
             .logout()
                //.logoutSuccessHandler(myLogoutSuccessHandler)
                .logoutSuccessUrl("/login?logout");
                
    }
    
    /** 
     * Configures the authentication manager bean which processes authentication
     * requests.
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }
    
    
    @Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
		db.setDataSource(dataSource);
		return db;
	}
    
    @Bean
  		public PersistentTokenBasedRememberMeServices getPersistentTokenBasedRememberMeServices() {
  			PersistentTokenBasedRememberMeServices tokenBasedservice = new PersistentTokenBasedRememberMeServices(
  					"remember-me", userDetailsService(), tokenRepository);
  			return tokenBasedservice;
  		}
	
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder();
    }
    
   /**
    * This bean is load the same page if user session time out and user log in to applicaion again.
    */
	@Bean
	public SavedRequestAwareAuthenticationSuccessHandler savedRequestAwareAuthenticationSuccessHandler() {
		SavedRequestAwareAuthenticationSuccessHandler auth = new SavedRequestAwareAuthenticationSuccessHandler();
		auth.setTargetUrlParameter("targetUrl");
		return auth;
	}
	
	  @Bean
	    public SessionRegistry sessionRegistry() {
	        return new SessionRegistryImpl();
	    }
}
