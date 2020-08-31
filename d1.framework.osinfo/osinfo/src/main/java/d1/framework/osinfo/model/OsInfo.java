package d1.framework.osinfo.model;

/**
 * 系统信息
 *
 * @author Kikki
 */
public class OsInfo {
    /**
     * 操作系统版本
     **/
    private String osVersion;
    /**
     * 空闲cpu
     **/
    private String freeCpu;
    /**
     * 空闲内存
     **/
    private String freeMemory;
    /**
     * 空闲硬盘
     **/
    private String freeHardDisk;
    /**
     * 外网ip
     **/
    private String ip;

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getFreeCpu() {
        return freeCpu;
    }

    public void setFreeCpu(String freeCpu) {
        this.freeCpu = freeCpu;
    }

    public String getFreeMemory() {
        return freeMemory;
    }

    public void setFreeMemory(String freeMemory) {
        this.freeMemory = freeMemory;
    }

    public String getFreeHardDisk() {
        return freeHardDisk;
    }

    public void setFreeHardDisk(String freeHardDisk) {
        this.freeHardDisk = freeHardDisk;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
