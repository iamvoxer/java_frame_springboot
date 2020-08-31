package d1.framework.storage;

import java.io.InputStream;

public interface IDoStorage {
    String writeFile(String _path, byte[] _content) throws Exception;

    byte[] readFile(String _path) throws Exception;

    //网络云存储写大文件支持断点续传
    String writeBigFile(String _path, byte[] _content) throws Exception;

    String writeBigFile(String _path, InputStream _input) throws Exception;

    //云存储后获取访问的地址
    String getAccessUrl(String _path);

    String getUploadToken() throws Exception;

}
