package d1.framework.webapi.configuration;

import com.alibaba.fastjson.JSONObject;
import d1.framework.cache.IDoCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class AuthFilter implements Filter {

    @Autowired
    IDoCache cache;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
//        System.out.println("filter init 被调用");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
//        System.out.println("filter doFilter 被调用");
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String auth = request.getHeader("Authorization");
        if (auth == null || auth.isEmpty()) {//没定义Authorization认为是无需认证
            chain.doFilter(req, res);
            return;
        }
        if (!auth.contains(" ") || !(auth.startsWith("token ") || auth.startsWith("sign "))) {//必须是token 或sign 开头
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Authorization必须是token或sign开头");
            return;
        }
        String[] auths = auth.split(" ");
        if ("token".equals(auths[0])) {
            String accessToken = auths[1];
            JSONObject entity = cache.getData(accessToken);
            if (entity != null) {//内存里如果有，认证通过
                chain.doFilter(req, res);
                return;
            } else {
                response.sendError(HttpStatus.UNAUTHORIZED.value(), "token失效");
                return;
            }
        } else if ("sign".equals(auths[0])) {
            //不在这里统一校验，改在HMACSignService里校验
        }
        chain.doFilter(req, res);
    }

    @Override
    public void destroy() {
        //System.out.println("filter destroy 被调用");
    }
}
