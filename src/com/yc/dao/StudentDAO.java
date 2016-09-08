package com.yc.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yc.commons.DBHelper;

public class StudentDAO {

	DBHelper db = new DBHelper();
	/**
	 * 学生登录操作
	 * @param stuName
	 * @param stuPwd
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	public Map<String, Object> login(int stuID,String stuPwd) throws SQLException, IOException {
		
		Map<String, Object> map = new HashMap<String, Object>();
		String sql ="select stu_id,stu_name,stu_sex,stu_pwd,stu_addr,stu_img,to_char(school_date,'yyyy-MM-dd') school_date," +
				"status from student where  stu_id =?  and stu_pwd =?";
		List<Object> params  =new ArrayList<Object>();
		params.add(stuID);
		params.add(stuPwd);
		map = db.findSingleObject(sql, params);
		return map;
	}
	
	/**
	 * 学生信息注册
	 * @return
	 * @throws IOException 
	 * @throws SQLException 
	 */
	public boolean   registerStudent(List<Object> params) throws SQLException, IOException{
		String sql ="insert  into student values(seq_stu_id.nextval,?,?,default,?,?,?,sysdate,null,default)";
		//插入基本信息
		int i =db.doUpdate(sql, params);
		if(i>0){
			return true;
		}else{
			return false;
		}
		
	}
	
	/**
	 * 查看所有
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	public  List<Map<String, Object>>  findAllStudent() throws SQLException, IOException{
		String sql ="select stu_id,stu_name,stu_sex,stu_pwd,stu_addr,stu_img,to_char(school_date,'yyyy-MM-dd') school_date," +
				"status,class_name ,s.class_id from  student s inner join classinfo c on s.class_id =c.class_id";
		return db.findMultiObject(sql, null);
	}
	
	/**
	 * 根据学号查询
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	public  Map<String, Object>  findStudentByID(int stu_id) throws SQLException, IOException{
		String sql ="select stu_id,stu_name,stu_sex,stu_pwd,stu_addr,stu_img,to_char(school_date,'yyyy-MM-dd') school_date," +
				"status,class_name ,s.class_id from  student s inner join classinfo c on s.class_id =c.class_id  and  s.stu_id = ?";
		List<Object> params  =new ArrayList<Object>();
		params.add(stu_id);
		return db.findSingleObject(sql, params);
	} 
	
	/**
	 * 根据班级信息查看学生
	 * @param className
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	public  List<Map<String, Object>>  findStudentByClassName(String className) throws SQLException, IOException{
		String sql ="select stu_id,stu_name,stu_sex,stu_pwd,stu_addr,stu_img,to_char(school_date,'yyyy-MM-dd') school_date," +
				"status,class_name ,s.class_id from student s inner join classinfo c on s.class_id =c.class_id  and  c.class_name = ?";
		List<Object> params  =new ArrayList<Object>();
		params.add(className);
		return db.findMultiObject(sql, params);
	} 
	
}
