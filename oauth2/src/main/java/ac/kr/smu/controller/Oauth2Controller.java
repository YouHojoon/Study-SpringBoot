package ac.kr.smu.controller;

import ac.kr.smu.model.OAuthToken;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@RestController
@RequestMapping("/oauth2")
@Log
public class Oauth2Controller {
    private final Gson gson;
    private final RestTemplate restTemplate;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/callback")
    public OAuthToken callbackSocial(@RequestParam("code") String code){
        log.info(passwordEncoder.encode("pass"));
        String credentials = "testClientId:testSecret";
        String encodedCredentials = new String(Base64.encodeBase64(credentials.getBytes()));
        log.info(encodedCredentials);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Authorization","Basic " + encodedCredentials);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code",code);
        params.add("grant_type","authorization_code");
        params.add("redirect_uri","http://localhost:8081/oauth2/callback");
        HttpEntity<MultiValueMap<String,String>> request = new HttpEntity<>(params,headers);
        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8081/oauth/token",request,String.class);
        if(response.getStatusCode() == HttpStatus.OK){
            return gson.fromJson(response.getBody(), OAuthToken.class);
        }
        return null;
    }
}
