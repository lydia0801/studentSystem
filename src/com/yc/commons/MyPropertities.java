package com.yc.commons;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
//继承Properties类，这样就自定义的Myproperties就有了Properties功能
//在设计成单例模式
public class MyPropertities extends Properties{

	private static MyPropertities myPropertities;
	
	private MyPropertities() throws IOException{
		InputStream  iss =MyPropertities.class.getClassLoader().getResourceAsStream("db.properties");
		this.load(iss);
	}
	
	public static MyPropertities getInstance() throws IOException{
		if(null==myPropertities){
			myPropertities=new MyPropertities();
		}
		return myPropertities;
	}
}
