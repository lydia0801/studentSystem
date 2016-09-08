package com.yc.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yc.commons.DBHelper;

public class TeacherDAO {

	/**
	 * 教师登录
	 * @param name ：教师名称
	 * @param pwd：登录密码
	 * @return
	 * @throws IOException 
	 * @throws SQLException 
	 */
	public  Map<String, Object> login (String name,String pwd) throws SQLException, IOException{
		DBHelper db = new DBHelper();
		Map<String, Object>  map  = new HashMap<String, Object>();
		String sql ="select  t.tea_id,t.tea_name,t.tea_pwd ,t.tid ,tp.tname  from teacher t inner join  typeinfo tp  on   t.tid = tp.tid and t.tea_name=? and t.tea_pwd=?";
		List<Object> params = new ArrayList<Object>();
		params.add(name);
		params.add(pwd);
		map = db.findSingleObject(sql, params);
		return map;
	}
	
	
	public  boolean  updatePwd(){
		
		return false;
	}
}
