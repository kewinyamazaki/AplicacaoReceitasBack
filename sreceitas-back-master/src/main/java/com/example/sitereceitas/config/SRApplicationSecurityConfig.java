package com.example.sitereceitas.config;

import com.example.sitereceitas.classes.auth.security.JwtAuthenticationFilter;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.sitereceitas.classes.usuario.service.ImplementsUserDetailService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@AllArgsConstructor
public class SRApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

	private final ImplementsUserDetailService userDetailsService;
	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and()
				.csrf().disable()
				.authorizeRequests()
				.antMatchers(HttpMethod.POST, "/v1/cadastrar").permitAll()
				.antMatchers(HttpMethod.GET, "/v1/verificar/{token}").permitAll()
				.antMatchers(HttpMethod.POST, "/v1/login").permitAll()
				.antMatchers(HttpMethod.POST, "/v1/refresh/token").permitAll()
				.antMatchers(HttpMethod.GET,"/v1/receita/{idReceita}").permitAll()
				.antMatchers(HttpMethod.POST,"/v1/receita/listar").permitAll()
				.antMatchers(HttpMethod.POST,"/v1/receita/filtrar").permitAll()
				.antMatchers(HttpMethod.GET,"/v1/comentario/{idReceita}").permitAll()

				.anyRequest().authenticated();
				//.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"));

		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder
				.userDetailsService(userDetailsService)
				.passwordEncoder(passwordEncoder());
	}

	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
}
