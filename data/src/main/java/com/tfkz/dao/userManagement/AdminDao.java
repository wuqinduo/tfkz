package com.tfkz.dao.userManagement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tfkz.mapper.userManagement.AdminMapper;
import com.tfkz.model.userManagement.Admin;

@Repository
public class AdminDao {

	@Autowired
    private AdminMapper adminMapper;
    
    public boolean register(Admin admin){
        return adminMapper.insertAdmin(admin)==1?true:false;
    }
    
    public Admin login(String username ,String password){
        return adminMapper.selectAdmin(username, password);
    }
    public List<Admin> findAdmin(String username, String password, int start, int limit){
        return adminMapper.getAdminByConditions(username, password, start, limit);
    }
    
    public int insertAdminWithBackId(Admin admin){
        return adminMapper.insertAdminWithBackId(admin);
    }
    
    /******************guava cache********************/
    public List<Admin> getUserByName(String username){
        return adminMapper.getUserByName(username);
    }
    
    /******************memcached********************/
    public Admin getUserById(int id){
            return adminMapper.selectById(id);
    }
    
}
