package com.yupi.xjapi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xj.xjapicommon.model.entity.UserInterfaceInfo;

import java.util.List;

/**
* @author 嘻精
* @description 针对表【user_interface_info(用户调用接口关系表)】的数据库操作Mapper
* @createDate 2023-05-28 16:13:33
* @Entity com.yupi.xjapi.model.entity.UserInterfaceInfo
*/
public interface UserInterfaceInfoMapper extends BaseMapper<UserInterfaceInfo> {

	// select interfaceInfoId,sum(totalNum)as totalNum from user_interface_info group by interfaceInfoId order by totalNum desc limit 3;
	List<UserInterfaceInfo> listTopInvokeInterfaceInfo(int limit);

}




