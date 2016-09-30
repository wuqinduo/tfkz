package com.tfkz.model.userManagement;

import com.alibaba.fastjson.JSON;

public class Admin {
	
	private int id;
    private String username;
    private String password;
    
	public Admin() {
		
	}
	public Admin(int id, String username, String password) {
		this.id = id;
		this.username = username;
		this.password = password;
	}
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
	@Override
	public String toString() {
		return "Admin [id=" + id + ", username=" + username + ", password="
				+ password + "]";
	}
	
	/*-------------------公有方法-----------------------*/
	/**
	 * 增加了两个方法，
	 * 			一个是将当前实例转化为json串，
	 * 			一个是将json串转化为Admin；
	 * 			这是因为cookie的传输只能传递字符串而不能传递对象。
	 * @param jsonStr
	 * @return
	 */
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
