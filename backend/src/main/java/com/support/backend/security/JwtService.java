package com.support.backend.security;


import java.security.Key;
import java.util.Date;


import org.springframework.stereotype.Service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;


@Service
public class JwtService {


    private static final String SECRET_KEY =
            "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz123456";


    private Key getSigningKey() {

        return Keys.hmacShaKeyFor(
                SECRET_KEY.getBytes()
        );

    }



    public String generateToken(
            String email
    ) {


        return Jwts.builder()

                .subject(email)

                .issuedAt(
                        new Date(System.currentTimeMillis())
                )

                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                + 1000 * 60 * 60 * 24
                        )
                )

                .signWith(getSigningKey())

                .compact();

    }




    public String extractEmail(
            String token
    ) {


        Claims claims =
                Jwts.parser()

                .verifyWith(
                        (javax.crypto.SecretKey)
                                getSigningKey()
                )

                .build()

                .parseSignedClaims(token)

                .getPayload();



        return claims.getSubject();

    }




    public boolean isTokenValid(
            String token,
            String email
    ) {


        String extractedEmail =
                extractEmail(token);



        return extractedEmail.equals(email);

    }


}