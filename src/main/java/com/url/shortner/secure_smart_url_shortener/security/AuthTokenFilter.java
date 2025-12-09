package com.url.shortner.secure_smart_url_shortener.security;

import com.url.shortner.secure_smart_url_shortener.entity.UrlRecord;
import com.url.shortner.secure_smart_url_shortener.enums.AccessType;
import com.url.shortner.secure_smart_url_shortener.repo.UrlRecordRepo;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtill jwtUtils;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private UrlRecordRepo urlRecordRepo;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String uri = request.getRequestURI();
        Authentication authentication = null;
        if (uri.startsWith("/r/")) {
            String code = uri.substring("/r/".length());
            UrlRecord record = urlRecordRepo.findByShortCode(code).orElse(null);

            if (record != null && !record.getAccessType().equals(AccessType.PUBLIC.toString())) {
                // PRIVATE or ROLE_BASED → JWT required
                String token = parseJwt(request);
                if (token == null || token.isBlank()) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT token required");
                    return;
                }

                // Validate JWT
                try {
                    String username = jwtUtils.extractUsername(token);
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                    if (!jwtUtils.validateJwtToken(token, userDetails)) {
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
                        return;
                    }

                    authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                } catch (Exception e) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
                    return;
                }
            }
        } else {
            try {
                // Extracted token
                String token = parseJwt(request);

                // Only process JWT if token exists
                if (token != null && !token.isBlank()) {
                    // Extract username from token for load user
                    String username = jwtUtils.extractUsername(token);

                    // Load user details from DB
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                    // Check token is validated based on extracted token and userDetail
                    if (jwtUtils.validateJwtToken(token, userDetails)) {
                        // Create authentication object
                        authentication =
                                new UsernamePasswordAuthenticationToken(
                                        userDetails,
                                        null,
                                        userDetails.getAuthorities()
                                );
//                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }

            } catch (Exception e) {
                e.getStackTrace();
            }
        }

        filterChain.doFilter(request, response);
    }

    // Extract token from authorization field
    private String parseJwt(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }
}
