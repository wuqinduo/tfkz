package com.tfkz.web.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tfkz.model.userManagement.Admin;
import com.tfkz.service.userManagement.AdminService;



/**
 * adminController
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;

	/**
	 * 管理员注册
	 */
	@ResponseBody
	@RequestMapping("/register")
	public boolean register(@RequestParam("username") String username,
			@RequestParam("password") String password) {
		Admin admin = new Admin();
		admin.setUsername(username);
		admin.setPassword(password);

		boolean isRegisterSuccess = adminService.registerLog(admin);

		return isRegisterSuccess;
	}

	/**
	 * 管理员登录
	 */
	@RequestMapping("/login")
	public ModelAndView login(@RequestParam("username") String username,
			@RequestParam("password") String password,
			HttpServletResponse response) {
		Admin admin = adminService.login(username, password);

		ModelAndView modelAndView = new ModelAndView();
		if (admin == null) {
			modelAndView.addObject("message", "用户不存在或者密码错误！请重新输入");
			modelAndView.setViewName("error");
		} else {
			modelAndView.addObject("admin", admin);
			modelAndView.setViewName("userinfo");
		}

		//AdminCookieUtil.addLoginCookie(admin, response);

		return modelAndView;
	}

	/***************************** mybatis xml方式解决的问题 *******************************/
	/**
	 * 根据username或password查找List<Admin>
	 */
	@ResponseBody
	@RequestMapping("/findAdmin")
	public List<Admin> findAdmin(
			@RequestParam(value = "username", required = false) String username,
			@RequestParam(value = "password", required = false) String password,
			@RequestParam("start") int start, @RequestParam("limit") int limit,
			HttpServletRequest request) {
		/*
		 * Admin admin = AdminCookieUtil.getLoginCookie(request);
		 * 
		 * if(admin == null ){ //未登录 return null ; }
		 */

		List<Admin> adminList = adminService.findAdmin(username, password,
				start, limit);
		return adminList;
	}

	/**
	 * 插入一个用户并返回主键 注意：get请求也会自动装配（即将前台传入的username和password传入admin）
	 */
	@ResponseBody
	@RequestMapping("/insert")
	public Admin insertAdminWithBackId(Admin admin) {
		return adminService.insertAdminWithBackId(admin);
	}

	/************************* guava cache ******************************/
	/**
	 * 根据username查找List<Admin>
	 */
	@ResponseBody
	@RequestMapping("/findAdminByUsername")
	public List<Admin> findAdminByUserName(
			@RequestParam(value = "username") String username) {

		List<Admin> adminList = adminService.findByUsername(username);
		return adminList;
	}

	@ResponseBody
	@RequestMapping("/findAdminList")
	public List<Admin> findAdminList(
			@RequestParam(value = "username") String username,
			@RequestParam(value = "password", required = false) String password,
			@RequestParam("start") int start, @RequestParam("limit") int limit) {

		List<Admin> adminList = adminService.findAdminList(username, password,
				start, limit);
		return adminList;
	}
	
	/*************************redis******************************/
    /**
     * 根据id查找Admin
     */
    @ResponseBody
    @RequestMapping("/findAdminByIdFromRedis")
    public Admin findAdminByIdFromRedis(@RequestParam(value="id") int id){
        
        return adminService.findAdminByIdFromRedis(id);
    }
    
    /**
     * 根据id查找Admin
     */
    @ResponseBody
    @RequestMapping("/findAdminByIdFromRedisHash")
    public Admin findAdminByIdFromRedisHash(@RequestParam(value="id") int id){
        
        return adminService.findAdminByIdFromRedisHash(id);
    }
}
