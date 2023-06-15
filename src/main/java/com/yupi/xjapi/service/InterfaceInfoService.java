package com.yupi.xjapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xj.xjapicommon.model.entity.InterfaceInfo;

/**
* @author 嘻精
* @description 针对表【interface_info(接口信息表)】的数据库操作Service
* @createDate 2023-05-12 20:18:54
*/
public interface InterfaceInfoService extends IService<InterfaceInfo> {
    
    void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add);
}
