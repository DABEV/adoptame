package mx.edu.utez.adoptame.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true,  securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private SimpleAuthenticationSuccessHandler successHandler;

	@Autowired
	private DataSource dataSource;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource)				
            .usersByUsernameQuery("SELECT correo, contrasena, habilitado FROM usuarios WHERE correo = ?")
            .authoritiesByUsernameQuery("SELECT u.correo, r.nombre FROM usuario_rol AS ur "
                + "INNER JOIN usuarios AS u ON u.id = ur.usuario_id "
                + "INNER JOIN roles AS r ON r.id = ur.rol_id WHERE u.correo = ?");
	}

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
            .antMatchers("/css/**", "/js/**", "/images/**").permitAll()

            // Sección donde se adjuntarán las URL públicas que no ocuparán de autenticación
            .antMatchers("/", "/signup", "/reset/password/**", "/imagenes/**", "/mascota/consultarTodas/**", "/mascota/filtrar").permitAll()

            // Las demas URL requieren autenticacion
            .anyRequest().authenticated()

            // Formulario de inicio de sesion no requiere autenticacion
            .and().formLogin().loginPage("/login").usernameParameter("correo").passwordParameter("contrasena").successHandler(successHandler).permitAll()
            .and().logout().permitAll();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
