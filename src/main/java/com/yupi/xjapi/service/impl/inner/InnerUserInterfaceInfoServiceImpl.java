package com.yupi.xjapi.service.impl.inner;

import com.xj.xjapicommon.service.InnerUserInterfaceInfoService;
import com.yupi.xjapi.service.UserInterfaceInfoService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @ClassName InnerUserInterfaceInfoServiceImpl
 * @Description TODO
 * @Author 嘻精
 * @Date 2023/6/12 16:57
 * @Version 1.0
 */
@DubboService
public class InnerUserInterfaceInfoServiceImpl implements InnerUserInterfaceInfoService {
	
	@Resource
	private UserInterfaceInfoService userInterfaceInfoService;
	@Override
	public boolean invokeCount(long interfaceInfoId, long userId) {
		return userInterfaceInfoService.invokeCount(interfaceInfoId, userId);
	}
}
