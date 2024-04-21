package com.example.vuesecurity.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * &#064;Author  张乔
 *
 * @Date 2024/1/22 14:28
 */
@Component
public class JwtUtil {

    private final String secret = "zhangqiao";

    private final Long expiration = 36000000L;

    public String generateToken(Integer id) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
                .withSubject(String.valueOf(id))
                .withIssuedAt(now)
                .withExpiresAt(expiryDate)
                .sign(algorithm);
    }

    public Integer getUsernameFromToken(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return Integer.valueOf(jwt.getSubject());
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /*
     * 判断token是否过期
     * */
    public boolean isTokenValid(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWT.require(algorithm).build().verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /*
     * 刷新token
     * */

    public String refreshToken(String token, Long refreshTime) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            String username = jwt.getSubject();
            Algorithm algorithm = Algorithm.HMAC256(secret);

            Date now = new Date();
            Date expiryDate = new Date(now.getTime() + refreshTime);

            return JWT.create()
                    .withSubject(username)
                    .withIssuedAt(now)
                    .withExpiresAt(expiryDate)
                    .sign(algorithm);
        } catch (JWTDecodeException e) {
            return null;
        }
    }
}




