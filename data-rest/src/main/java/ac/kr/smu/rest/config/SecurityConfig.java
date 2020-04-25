package ac.kr.smu.rest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin(CorsConfiguration.ALL);//cors 모든 origin에 대해 허용
        configuration.addAllowedMethod(CorsConfiguration.ALL);//cors 모든 메소드에 대해 허용
        configuration.addAllowedHeader(CorsConfiguration.ALL);//cors 모든 헤더에 대해 허용
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        http.httpBasic().and().authorizeRequests().anyRequest().permitAll()
                .and().cors().configurationSource(source)
                .and().csrf().disable();
    }
    @Bean
    InMemoryUserDetailsManager userDetailsManager(){
        User.UserBuilder commonUser= User.withUsername("commonUser").password("{noop}common").roles("USER");
        User.UserBuilder adminUser= User.withUsername("adminUser").password("{noop}admin").roles("ADMIN","USER");
        List<UserDetails> userDetailsList = new ArrayList<>();
        userDetailsList.add(commonUser.build());
        userDetailsList.add(adminUser.build());
        return new InMemoryUserDetailsManager(userDetailsList);
    }
}
