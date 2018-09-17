package com.bupt.ZigbeeResolution.http;

import com.bupt.ZigbeeResolution.data.User;
import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HttpControl {
    static private Cookie ck;
    private String host = "10.108.218.64";
    private static String session;
    public static String id;
    public String deviceToken;

    private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();
    private static final MediaType js = MediaType.parse("application/json; charset=utf-8");

    private OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
            .cookieJar(new CookieJar() {
                @Override
                public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                    cookieStore.put(url.host(), cookies);
                }

                @Override
                public List<Cookie> loadForRequest(HttpUrl url) {
                    List<Cookie> cookies = cookieStore.get(url.host());
                    return cookies != null ? cookies : new ArrayList<Cookie>();
                }
            })
            .build();

    public void httpregister(User user) throws IOException {
        RequestBody registerBody = RequestBody.create(js,"{\"userName\":\""+user.getUserName()+"\",\"passwd\":\""+user.getPasswd()+"\",\"wechatName\":\"\",\"role\":1}");

        System.out.println("{\"userName\":\""+user.getUserName()+"\",\"passwd\":\""+user.getPasswd()+"\",\"wechatName\":\"\",\"role\",1}");
        final Request requestRegister = new Request.Builder()
                .url("http://10.108.218.64:30080/api/v1/account/register")
                .header("Accept","text/plain, */*; q=0.01")
                .addHeader("Connection","keep-alive")
                .addHeader("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_2)AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.139 Safari/537.36")
                .addHeader("Content-Type","application/json; charset=UTF-8")
                .post(registerBody)
                .build();

        Response response = mOkHttpClient.newCall(requestRegister).execute();
        Call call = mOkHttpClient.newCall(requestRegister);
        //请求加入调度
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println(response.body().string());
            }
        });


    }
}
