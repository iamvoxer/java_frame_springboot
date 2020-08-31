package d1.framework.storagesample;

import d1.framework.storage.IDoStorage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StoragesampleApplicationTests {
    @Autowired
    IDoStorage qiniuDoStorage;
    @Autowired
    IDoStorage localDoStorage;

    @Test
    public void contextLoads() throws Exception {
        test1();
        test2();
    }

    private void test1() throws Exception {
        String test = "/temp/test.png";
        byte[] content = localDoStorage.readFile(test);
        localDoStorage.writeFile("/test/my.png", content);
        localDoStorage.writeBigFile("/test/mybig.png", content);
        System.out.println(localDoStorage.getAccessUrl("/test/mybig.png"));
    }

    private void test2() throws Exception {
        String test = "/temp/test.png";
        byte[] content = readFile(test);
        System.out.println(qiniuDoStorage.writeFile("/test/my.png", content));
        System.out.println(qiniuDoStorage.writeBigFile("/test/mybig.png", content));
        System.out.println(qiniuDoStorage.getAccessUrl("/test/mybig.png"));
        byte[] results = qiniuDoStorage.readFile("/test/my.png");
        System.out.println(qiniuDoStorage.getUploadToken());
        System.out.println(results.length);
    }

    public byte[] readFile(String _path) throws IOException {
        FileChannel fc = null;
        try {
            fc = new RandomAccessFile(getDataPath("./data/", _path), "r").getChannel();
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

    private String getDataPath(String _root, String _path) {
        return (_root + _path).replace("\\", "/").replace("//", "/");
    }

}
