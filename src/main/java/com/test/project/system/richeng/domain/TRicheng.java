package com.test.project.system.richeng.domain;

import java.util.Date;
import com.test.framework.aspectj.lang.annotation.Excel;
import com.test.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 日程管理对象 t_richeng
 * 
 * @author test
 * @date 2022-04-15
 */
public class TRicheng extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 级别 */
    @Excel(name = "级别")
    private String jibie;

    /** 人员 */
    @Excel(name = "人员")
    private String toUser;

    /** 内容 */
    @Excel(name = "内容")
    private String content;

    /** 开始时间 */
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date beginTime;

    /** 结束时间 */
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

    /** 状态 */
    @Excel(name = "状态")
    private String state;

    /** 发布者级别 */
    @Excel(name = "发布者级别")
    private String fabuzhe;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setJibie(String jibie)
    {
        this.jibie = jibie;
    }

    public String getJibie()
    {
        return jibie;
    }
    public void setToUser(String toUser)
    {
        this.toUser = toUser;
    }

    public String getToUser()
    {
        return toUser;
    }
    public void setContent(String content)
    {
        this.content = content;
    }

    public String getContent()
    {
        return content;
    }
    public void setBeginTime(Date beginTime)
    {
        this.beginTime = beginTime;
    }

    public Date getBeginTime()
    {
        return beginTime;
    }
    public void setEndTime(Date endTime)
    {
        this.endTime = endTime;
    }

    public Date getEndTime()
    {
        return endTime;
    }
    public void setState(String state)
    {
        this.state = state;
    }

    public String getState()
    {
        return state;
    }
    public void setFabuzhe(String fabuzhe)
    {
        this.fabuzhe = fabuzhe;
    }

    public String getFabuzhe()
    {
        return fabuzhe;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("jibie", getJibie())
            .append("toUser", getToUser())
            .append("content", getContent())
            .append("beginTime", getBeginTime())
            .append("endTime", getEndTime())
            .append("state", getState())
            .append("createBy", getCreateBy())
            .append("fabuzhe", getFabuzhe())
            .toString();
    }
}
