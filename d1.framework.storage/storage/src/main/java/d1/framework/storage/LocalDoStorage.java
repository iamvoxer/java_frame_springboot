package d1.framework.storage;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;


public class LocalDoStorage implements IDoStorage {
    private String DATA_FOLDER = "./data/";

    @Override
    public String writeFile(String _path, byte[] _content) throws IOException {
        return write(DATA_FOLDER, _path, _content);
    }

    @Override
    public byte[] readFile(String _path) throws IOException {
        FileChannel fc = null;
        try {
            fc = new RandomAccessFile(getDataPath(DATA_FOLDER, _path), "r").getChannel();
            MappedByteBuffer byteBuffer = fc.map(FileChannel.MapMode.READ_ONLY, 0,
                    fc.size()).load();
            byte[] result = new byte[(int) fc.size()];
            if (byteBuffer.remaining() > 0) {
                byteBuffer.get(result, 0, byteBuffer.remaining());
            }
            return result;
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                fc.close();
            } catch (IOException e) {
                throw e;
            }
        }
    }

    @Override
    public String writeBigFile(String _path, byte[] _content) throws IOException {
        return write(DATA_FOLDER, _path, _content);
    }

    @Override
    public String writeBigFile(String _path, InputStream _input) throws IOException {
        String _filePath = getDataPath(DATA_FOLDER, _path);
        try {
            OutputStream outStream = new FileOutputStream(_filePath);
            byte[] buffer = new byte[8 * 1024];
            int bytesRead;
            while ((bytesRead = _input.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            return _filePath;
        }
    }

    @Override
    public String getAccessUrl(String _path) {
        return getDataPath(DATA_FOLDER, _path);
    }

    @Override
    public String getUploadToken() {
        return null;
    }

    private String getDataPath(String _root, String _path) {
        return (_root + _path).replace("\\", "/").replace("//", "/");
    }

    private String write(String _root, String _path, byte[] _content) throws IOException {
        String _filePath = getDataPath(_root, _path);
        File file = new File(_filePath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            FileOutputStream outputStream = new FileOutputStream(new File(_filePath));
            outputStream.write(_content);
            outputStream.close();
        } catch (Exception e) {
            throw e;
        }
        return _path;
    }

}
