package d1.framework.webapi.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;


@Component
public class CorsFilter implements Filter {

    @Value("${d1.framework.webapi.cors:*}")
    private String cors;//缺省是*,生产环境需要单独设置，如果是多域名，中间用,隔开

    public static void initCorsHeader(String cors, HttpServletRequest req, HttpServletResponse response) {
        if (cors.isEmpty() || cors.equals("*")) {
            response.setHeader("Access-Control-Allow-Origin", cors);
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE");
            response.setHeader("Access-Control-Max-Age", "3600");
            //ie11不支撑header这里为*
            response.setHeader("Access-Control-Allow-Headers", "authorization,x-requested-with,content-type");
        } else {
            String originHeader = req.getHeader("Origin");
            String[] corsArray = cors.split(",");
            if (!Arrays.asList(corsArray).contains(originHeader)) {
                return;
            }
            response.setHeader("Access-Control-Allow-Origin", originHeader);
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers", "authorization,x-requested-with,content-type");
        }
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        initCorsHeader(cors, (HttpServletRequest) req, response);
        //System.out.println("*********************************过滤器被使用**************************");
        chain.doFilter(req, res);
    }

    public void init(FilterConfig filterConfig) {
    }

    public void destroy() {
    }
}