package com.interlem.sapio.utils;


import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
public class JsonHttpRetriever {

    public static JSONObject getJsonFromUrl(String url) throws IOException {

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);

        HttpResponse responseRest = client.execute(get);
        String json = EntityUtils.toString(responseRest.getEntity());
        JSONObject jsonObject = new JSONObject(json);

        return jsonObject;
    }

}
