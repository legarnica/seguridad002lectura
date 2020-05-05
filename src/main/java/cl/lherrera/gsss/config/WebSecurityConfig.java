package cl.lherrera.gsss.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	/**
	 * Sobreescribir este método, permite habilitar 
	 * la autenticación en memoria, además de poder definir credenciales.
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		String usuario = "foo";
		String contrasenia =  passwordEncoder().encode("bar");
		
		auth.inMemoryAuthentication()
			.withUser(usuario)
			.password(contrasenia)
			.roles("ADMIN");
	}
	
	/**
	 * csrf : Adds CSRF support. This is activated by default when using.
	 * 
	 * disable: Desabilita la orden anterior.
	 * 
	 * authorizeRequests : Allows restricting access based upon the HttpServletRequest.
	 * 
	 * antMatchers : The mapping matches URLs using the following rules:
	 * 		? matches one character
	 * 		* matches zero or more characters
	 * 		** matches zero or more directories in a path
	 * 		{spring:[a-z]+} matches the regexp [a-z]+ as a path variable named "spring"
	 * 
	 * permitAll : Specify that URLs are allowed by anyone.
	 * 
	 * anyRequest : Maps any request.
	 * 
	 * authenticated : Specify that URLs are allowed by any authenticated user.
	 * 
	 * and : This is useful for method chaining.
	 * 
	 * formLogin : Specifies to support form based authentication. (no desde el config.xml)
	 * 
	 * loginPage : Specifies the URL to send users to if login is required.
	 * 
	 * failureUrl : The URL to send users if authentication fails. The default is "/login?error".
	 * 
	 * usernameParameter : The HTTP parameter to look for the username when performing authentication. Default is "username".
	 * 
	 * passwordParameter : The HTTP parameter to look for the password when performing authentication. Default is "password".
	 * 
	 * defaultSuccessUrl : Specifies where users will be redirected after authenticating successfully if they have not visited a secured page prior to authenticating.
	 * 
	 */
	@Override
	public void configure(HttpSecurity http) throws Exception {
		// se desabilita el sistema de seguridad csrf (muy vulnerable)
		http.csrf().disable()
			// se configura la ruta login para ser accedida sin autenticacion
			.authorizeRequests().antMatchers("/login").permitAll()
			// para todos los Request se habilita para usuarios autentificados.
			.anyRequest().authenticated()
			// indicación que se unsará el manejo por formulario
			.and().formLogin()
			// se especifica la página para el login personalizado.
			.loginPage("/login")
			// se especifica la url para cuando la verificación de credenciales falle.
			.failureUrl("/login?error=true")
			// se especifica el nombre del atributo name para el usuario y la contraseña.
			.usernameParameter("email").passwordParameter("password")
			// redireccionamiento en caso de éxito.
			.defaultSuccessUrl("/user");

	}

	/**
	 * Así inicializamos el encoder.
	 */
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	

}
