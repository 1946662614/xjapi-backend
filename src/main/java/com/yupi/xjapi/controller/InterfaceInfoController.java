package com.yupi.xjapi.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.xj.xjapiclientsdk.client.XjApiClient;
import com.yupi.xjapi.annotation.AuthCheck;
import com.yupi.xjapi.common.*;
import com.yupi.xjapi.constant.UserConstant;
import com.yupi.xjapi.exception.BusinessException;
import com.yupi.xjapi.exception.ThrowUtils;
import com.yupi.xjapi.model.dto.interfaceInfo.InterfaceInfoAddRequest;
import com.yupi.xjapi.model.dto.interfaceInfo.InterfaceInfoInvokeRequest;
import com.yupi.xjapi.model.dto.interfaceInfo.InterfaceInfoQueryRequest;
import com.yupi.xjapi.model.dto.interfaceInfo.InterfaceInfoUpdateRequest;
import com.yupi.xjapi.model.entity.InterfaceInfo;
import com.yupi.xjapi.model.entity.User;
import com.yupi.xjapi.model.enums.InterfaceInfoStatusEnum;
import com.yupi.xjapi.service.InterfaceInfoService;
import com.yupi.xjapi.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 帖子接口
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@RestController
@RequestMapping("/interfaceInfo")
@Slf4j
public class InterfaceInfoController {

    @Resource
    private InterfaceInfoService interfaceInfoService;

    @Resource
    private UserService userService;

    private final static Gson GSON = new Gson();
    
    // 引入api客户端实例
    @Resource
    private  XjApiClient xjApiClient;
    
    // region 增删改查

    /**
     * 创建
     *
     * @param interfaceInfoAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addInterfaceInfo(@RequestBody InterfaceInfoAddRequest interfaceInfoAddRequest, HttpServletRequest request) {
        if (interfaceInfoAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceInfoAddRequest, interfaceInfo);
        
        interfaceInfoService.validInterfaceInfo(interfaceInfo, true);
        User loginUser = userService.getLoginUser(request);
        interfaceInfo.setUserId(String.valueOf(loginUser.getId()));
        boolean result = interfaceInfoService.save(interfaceInfo);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        long newInterfaceInfoId = interfaceInfo.getId();
        return ResultUtils.success(newInterfaceInfoId);
    }

    /**
     * 删除
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteInterfaceInfo(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        ThrowUtils.throwIf(oldInterfaceInfo == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可删除
        if (!oldInterfaceInfo.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = interfaceInfoService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 更新（仅管理员）
     *
     * @param interfaceInfoUpdateRequest
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateInterfaceInfo(@RequestBody InterfaceInfoUpdateRequest interfaceInfoUpdateRequest) {
        if (interfaceInfoUpdateRequest == null || interfaceInfoUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceInfoUpdateRequest, interfaceInfo);
        // 参数校验
        interfaceInfoService.validInterfaceInfo(interfaceInfo, false);
        long id = interfaceInfoUpdateRequest.getId();
        // 判断是否存在
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        ThrowUtils.throwIf(oldInterfaceInfo == null, ErrorCode.NOT_FOUND_ERROR);
        boolean result = interfaceInfoService.updateById(interfaceInfo);
        return ResultUtils.success(result);
    }

    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @GetMapping("/get")
    public BaseResponse<InterfaceInfo> getInterfaceInfoById(long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(id);
        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        return ResultUtils.success(interfaceInfo);
    }
    
    /**
     * 获取列表（仅管理员可使用）
     *
     * @param interfaceInfoQueryRequest
     * @return
     */
    @AuthCheck(mustRole = "admin")
    @GetMapping("/list")
    public BaseResponse<List<InterfaceInfo>> listInterfaceInfo(InterfaceInfoQueryRequest interfaceInfoQueryRequest) {
        InterfaceInfo interfaceInfoQuery = new InterfaceInfo();
        if (interfaceInfoQueryRequest != null) {
            BeanUtils.copyProperties(interfaceInfoQueryRequest, interfaceInfoQuery);
        }
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>(interfaceInfoQuery);
        List<InterfaceInfo> interfaceInfoList = interfaceInfoService.list(queryWrapper);
        return ResultUtils.success(interfaceInfoList);
    }
    
