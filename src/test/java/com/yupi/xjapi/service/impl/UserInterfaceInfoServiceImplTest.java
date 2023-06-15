package com.yupi.xjapi.service.impl;

import com.xj.xjapicommon.service.InnerUserInterfaceInfoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;


@SpringBootTest
public class UserInterfaceInfoServiceImplTest {
    @Resource
    private InnerUserInterfaceInfoService userInterfaceInfoService;
    
   @Test
    public void invokeCount() {
       boolean b = userInterfaceInfoService.invokeCount(1L, 1L);
       Assertions.assertTrue(b);
   }
}