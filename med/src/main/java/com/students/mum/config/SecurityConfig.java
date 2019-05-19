package com.students.mum.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.students.mum.security.JwtAuthenticationFilter;
import com.students.mum.security.JwtAuthorizationFilter;
import com.students.mum.security.SecurityConstants;
import com.students.mum.security.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
//
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private static final String LOGIN_ERROR = "/login-error";
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	@Autowired
	private DataSource dataSource;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/h2-console/**").permitAll();
		// FormLoginConfigurer<HttpSecurity> formLogin = new FormLoginConfigurer<>();
		// formLogin.defaultSuccessUrl("/").loginPage("/login").failureUrl("/login-error");

		JwtAuthenticationFilter authen = new JwtAuthenticationFilter(authenticationManager());
		authen.setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler(LOGIN_ERROR));
		SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
		successHandler.setDefaultTargetUrl("/");
		authen.setAuthenticationSuccessHandler(successHandler);

		// authen.setAuthenticationFailureHandler(failureHandler);
		http
		.addFilter(authen).addFilter(new JwtAuthorizationFilter(authenticationManager()))
		.authorizeRequests()
				.antMatchers("/login", "/registration", "/h2-console/**").permitAll().and().formLogin()
				.loginPage("/login").failureUrl(LOGIN_ERROR).defaultSuccessUrl("/").and().logout()
				.deleteCookies(SecurityConstants.TOKEN_COOKIE_NAME).and().csrf().ignoringAntMatchers("/h2-console/**").and()// .ignoringAntMatchers("/h2-console/**");//
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and() // .disable();//
				// // don't apply CSRF
				// protection to /h2-console
				.exceptionHandling().accessDeniedPage("/error/access-denied").and().rememberMe()
				.rememberMeParameter("remember-me").tokenRepository(tokenRepository());
		// http.sessionManagement().disable();
		// http.rememberMe().rememberMeParameter("remember-me").key("uniqueAndSecret");
		http.headers().frameOptions().disable();
	}

	@Bean
	public PersistentTokenRepository tokenRepository() {
		JdbcTokenRepositoryImpl jdbcTokenRepositoryImpl = new JdbcTokenRepositoryImpl();
		jdbcTokenRepositoryImpl.setDataSource(dataSource);
		return jdbcTokenRepositoryImpl;
	}
}