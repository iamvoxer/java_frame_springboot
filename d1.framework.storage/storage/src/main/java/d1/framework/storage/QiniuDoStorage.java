package d1.framework.storage;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class QiniuDoStorage implements IDoStorage {
    private String accessKey;

    private String secretKey;

    private String bucket;

    private String DNS;

    public QiniuDoStorage(String accessKey, String secretKey, String bucket, String DNS) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.bucket = bucket;
        this.DNS = DNS;
    }


    private void validate() throws Exception {
        if (accessKey == null || accessKey.isEmpty())
            throw new Exception("七牛AK不能为空");

        if (secretKey == null || secretKey.isEmpty())
            throw new Exception("七牛SK不能为空");

        if (bucket == null || bucket.isEmpty())
            throw new Exception("七牛桶不能为空");

        if (DNS == null || DNS.isEmpty())
            throw new Exception("七牛AK不能为空");
    }

    @Override
    public String writeFile(String _path, byte[] _content) throws Exception {
        return write(_path, _content);
    }

    @Override
    public byte[] readFile(String _path) throws Exception {
        validate();
        String url = getAccessUrl(_path);
        URL temp = new URL(url);
        URLConnection connection = temp.openConnection();
        InputStream input = connection.getInputStream();
        byte[] buffer = new byte[4096];
        int n;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            while ((n = input.read(buffer)) != -1) {
                output.write(buffer, 0, n);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            output.close();
        }
        return output.toByteArray();
    }

    @Override
    public String writeBigFile(String _path, byte[] _content) throws Exception {
        return write(_path, _content);
    }

    @Override
    public String writeBigFile(String _path, InputStream _input) throws Exception {
        return write(_path, _input);
    }

    private String write(String _paht, Object _obj) throws Exception {
        validate();
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);

        try {
            Response response = null;
            if (_obj instanceof InputStream)
                response = uploadManager.put((InputStream) _obj, _paht, upToken, null, null);
            else if (_obj instanceof byte[])
                response = uploadManager.put((byte[]) _obj, _paht, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key + "," + putRet.hash);
            return putRet.key;
        } catch (QiniuException ex) {
            throw ex;
        }
    }

    @Override
    public String getAccessUrl(String _path) {
        String temp = DNS + _path;
        return temp;
    }

    @Override
    public String getUploadToken() throws Exception {
        validate();
        return Auth.create(accessKey, secretKey).uploadToken(bucket);
    }


}
