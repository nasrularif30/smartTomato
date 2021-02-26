package com.example.tomato;

import android.renderscript.Sampler;
import android.util.Log;

import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.ubidots.Value;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UbidotsClient {
    private UbiListener ubiListener;

    public UbiListener getListener() {
        return ubiListener;
    }

    public void setListener(UbiListener ubiListener) {
        this.ubiListener = ubiListener;
    }

    public void handleUbidots(String varId, String apiKey, final UbiListener listener){
        final List<Value> results = new ArrayList<>();
        OkHttpClient client = new OkHttpClient();
        Request req = new Request.Builder().addHeader("X-Auth-Token", apiKey)
                .url("https://industrial.api.ubidots.com/api/v1.6/variables/" + varId + "/values")
                .build();

        client.newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.d("Data", "Network Error");
                e.printStackTrace();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String body = response.body().string();
                Log.d("Data", body);

                try {
                    JSONObject jObj = new JSONObject(body);
                    JSONArray jRes = jObj.getJSONArray("results");
                    for (int i=0; i<jRes.length();i++){
                        JSONObject obj = jRes.getJSONObject(i);
                        Value val = new Value();
                        val.timestamp = obj.getLong("timestamp");
                        val.value = (float) obj.getDouble("value");
                        results.add(val);
                    }
                    listener.onDataReady(results);
                }
                catch (JSONException jse){
                    jse.printStackTrace();
                }

            }
        });

    }

    public void handleUbidotspost(String varId, String apiKey, String id, final UbiListener listener){
        final List<Value> results = new ArrayList<>();
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormEncodingBuilder()
                .add("value", id)
                .build();
        Request req = new Request.Builder().addHeader("X-Auth-Token", apiKey)
                .url("https://industrial.api.ubidots.com/api/v1.6/variables/" + varId + "/values")
                .post(formBody)
                .build();

        client.newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.i("ErrorCOk", "Network Error");
                e.printStackTrace();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String body = response.body().string();
                Log.i("Dicobaagan", body);

//                try {
//                    JSONObject jObj = new JSONObject(body);
//                    JSONArray jRes = jObj.getJSONArray("results");
//                    for (int i=0; i<jRes.length();i++){
//                        JSONObject obj = jRes.getJSONObject(i);
//                        Value val = new Value();
//                        val.timestamp = obj.getLong("timestamp");
//                        val.value = (float) obj.getDouble("value");
//                        results.add(val);
//                    }
//                    listener.onDataReady(results);
//                }
//                catch (JSONException jse){
//                    jse.printStackTrace();
//                }

            }
        });

    }

    protected static class Value {
        float value;
        long timestamp;
    }
    protected interface UbiListener {
        void onDataReady(List<Value> result);
    }
}
