package com.fredywise.freshfruitapi;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

@Component
public class MyHaderCheckerFilter implements Filter {

    private static final String API_KEY = "7def4ec4deab71e2c5911ee718db181c8bf077582e9cc397af95c76fb0d459f0";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization code, if needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String apiKey = httpRequest.getHeader("apikey");
        String xContentTypeOptions = httpRequest.getHeader("x-content-type-options");
        String xXssProtection = httpRequest.getHeader("x-xss-protection");
        String strictTransportSecurity = httpRequest.getHeader("strict-transport-security");
        String xFrameOptions = httpRequest.getHeader("x-frame-options");


        if (apiKey == null || xContentTypeOptions == null ||
            xXssProtection == null || strictTransportSecurity == null ||
            xFrameOptions == null) {
            Map<String, Object> errorResponse = ResponseMapper.toErrorResponse("Missing required headers");
            httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            httpResponse.setContentType("application/json");
            httpResponse.getWriter().write(objectMapper.writeValueAsString(errorResponse));
            return;
        }

        if (!API_KEY.equals(apiKey)) {
            Map<String, Object> errorResponse = ResponseMapper.toErrorResponse("You do not have permission to access the API!");
            httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            httpResponse.setContentType("application/json");
            httpResponse.getWriter().write(objectMapper.writeValueAsString(errorResponse));
            return;
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Cleanup code, if needed
    }
}
