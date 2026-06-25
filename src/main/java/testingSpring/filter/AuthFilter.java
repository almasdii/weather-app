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
import testingSpring.util.SessionParams;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Component
@Slf4j
public class AuthFilter extends OncePerRequestFilter {

    private final AuthService service;

    @Autowired
    public AuthFilter(AuthService service) {
        this.service = service;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String servletPath = request.getServletPath();

        log.debug("servlet path before do filter = {}",servletPath);

        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            response.sendRedirect("/home/auth/sign-in");
            return;
        }

        Optional<String> maybeValue = Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(SessionParams.SESSION_UUID))
                .map(Cookie::getValue)
                .findFirst();

        maybeValue.ifPresent(s -> log.debug("Session UUID : {} ", s));
        if (maybeValue.isEmpty() || !service.isAuthenticated(maybeValue.get())) {
            log.debug("Sending redirect ");
            response.sendRedirect("/home/auth/sign-in");
            return;
        }


        filterChain.doFilter(request, response);
        log.debug("servlet path after do filter = {}",servletPath);
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
