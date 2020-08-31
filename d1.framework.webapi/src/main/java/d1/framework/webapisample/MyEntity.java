package d1.framework.webapisample;

import d1.framework.webapi.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Date;

@ApiModel(value = "MyEntity", description = "测试表")
@Entity
@Table(name = "d1_my_entity")
public class MyEntity extends BaseEntity {

    @ApiModelProperty(value = "用户Id")
    private String userId;

    @ApiModelProperty(value = "操作人Id")
    private Long operatorId;

    @Column(length = 20)
    @ApiModelProperty(value = "操作人")
    private String operator;

    @Column(length = 100)
    @ApiModelProperty(value = "操作资源标识")
    private String opearteFlag;

    @Column(length = 100)
    @ApiModelProperty(value = "操作资源说明")
    private String opearteDesc;

    @Column(length = 500)
    @ApiModelProperty(value = "详细操作说明")
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    @ApiModelProperty(value = "操作时间", example = "2018-01-23 09:12:32")
    private Date signUpTime;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    @ApiModelProperty(value = "创建时间", example = "2018-01-23 09:12:32")
    private Date createTime;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getOpearteFlag() {
        return opearteFlag;
    }

    public void setOpearteFlag(String opearteFlag) {
        this.opearteFlag = opearteFlag;
    }

    public String getOpearteDesc() {
        return opearteDesc;
    }

    public void setOpearteDesc(String opearteDesc) {
        this.opearteDesc = opearteDesc;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getSignUpTime() {
        return signUpTime;
    }

    public void setSignUpTime(Date signUpTime) {
        this.signUpTime = signUpTime;
    }
}