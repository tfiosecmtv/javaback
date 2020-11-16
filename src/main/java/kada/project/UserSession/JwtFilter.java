package kada.project.UserSession;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static org.springframework.util.StringUtils.hasText;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

@Component
@Log
public class JwtFilter extends GenericFilterBean {

    public static final String AUTHORIZATION = "Authorization";

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("do filter...");
        String token = getTokenFromRequest((HttpServletRequest) servletRequest);
        System.out.println(jwtProvider.hashmap.size());

        if (token != null && jwtProvider.validateToken(token) && jwtProvider.hashmap.containsKey( token ) == false) {
            String userLogin = jwtProvider.getLoginFromToken(token);
            System.out.println(userLogin);
            CustomUserDetails customUserDetails = customUserDetailsService.loadUserByUsername(userLogin);
            System.out.println("guessst");
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        } else if(token != null && jwtProvider.validateToken(token)){
            String userLogin = jwtProvider.getLoginFromToken(token);
            System.out.println(userLogin);
            CustomUserDetails customUserDetails = customUserDetailsService.loadEmployeeByUsername(userLogin);
            System.out.println(customUserDetails.getAuthorities());
            System.out.println(customUserDetails.getPassword());
            System.out.println("employeeee");
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        } else if(token == null) SecurityContextHolder.getContext().setAuthentication(null);
        System.out.println("proceed");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearer = request.getHeader(AUTHORIZATION);
        if (hasText(bearer) && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }
}