package com.tfkz.model.userManagement;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 管理员 
 */
public class Admin  implements Serializable{
	
	private static final long serialVersionUID = 7149009421720474527L;
	
    private int id;
    private String username;
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    //将json串转为Admin
    public static Admin parseJsonToAdmin(String jsonStr){
        try {
            return JSON.parseObject(jsonStr, Admin.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    //将当前实例转化为json串
    public String toJson(){
        return JSON.toJSONString(this);
    }
}