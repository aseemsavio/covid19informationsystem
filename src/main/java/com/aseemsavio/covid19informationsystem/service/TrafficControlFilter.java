package com.aseemsavio.covid19informationsystem.service;

import com.aseemsavio.covid19informationsystem.model.Error;
import com.aseemsavio.covid19informationsystem.model.FilterErrorResponse;
import com.aseemsavio.covid19informationsystem.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static com.aseemsavio.covid19informationsystem.utils.C19ISConstants.*;

@Component
public class TrafficControlFilter implements Filter {

    @Value("${request.per.minute.plan.free}")
    private int requestsPerMinuteFree;

    @Value("${request.per.minute.plan.a}")
    private int requestPerMinutePlanA;

    @Value("${request.per.minute.plan.b}")
    private int requestPerMinutePlanB;

    @Value("${request.per.minute.plan.c}")
    private int requestPerMinutePlanC;

    private LoadingCache<String, Integer> requestAllowedPerAuthorizationCode;
    private static final Logger log = LoggerFactory.getLogger(TrafficControlFilter.class);


    public TrafficControlFilter() {
        super();
        requestAllowedPerAuthorizationCode = CacheBuilder.newBuilder()
                .expireAfterWrite(1, TimeUnit.SECONDS)
                .build(new CacheLoader<String, Integer>() {
                    public Integer load(String key) {
                        return 0;
                    }
                });
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        String uri = EMPTY_STRING;
        if (request instanceof HttpServletRequest) {
            uri = ((HttpServletRequest) request).getRequestURI();
        }

        if (uri.contains("/api/")) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            Enumeration<String> headers = httpRequest.getHeaderNames();
            String authorizationCode = EMPTY_STRING;
            boolean canProceed = false;
            if (headers != null) {
                String header = EMPTY_STRING;
                while (headers.hasMoreElements()) {
                    header = headers.nextElement();
                    if (header.equals(HEADER_AUTHORIZATION_CODE)) {
                        canProceed = true;
                        break;
                    }
                }
                if (canProceed) {
                    LocalCache localCache = LocalCache.getInstance();
                    authorizationCode = httpRequest.getHeader(header);

                    if (!isAuthorizationCodeValid(authorizationCode, localCache)) {
                        FilterErrorResponse errorResponse = getErrorResponse(ERROR_CODE_NOT_A_REGISTERED_USER, ERROR_MSG_NOT_A_REGISTERED_USER);
                        sendServletResponse(response, errorResponse);
                        return;
                    }

                    if (requestLimitExceeded(authorizationCode, localCache)) {
                        FilterErrorResponse errorResponse = getErrorResponse(ERROR_CODE_LIMIT_EXCEEDED, ERROR_MSG_LIMIT_EXCEEDED);
                        sendServletResponse(response, errorResponse);
                        return;
                    }

                } else {
                    FilterErrorResponse errorResponse = getErrorResponse(ERROR_CODE_HEADER_NOT_FOUND, ERROR_MSG_HEADER_NOT_FOUND);
                    sendServletResponse(response, errorResponse);
                    return;
                }
            } else {
                FilterErrorResponse errorResponse = getErrorResponse(ERROR_CODE_HEADER_NOT_FOUND, ERROR_MSG_HEADER_NOT_FOUND);
                sendServletResponse(response, errorResponse);
                return;
            }
        }
        chain.doFilter(request, response);
    }

    private boolean requestLimitExceeded(String authorizationCode, LocalCache localCache) {
        int MAX_REQUESTS = 0;
        int requests = 0;
        User user = LocalCache.getInstance().getUsers().getOrDefault(authorizationCode, new User());
        if (user.getAuthorizationKey() != null) {
            int plan = user.getPlan();
            switch (plan) {
                case 0:
                    MAX_REQUESTS = requestsPerMinuteFree;
                    break;
                case 1:
                    MAX_REQUESTS = requestPerMinutePlanA;
                    break;
                case 2:
                    MAX_REQUESTS = requestPerMinutePlanB;
                    break;
                case 3:
                    MAX_REQUESTS = requestPerMinutePlanC;
                    break;
            }
            try {
                requests = requestAllowedPerAuthorizationCode.get(authorizationCode);
                if (requests > MAX_REQUESTS) {
                    requestAllowedPerAuthorizationCode.put(authorizationCode, requests);
                    return true;
                }
            } catch (ExecutionException e) {
                log.error("Unexpected Exception occurred in the Service: " + e);
            }
            requests++;
            requestAllowedPerAuthorizationCode.put(authorizationCode, requests);
            return false;

        }
        return false;
    }

    private boolean isAuthorizationCodeValid(String authorizationCode, LocalCache localCache) {
        return localCache != null && localCache.getUsers() != null && localCache.getUsers().containsKey(authorizationCode);
    }

    private void sendServletResponse(ServletResponse response, FilterErrorResponse errorResponse) throws IOException {
        byte[] responseToSend = getResponseInBytes(errorResponse);
        ((HttpServletResponse) response).setHeader("Content-Type", "application/json");
        ((HttpServletResponse) response).setStatus(403);
        response.getOutputStream().write(responseToSend);
    }

    private FilterErrorResponse getErrorResponse(int errorCode, String errorMessage) {
        FilterErrorResponse errorResponse = new FilterErrorResponse();
        errorResponse.setStatus(STATUS_FAILED);
        errorResponse.setTotalResults(0);
        Error error = new Error(errorCode, errorMessage);
        errorResponse.setErrors(Arrays.asList(error));
        return errorResponse;
    }

    private byte[] getResponseInBytes(FilterErrorResponse errorResponse) throws JsonProcessingException {
        String serialized = new ObjectMapper().writeValueAsString(errorResponse);
        return serialized.getBytes();
    }

    @Override
    public void destroy() {

    }
}
