package com.smg.gateway.filter;

import com.smg.gateway.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

public class AuthFilter implements GlobalFilter {

    private static final List<String> EXCLUDE_PATHS = Arrays.asList("/search","/register");
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if(EXCLUDE_PATHS.contains(exchange.getRequest().getPath().value()))
            return chain.filter(exchange);
        //获取请求参数中的 token
        String token =
                exchange.getRequest().getQueryParams().getFirst("token");
        Claims claims = JwtUtil.parseJWT("zhouchongrui", token);
        System.out.println(66);
        if(claims==null){
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            //请求结束
            return exchange.getResponse().setComplete();
        }
        System.out.println(666);
        System.out.println(token);
//        if(!token.equals("1")){
//            //响应 http 状态码（401）
//
//        }
        //继续执行过滤器链中的下一个资源
        return chain.filter(exchange);
    }
}