    /**
     * 分页获取列表（封装类）
     *
     * @param interfaceInfoQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page")
    public BaseResponse<Page<InterfaceInfo>> listInterfaceInfoByPage(@RequestBody InterfaceInfoQueryRequest interfaceInfoQueryRequest,
            HttpServletRequest request) {
        long current = interfaceInfoQueryRequest.getCurrent();
        long size = interfaceInfoQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        QueryWrapper queryWrapper = new QueryWrapper();
        Page<InterfaceInfo> interfaceInfoPage = interfaceInfoService.page(new Page<>(current, size), queryWrapper);
        return ResultUtils.success(interfaceInfoPage);
    }
    
    /**
     * 发布
     *
     * @param idRequest
     * @return
     */
    @PostMapping("/online")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    // 传入接口id
    public BaseResponse<Boolean> onlineInterfaceInfo(@RequestBody IdRequest idRequest ,HttpServletRequest request ) {
        if (idRequest == null || idRequest.getId() <= 0) {
            //请求参数错误
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 取出id
        long id = idRequest.getId();
        // 1.1判断id是否存在
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        // 1.2 不存在则抛异常
        ThrowUtils.throwIf(oldInterfaceInfo == null, ErrorCode.NOT_FOUND_ERROR);
        // 2. 判断该接口是否可以调用
        // 首先要引入api的pom文件，在配置文件中配置    access-key secret-key
        // 引入api客户端实例
        com.xj.xjapiclientsdk.model.User user = new com.xj.xjapiclientsdk.model.User();
        user.setUsername("test");
        String userName = xjApiClient.getUserNameByPost(user);
        if (StringUtils.isBlank(userName)) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"接口验证失败");
        }
        // 仅本人或管理员可修改
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        interfaceInfo.setId(id);
        // 更新api状态信息
        interfaceInfo.setStatus(InterfaceInfoStatusEnum.ONLINE.getValue());
        boolean result = interfaceInfoService.updateById(interfaceInfo);
        return ResultUtils.success(result);
    }
    
    /**
     * 下线
     *
     * @param idRequest
     * @return
     */
    @PostMapping("/offline")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> offlineInterfaceInfo(@RequestBody IdRequest idRequest) {
        if (idRequest == null || idRequest.getId() <= 0) {
            //请求参数错误
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 取出id
        long id = idRequest.getId();
        // 1.1判断id是否存在
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        // 1.2 不存在则抛异常
        ThrowUtils.throwIf(oldInterfaceInfo == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可修改
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        interfaceInfo.setId(id);
        // 更新api状态信息
        interfaceInfo.setStatus(InterfaceInfoStatusEnum.OFFLINE.getValue());
        boolean result = interfaceInfoService.updateById(interfaceInfo);
        return ResultUtils.success(result);
    }
    
    /**
     * 测试调用
     *
     * @param
     * @return
     */
    @PostMapping("/invoke")
    public BaseResponse<Object> invokeInterfaceInfo(@RequestBody InterfaceInfoInvokeRequest interfaceInfoInvokeRequest, HttpServletRequest request) {
        if (interfaceInfoInvokeRequest == null || interfaceInfoInvokeRequest.getId() <= 0) {
            //请求参数错误
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 取出id
        long id = interfaceInfoInvokeRequest.getId();
        // 取出参数
        String userRequestParams = interfaceInfoInvokeRequest.getUserRequestParams();
        // 1.1判断id是否存在
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        // 1.2 不存在则抛异常
        ThrowUtils.throwIf(oldInterfaceInfo == null, ErrorCode.NOT_FOUND_ERROR);
        // 判断接口是否关闭
        ThrowUtils.throwIf(oldInterfaceInfo.getStatus().equals(InterfaceInfoStatusEnum.OFFLINE), ErrorCode.NOT_FOUND_ERROR,"接口已关闭");
        
        // 获取用户的key
        User loginUser = userService.getLoginUser(request);
        String accessKey = loginUser.getAccessKey();
        String secretKey = loginUser.getSecretKey();
        // 新建接口客户端对象，专门给用户调用
        XjApiClient tempClient = new XjApiClient(accessKey,secretKey);
        // 解析前端传来的测试参数
        Gson gson = new Gson();
        com.xj.xjapiclientsdk.model.User user = gson.fromJson(userRequestParams, com.xj.xjapiclientsdk.model.User.class);
        // 调用接口
        String userNameByPost = tempClient.getUserNameByPost(user);
        // 返回结果对象
        return ResultUtils.success(userNameByPost);
    }

}
