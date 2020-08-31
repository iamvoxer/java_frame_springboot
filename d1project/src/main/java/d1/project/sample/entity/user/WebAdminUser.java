package d1.project.sample.entity.user;

import d1.framework.webapi.entity.DoUserBaseEntity;
import io.swagger.annotations.ApiModel;

import javax.persistence.Entity;
import javax.persistence.Table;

//示例管理页面端的用户entity，和普通用户的差别就是多一个操作资源permission控制
@ApiModel(value = "WebAdminUser", description = "系统用户管理表")
@Entity
@Table(name = "d1_webadmin_user")
public class WebAdminUser extends DoUserBaseEntity {

    @Override
    public String getType() {
        return "webadmin";
    }
}
