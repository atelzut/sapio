package com.interlem.sapio.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import com.interlem.sapio.bean.ResponseDetails;
import com.interlem.sapio.constants.Constants;
import com.interlem.sapio.utils.JsonHttpRetriever;
import com.interlem.sapio.utils.TokenUtils;
import com.nimbusds.jose.JOSEException;
import org.springframework.web.bind.annotation.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.yaml.snakeyaml.scanner.Constant;

@RestController
public class TokenController {


    static String JWKS_URI = "jwks_uri";


    @PostMapping("/token-validate")
    public String greetingPost(@RequestBody String body) {

        JSONObject jsonObject;
        JSONObject token = new JSONObject(body);
        JSONObject response = new JSONObject();
        try {
            jsonObject = JsonHttpRetriever.getJsonFromUrl(Constants.BASE_URL + Constants.OPENID_CONFIGURATION_ENDPOINT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        try {
            jsonObject = JsonHttpRetriever.getJsonFromUrl(jsonObject.getString(JWKS_URI));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        Date expDate = TokenUtils.getExpDate(token.getString("access_token"));
        if (TokenUtils.isTokenExpired(expDate)) {
            response.put("type", "/token-validator");
            response.put("title", "token expired");
            response.put("details", "your token is out of date");
            return response.toString();

        }

        System.out.println("is expired: " + TokenUtils.isTokenExpired(expDate));
        JSONArray array = jsonObject.getJSONArray("keys");

        try {
            Boolean signatureValid = TokenUtils.isSignatureValid(token.getString("access_token"), TokenUtils.getRSAPublicKeyFromString(jsonObject.getJSONArray("keys").get(0).toString()));
            System.out.println("is valid: " + signatureValid);
           if (!signatureValid)
           {
               response.put("type", "/token-validator");
               response.put("title", "Invalid token");
               response.put("details", "your token is not a valid token");
               return response.toString();
           }
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        response.put("type", "/token-validator");
        response.put("title", "Valid token");
        response.put("details", "your token is a valid token");
        return response.toString();
    }
}