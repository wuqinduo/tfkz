package com.tfkz.service.userManagement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfkz.dao.userManagement.AdminDao;
import com.tfkz.model.userManagement.Admin;

@Service
public class AdminService {

	 @Autowired
	    private AdminDao adminDao;
	    
	    public boolean register(Admin admin){
	        return adminDao.register(admin);
	    }
	    
	    public Admin login(String username, String password) {
	        return adminDao.login(username, password);
	    }
	    /***********以下方法是为了测试mybatis中使用xml**********/
	    public List<Admin> findAdmin(String username, String password, int start, int limit){
	        return adminDao.findAdmin(username, password, start, limit);
	    }
	    
	    public Admin insertAdminWithBackId(Admin admin){
	        int record = adminDao.insertAdminWithBackId(admin);
	        if(record==1){
	            return admin;//这时的admin已经被赋予主键了
	        }
	        return null;
	    }
}
