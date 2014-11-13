package fi.rivermouth.talous.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

import fi.rivermouth.talous.auth.UserAuthenticationProvider;
import fi.rivermouth.talous.domain.User;
import fi.rivermouth.talous.service.UserService;

@Configuration
@EnableWebMvcSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.authorizeRequests()
				.antMatchers("/auth", "/register").permitAll().and()
			.authorizeRequests().antMatchers("/**").authenticated();
	}

	@Configuration
	protected static class AuthenticationConfiguration extends
			GlobalAuthenticationConfigurerAdapter {

		@Autowired
		private UserAuthenticationProvider authProvider;

		@Override
		public void init(AuthenticationManagerBuilder auth) throws Exception {
			auth.authenticationProvider(authProvider);
		}
	}

}
