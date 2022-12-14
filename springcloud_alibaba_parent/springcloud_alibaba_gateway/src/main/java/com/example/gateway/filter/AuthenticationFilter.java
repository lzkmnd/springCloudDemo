package com.example.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import com.example.gateway.config.WhiteListConfig;
import com.example.gateway.model.TokenInfo;
import com.example.gateway.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户验证过滤器
 * @author Charon
 */
@Component
@Slf4j
public class AuthenticationFilter implements GlobalFilter, Ordered {

    private static final String AUTHORIZE_TOKEN = "token";
    static String LOGIN_NOT = "{code:'-1',message:'没有登陆'}";
    private static AntPathMatcher matcher = new AntPathMatcher();

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        String url = request.getPath().toString();
        log.info("【登录拦截】获取请求地址路径："+url);
        // 不需要拦截的url直接通过
        if(isNotAuth(url)){
            return chain.filter(exchange);
        }
        String token = request.getHeaders().getFirst(AUTHORIZE_TOKEN);

        if(StringUtils.isEmpty(token)){
            log.info("【登录拦截】未获取到token值...");
            return loginResponse(exchange);
        }
        log.debug("【登录拦截】获取token值:"+token);
        try {
            TokenInfo tokenInfo = JwtUtil.getTokenByString(token);
            if(tokenInfo==null){//获取的token解析不出则不给予访问
                return loginResponse(exchange);
            }
        } catch (Exception e) {
            log.error("【登录拦截】异常:"+e.getMessage());
            return loginResponse(exchange);
        }
        return chain.filter(exchange);
    }

    public static Mono<Void> loginResponse(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        DataBuffer wrap = response.bufferFactory().wrap(LOGIN_NOT.getBytes());
        return response.writeWith(Mono.just(wrap));
    }

    private boolean isNotAuth(String url) {
        List<String> uriList = new ArrayList<>();
        // admin后台不需要拦截
        uriList.add("/user/login");
//        uriList.add("/user/logout");

        for (String pattern : uriList) {
            if (matcher.match(pattern, url)) {
                // 不需要拦截
                return true;
            }
        }
        return false;
    }
}