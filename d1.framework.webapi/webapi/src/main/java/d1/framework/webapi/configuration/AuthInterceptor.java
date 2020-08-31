package d1.framework.webapi.configuration;

import com.alibaba.fastjson.JSONObject;
import d1.framework.cache.IDoCache;
import d1.framework.webapi.annotation.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;


public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    IDoCache cache;

    //在请求处理之前进行调用（Controller方法调用之前
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
//        System.out.println("preHandle被调用");

        // 验证权限
        if (this.hasAuth(httpServletRequest, handler)) {
            return true;
        }
        httpServletResponse.sendError(HttpStatus.FORBIDDEN.value(), "无权限");
        return false;//如果false，停止流程，api被拦截
    }


    /**
     * 是否有权限
     *
     * @param handler
     * @return
     */
    private boolean hasAuth(HttpServletRequest httpServletRequest, Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;

            // 获取方法上的注解
            Auth authAnno = handlerMethod.getMethod().getAnnotation(Auth.class);
            // 如果方法上的注解为空 则获取类的注解
            if (authAnno == null) {
                authAnno = handlerMethod.getBean().getClass().getAnnotation(Auth.class);
            }


            // 如果标记了注解，则判断权限
            if (authAnno != null) {

                String[] authGroup = authAnno.value();
                if (Arrays.asList(authGroup).contains("noauth")) return true;
                if (authGroup != null && authGroup.length > 0) {
                    String auth = httpServletRequest.getHeader("Authorization");
                    if (auth == null) {//没定义Authorization不让调用
                        return false;
                    }
                    if (auth.isEmpty()) {//定义了Authorization但是为空不行
                        return false;
                    }

                    String[] auth_array = auth.split(" ");
                    if ("token".equals(auth_array[0])) {
                        String accessToken = auth_array[1];
                        JSONObject entity = cache.getData(accessToken);
                        if (entity != null && entity.containsKey("type")) {//内存里如果有，认证通过
                            return Arrays.asList(authGroup).contains(entity.get("type"));
                        } else {
                            return false;
                        }
                    } else if ("sign".equals(auth_array[0])) {
                        //TODO
                        return false;
                    }
                }
            }
        }

        return true;
    }


    //请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
//        System.out.println("postHandle被调用");
    }

    //在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
//        System.out.println("afterCompletion被调用");
    }

}
