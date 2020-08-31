package d1.framework.cachesample;

import d1.framework.cache.IDoCache;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.stereotype.Component;

@Component
public class DestroyEhcacheBean implements DisposableBean, ExitCodeGenerator {

    @Autowired
    IDoCache cache;
    @Override
    public void destroy() throws Exception {
        System.out.println("程序推出前先shutdown ehcache");
        if(cache!=null)
            cache.shutDown();
    }

    @Override
    public int getExitCode() {
        return 0;
    }
}