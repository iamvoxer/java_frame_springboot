package d1.framework.osinfo;

import d1.framework.osinfo.model.OsInfo;
import d1.framework.osinfo.service.OsInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Kikki
 */
@RestController
@RequestMapping("/mytest")
public class TestController {
    @Autowired
    OsInfoService osInfoService;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public OsInfo test() throws Exception {
        return osInfoService.getOsInfo();
    }
}
