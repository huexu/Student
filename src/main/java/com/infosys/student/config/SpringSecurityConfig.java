package com.infosys.student.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	/*
	 * @Autowired DataSource dataSource;
	 * 
	 * @Autowired BCryptPasswordEncoder encoder;
	 */

	@Autowired
	private AuthenticationEntryPoint authEntryPoint;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

		/*
		 * auth.jdbcAuthentication() .dataSource(dataSource) .passwordEncoder(encoder)
		 * .usersByUsernameQuery(
		 * "select username,password,enabled from users where username=?")
		 * .authoritiesByUsernameQuery(
		 * "select username, role from user_roles where username=?");
		 */

		/*
		 * auth.jdbcAuthentication().dataSource(dataSource)
		 * .usersByUsernameQuery("select username,password,enabled from users where username=?"
		 * )
		 * .authoritiesByUsernameQuery("select username, role from user_roles where username=?"
		 * );
		 */

		auth.inMemoryAuthentication().withUser("john").password("{noop}123456").roles("USER").and().withUser("huxu")
				.password("{noop}12345").roles("USER");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().antMatchers("/images/**").permitAll().antMatchers("/login*")
				.permitAll().anyRequest().authenticated().and().httpBasic().authenticationEntryPoint(authEntryPoint);
	}

}