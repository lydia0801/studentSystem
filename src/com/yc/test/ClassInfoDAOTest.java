package com.yc.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.yc.dao.ClassInfoDAO;

public class ClassInfoDAOTest {

	ClassInfoDAO dao = new ClassInfoDAO();
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFindAllClassInfo() throws SQLException, IOException {
		System.out.println(dao.findAllClassInfo().size());
	}

}
