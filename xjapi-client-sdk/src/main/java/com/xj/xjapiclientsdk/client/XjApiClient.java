package com.xj.xjapiclientsdk.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.xj.xjapiclientsdk.model.User;

import java.util.HashMap;
import java.util.Map;

import static com.xj.xjapiclientsdk.utils.SignUtils.genSign;


/**
 * @ClassName XjApiClient
 * @Description 使用HuTool工具类调用接口
 * @Author 嘻精
 * @Date 2023/5/20 14:12
 * @Version 1.0
 */

public class XjApiClient {
    
    private String accessKey;
    
    private String secretKey;
    
    public XjApiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }
    
    public String getNameByGet(String name) {
        //可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
    
        String result= HttpUtil.get("http://localhost:8123/api/name/", paramMap);
        System.out.println(result);
        return result;
    }
    
    public String getNameByPost(String name) {
        //可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
    
        String result= HttpUtil.get("http://localhost:8123/api/name/", paramMap);
        System.out.println(result);
        return result;
    }
    

    
    private Map<String,String> getHeaderMap(String body) {
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("accessKey",accessKey);
        //一定不能直接在服务器之间发送密钥
//        hashMap.put("secretKey",secretKey);
        // 生成一个随机数
        hashMap.put("nonce", RandomUtil.randomNumbers(4));
        // 用户请求参数
        hashMap.put("body", body);
        // 时间戳
        hashMap.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        hashMap.put("sign", genSign(body,secretKey));
        return hashMap;
    }
    
    public String getUserNameByPost( User user) {
        String json = JSONUtil.toJsonStr(user);
        HttpResponse httpResponse = HttpRequest.post("http://localhost:8123/api/name/user")
                                            .addHeaders(getHeaderMap(json))
                                       .body(json)
                                       .execute();
        System.out.println(httpResponse.getStatus());
        String result = httpResponse.body();
        System.out.println(result);
        return result;
    }
}
