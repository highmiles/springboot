package milky;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	/*@Autowired
	@Qualifier("customUserDetailService")
	private UserDetailService customUserDetailsService;*/

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
		
		// Security configuration for H2 console access
		// !!!! You MUST NOT use this configuration for PRODUCTION site !!!!
/*		httpSecurity.authorizeRequests().antMatchers("/console/**").permitAll();
		httpSecurity.csrf().disable();
		httpSecurity.headers().frameOptions().disable();*/

		// static resources
		httpSecurity
			.authorizeRequests()
				.antMatchers("/css/**", "/js/**", "/webjars/**", "../template/layout/**").permitAll();

        httpSecurity
            .authorizeRequests()
                .antMatchers("/login").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
            .logout()
                .permitAll();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .inMemoryAuthentication()
                .withUser("admin").password("kulcloud").roles("USER");
    }
}
