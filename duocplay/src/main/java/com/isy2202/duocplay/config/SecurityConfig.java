package com.isy2202.duocplay.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.isy2202.duocplay.security.JwtAuthenticactionFilter;
import com.isy2202.duocplay.security.UsuarioDetailsService;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import jakarta.servlet.DispatcherType;


@Configuration 
public class SecurityConfig {

    private static final String CSP_POLICY =
    "default-src 'self'; " +
    "base-uri 'self'; " +
    "form-action 'self'; " +
    "frame-ancestors 'self'; " +
    "object-src 'none'; " +
    "img-src 'self' data:; " +
    "style-src 'self'; " +
    "script-src 'self'; " +
    "font-src 'self'; " +
    "connect-src 'self'";
    
    @Bean 
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(
            UsuarioDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {

        DaoAuthenticationProvider provider =
            new DaoAuthenticationProvider(userDetailsService);

        provider.setPasswordEncoder(passwordEncoder);

        return provider;
    }


    // EXPONER AUTHENTICATION MANAGER
    @Bean
    public AuthenticationManager authenticationManager(
            DaoAuthenticationProvider authenticationProvider) {

        return new ProviderManager(authenticationProvider);
    }


    // EVITAR REGISTRO AUTOMATICO DEL FILTRO JWT
    @Bean
    public FilterRegistrationBean<JwtAuthenticactionFilter> jwtFilterRegistration(
            JwtAuthenticactionFilter filter) {

        FilterRegistrationBean<JwtAuthenticactionFilter> registration =
            new FilterRegistrationBean<>(filter);

        registration.setEnabled(false);

        return registration;
    }


    // SEGURIDAD PARA API REST CON JWT
    @Bean
    @Order(1)
    public SecurityFilterChain apiSecurityFilterChain(
            org.springframework.security.config.annotation.web.builders.HttpSecurity http,
            JwtAuthenticactionFilter jwtFilter,
            DaoAuthenticationProvider authenticationProvider) throws Exception {

        http
            .securityMatcher("/api/**")

            // API sin sesiones y con autenticacion JWT
            .csrf(csrf -> csrf.disable())

            .headers(headers -> headers
               .contentSecurityPolicy(csp -> csp
                  .policyDirectives(CSP_POLICY)
                )
            )

            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            .authenticationProvider(authenticationProvider)

            // PERMISOS DE LOS ENDPOINTS
            .authorizeHttpRequests(auth -> auth

                // Registro publico
                .requestMatchers(HttpMethod.POST, "/api/usuarios").permitAll()

                // Login publico
                .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()

                // Consulta publica de eventos
                .requestMatchers(HttpMethod.GET, "/api/eventos", "/api/eventos/**").permitAll()

                // Crear eventos: ADMIN y ORGANIZADOR
                .requestMatchers(HttpMethod.POST, "/api/eventos")
                    .hasAnyRole("ADMIN", "ORGANIZADOR")

                // Actualizar eventos: temporalmente solo ADMIN
                .requestMatchers(HttpMethod.PUT, "/api/eventos")
                    .hasRole("ADMIN")

                // Desactivar eventos: temporalmente solo ADMIN
                .requestMatchers(HttpMethod.DELETE, "/api/eventos/**")
                    .hasRole("ADMIN")

                // Administracion de usuarios: ADMIN
                .requestMatchers("/api/usuarios", "/api/usuarios/**")
                    .hasRole("ADMIN")

                // Cualquier otro endpoint requiere autenticacion
                .anyRequest().authenticated()
            )

            // Respuestas HTTP para errores de seguridad
            .exceptionHandling(exception -> exception

                .authenticationEntryPoint((request, response, authException) ->
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED)
                )

                .accessDeniedHandler((request, response, accessDeniedException) ->
                    response.sendError(HttpServletResponse.SC_FORBIDDEN)
                )
            )

            // Incorporar filtro JWT
            .addFilterBefore(
                jwtFilter,
                UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }


    // SEGURIDAD PARA PAGINAS THYMELEAF
    @Bean
    @Order(2)
    public SecurityFilterChain webSecurityFilterChain(
            org.springframework.security.config.annotation.web.builders.HttpSecurity http,
            DaoAuthenticationProvider authenticationProvider) throws Exception {

        http

            .headers(headers -> headers
                    .contentSecurityPolicy(csp -> csp
                        .policyDirectives(CSP_POLICY)
                    )
            )

            .authenticationProvider(authenticationProvider)

            .authorizeHttpRequests(auth -> auth

                .dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()

                // Paginas y recursos publicos
                .requestMatchers(
                    "/",
                    "/login",
                    "/registro",
                    "/eventos",
                    "/css/**",
                    "/js/**",
                    "/img/**",
                    "/images/**",
                    "/webjars/**",
                    "/favicon.ico"
                ).permitAll()

                // Resto de las paginas requieren autenticacion
                .anyRequest().authenticated()
            )

            // Login mediante formulario para Thymeleaf
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?error")
                .permitAll()
            )

            // Cierre de sesion
            .logout(Customizer.withDefaults());

        return http.build();
    }

}
