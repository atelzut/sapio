package com.interlem.sapio.utils;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.SignedJWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.json.JSONObject;

import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;
import java.util.Base64;
import java.util.Date;

public class TokenUtils {



    public static RSAPublicKey getRSAPublicKeyFromString(String keyInput) throws JOSEException, ParseException {

        RSAKey result = RSAKey.parse(keyInput);

        return result.toRSAPublicKey();

    }

  public static Boolean isTokenExpired(Date exp){
        return exp.before(new Date());
  }
    public static boolean isSignatureValid(String token, RSAPublicKey rsaKey) {
        SignedJWT signedJWT;
        try {
            signedJWT = SignedJWT.parse(token);
            JWSVerifier verifier = new RSASSAVerifier(rsaKey);
            return signedJWT.verify(verifier);
        } catch (ParseException | JOSEException e) {
            return false;
        }
    }

    public static Claims getAllClaimsFromToken(String token, String key) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            //LOGGER.error("Could not get all claims Token from passed token");
            System.out.println("Could not get all claims Token from passed token");
            claims = null;
        }
        return claims;
    }


    public static Date getExpDate(String token){
        String[] chunks = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();

        String payload = new String(decoder.decode(chunks[1]));

        Long millis = Long.parseLong(new JSONObject(payload).get("exp").toString())*1000;
        return new Date(millis);

    }


}
