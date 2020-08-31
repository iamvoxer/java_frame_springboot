package d1.framework.util;

import org.apache.commons.lang3.RandomStringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.util.*;

//杂七杂八的工具类
public class MiscHelper {
    //获取32位随机字符串
    public static String getUUID32() {
        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }

    /**
     * 获取多位随机全数字字符串，一般用于生成验证码
     *
     * @param places 如果小于=0，就返回4位数字
     * @return
     */
    public static String getRandomNumbers(int places) {
        if (places <= 0) {
            places = 4;
        }
        return RandomStringUtils.random(places, "0123456789");
    }

    //对象转Map
    private static Map<String, Object> objectToMap(Object obj, boolean includeNull) throws Exception {
        if (obj == null) {
            return null;
        }

        Map<String, Object> map = new HashMap<String, Object>();

        List<Field> declaredFields = getParentAndMyFields(obj.getClass());
        for (Field field : declaredFields) {
            field.setAccessible(true);
            if (includeNull || field.get(obj) != null)
                map.put(field.getName(), field.get(obj));
        }
        return map;
    }

    private static List<Field> getParentAndMyFields(Class tempClass) {
        List<Field> fieldList = new ArrayList<>();
        while (tempClass != null && !tempClass.getName().toLowerCase().equals("java.lang.object")) {
            fieldList.addAll(Arrays.asList(tempClass.getDeclaredFields()));
            tempClass = tempClass.getSuperclass(); //得到父类,然后赋给自己
            if (tempClass.getName().toLowerCase().equals("java.lang.object")) break;
        }
        return fieldList;
    }

    //对象转Map,如果值为null，则不包含
    public static Map<String, Object> objectToMapWithoutNull(Object obj) throws Exception {
        return objectToMap(obj, false);
    }

    //对象转Map,如果值为null也包含
    public static Map<String, Object> objectToMapWithNull(Object obj) throws Exception {
        return objectToMap(obj, true);
    }

    //文件转base64，通常是图片文件
    public static String file2Base64(String imgFile) throws Exception {
        String imgBase64 = "";
        File file = new File(imgFile);
        byte[] content = new byte[(int) file.length()];
        FileInputStream finputstream = new FileInputStream(file);
        finputstream.read(content);
        finputstream.close();
        imgBase64 = new String(org.apache.commons.codec.binary.Base64.encodeBase64(content));
        return imgBase64;
    }
}
