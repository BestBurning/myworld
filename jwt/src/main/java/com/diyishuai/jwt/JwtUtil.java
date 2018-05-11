package com.diyishuai.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Bruce
 * @since 2018/5/11
 */
public class JwtUtil {
    final static String secret = "diyishuai"; //盐
    final static String issuer = "bruce"; //发行人
    public static void main(String[] args) throws UnsupportedEncodingException {
        String token = createToken(secret, issuer);
//        String token = createToken("hello", "hello");
        System.out.println("token : "+token);
        DecodedJWT jwt = verify(token);
        System.out.println("heard : " + jwt.getHeader());
        System.out.println("payload : " + jwt.getPayload());
        System.out.println("singnature : "+jwt.getSignature());
        System.out.println("algorithm : " + jwt.getAlgorithm());
        System.out.println("ctp : " + jwt.getContentType());
        System.out.println("type : " + jwt.getType());
        System.out.println("Audience : " + jwt.getAudience());
        System.out.println("exp : " + jwt.getExpiresAt());
        System.out.println("claim : " + jwt.getClaim("admin").asString());
        System.out.println("claim : " + jwt.getClaim("email").asString());
    }

    public static String createToken(String secret, String issuer) throws UnsupportedEncodingException {
        Algorithm algorithmHS = Algorithm.HMAC256(secret);
        String token = JWT.create()
                .withIssuer(issuer)
                .withClaim("admin","true")
                .withClaim("email","zhushuai026@gmail.com")
                .withExpiresAt(new Date())
                .sign(algorithmHS);
        return token;
    }

    public static DecodedJWT verify(String token) throws UnsupportedEncodingException {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(issuer)
                .acceptLeeway(1)    //1 sec for nbf 什么时间前不可用, iat 签发时间
                .acceptExpiresAt(5)   //5 secs for exp 过期时间
                .build(); //Reusable verifier instance
        DecodedJWT jwt = verifier.verify(token);
        return jwt;
    }

}
