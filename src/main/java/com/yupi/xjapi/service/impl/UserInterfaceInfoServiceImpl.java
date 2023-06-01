package com.yupi.xjapi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.xjapi.model.entity.UserInterfaceInfo;
import com.yupi.xjapi.service.UserInterfaceInfoService;
import com.yupi.xjapi.mapper.UserInterfaceInfoMapper;
import org.springframework.stereotype.Service;

/**
* @author 嘻精
* @description 针对表【user_interface_info(用户调用接口关系表)】的数据库操作Service实现
* @createDate 2023-05-28 16:13:33
*/
@Service
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo>
    implements UserInterfaceInfoService{

}




