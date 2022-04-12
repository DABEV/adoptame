package mx.edu.utez.adoptame.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource)
				.usersByUsernameQuery("SELECT nombre, contrasenia FROM usuarios WHERE nombre = ?")
				.authoritiesByUsernameQuery("SELECT u.nombre, r.titulo FROM rol_usuario AS ru "
						+ "INNER JOIN usuarios AS u ON u.id = ru.usuario_id "
						+ "INNER JOIN roles AS r ON r.id = ru.rol_id WHERE u.nombre = ?");
	}

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                .antMatchers("/css/**", "/js/**", "/images/**").permitAll()

                // Sección donde se adjuntarán las URL públicas que no ocuparán de autenticación
                .antMatchers("/", "/signup").permitAll();
    }

}