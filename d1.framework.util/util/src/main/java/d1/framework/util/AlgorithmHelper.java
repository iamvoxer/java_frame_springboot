package d1.framework.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.util.*;

public class AlgorithmHelper {
    //算法参考https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=4_3
    //微信算法，但是我们也可以用于其它地方

    /**
     * @param maps
     * @param keyName
     * @param keyValue
     */
    public static String hmacSign(Map<String, Object> maps, String keyName, String keyValue) throws UnsupportedEncodingException {
        List<Map.Entry<String, Object>> list = new ArrayList<Map.Entry<String, Object>>(maps.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Object>>() {
            @Override
            public int compare(Map.Entry<String, Object> o1, Map.Entry<String, Object> o2) {
                // 升序排序
                return o1.getKey().compareTo(o2.getKey());
            }
        });

        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, Object> entry : list) {
            sb.append(entry.getKey() + "=" + entry.getValue());
            sb.append("&");
        }
        sb.append(keyName + "=" + keyValue);
        String temp = md5_32(sb.toString(), true);
        return temp;
    }

    public static String md5_32(String source, boolean isUpper) throws UnsupportedEncodingException {
        String result = DigestUtils.md5Hex(source.getBytes("utf-8"));
        if (isUpper)
            result = result.toUpperCase();
        else
            result = result.toLowerCase();
        return result;
    }
}
