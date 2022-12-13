//package com.example.gateway.filter;
//
//import com.alibaba.fastjson.JSONObject;
//import com.example.gateway.config.WhiteListConfig;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.Ordered;
//import org.springframework.core.io.buffer.DataBuffer;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.http.server.reactive.ServerHttpResponse;
//import org.springframework.stereotype.Component;
//import org.springframework.util.AntPathMatcher;
//import org.springframework.util.StringUtils;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//import java.nio.charset.StandardCharsets;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 用户验证过滤器
// * @author Charon
// */
//@Component
////@Slf4j
//public class AuthenticationFilter implements GlobalFilter, Ordered {
//
//    private static final String AUTHORIZE_TOKEN = "token";
//
//    private static AntPathMatcher matcher = new AntPathMatcher();
//
//    @Override
//    public int getOrder() {
//        return 0;
//    }
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        ServerHttpRequest request = exchange.getRequest();
//        String url = request.getPath().toString();
////        log.info("【登录拦截】获取请求地址路径："+url);
//        // 不需要拦截的url直接通过
//        if(isNotAuth(url)){
//            return chain.filter(exchange);
//        }
//        String token = request.getHeaders().getFirst(AUTHORIZE_TOKEN);
//        if(StringUtils.isEmpty(token)){
//            token = request.getQueryParams().getFirst(AUTHORIZE_TOKEN);
//        }
//        if(StringUtils.isEmpty(token)){
////            log.info("【登录拦截】未获取到token值...");
//            loginResponse(exchange);
//        }
////        log.debug("【登录拦截】获取token值:"+token);
//        try {
//            /*SSOToken ssoToken = SSOToken.parser(token);
//            // 获取token中的用户名
//            String userId = ssoToken.getId();*/
////            log.info("【登录拦截】获取userId:"+userId);
//        } catch (Exception e) {
////            log.error("【登录拦截】异常:"+e.getMessage());
//            return loginResponse(exchange);
//        }
//        return chain.filter(exchange);
//    }
//
//    public static Mono<Void> loginResponse(ServerWebExchange exchange) {
//        ServerHttpResponse response = exchange.getResponse();
//       /* JSONObject json = new JSONObject();
//        json.set("code", 401);
//        json.set("msg", "请重新登陆授权");
//        byte[] bytes = JSONUtil.toJsonStr(json).getBytes(StandardCharsets.UTF_8);*/
//        //指定编码，否则在浏览器中会中文乱码
//        response.getHeaders().add("Content-Type", "text/plain;charset=UTF-8");
////        DataBuffer buffer = response.bufferFactory().wrap(bytes);
////        return response.writeWith(Flux.just(buffer));
//        return response.writeWith(Mono.empty());
//    }
//
//    private boolean isNotAuth(String url) {
//        List<String> uriList = new ArrayList<>();
//        // admin后台不需要拦截
//        uriList.add("/user/login");
//        uriList.add("/user/logout");
//
//        for (String pattern : uriList) {
//            if (matcher.match(pattern, url)) {
//                // 不需要拦截
//                return true;
//            }
//        }
//        return false;
//    }
//}