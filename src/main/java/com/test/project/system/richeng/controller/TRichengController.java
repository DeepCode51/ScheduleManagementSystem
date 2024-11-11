package com.test.project.system.richeng.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.test.framework.aspectj.lang.enums.BusinessType;
import com.test.project.system.richeng.domain.TRicheng;
import com.test.project.system.richeng.service.ITRichengService;
import com.test.project.system.user.domain.User;
import com.test.project.system.user.service.IUserService;
import com.test.framework.web.controller.BaseController;
import com.test.framework.web.domain.AjaxResult;
import com.test.common.utils.poi.ExcelUtil;
import com.test.framework.web.page.TableDataInfo;

/**
 * 日程管理Controller
 * 
 * @author test
 * @date 2022-04-15
 */
@Controller
@RequestMapping("/system/richeng")
public class TRichengController extends BaseController
{
    private String prefix = "system/richeng";

    @Autowired
    private IUserService userService;
    @Autowired
    private ITRichengService tRichengService;

    @GetMapping()
    public String richeng()
    {
        return prefix + "/richeng";
    }
    @GetMapping("/{type}")
    public String type(@PathVariable String type,ModelMap mmap)
    {
    	mmap.put("user", getLoginName());
        return prefix + "/"+type;
    }
    /**
     * 查询日程管理列表
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(TRicheng tRicheng)
    {
        startPage();
//        if(getSysUser().getRoleId() ==1) {
        	tRicheng.setCreateBy(getLoginName());
//        }
        List<TRicheng> list = tRichengService.selectTRichengList(tRicheng);
        return getDataTable(list);
    }
    @PostMapping("/mlist")
    @ResponseBody
    public TableDataInfo mlist(TRicheng tRicheng)
    {
        startPage();
//        if(getSysUser().getRoleId() ==1) {
        	tRicheng.setToUser(getLoginName());
//        }
        List<TRicheng> list = tRichengService.selectTRichengList(tRicheng);
        return getDataTable(list);
    }

    /**
     * 导出日程管理列表
     */
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(TRicheng tRicheng)
    {
        List<TRicheng> list = tRichengService.selectTRichengList(tRicheng);
        ExcelUtil<TRicheng> util = new ExcelUtil<TRicheng>(TRicheng.class);
        return util.exportExcel(list, "richeng");
    }

    /**
     * 新增日程管理
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
    	List<User> userList = userService.selectUserList(new User());
    	List<User> collect = new ArrayList<User>();
    	if(getSysUser().getRoleId() == 1) {
    		collect = userList.stream().filter(e->e.getRoleId()>1).collect(Collectors.toList());
    	}
    	if(getSysUser().getRoleId() == 2) {
    		collect = userList.stream().filter(e->e.getRoleId()>2).collect(Collectors.toList());
    	} 
    	mmap.put("list", collect);
        return prefix + "/add";
    }

    /**
     * 新增保存日程管理
     */
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(TRicheng tRicheng)
    {
    
    	tRicheng.setFabuzhe(getSysUser().getRoleId()+"");
    	tRicheng.setState("11");
    	if(getSysUser().getRoleId() == 3 || tRicheng.getToUser() == null ||  tRicheng.getToUser().length() == 0) {
    		tRicheng.setToUser(getLoginName());
    	}
        return toAjax(tRichengService.insertTRicheng(tRicheng));
    }
    
    private static long ctime = System.currentTimeMillis();
    @PostMapping("/getm")
    @ResponseBody
    public AjaxResult getm()
    {
    	TRicheng tRicheng = new TRicheng();
    	tRicheng.setToUser(getLoginName());
    	List<TRicheng> selectTRichengList = tRichengService.selectTRichengList(tRicheng);
    	List<TRicheng> collect = selectTRichengList.stream().filter(e->{
    		if(e.getBeginTime().getTime()>ctime && e.getBeginTime().getTime()<ctime+10*1000) {
    			return true;
    		}
    		return false;
    	}).collect(Collectors.toList());
    	ctime  = System.currentTimeMillis();
    	if(collect.size()>0) {
    		return AjaxResult.success(collect.get(0));
    	}else {
    		return AjaxResult.error();
    	} 
    }


    /**
     * 修改日程管理
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        TRicheng tRicheng = tRichengService.selectTRichengById(id);
        mmap.put("tRicheng", tRicheng);
        return prefix + "/edit";
    }

    /**
     * 修改保存日程管理
     */
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(TRicheng tRicheng)
    {
        return toAjax(tRichengService.updateTRicheng(tRicheng));
    }

    /**
     * 删除日程管理
     */
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(tRichengService.deleteTRichengByIds(ids));
    }
}
