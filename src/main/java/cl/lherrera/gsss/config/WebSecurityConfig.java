package cl.lherrera.gsss.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired 
	private UserDetailsService userDetailsService;

	private AuthenticationSuccessHandler authenticationSuccessHandler;

	@Autowired
	public WebSecurityConfig(AuthenticationSuccessHandler authenticationSuccessHandler) {
		this.authenticationSuccessHandler = authenticationSuccessHandler;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder() );
	}

	/*
	 * Dejamos la aplicación distribuídos en direcctorios para poder restringir por
	 * medio de estos direcctorios y le damos acceso según su rol.
	 */
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.authorizeRequests()
				.antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
				.antMatchers("/user/**").hasAuthority("ROLE_USER")
				.antMatchers("/login").permitAll()
				// incluyendo el main
				.anyRequest().authenticated()

				.and()
				.formLogin()
				.loginPage("/login")
				.successHandler(authenticationSuccessHandler)// esto es lo nuevo
				.failureUrl("/login?error=true").usernameParameter("email").passwordParameter("password")
				// página por defecto en caso de éxito en el inicio de sesión.
				// se comenta para que no interfirera con la redirección
//				.defaultSuccessUrl("/user")

				.and()

				.exceptionHandling().accessDeniedPage("/recurso-prohibido");
	}

	/**
	 * Así inicializamos el encoder.
	 */
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
