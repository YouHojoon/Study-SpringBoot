package ac.kr.smu.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.endpoint.jmx.EndpointObjectNameFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
@EnableAuthorizationServer
public class Oauth2AuthorizationConfig extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Value("${security.oauth2.jwt.signkey}")
    private String signkey;
    @Autowired
    private CustomUserDetailService userDetailService;
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        /* DB에 토큰 저장
        endpoints.tokenStore(new JdbcTokenStore(dataSource));
         */
        endpoints.accessTokenConverter(jwtAccessTokenConverter()).userDetailsService(userDetailService);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        /*clients.inMemory().withClient("testClientId").secret("{noop}testSecret")
                .redirectUris("http://localhost:8081/oauth2/callback")
                .authorizedGrantTypes("authorization_code","refresh_token").scopes("read","write").accessTokenValiditySeconds(60*30)
        .refreshTokenValiditySeconds(60*60*24*7); 인메모리*/
        clients.jdbc(dataSource).passwordEncoder(passwordEncoder);
    }
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(signkey);
        return converter;
    }
}
