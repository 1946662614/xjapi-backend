package com.yupi.xjapi.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xj.xjapicommon.model.entity.InterfaceInfo;
import com.xj.xjapicommon.service.InnerInterfaceInfoService;
import com.yupi.xjapi.common.ErrorCode;
import com.yupi.xjapi.exception.BusinessException;
import com.yupi.xjapi.mapper.InterfaceInfoMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @ClassName InnerInterfaceInfoServiceImpl
 * @Description TODO
 * @Author 嘻精
 * @Date 2023/6/12 16:56
 * @Version 1.0
 */
@DubboService
public class InnerInterfaceInfoServiceImpl implements InnerInterfaceInfoService {
	
	@Resource
	private InterfaceInfoMapper interfaceInfoMapper;
	@Override
	public InterfaceInfo getInterfaceInfo(String url, String method) {
		if (StringUtils.isAnyBlank(url,method)) {
			throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.eq("url", url);
		queryWrapper.eq("method", method);
		return  interfaceInfoMapper.selectOne(queryWrapper);
		
	}
}
