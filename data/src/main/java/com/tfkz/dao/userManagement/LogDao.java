package com.tfkz.dao.userManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tfkz.mapper.userManagement.LogMapper;
import com.tfkz.model.userManagement.Log;

@Repository
public class LogDao {
	
	@Autowired
    private LogMapper logMapper;
    /***************注解*****************/
    public boolean insertLog(Log log){
        return logMapper.insertLog(log)==1?true:false;
    }

}
