package d1.framework.osinfo.service;

import d1.framework.osinfo.model.OsInfo;
import org.hyperic.jni.ArchNotSupportedException;
import org.hyperic.sigar.*;
import org.hyperic.sigar.FileSystem;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Kikki
 */
public class OsInfoService {
    private String httpGetIp;
    private String IP_REGEX = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";

    public OsInfoService() throws IOException {
        this("https://www.taobao.com/help/getip.php");
    }

    public OsInfoService(String httpGetIp) throws IOException {
        this.httpGetIp = httpGetIp;
        SigarLoader loader = new SigarLoader(Sigar.class);
        try {
            String lib = loader.getLibraryName();
            String path = "sigar.so/" + lib;
            ClassLoader cl = getDefaultClassLoader();
            URL resource = (cl != null ? cl.getResource(path) : ClassLoader.getSystemResource(path));
            InputStream is = resource.openStream();
            File tempDir = new File("/var/sigar");
            if (!tempDir.exists()) {
                boolean mkdirs = tempDir.mkdirs();
                System.out.println(mkdirs);
            }
            BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(new File(tempDir, lib), false));
            int len;
            while ((len = is.read()) != -1) {
                os.write(len);
            }
            is.close();
            os.close();
            System.setProperty("org.hyperic.sigar.path", tempDir.getCanonicalPath());
        } catch (ArchNotSupportedException e) {
            e.printStackTrace();
        }
    }

    //获取外网ip需要发送http请求，需要时间，所以和OsInfo分开
    public String getWANIP() throws Exception {
        String net = doGet(httpGetIp);
        Pattern pattern = Pattern.compile(IP_REGEX);
        Matcher matcher = pattern.matcher(net);
        if (matcher.find()) {
            return matcher.group(0);
        }
        return "";
    }

    public OsInfo getOsInfo() throws Exception {
        OsInfo osInfo = new OsInfo();
        osInfo.setIp(getLANIP());
        osInfo.setFreeCpu(getCpuIdle());
        osInfo.setFreeHardDisk(getLocalDisk());
        osInfo.setFreeMemory(getMemoryIdle());
        osInfo.setOsVersion(getVersion());
        return osInfo;
    }

    private String getLANIP() throws UnknownHostException {
        try {
            InetAddress candidateAddress = null;
            // 遍历所有的网络接口
            for (Enumeration ifaces = NetworkInterface.getNetworkInterfaces(); ifaces.hasMoreElements(); ) {
                NetworkInterface iface = (NetworkInterface) ifaces.nextElement();
                // 在所有的接口下再遍历IP
                for (Enumeration inetAddrs = iface.getInetAddresses(); inetAddrs.hasMoreElements(); ) {
                    InetAddress inetAddr = (InetAddress) inetAddrs.nextElement();
                    // 排除loopback类型地址
                    if (!inetAddr.isLoopbackAddress()) {
                        if (inetAddr.isSiteLocalAddress()) {
                            // 如果是site-local地址，就是它了
                            return inetAddr.getHostAddress();
                        } else if (candidateAddress == null) {
                            // site-local类型的地址未被发现，先记录候选地址
                            candidateAddress = inetAddr;
                        }
                    }
                }
            }
            if (candidateAddress != null) {
                return candidateAddress.getHostAddress();
            }
            // 如果没有发现 non-loopback地址.只能用最次选的方案
            InetAddress jdkSuppliedAddress = InetAddress.getLocalHost();
            if (jdkSuppliedAddress == null) {
                throw new UnknownHostException("The JDK InetAddress.getLocalHost() method unexpectedly returned null.");
            }
            return jdkSuppliedAddress.getHostAddress();
        } catch (Exception e) {
            UnknownHostException unknownHostException = new UnknownHostException(
                    "Failed to determine LAN address: " + e);
            unknownHostException.initCause(e);
            throw unknownHostException;
        }
    }

    private String doGet(String httpUrl) {
        HttpURLConnection connection = null;
        InputStream is = null;
        BufferedReader br = null;
        String result = null;
        // 返回结果字符串
        try {
            // 创建远程url连接对象
            URL url = new URL(httpUrl);
            // 通过远程url连接对象打开一个连接，强转成httpURLConnection类
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setInstanceFollowRedirects(false);
            // 设置连接方式：get
            connection.setRequestMethod("GET");
            // 设置连接主机服务器的超时时间：15000毫秒
            connection.setConnectTimeout(15000);
            // 设置读取远程返回的数据时间：60000毫秒
            connection.setReadTimeout(60000);
            // 发送请求
            connection.connect();
            // 通过connection连接，获取输入流
            int codeOk = 200;
            if (connection.getResponseCode() == codeOk) {
                is = connection.getInputStream();
                // 封装输入流is，并指定字符集
                br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
                // 存放数据
                StringBuilder sbf = new StringBuilder();
                String temp;
                while ((temp = br.readLine()) != null) {
                    sbf.append(temp);
                }


                result = sbf.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (connection != null) {
                connection.disconnect();
                // 关闭远程连接
            }
        }

        return result;
    }

    private String getLocalDisk() throws SigarException {
        long free = 0L;
        long total = 0L;
        Sigar sigar = new Sigar();
        FileSystem[] fsList = sigar.getFileSystemList();
        for (FileSystem fs : fsList) {
            FileSystemUsage usage;
            try {
                usage = sigar.getFileSystemUsage(fs.getDirName());
            } catch (SigarException e) {
                continue;
            }
            if (fs.getType() == 2) {
                // TYPE_LOCAL_DISK : 本地硬盘
                // 文件系统总大小
                free += usage.getFree();
                total += usage.getTotal();
            }
        }

        return CpuPerc.format((double) free / (double) total);
    }

    private String getMemoryIdle() throws SigarException {
        Sigar sigar = new Sigar();
        Mem mem = sigar.getMem();
        double memoryIdle = (double) (mem.getFree() / 1024L) / (double) (mem.getTotal() / 1024L);
        return CpuPerc.format(memoryIdle);
    }

    private String getCpuIdle() throws SigarException {
        Sigar sigar = new Sigar();
        CpuInfo[] infos = sigar.getCpuInfoList();
        CpuPerc[] cpuList;
        cpuList = sigar.getCpuPercList();
        double cpuSiGar = 0;
        for (int i = 0; i < infos.length; i++) {
            cpuSiGar += cpuList[i].getIdle();
        }
        return CpuPerc.format((cpuSiGar / infos.length));
    }

    private String getVersion() {
        OperatingSystem os = OperatingSystem.getInstance();
        return os.getVersion();
    }

    private ClassLoader getDefaultClassLoader() {
        ClassLoader cl = null;
        try {
            cl = Thread.currentThread().getContextClassLoader();
        } catch (Throwable ex) {
            // Cannot access thread context ClassLoader - falling back...
        }
        if (cl == null) {
            // No thread context class loader -> use class loader of this class.
            cl = OsInfoService.class.getClassLoader();
            if (cl == null) {
                // getClassLoader() returning null indicates the bootstrap ClassLoader
                try {
                    cl = ClassLoader.getSystemClassLoader();
                } catch (Throwable ex) {
                    // Cannot access system ClassLoader - oh well, maybe the caller can live with null...
                }
            }
        }
        return cl;
    }
}
