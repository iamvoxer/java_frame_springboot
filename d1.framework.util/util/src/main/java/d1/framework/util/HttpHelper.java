package d1.framework.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import okhttp3.*;

import java.io.IOException;
import java.util.Map;

//http相关的工具类
public class HttpHelper {
    //
    public static <T> T postObjectAsJSON(String url, Object obj, Class<T> tClass) throws Exception {
        return postObjectAsJSON(url, obj, tClass, null);
    }

    public static <T> T postObjectAsJSON(String url, Object obj, Class<T> tClass, Map<String, String> headers) throws Exception {
        OkHttpClient client = new OkHttpClient();
        String json = JSON.toJSONString(obj, SerializerFeature.DisableCircularReferenceDetect);
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8")
                , json);
        okhttp3.Request.Builder builder = new Request.Builder()
                .url(url);
        if (headers != null && headers.size() > 0) {
            for (String key : headers.keySet()) {
                builder = builder.addHeader(key, headers.get(key));
            }
        }
        Request request = builder.post(requestBody).build();
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("服务器端错误: " + response);
        }
        String result = new String(response.body().bytes(), "UTF-8");
        T t;
        if (tClass.equals(String.class))
            t = (T) result;
        else
            t = JSON.parseObject(result, tClass);
        return t;
    }

    public static byte[] downloadFileAsByte(String url) throws Exception {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url.toString()).build();

        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new Exception("下载失败：" + url);
            }
            ResponseBody body = response.body();
            return body.bytes();
        } catch (IOException e) {
            throw new Exception("下载失败：" + url, e);
        }
    }
}
