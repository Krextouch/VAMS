package edu.dhbw.stuttgart.tinf20b.vamsBE.security;

import edu.dhbw.stuttgart.tinf20b.vamsBE.security.model.DisabledTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WebAuthenticationFilter extends OncePerRequestFilter {

    private JwtTokenProvider jwtTokenProvider;
    private UserAuthorizationService webAuthorizationService;
    private DisabledTokenRepository disabledTokenRepository;

    @Autowired
    public WebAuthenticationFilter(JwtTokenProvider jwtTokenProvider,
                                   UserAuthorizationService webAuthorizationService,
                                   DisabledTokenRepository disabledTokenRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.webAuthorizationService = webAuthorizationService;
        this.disabledTokenRepository = disabledTokenRepository;

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getJwtFromRequest(request);

        //Check JWT and if it is valid
        if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {

            //Check if token is not on blacklist
            if (disabledTokenRepository.findByToken(token).isEmpty()) {
                String email = jwtTokenProvider.getUserMailFromToken(token);
                UserDetails user = webAuthorizationService.loadUserByUsername(email);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

                //give clearance to spring security
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        } else {
            return null;
        }
    }
}
