package com.yupi.xjapi.model.dto.userinterfaceinfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * 创建请求
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@Data
public class UserInterfaceInfoAddRequest implements Serializable {
    
    /**
     * id
     */
    private Long id;
    
    /**
     * 调用用户 id
     */
    private Long userId;
    
    /**
     * 接口 id
     */
    private Long interfaceInfoId;
    
    /**
     * 总调用次数
     */
    private Integer totalNum;
    
    /**
     * 剩余调用次数
     */
    private Integer leftNum;
    

}