package com.gigold.pay.ifsys.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gigold.pay.framework.base.DomainFactory;
import com.gigold.pay.framework.bootstrap.SystemPropertyConfigure;
import com.gigold.pay.framework.core.SysCode;
import com.gigold.pay.framework.web.BaseController;
import com.gigold.pay.ifsys.bo.InterFaceField;
import com.gigold.pay.ifsys.bo.InterFaceInfo;
import com.gigold.pay.ifsys.bo.InterFacePro;
import com.gigold.pay.ifsys.bo.InterFaceSysTem;
import com.gigold.pay.ifsys.bo.UserInfo;
import com.gigold.pay.ifsys.service.InterFaceFieldService;
import com.gigold.pay.ifsys.service.InterFaceProService;
import com.gigold.pay.ifsys.service.InterFaceService;
import com.gigold.pay.ifsys.service.InterFaceSysService;
import com.gigold.pay.ifsys.service.UserInfoService;
import com.gigold.pay.ifsys.util.Constant;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Controller
public class InterFaceController extends BaseController{
    @Autowired
    InterFaceService interFaceService;
    @Autowired
    UserInfoService userInfoService;
    @Autowired
    InterFaceSysService interFaceSysService;
    @Autowired
    InterFaceProService interFaceProService;
    @Autowired
    InterFaceFieldService interFaceFieldService;

    /**
     * 根据Id获取接口明细信息
     * 
     * @param id
     * @return
     */
    @RequestMapping(value = "/queryInterFaceById")
    public @ResponseBody InterFaceByIdResponseDto getInterFaceById(@RequestBody InterFaceRequestDto qdto) {
        //debug("调用getInterFaceById：");
        InterFaceInfo interFaceInfo = interFaceService.getInterFaceById(qdto.getInterFaceInfo());
       
        InterFaceByIdResponseDto dto = new InterFaceByIdResponseDto();
       if (interFaceInfo != null) {
           InterFaceSysTem interFaceSysTem= DomainFactory.getInstance().getDomain(InterFaceSysTem.class);
           interFaceSysTem.setId(interFaceInfo.getIfSysId());
           interFaceSysTem=interFaceSysService.getSysInfoById(interFaceSysTem);
           dto.setSystem(interFaceSysTem);
           
           InterFacePro interFacePro= DomainFactory.getInstance().getDomain(InterFacePro.class);
           interFacePro.setId(interFaceInfo.getIfProId());
           interFacePro=interFaceProService.getProInfoById(interFacePro);
           dto.setPro(interFacePro);
           
           UserInfo userInfo= DomainFactory.getInstance().getDomain(UserInfo.class);
           userInfo.setId(interFaceInfo.getUid());
           userInfo=userInfoService.getUser(interFaceInfo.getUid());
           dto.setUserInfo(userInfo);
           InterFaceField interFaceField= DomainFactory.getInstance().getDomain(InterFaceField.class);
           interFaceField.setIfId(interFaceInfo.getId());
           List<InterFaceField> fieldList=interFaceFieldService.getFieldByIfId(interFaceField);
           dto.setFieldList(fieldList);
            dto.setInterFaceInfo(interFaceInfo);
            dto.setRspCd(SysCode.SUCCESS);
        } else {
            dto.setRspCd(CodeItem.IF_FAILURE);
        }
        return dto;
    }
    
    @RequestMapping("/queryByCondition")
    public @ResponseBody InterFacePageResponseDto queryInterFaceByPage(@RequestBody InterFaceFuzzyQueryRequestDto qdto) {
    	int pageSize=Integer.parseInt(SystemPropertyConfigure.getProperty(Constant.SYSTEMPARAM_PAGESIZE, String.valueOf(Constant.PAGE_SIZE)));
		PageHelper.startPage(qdto.getPageInfo().getPageNum(), pageSize);
        List<InterFaceInfo> list = interFaceService.queryInterFaceByPage(qdto.getInterFaceInfo());
        debug("传入的参数：" + qdto.getPageInfo().getPageNum() + "====" + qdto.getPageInfo().getPageSize());
        InterFacePageResponseDto dto = new InterFacePageResponseDto();
        if (list != null) {
            PageInfo<InterFaceInfo> pageInfo = new PageInfo<InterFaceInfo>(list);
            dto.setPageInfo(pageInfo);
            dto.setRspCd(SysCode.SUCCESS);
        } else {
            dto.setRspCd(CodeItem.IF_FAILURE);
        }
        return dto;
    }
    /**
     * 分页查询
     * 
     * @param qdto
     * @return
     */

