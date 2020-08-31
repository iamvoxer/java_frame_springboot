package d1.framework.ocr;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import d1.framework.util.HttpHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AliyunOCRHelper {


    /**
     * 识别车牌号码
     *
     * @param appCode c参考https://market.aliyun.com/products/57124001/cmapi020094.html?spm=5176.2020520132.101.6.54927218B6CsqK#sku=yuncode1409400000
     * @param url     图片url
     * @return 车牌号码，识别不出，返回null
     */
    public static String recognitionVehiclePlate(String appCode, String url) throws Exception {

        String plateUrl = "http://ocrcp.market.alicloudapi.com/rest/160601/ocr/ocr_vehicle_plate.json";

        String result = aliyunRequest(appCode,url,plateUrl);

        String pattern = "[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z0]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}";
        Pattern r = Pattern.compile(pattern);

        // 现在创建 matcher 对象
        Matcher m = r.matcher(result);
        if (m.find()) {
            return m.group(0);
        }
        return null;
    }

    private static String aliyunRequest(String appCode, String url, String requestUrl) throws Exception {
        byte[] bytes = HttpHelper.downloadFileAsByte(url);

        String content = new String(org.apache.commons.codec.binary.Base64.encodeBase64(bytes));
        Map<String, String> requestModel = new HashMap<String, String>();
        requestModel.put("image", content);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "APPCODE " + appCode);

        return HttpHelper.<String>postObjectAsJSON(requestUrl, requestModel, String.class, headers);

    }

    /**
     * 识别车牌vin号码
     *
     * @param appCode c参考https://market.aliyun.com/products/57124001/cmapi023049.html?spm=5176.2020520132.101.5.36617218yCqFKu#sku=yuncode1704900000
     * @param url     图片url
     * @return vin号码，识别不出，返回null
     */
    public static String recognitionVehicleVin(String appCode, String url) throws Exception {
        String vinUrl = "http://vin.market.alicloudapi.com/api/predict/ocr_vin";
        String result = aliyunRequest(appCode,url,vinUrl);
        try {
            JSONObject obj = JSON.parseObject(result);
            if(obj.containsKey("vin"))
                return obj.getString("vin");
        }catch (Exception e){
            throw new Exception("Vin码识别失败:"+result);
        }
        return null;
    }
}
