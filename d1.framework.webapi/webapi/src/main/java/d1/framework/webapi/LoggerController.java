package d1.framework.webapi;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/log")
public class LoggerController {
    private static String LOG_ROOT = "./logs";
    @Value("${d1.framework.webapi.logcheck.host:/}")
    private String host;

    @ApiOperation(value = "查看日志文件", notes = "下载日志文件查看")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "path", value = "文件名,包括目录", required = true, dataType = "String")
    })
    @RequestMapping(value = "/download", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void downloaLog(String path, HttpServletRequest request, HttpServletResponse response) {

        response.setCharacterEncoding(request.getCharacterEncoding());
        response.setContentType("application/octet-stream");
        FileInputStream fis = null;
        try {
            File file = new File(LOG_ROOT + File.separator + path);
            fis = new FileInputStream(file);
            response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
            IOUtils.copy(fis, response.getOutputStream());
            response.flushBuffer();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @ApiOperation(value = "查看日志列表", notes = "日志文件查看列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listLog(HttpServletRequest request) {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            File file = new File(LOG_ROOT);
            File list[] = file.listFiles();
            stringBuffer.append("<html><body>\n");
            stringBuffer.append("   <table>\n");
            stringBuffer.append("   <tr><td>日期</td></tr>\n");
            for (int i = list.length - 1; i >= 0; i--) {
                File folder = list[i];
                stringBuffer.append("<tr><td>").append(list[i]).append(": </td>\n");
                String[] logs = folder.list();
                for (String temp : logs) {
                    stringBuffer.append("<td><a href=\"").append(host).append("log/download?path=");
                    stringBuffer.append(folder.getName() + "/" + temp).append("\">").append(temp).append("  </td>\n");
                }
                stringBuffer.append("</tr>");
            }
            stringBuffer.append("   </table>\n");
            stringBuffer.append("</body></html>");
            return stringBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}
