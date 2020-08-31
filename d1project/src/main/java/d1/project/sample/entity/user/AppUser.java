package d1.project.sample.entity.user;

import d1.framework.webapi.entity.DoUserBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.Table;

//示例App端的用户entity
@ApiModel(value = "AppUser", description = "App用户管理表") //swagger注解
@Entity
@Table(name = "d1_app_user") //表名
public class AppUser extends DoUserBaseEntity {

    @ApiModelProperty(value = "真实姓名")//swagger注解
    private String realName; //基类没有的字段，为这个项目扩展的字段

    @Override
    public String getType() {
        return "appuser";
    }//用"@Auth("appuser")"来控制对应的contrller

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}
