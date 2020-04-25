package ac.kr.smu.resolver;

import ac.kr.smu.annotation.SocialUser;
import ac.kr.smu.domain.User;
import ac.kr.smu.rest.domain.enums.SocialType;
import ac.kr.smu.repository.BoardRepository;
import ac.kr.smu.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    UserRepository userRepository;
    @Autowired
    BoardRepository boardRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {//파라미터 정의
        return parameter.getParameterAnnotation(SocialUser.class) != null && parameter.getParameterType().equals(User.class);
    }
    /*
        세션에서 유저를 받아와서 user객체 반환
    */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
        User user = (User) session.getAttribute("user");
        return getUser(user, session);
    }
    /*
        user객체가 있으면 바로 반환, 없다면 새로 생성
    */
    private User getUser(User user, HttpSession session) {
        if (user == null) {
            try {
                OAuth2AuthenticationToken authentication = (OAuth2AuthenticationToken) SecurityContextHolder.getContext()
                        .getAuthentication();//인증된 정보를 받아옴
                Map<String, Object> map = authentication.getPrincipal().getAttributes();//리소스 서버에서 받아온 개인정보
                User convertUser = convertUser(authentication.getAuthorizedClientRegistrationId(), map);
                user = userRepository.findByEmail(convertUser.getEmail());
                if (user == null) {
                    user = userRepository.save(convertUser);
                }
                setRoleIfNotSame(user, authentication, map);
                session.setAttribute("user", user);
            } catch (ClassCastException e) {
                return user;
            }
        }
        return user;
    }
    /*
        미디어 타입에 맞는 빌더를 사용
    */
    private User convertUser(String authority, Map<String, Object> map) {
        if (SocialType.FACEBOOK.getValue().equals(authority)) return getModernUser(SocialType.FACEBOOK, map);
        else if (SocialType.GOOGLE.getValue().equals(authority)) return getModernUser(SocialType.GOOGLE, map);
        else if (SocialType.KAKAO.getValue().equals(authority)) return getKakaoUser(map);
        return null;
    }
    /*
        구글, 페이스북을 위한 빌더
     */
    private User getModernUser(SocialType socialType, Map<String, Object> map) {
        return User.builder().name(String.valueOf(map.get("name"))).email(String.valueOf(map.get("email")))
                .principal(String.valueOf(map.get("id"))).socialType(socialType).createdDate(LocalDateTime.now()).build();
    }
    /*
        카카오를 위한 빌더
     */
    private User getKakaoUser(Map<String, Object> map) {
        HashMap<String, String> propertyMap = (HashMap<String, String>) map.get("properties");
        return User.builder().name(propertyMap.get("nickname")).principal(String.valueOf(map.get("id"))).socialType(SocialType.KAKAO).createdDate(LocalDateTime.now()).build();
    }
    /*
        권한이 있는지 확인
     */
    private void setRoleIfNotSame(User user, OAuth2AuthenticationToken authentication, Map<String, Object> map) {
        if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority(user.getSocialType().getRoleType()))) {
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(map, "N/A",
                    AuthorityUtils.createAuthorityList(user.getSocialType().getRoleType())));
        }
    }
}

