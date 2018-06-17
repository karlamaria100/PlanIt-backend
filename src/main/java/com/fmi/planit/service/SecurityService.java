package com.fmi.planit.service;

import com.fmi.planit.model.User;
import io.jsonwebtoken.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;

public class SecurityService {

    private Key signingKey;

    private String key = "1231qdsasdas123123";

    public SecurityService() {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(key);
        signingKey = new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS512.getJcaName());
    }


    public String createJWT(User user) {
        Map<String, Object> json = new HashMap<>();
        json.put("id", user.getId());
        json.put("email", user.getEmail());

        JwtBuilder builder = Jwts.builder()
                .setSubject(new JSONObject(json).toString())
                .signWith(SignatureAlgorithm.HS512, signingKey);
        return builder.compact();
    }


    public User getInfo(String token) {
        User user = new User();
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(signingKey)
                    .parseClaimsJws(token)
                    .getBody();
            JSONObject subject = new JSONObject(claims.getSubject());
            user.setEmail((String) subject.get("email"));
            user.setId(Long.parseLong(subject.get("id").toString()));
        } catch (Exception e) {
            user = null;
        }
        return user;
    }

    public boolean validateToken(String authToken) {

        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            return false;
        }
    }


}
