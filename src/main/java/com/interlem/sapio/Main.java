package com.interlem.sapio;


import com.interlem.sapio.utils.JsonHttpRetriever;
import com.interlem.sapio.utils.TokenUtils;
import com.nimbusds.jose.JOSEException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

public class Main {
    static String BASE_URL = "https://cdms-osas.preprod.mia-care.io";
    static String OPENID_CONFIGURATION_ENDPOINT = "/v1/hcsp/oauth/.well-known/openid-configuration";

    static String JWKS_URI = "jwks_uri";

    private static JSONObject token = new JSONObject("    {\n" +
            "        \"access_token\": \"eyJhbGciOiJSUzI1NiIsImtpZCI6ImYxOWI0MjkzLWQxMDAtNDY5Yi05MDM3LTFjYTVkZmE1M2IwOCIsInR5cCI6IkpXVCJ9.eyJjbGllbnRfbmFtZSI6InByZXByb2QiLCJleHAiOjE2NjQ1NDI0NzksImlhdCI6MTY2MjEyMzI3OSwiaXNzIjoibWlhY2FyZS1vc2FzIiwianRpIjoiYTRmOTI1OTYtZmQ5MS00YmEwLWIxZDUtMTI0Y2Y3ZmVmMzg3IiwicGVybWlzc2lvbnMiOltdLCJzdWIiOiJ0eFpZRnBjRFBhVGxVVmpFTWNTS1JyRG9TVFVmVVJUTiJ9.EAEhhK5izEzQiRmDCJEgJxMW6svKs_Q38evzvTZg1Phq5E17mAJ9G7Yq07lJU6E9CY55C6_QgTh_cpSDnA57brjysUEjERr_tfFNYnLadm_4_enp4ofn1vwLlloz8ni7f5reMCLiRnC4HuSnMkH1ndhrjZSVBVQhOjA9I3gEWqgWYr4q9Q2BK_5cnVpK-EIM0KAqL26UlIKot4vhgtTRvlsdJ9rhHytGMm53VoOS4fKkhZ37wTaXrEidirQCYy3iCxyX7Ib0JlJcngOAEcH-l8exbDbXVmEVh6v9JPXYAz0NHw-CCWV_qnLI0d2hUZxMHtpWAnT2lGtRDYD2yrdsvpO20RTzeQjP9aBXLSFyrjsjM4VSVrIKui_67RvMpl0JnvSuGSFHYwkFseQG4Um5XR6w6wPF9i_JsFrjHuZcoNCVSZCTDsPHBXe9MxS2QLJ5raIiP4KZl6l0mtCQf5gGjgMw4vi-1D6OkOWttOhhNgZT3MCSoQUrcSJIihOY_KdQB4SZlPrdajb_7IlynA48WY9WUxj2wLP7vei1vusgQjcqdrZWobP69txn0sZwHuY9tRAZckGFVCDcAy44A4S-ruXRN_44NnbaaOiMRy-yN5apPwu7BZ3rVj5RcOmlQ1vOqvJ_M-koVxEWTnYzHG4GLD2WilyGnfxAfx3XIFCxGaU\",\n" +
            "            \"token_type\": \"Bearer\",\n" +
            "            \"expires_in\": 2419200\n" +
            "    }");
//    private static JSONObject token = new JSONObject("    {\n" +
//        "\"access_token\": \"eyJhbGciOiJSUzI1NiIsImtZCI6ImYxOWI0MjkzLWQxMDAtNDY5Yi05MDM3LTFjYTVkZmE1M2IwOCIsInR5cCI6IkpXVCJ9.eyJjbGllbnRfbmFtZSI6InByZXByb2QiLCJleHAiOjE2NjkzMDM2ODksImlhdCI6MTY2NDQ2NTI4OSwiaXNzIjoibWlhY2FyZS1vc2FzIiwianRpIjoiYzQyOThiZGMtZmE3Yy00NzQ1LTg1MWEtMWMwMWE0OWE1ZmViIiwicGVybWlzc2lvbnMiOltdLCJzdWIiOiJ0eFpZRnBjRFBhVGxVVmpFTWNTS1JyRG9TVFVmVVJUTiJ9.GPA9WXL48Ye-bQq3MnTPxipaSG1bVS3CpZTL-f18WVGtAK3tnfL_BHpapu7M5iQkfPKkwZoHcrjWoAOaL-7r8PQ9zoeIVsnbn2iS3YQfEw5SzMBJ65QfTWs41OQTdSg-TgUMV6BRRH7oID1qgBq4SnLlcEQTA1ZHBfmYg_fuUOMkzeA3kJqMKUNlzpA1ChU0D2Vmq8OUhs6w8rHQWNkaFp5-q1FSstS6ttdrEtwfFUm93mIRqNoHXQ6zPJS6xWQuubOLwYYUHDUX6oweSval3JyMVNUWfn4F0t1j0Hbofxze7uFgOgmNlcfr3cA0a7fkkdlUeLqDhKULAuQJwYm_PdHW9FfnE_NEtpFYQobuFKbtqrWZvqxbZyhsBQ1-vSFQnWWt5M-GLjAyKWYUtDmsZCapCSGFt2iTxRZ-6_zgoHR-cYgqqFam1ytU0eaphujOC439DrSiAUP6Bmq85i6Ge1z93cbVVZQeI0JJ_fXE_wqo_PccXGPvJlBt0Gxl36SAYQxH8GQ0gs0KRDydGUp_fNBo5qMro8rWFDnE3SKhgavPmUd8tOoFJf3j5GhuMvevJbETPhStAixw184RwxWSXSlOpTnH4NKGartL8l3Svs4w_DhcYkRzKQEbDDzmHSE-TV2x3oTv1oDhEjLqWFhFYD_5_BJ97CKVKkWbfTxGE0U\",\n" +
//        "\"token_type\": \"Bearer\",\n" +
//        "\"expires_in\": 4838400\n" +
//        "}");

    public static void main(String[] args) {

        JSONObject jsonObject;

        try {
            jsonObject = JsonHttpRetriever.getJsonFromUrl(BASE_URL + OPENID_CONFIGURATION_ENDPOINT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        try {
            jsonObject = JsonHttpRetriever.getJsonFromUrl(jsonObject.getString(JWKS_URI));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        Date expDate= TokenUtils.getExpDate(token.getString("access_token"));
        System.out.println("is expired: " + TokenUtils.isTokenExpired(expDate));
        JSONArray array = jsonObject.getJSONArray("keys");

        try {
            Boolean b = TokenUtils.isSignatureValid(token.getString("access_token"), TokenUtils.getRSAPublicKeyFromString(jsonObject.getJSONArray("keys").get(0).toString()));
            System.out.println("is vald: " + b);
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }


    }


}
