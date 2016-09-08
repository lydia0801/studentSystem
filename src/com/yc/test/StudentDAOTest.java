package com.yc.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.yc.dao.StudentDAO;

public class StudentDAOTest {
	StudentDAO  dao =new StudentDAO();

	@Before
	public void setUp() throws Exception {
		 
	}

	@After
	public void tearDown() throws Exception {
	}

	@Ignore
	public void testLogin() throws SQLException, IOException {
		System.out.println( dao.login(1000, "aaaaa"));
	}

	@Ignore
	public void testRegisterStudent() throws SQLException, IOException {
		 List<Object> params = new ArrayList<Object>();
		 params.add(2);
		 params.add("张三");
		 params.add("男");
		 params.add("湖南衡阳");
		 params.add(new File("D:\\1.gif"));
		 dao.registerStudent(params);
	}

	@Test
	public void testFindAllStudent() throws SQLException, IOException {
		System.out.println(dao.findAllStudent().size());
	}

	@Ignore
	public void testFindStudentByID() throws SQLException, IOException {
		System.out.println(dao.findStudentByID(1000).size());
	}

	@Ignore
	public void testFindStudentByClassName() throws SQLException, IOException {
		System.out.println(dao.findStudentByClassName("计科1401").size());
	}

}
