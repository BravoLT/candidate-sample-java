package com.bravo.user.filter;

import java.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class CorsFilter extends OncePerRequestFilter {

  private static final String ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";

  @Override
  protected void doFilterInternal(
      final HttpServletRequest request,
      final HttpServletResponse response,
      final FilterChain filterChain
  ) throws ServletException, IOException {
    response.addHeader("Access-Control-Allow-Origin", "*");
    response.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, PATCH, HEAD");
    response.addHeader(ACCESS_CONTROL_ALLOW_HEADERS, "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
    response.addHeader(ACCESS_CONTROL_ALLOW_HEADERS, "Access-Control-Allow-Origin, Access-Control-Allow-Credentials");
    response.addHeader("Access-Control-Allow-Credentials", "true");
    response.addIntHeader("Access-Control-Max-Age", 10);
    filterChain.doFilter(request, response);
  }
}
