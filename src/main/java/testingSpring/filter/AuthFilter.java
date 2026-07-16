package testingSpring.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import testingSpring.serivce.AuthService;
import testingSpring.serivce.SessionService;
import testingSpring.util.SessionParameters;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Component
@Slf4j
public class AuthFilter extends OncePerRequestFilter {

    private final AuthService service;
    private final SessionService sessionService;

    @Autowired
    public AuthFilter(AuthService service, SessionService sessionService) {
        this.service = service;
        this.sessionService = sessionService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String servletPath = request.getServletPath();
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            response.sendRedirect("/weather/auth/sign-in");
            return;
        }
        Optional<String> sessionUuidOptional = getSession(cookies);
        if (sessionUuidOptional.isEmpty() || !service.isAuthenticated(sessionUuidOptional.get())) {
            response.sendRedirect("/weather/auth/sign-in");
            return;
        }
        request.setAttribute("userSession",sessionUuidOptional.get());
        filterChain.doFilter(request, response);
    }

    private Optional<String> getSession(Cookie[] cookies){
        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(SessionParameters.SESSION_UUID))
                .map(Cookie::getValue)
                .findFirst();
    }


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {

        String path = request.getServletPath();
        log.debug("starts with {}",path);

        return path.startsWith("/auth")
                || path.startsWith("/css")
                || path.startsWith("/js")
                || path.startsWith("/images");
    }
}
