package com.yupi.xjapi.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xj.xjapicommon.model.entity.User;
import com.xj.xjapicommon.service.InnerUserService;
import com.yupi.xjapi.common.ErrorCode;
import com.yupi.xjapi.exception.BusinessException;
import com.yupi.xjapi.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @ClassName InnerUserServiceImpl
 * @Description TODO
 * @Author 嘻精
 * @Date 2023/6/12 16:58
 * @Version 1.0
 */
@DubboService
public class InnerUserServiceImpl implements InnerUserService {
	
	@Resource
	private UserMapper userMapper;
	@Override
	public User getInvokeUser(String accessKey) {
		if (StringUtils.isAnyBlank(accessKey)) {
			throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.eq("accessKey", accessKey);
		User user =  userMapper.selectOne(queryWrapper);
		return user;
	}
}
