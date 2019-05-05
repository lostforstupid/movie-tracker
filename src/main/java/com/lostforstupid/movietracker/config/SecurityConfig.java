package com.lostforstupid.movietracker.config;

import com.lostforstupid.movietracker.user.User;
import com.lostforstupid.movietracker.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
@EnableOAuth2Sso
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final UserRepository userRepository;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .antMatcher("/**")
        .authorizeRequests()
        .antMatchers("/login**", "/js/**", "/css/**", "/error**")
        .permitAll()
        .anyRequest().authenticated()
        .and()
        .csrf().disable();
  }

  @Bean
  public PrincipalExtractor principalExtractor() {
    return map -> {
      String id = (String) map.get("sub");

      return userRepository.findById(id).orElseGet(() -> {

        User newUser = new User();

        newUser.setId(id);
        newUser.setName((String) map.get("name"));
        newUser.setEmail((String) map.get("email"));
        newUser.setProfilePictureUrl((String) map.get("picture"));

        return userRepository.save(newUser);
      });
    };
  }
}