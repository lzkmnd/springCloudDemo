package com.example.users.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.users.model.TokenInfo;
import io.jsonwebtoken.*;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liam
 * @date 2020/3/26 下午10:10
 * @description JWT工具类
 */
public class JwtUtil {
    public static String makeToken(Integer userId, String userName, String role) {
        //--1
        JwtBuilder builder = Jwts.builder();

        builder.setId(String.valueOf(userId));
        builder.setSubject(userName);

        // jwt 生成时间
        builder.setIssuedAt(new Date());

        // jwt 过期时间
        long now = System.currentTimeMillis();
        long exp = now+1000*60*60;
        builder.setExpiration( new Date( exp ) );

        // 数据区
        builder.claim( "userId", userId );
        builder.claim( "userName",userName );
        builder.claim( "role", role );

        // 设置jwt签名算法
        builder.signWith(SignatureAlgorithm.HS256,"123456");

        // 生产token
        String token = builder.compact();

        return token;
    }

    public static boolean verifyToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey("123456")
                    .parseClaimsJws(token)
                    .getBody();
            String userName = (String)claims.get("userName");
            System.out.println(userName);
            return true;
        }
        catch (ExpiredJwtException e) {
            System.out.println("token expired");
        } catch (SignatureException e) {
            System.out.println("token signature error");
        } catch (Exception e) {
            System.out.println("token error");
        }
        return false;
    }

    public static String makeTokenByInfo(TokenInfo info) {
        return makeToken(info.getUserId(), info.getUserName(), info.getRole());
    }

    public static TokenInfo getTokenByString(String token) {
        TokenInfo info = null;
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey("123456")
                    .parseClaimsJws(token)
                    .getBody();

            info = new TokenInfo();
            info.setUserId(Integer.valueOf((String)claims.get("userId")));
            info.setUserName((String)claims.get("userName"));
            info.setRole((String)claims.get("role"));

            return info;
        }
        catch (ExpiredJwtException e) {
            Claims claims = e.getClaims();
            info = new TokenInfo();
            info.setUserId(Integer.valueOf((String)claims.get("userId")));
            info.setUserName((String)claims.get("userName"));
            info.setRole((String)claims.get("role"));

            return info;
        } catch (SignatureException e) {
            System.out.println("token signature error");
        } catch (Exception e) {
            System.out.println("token error");
        }

        return info;
    }
}