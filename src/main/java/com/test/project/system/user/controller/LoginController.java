package com.test.project.system.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.test.common.exception.user.UserBlockedException;
import com.test.common.exception.user.UserDeleteException;
import com.test.common.exception.user.UserNotExistsException;
import com.test.common.utils.ServletUtils;
import com.test.common.utils.StringUtils;
import com.test.framework.config.TestConfig;
import com.test.framework.shiro.service.PasswordService;
import com.test.framework.web.controller.BaseController;
import com.test.framework.web.domain.AjaxResult;
import com.test.project.system.user.domain.User;
import com.test.project.system.user.domain.UserStatus;
import com.test.project.system.user.service.IUserService;

/**
 * 登录验证
 * 
 * @author test
 */
@Controller
public class LoginController extends BaseController
{
	 @Autowired
	    private IUserService userService;
	@Autowired
    private TestConfig testConfig;
	@Autowired
    private PasswordService passwordService;
    @GetMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response,ModelMap mmap)
    {
    	 mmap.put("sysName", testConfig.getName());
        // 如果是Ajax请求，返回Json字符串。
        if (ServletUtils.isAjaxRequest(request))
        {
            return ServletUtils.renderString(response, "{\"code\":\"1\",\"msg\":\"未登录或登录超时。请重新登录\"}");
        }

        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public AjaxResult ajaxLogin(String username, String password, Boolean rememberMe)
    {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe);
        Subject subject = SecurityUtils.getSubject();
        try
        {
            subject.login(token);
            return success();
        }
        catch (AuthenticationException e)
        {
            String msg = "用户或密码错误";
            if (StringUtils.isNotEmpty(e.getMessage()))
            {
                msg = e.getMessage();
            }
            return error(msg);
        }
    }
    @GetMapping("/ajaxlogin")
    @ResponseBody
    public AjaxResult ajaxLogin1(String username, String password)
    {
    	 // 查询用户信息
        User user = userService.selectUserByLoginName(username);
        if (user == null)
        {
            throw new UserNotExistsException();
        }
        
        if (UserStatus.DELETED.getCode().equals(user.getDelFlag()))
        {
            throw new UserDeleteException();
        }
        
        if (UserStatus.DISABLE.getCode().equals(user.getStatus()))
        {
            throw new UserBlockedException();
        }

        passwordService.validate(user, password);
        return AjaxResult.success(user);
    }
    @GetMapping("/unauth")
    public String unauth()
    {
        return "error/unauth";
    }
}
