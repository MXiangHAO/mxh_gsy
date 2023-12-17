package com.example.chapter_blog.util;

import android.os.Handler;
import android.os.Looper;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Client {

    private static final String BASE_URL = "http://172.27.46.239:8080"; // 修改为你的实际服务器地址

    private static final OkHttpClient okHttpClient = new OkHttpClient();
    private static final Handler mainHandler = new Handler(Looper.getMainLooper());
    public static CompletableFuture<Boolean> post(String endpoint, String jsonBody) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();

        String url = BASE_URL + endpoint;
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonBody);

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                future.completeExceptionally(e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final String result = response.body().string();
                System.out.println(result);
                if (response.isSuccessful()) {
                    future.complete(true);
                } else {
                    future.complete(false);
                }
            }
        });

        return future;
    }

}
