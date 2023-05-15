package com.yupi.xjapi.model.dto.post;

import com.yupi.xjapi.common.PageRequest;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 查询请求
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PostQueryRequest extends PageRequest implements Serializable {
    
    /**
     * 主键
     */
    private Long id;
    
    /**
     * 用户名
     */
    private String name;
    
    /**
     * 描述
     */
    private String description;
    
    /**
     * 接口地址
     */
    private String url;
    
    /**
     * 请求头
     */
    private String requestHeader;
    
    /**
     * 响应头
     */
    private String responseHeader;
    
    /**
     * 创建人
     */
    private String userId;
    
    /**
     * 接口状态（0 - 关闭， 1 - 开启））
     */
    private Integer status;
    
    /**
     * 请求类型
     */
    private String method;
    
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public void setRequestHeader(String requestHeader) {
        this.requestHeader = requestHeader;
    }
    
    public void setResponseHeader(String responseHeader) {
        this.responseHeader = responseHeader;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }
    
    public void setMethod(String method) {
        this.method = method;
    }
    
    public Long getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public String getUrl() {
        return url;
    }
    
    public String getRequestHeader() {
        return requestHeader;
    }
    
    public String getResponseHeader() {
        return responseHeader;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public Integer getStatus() {
        return status;
    }
    
    public String getMethod() {
        return method;
    }
    
    private static final long serialVersionUID = 1L;
}