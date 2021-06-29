package de.dev.eth0.demo.keycloakspring.config

import org.keycloak.adapters.KeycloakConfigResolver
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver
import org.keycloak.adapters.springsecurity.KeycloakConfiguration
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy


/**
 * Configuration for security (provided by keycloak)
 */
@KeycloakConfiguration
class SecurityConfig : KeycloakWebSecurityConfigurerAdapter() {

  /**
   * Registers the KeycloakAuthenticationProvider with the authentication manager.
   */
  @Autowired
  fun configureGlobal(auth: AuthenticationManagerBuilder) {
    auth.authenticationProvider(keycloakAuthenticationProvider())
  }

  /**
   * Register Spring Boot Config Resolver to allow configuration in application.yml
   */
  @Bean
  fun keycloakConfigResolver(): KeycloakConfigResolver? {
    return KeycloakSpringBootConfigResolver()
  }

  /**
   * Defines the session authentication strategy.
   */
  @Bean
  @Override
  override fun sessionAuthenticationStrategy(): SessionAuthenticationStrategy {
    return NullAuthenticatedSessionStrategy()
  }


  @Throws(Exception::class)
  override fun configure(http: HttpSecurity) {
    super.configure(http)
    // Security filter config
    http
        // CSRF Config
        .csrf()
        .disable()
        // Header Config
        .headers()
        .frameOptions()
        .disable()
        .and()
        // Session Config (stateless api ftw)
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .sessionAuthenticationStrategy(sessionAuthenticationStrategy())
        .and()
        // Authorization
        .authorizeRequests()
        .antMatchers("/sso/login*").permitAll()
        .antMatchers("/api*").hasRole("user")
        .anyRequest().permitAll()

  }

}
