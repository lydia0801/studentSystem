package com.yc.ui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.graphics.Rectangle;

import com.yc.commons.LogUtil;
import com.yc.dao.StudentDAO;
import com.yc.dao.TeacherDAO;
import com.yc.utils.SwtUtil;
import com.yc.utils.TeacherUtil;

import org.eclipse.wb.swt.SWTResourceManager;
 

public class Login {

	protected Shell shell;
	private Text text_name;
	private Text text_pwd;
	Label label_name;
	StudentDAO studentDAO = new StudentDAO();
	TeacherDAO teacherDAO = new TeacherDAO();

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Login window = new Login();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		SwtUtil.centerShell(display,shell);
		shell.open();
		shell.pack();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();

		shell.setBackgroundMode(SWT.INHERIT_DEFAULT);// 窗体上的控件背景透明
		shell.setSize(800, 414);
		shell.setText("用户登录");
		// 主窗体居中
		// Dimension：标出尺寸 Toolkit：工具包
		Dimension dem = Toolkit.getDefaultToolkit().getScreenSize();
		// 窗口剧中显示
		shell.setLocation((dem.width - shell.getSize().x) / 2,
				(dem.height - shell.getSize().y) / 2);
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setBackgroundImage(SWTResourceManager.getImage(Login.class, "/images/login_bg.png") );
		composite.setBounds(0, 0, 800, 414);
		 
		Label lblNewLabel = new Label(composite, SWT.NONE);
		lblNewLabel.setBounds(481, 147, 61, 17);
		lblNewLabel.setText("\u7528\u6237\u7C7B\u578B\uFF1A");

		final Button btn_teacher = new Button(composite, SWT.RADIO);
		btn_teacher.setSelection(true);
		btn_teacher.setBounds(560, 147, 61, 17);
		btn_teacher.setText("\u8001\u5E08");

		final Button btn_student = new Button(composite, SWT.RADIO);
		btn_student.setBounds(641, 147, 61, 17);
		btn_student.setText("\u5B66\u751F");

		Label lblNewLabel_1 = new Label(composite, SWT.RIGHT);
		lblNewLabel_1.setBounds(481, 192, 61, 17);
		lblNewLabel_1.setText("\u7528\u6237\u540D\uFF1A");

		Label lblNewLabel_2 = new Label(composite, SWT.RIGHT);
		lblNewLabel_2.setBounds(481, 234, 61, 17);
		lblNewLabel_2.setText("\u5BC6\u7801\uFF1A");

		text_name = new Text(composite, SWT.BORDER);
		// 当学生进行登录时，判断用户名文本框失去焦点时验证数据格式
		text_name.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String name =text_name.getText();
				// 学生  只能输入数字
				if(btn_student.getSelection()){
					if(name.matches("^[0-9]{3,15}$")){
						label_name.setText("*");
					}else{
						label_name.setText("学号不合法！！");
					}
				}
				
				//老师登录
				if(btn_teacher.getSelection()){
					if(name.matches("[\u4E00-\u9FA5]{2,12}")){  //只能输入汉字  长度为2,12之间
						label_name.setText("*");
					}else{
						label_name.setText("用户名不合法！！");
					}
				}
			}
		});
		text_name.setBounds(560, 186, 128, 23);

		text_pwd = new Text(composite, SWT.BORDER | SWT.PASSWORD);
		text_pwd.setBounds(560, 231, 128, 23);

		Button btnNewButton = new Button(composite, SWT.TOGGLE);
		btnNewButton.setImage(SWTResourceManager.getImage(Login.class, "/images/login_btn.png"));
		// 用户登录
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//获取用户名和密码
				String name =text_name.getText();
				String pwd = text_pwd.getText();
				//检查用户名是否合法,不合法无法进行登录操作
				if(btn_student.getSelection()){
					if(!name.matches("^[0-9]{3,15}$")){
						return ;
					}
				}
				if(btn_teacher.getSelection()){
					if(!name.matches("^[\u4E00-\u9FA5]{2,12}$")){
						return ;
					}
				}
				
				
				//学生登录
				if(btn_student.getSelection()){
					 try {
						Map<String, Object> map =studentDAO.login(Integer.parseInt(name) ,pwd  );
						if(null!=map&&map.size()>0){
							LogUtil.logger.info(map.get("STU_NAME")+"登录成功！！！");
							
							shell.dispose();
							StudentMain main = new StudentMain();
							main.open();
						}else{
							LogUtil.logger.error(name+"登录失败");
							SwtUtil.showMessageBox(shell, "错误提示", "登录失败");
						}
						
					} catch (NumberFormatException e1) {
						LogUtil.logger.error(e1.getMessage());
					} catch (SQLException e1) {
						LogUtil.logger.error(e1.getMessage());
					} catch (IOException e1) {
						LogUtil.logger.error(e1.getMessage());
					}
				}
				
				//教师登录
				if(btn_teacher.getSelection()){
					try {
						Map<String, Object> map =teacherDAO.login(name, pwd);
						
						if(null!=map&&map.size()>0){
							LogUtil.logger.info(map.get("TEA_NAME")+"登录成功");
							TeacherUtil.map =map;
							shell.dispose();
							Main main = new Main();
							main.open();
						}else{
							LogUtil.logger.error(name+"登录失败");
							SwtUtil.showMessageBox(shell, "错误提示", "登录失败");
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
				}
				 
				
			}
		});
		btnNewButton.setBounds(481, 285, 88, 27);

		Button btnNewButton_1 = new Button(composite, SWT.TOGGLE);
		btnNewButton_1.setImage(SWTResourceManager.getImage(Login.class, "/images/login_btn2.png"));
		// 退出系统
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//记事本相同
				boolean flag =MessageDialog.openConfirm(shell, "温馨提示", "您确定要退出系统?");	
				if(flag){
					System.exit(0);
				}
				
			}
		});
		 
		btnNewButton_1.setBounds(600, 285, 88, 27);

		label_name = new Label(composite, SWT.NONE);
		label_name.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		label_name.setBounds(694, 192, 96, 17);

	}
	
	
}
