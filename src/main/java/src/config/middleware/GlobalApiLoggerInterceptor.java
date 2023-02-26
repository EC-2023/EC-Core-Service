package src.config.middleware;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class GlobalApiLoggerInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(GlobalApiLoggerInterceptor.class);
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (request.getRequestURI().contains("api")) {
            String requestId = (String) request.getAttribute("requestId");
            String requestIP = (String) request.getAttribute("requestIP");
            String responseStatus = String.valueOf(response.getStatus());
            String responseTime = String.valueOf(System.currentTimeMillis() - (Long) request.getAttribute("startTime"));
            String responseData = response.getContentType().startsWith("application/json") ? response.getWriter().toString() : "";

            logger.info("Request ID: {}", requestId);
            logger.info("Response Status: {}", responseStatus);
            logger.info("Response Time: {} ms", responseTime);
            logger.info("Response Data: {}", responseData);
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // Add the start time of the request
        if (request.getRequestURI().contains("api")) {
            request.setAttribute("startTime", System.currentTimeMillis());
        }
    }
}