package com.yc.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.yc.commons.DBHelper;

public class ClassInfoDAO {

	 DBHelper db = new DBHelper();
	/**
	 * 查看所有的班级信息
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	public List<Map<String, Object>>  findAllClassInfo() throws SQLException, IOException{
		String sql = "select * from classInfo";
	
		List<Map<String, Object>> list = db.findMultiObject(sql, null);
		return list;
	}
	
	/**
	 * 添加班级信息
	 * @param className
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	public  boolean addClassInfo(String className) throws SQLException, IOException{
		String sql="insert into classInfo  values(seq_class_id.nextval,?)";
		List<Object> params  =new ArrayList<Object>();
		params.add(className);
		int i =db.doUpdate(sql, params);
		if(i>0){
			return true;
		}
		return false;
	}
	
	public  Map<String, Object>   findClassInfoByName(String className) throws SQLException, IOException{
		String sql = "select * from classInfo  where  class_name =?";
		List<Object> params  =new ArrayList<Object>();
		params.add(className);
		List<Map<String, Object>> list = db.findMultiObject(sql, params);
		if(null!=list&&list.size()>0){
			return list.get(0);
		}
		return null;
	}
}