    @RequestMapping("/getInterFaceByPage")
    public @ResponseBody InterFacePageResponseDto getAllInterFace(@RequestBody InterFacePageRequestDto qdto) {
        debug("调用getInterFaceByPage：");
        int pageSize=Integer.parseInt(SystemPropertyConfigure.getProperty(Constant.SYSTEMPARAM_PAGESIZE, String.valueOf(Constant.PAGE_SIZE)));
		PageHelper.startPage(qdto.getPageInfo().getPageNum(), pageSize);
        List<InterFaceInfo> list = interFaceService.getAllInterFaceByPage();
        debug("传入的参数：" + qdto.getPageInfo().getPageNum() + "====" + qdto.getPageInfo().getPageSize());
        InterFacePageResponseDto dto = new InterFacePageResponseDto();
        if (list != null) {
            PageInfo<InterFaceInfo> pageInfo = new PageInfo<InterFaceInfo>(list);
            dto.setPageInfo(pageInfo);
            dto.setRspCd(SysCode.SUCCESS);
        } else {
            dto.setRspCd(CodeItem.IF_FAILURE);
        }
        return dto;

    }

    /**
     * 新增接口纪录
     * 
     * @param interFaceInfo
     * @return
     */

    @RequestMapping("/addInterface")
    public @ResponseBody InterFaceResponseDto addInterface(@RequestBody InterFaceRequestDto qdto,HttpSession session) {
        InterFaceInfo interFaceInfo=qdto.getInterFaceInfo();
        UserInfo user=(UserInfo)session.getAttribute(SystemPropertyConfigure.getLoginKey());
       if(user!=null){
           interFaceInfo.setIfCreateBy(user.getId());
       }
        interFaceInfo.setIfCreateTime(new Timestamp((new Date()).getTime()));
        boolean flag = interFaceService.addInterFace(interFaceInfo);
        InterFaceResponseDto dto = new InterFaceResponseDto();
        if (flag) {
            dto.setRspCd(SysCode.SUCCESS);
            dto.setInterFaceInfo(interFaceInfo);

        } else {
            dto.setRspCd(CodeItem.IF_FAILURE);
        }

        return dto;

    }

    /**
     * 删除接口
     * 
     * @param id
     * @return
     */
    @RequestMapping("/deleteInterFaceById")
    public @ResponseBody InterFaceResponseDto deleteInterFace(@RequestBody InterFaceRequestDto qdto) {
        boolean flag = interFaceService.deleteInterFaceById(qdto.getInterFaceInfo());
        InterFaceResponseDto dto = new InterFaceResponseDto();
        if (flag) {
            dto.setRspCd(SysCode.SUCCESS);

        } else {
            dto.setRspCd(CodeItem.IF_FAILURE);
        }
        return dto;

    }

    /**
     * 修改接口
     * 
     * @param interFaceInfo
     * @return
     */
    @RequestMapping("/updateInterFace")
    public @ResponseBody InterFaceResponseDto updateInterFace(@RequestBody InterFaceRequestDto qdto) {
        InterFaceInfo interFaceInfo=qdto.getInterFaceInfo();
        boolean flag = interFaceService.updateInterFace(interFaceInfo);
        InterFaceResponseDto dto = new InterFaceResponseDto();
        if (flag) {
            dto.setRspCd(SysCode.SUCCESS);   
            dto.setInterFaceInfo(interFaceInfo);
        } else {
            dto.setRspCd(CodeItem.IF_FAILURE);
        }
        return dto;

    }

}
