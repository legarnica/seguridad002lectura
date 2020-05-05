package cl.lherrera.gsss.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private AuthenticationSuccessHandler authenticationSuccessHandler;

	@Autowired
	public WebSecurityConfig(AuthenticationSuccessHandler authenticationSuccessHandler) {
		this.authenticationSuccessHandler = authenticationSuccessHandler;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		String usuario = "mail@fake.dev";
		String contrasenia = passwordEncoder().encode("1234");
		String rol = "ADMIN";

		String usuario2 = "mail2@fake.dev";
		String contrasenia2 = passwordEncoder().encode("1234");
		String rol2 = "USER";

		auth.inMemoryAuthentication()
			.withUser(usuario).password(contrasenia).roles(rol)
			
			.and()
			
			.withUser(usuario2).password(contrasenia2).roles(rol2);
	}

	/*
	 * Dejamos la aplicación distribuídos en direcctorios para poder restringir por
	 * medio de estos direcctorios y le damos acceso según su rol.
	 */
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.authorizeRequests()
				.antMatchers("/admin/**").hasRole("ADMIN")
				.antMatchers("/user/**").hasRole("USER")
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
