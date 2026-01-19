package com.api_gateway.api_gateway.filter;


import com.api_gateway.api_gateway.util.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import org.springframework.http.server.reactive.ServerHttpRequest;



@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter implements GlobalFilter, Ordered {

    private final JwtUtil jwtUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();
        HttpMethod method = exchange.getRequest().getMethod();

        // Public endpoints
        if (path.startsWith("/auth")) {
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return unauthorized(exchange, "Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);

        Claims claims;
        try {
            claims = jwtUtil.getClaims(token); // or extractClaims
        } catch (Exception e) {
            return unauthorized(exchange, "Invalid or expired token");
        }

        Long userId = claims.get("userId", Long.class);
        String role = claims.get("role", String.class);

        if (userId == null || role == null) {
            return unauthorized(exchange, "Invalid token claims");
        }

        // Role-based access for product service
        if (path.startsWith("/products")) {

            // NORMAL → GET only
            if ("NORMAL".equalsIgnoreCase(role)) {
                // NORMAL → GET only
                if (!HttpMethod.GET.equals(method)) {
                    return forbidden(exchange ,"User not Authorized for this action");
                }
            }

            if ("ENTERPRISE".equalsIgnoreCase(role)) {
                // ENTERPRISE → GET, POST, PUT
                if (HttpMethod.DELETE.equals(method)) {
                    return forbidden(exchange,"User not Authorized for this action");
                }
            }
        }

        // Inject headers
        ServerHttpRequest modifiedRequest = exchange.getRequest()
                .mutate()
                .header("X-User-Id", String.valueOf(userId))
                .build();

        return chain.filter(
                exchange.mutate().request(modifiedRequest).build()
        );
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange, String message) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    private Mono<Void> forbidden(ServerWebExchange exchange, String message) {
        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
        return exchange.getResponse().setComplete();
    }

    @Override
    public int getOrder() {
        return -1;
    }
}

