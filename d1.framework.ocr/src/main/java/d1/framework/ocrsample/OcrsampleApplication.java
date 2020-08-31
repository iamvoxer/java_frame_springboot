package d1.framework.ocrsample;

import d1.framework.ocr.AliyunOCRHelper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OcrsampleApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(OcrsampleApplication.class, args);

        test2();
    }
    public static void test1() throws Exception {
        for(int i = 1;i<=7;i++){
            String plate = AliyunOCRHelper.recognitionVehiclePlate("0936085k5ko2380be995b40f00930",
                    "http://x9rlwpbkk.bkt.clouddn.com/car"+i+".jpg");
            System.out.println(plate);
        }
    }
    public static void test2() throws Exception {
        for(int i = 1;i<=7;i++){
            String plate = AliyunOCRHelper.recognitionVehicleVin("09360871641a445kf030295b40f00930",
                    "http://x9rlwpbkk.bkt.clouddn.com/vin"+i+".jpg");
            System.out.println(plate);
        }
    }
}
