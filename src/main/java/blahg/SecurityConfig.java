package blahg;

import org.springframework.http.HttpMethod;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;

//@EnableOAuth2Sso
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
 
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
         .authorizeRequests()
         .antMatchers(HttpMethod.GET, "/posts", "/posts/**")
         .permitAll()
         .antMatchers("/login**", "/webjars/**", "/error**")
         .permitAll()
         .anyRequest()
         .authenticated()
         .and()
         .oauth2Login();
    }
}
