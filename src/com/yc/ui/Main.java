package com.yc.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;

import com.yc.commons.LogUtil;
import com.yc.dao.ClassInfoDAO;
import com.yc.dao.StudentDAO;
import com.yc.utils.StudentUtil;
import com.yc.utils.SwtUtil;
import com.yc.utils.TeacherUtil;

import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class Main {

	protected Shell shell;
	private StackLayout stackLayout;
	private Composite com_findStu;
	private Composite com_addStu;
	private Composite com_main;
	private Composite com_classinfo;
	private Text text;
	private Text text_2;
	Button btn_upload;
	Label label_name;
	Combo combo_1;
	StudentDAO studentDao = new StudentDAO();
	ClassInfoDAO classDao = new ClassInfoDAO();

	MenuItem mitem_reg;
	MenuItem mitem_update;
	MenuItem mitem_find;
	private Table table;
	private Text text_1;
	Combo combo;
	Label lab_find;
	Label lab_class;
	private Text text_3;
	private Table table_1;
	private Text text_4;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Main window = new Main();
			// Main界面必须通过管理员正确登录才能访问的
			// if(null!=TeacherUtil.map&&TeacherUtil.map.size()>0){ //管理员正确登录
			window.open();
			// }else{//非法访问
			// Login login =new Login();
			// login.open();

			// }
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
		SwtUtil.centerShell(display, shell);
		shell.open();
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
		shell.setBackground(SWTResourceManager.getColor(0, 255, 255));
		shell.setSize(800, 518);
		shell.setText("SWT Application");
		stackLayout = new StackLayout();
		shell.setLayout(stackLayout);// 将主窗体设置为堆栈式布局

		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);

		MenuItem mntmNewSubmenu = new MenuItem(menu, SWT.CASCADE);
		mntmNewSubmenu.setImage(SWTResourceManager.getImage(Main.class,
				"/images/add1.png"));
		mntmNewSubmenu.setText("\u5B66\u751F\u5B66\u7C4D\u7BA1\u7406");

		Menu menu_1 = new Menu(mntmNewSubmenu);
		mntmNewSubmenu.setMenu(menu_1);
		if ("辅导员".equals(TeacherUtil.map.get("TNAME").toString())) {
			mitem_find = new MenuItem(menu_1, SWT.CHECK);
		} else if ("教务人员".equals(TeacherUtil.map.get("TNAME").toString())) {
			mitem_reg = new MenuItem(menu_1, SWT.NONE);
			// 注册
			mitem_update = new MenuItem(menu_1, SWT.NONE);
			mitem_find = new MenuItem(menu_1, SWT.CHECK);
			// 查看学生信息
		} else if ("超级管理员".equals(TeacherUtil.map.get("TNAME").toString())) {
			mitem_reg = new MenuItem(menu_1, SWT.NONE);
			mitem_update = new MenuItem(menu_1, SWT.NONE);
			mitem_find = new MenuItem(menu_1, SWT.CHECK);
		}

		// 根据权限不同创建的菜单项来设置位置，图片，并添加相同的事件
		if (null != mitem_find) {// 查看学生信息
			mitem_find.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					stackLayout.topControl = com_findStu;
					shell.layout();
				}
			});
			mitem_find.setImage(SWTResourceManager.getImage(Main.class,
					"/images/find.png"));
			mitem_find.setText("\u67E5\u770B\u5B66\u751F\u4FE1\u606F");
		}

		if (null != mitem_update) {// 修改学生信息
			mitem_update.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					// 自定义类继承Compsite完成学籍修改的面板相应的操作
					// 实例化面板对象UpdateStudent
					UpdateStudent update = new UpdateStudent(shell, SWT.NONE);
					stackLayout.topControl = update;
					shell.layout();
				}
			});
			mitem_update.setImage(SWTResourceManager.getImage(Main.class,
					"/images/update.png"));
			mitem_update.setText("\u5B66\u7C4D\u66F4\u6539");
		}

		if (null != mitem_reg) {// 学籍注册
			mitem_reg.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					stackLayout.topControl = com_addStu;
					shell.layout();
				}
			});

			mitem_reg.setImage(SWTResourceManager.getImage(Main.class,
					"/images/add.png"));
			mitem_reg.setText("\u5B66\u751F\u6CE8\u518C");
		}

		MenuItem mntmNewSubmenu_2 = new MenuItem(menu, SWT.CASCADE);
		mntmNewSubmenu_2.setText("\u9662\u7CFB\u7BA1\u7406");

		Menu menu_3 = new Menu(mntmNewSubmenu_2);
		mntmNewSubmenu_2.setMenu(menu_3);

		MenuItem menuItem = new MenuItem(menu_3, SWT.NONE);
		menuItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				stackLayout.topControl = com_classinfo;
				shell.layout();
			}
		});
		menuItem.setText("班级管理");

		MenuItem mntmNewSubmenu_1 = new MenuItem(menu, SWT.CASCADE);
		mntmNewSubmenu_1.setText("\u6559\u5E08\u4FE1\u606F\u7BA1\u7406");

		Menu menu_2 = new Menu(mntmNewSubmenu_1);
		mntmNewSubmenu_1.setMenu(menu_2);

		MenuItem mntmNewItem_2 = new MenuItem(menu_2, SWT.NONE);
		mntmNewItem_2.setText("\u6DFB\u52A0\u6559\u5E08\u4FE1\u606F");

		MenuItem mntmNewItem_3 = new MenuItem(menu, SWT.NONE);
		mntmNewItem_3.setText("\u4FEE\u6539\u5BC6\u7801");

		MenuItem mntmNewItem_4 = new MenuItem(menu, SWT.NONE);
		mntmNewItem_4.setText("\u5E2E\u52A9");

		MenuItem mntmNewItem_5 = new MenuItem(menu, SWT.NONE);
		// 退出系统
		mntmNewItem_5.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				boolean flag = MessageDialog.openConfirm(shell, "温馨提示",
						"您确定要退出系统?");
				if (flag) {
					System.exit(0);
				}
			}
		});
		mntmNewItem_5.setText("\u9000\u51FA\u7CFB\u7EDF");

		com_classinfo = new Composite(shell, SWT.NONE);
		com_classinfo.setBackgroundImage(SWTResourceManager.getImage(
				Main.class, "/images/bg.jpg"));

		text_3 = new Text(com_classinfo, SWT.BORDER);
		text_3.setBounds(310, 60, 126, 18);

		Label lblNewLabel_3 = new Label(com_classinfo, SWT.NONE);
		lblNewLabel_3.setBounds(237, 63, 54, 17);
		lblNewLabel_3.setText("班级名称：");

		Button button = new Button(com_classinfo, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					String className = text_3.getText().trim();
					Map<String, Object> map = classDao
							.findClassInfoByName(className);
					if (null != map && map.size() > 0) {
						table_1.removeAll();

						TableItem item = new TableItem(table_1, SWT.NONE);
						item.setText(new String[] {
								map.get("CLASS_ID").toString(),
								map.get("CLASS_NAME").toString() });

					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		button.setBounds(485, 58, 72, 22);
		button.setText("查看班级");

		table_1 = new Table(com_classinfo, SWT.BORDER | SWT.FULL_SELECTION);
		table_1.setBounds(234, 113, 323, 213);
		table_1.setHeaderVisible(true);
		table_1.setLinesVisible(true);

		TableColumn tblclmnNewColumn_7 = new TableColumn(table_1, SWT.NONE);
		tblclmnNewColumn_7.setWidth(124);
		tblclmnNewColumn_7.setText("班级编号");

		TableColumn tblclmnNewColumn_8 = new TableColumn(table_1, SWT.NONE);
		tblclmnNewColumn_8.setWidth(182);
		tblclmnNewColumn_8.setText("班级名称");
		// 默认加载班级信息
		try {
			List<Map<String, Object>> list = classDao.findAllClassInfo();
			if (null != list && list.size() > 0) {
				table_1.removeAll();
				for (Map<String, Object> map : list) {
					TableItem item = new TableItem(table_1, SWT.NONE);
					item.setText(new String[] { map.get("CLASS_ID").toString(),
							map.get("CLASS_NAME").toString() });
				}
			}
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		Label label = new Label(com_classinfo, SWT.NONE);
		label.setText("班级名称：");
		label.setBounds(249, 391, 54, 12);

		text_4 = new Text(com_classinfo, SWT.BORDER);
		text_4.setBounds(324, 388, 126, 18);

		Button button_1 = new Button(com_classinfo, SWT.NONE);
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String className = text_4.getText().trim();
				try {
					Map<String, Object> map = classDao
							.findClassInfoByName(className);
					if (null != map && map.size() > 0) {
						lab_class.setText("班级名已存在");
						return;
					}

					if (classDao.addClassInfo(className)) {
						List<Map<String, Object>> list = classDao
								.findAllClassInfo();
						if (null != list && list.size() > 0) {
							table_1.removeAll();
							for (Map<String, Object> map2 : list) {
								TableItem item = new TableItem(table_1,
										SWT.NONE);
								item.setText(new String[] {
										map2.get("CLASS_ID").toString(),
										map2.get("CLASS_NAME").toString() });
							}
						}

					} else {
						SwtUtil.showMessageBox(shell, "错误提示", "添加失败");
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		button_1.setText("添加班级");
		button_1.setBounds(485, 386, 72, 22);

		lab_class = new Label(com_classinfo, SWT.NONE);
		lab_class.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		lab_class.setBounds(334, 412, 116, 18);

		com_addStu = new Composite(shell, SWT.NONE);
		com_addStu.setBackgroundImage(SWTResourceManager.getImage(Main.class,
				"/images/bg.jpg"));

		Label lblNewLabel = new Label(com_addStu, SWT.NONE);
		lblNewLabel.setBounds(127, 77, 61, 17);
		lblNewLabel.setText("\u5B66\u751F\u59D3\u540D\uFF1A");

		Label lblNewLabel_1 = new Label(com_addStu, SWT.NONE);
		lblNewLabel_1.setBounds(127, 129, 61, 17);
		lblNewLabel_1.setText("\u5B66\u751F\u6027\u522B\uFF1A");

		Label lblNewLabel_2 = new Label(com_addStu, SWT.NONE);
		lblNewLabel_2.setBounds(127, 186, 61, 17);
		lblNewLabel_2.setText("\u5B66\u751F\u73ED\u7EA7\uFF1A");

		text = new Text(com_addStu, SWT.BORDER);
		text.addFocusListener(new FocusAdapter() {

			// 文本框失去焦点时，触发事件，判断文本框中的数据格式是否正确，此文本框只允许输入汉字
			@Override
			public void focusLost(FocusEvent e) {
				// 获取文本框中的名字
				String username = text.getText();
				if (username.matches("[\u4E00-\u9FA5]{2,10}")) {
					label_name.setText("");
				} else {
					label_name.setText("必须输入汉字，长度在2-10之间！！");
				}

			}
		});
		text.setBounds(218, 71, 169, 23);

		final Button btn_sex1 = new Button(com_addStu, SWT.RADIO);
		btn_sex1.setBounds(218, 129, 61, 17);
		btn_sex1.setText("\u7537");

		final Button btn_sex2 = new Button(com_addStu, SWT.RADIO);
		btn_sex2.setBounds(290, 129, 61, 17);
		btn_sex2.setText("\u5973");

		Label lab_addr = new Label(com_addStu, SWT.NONE);
		lab_addr.setBounds(127, 249, 61, 17);
		lab_addr.setText("\u5BB6\u5EAD\u4F4F\u5740\uFF1A");

		text_2 = new Text(com_addStu, SWT.BORDER);
		text_2.setBounds(218, 249, 375, 23);

		final Label label_images = new Label(com_addStu, SWT.NONE);
		label_images.setBounds(450, 71, 134, 113);
		label_images.setText("\u7167\u7247\u4FE1\u606F");
		label_images.setImage(SWTResourceManager.getImage(Main.class,
				"/images/15.gif"));

		Button btnNewButton = new Button(com_addStu, SWT.NONE);
		// 注册
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// 文本框的验证

				// 从页面获取所有信息
				String name = text.getText().trim();
				String sex = "男";
				if (btn_sex1.getSelection()) {
					sex = btn_sex1.getText().trim();
				}
				if (btn_sex2.getSelection()) {
					sex = btn_sex2.getText().trim();
				}
				String classinfo = combo_1.getText().trim();// 1--网络1401
				String[] info = classinfo.split("--");
				String classId = info[0];
				String addr = text_2.getText().trim();
				File f = new File(StudentUtil.opnFilePath);
				// 将页面获取到的数据按照添加sql语句的?顺序来添加到params中
				List<Object> params = new ArrayList<Object>();
				params.add(classId);
				params.add(name);
				params.add(sex);
				params.add(addr);
				params.add(f);

				try {
					// 注册
					boolean flag = studentDao.registerStudent(params);
					if (flag) {
						// 将页面上的数据清空
						text.setText("");
						text_2.setText("");
						StudentUtil.opnFilePath = "";
						label_images.setImage(SWTResourceManager.getImage(
								Main.class, "/images/15.gif"));
					} else {
						SwtUtil.showMessageBox(shell, "出错了", "注册失败");
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(307, 337, 80, 27);
		btnNewButton.setText("\u6CE8\u518C");

		btn_upload = new Button(com_addStu, SWT.NONE);
		// 上传照片
		btn_upload.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fd = new FileDialog(shell, SWT.SINGLE);
				fd.setText("请选择上传的图片");
				fd.setFilterPath("SystemRoot");
				fd.setFilterExtensions(new String[] { "*.jpg", "*png", "*.gif",
						"*.*" });
				String selected = fd.open();
				if (null == selected) {
					return;
				}
				File file = new File(selected);
				StudentUtil.opnFilePath = selected;// 将路径存储
				InputStream in = null;
				try {
					in = new FileInputStream(file);
					Image image = new Image(Display.getDefault(), in);
					label_images.setImage(image);
				} catch (FileNotFoundException e1) {
					LogUtil.logger.error(e1.getMessage());
					SwtUtil.showMessageBox(shell, "出错了", e1.getMessage());
				} finally {
					if (null != in) {
						try {
							in.close();
						} catch (IOException e1) {
							LogUtil.logger.error(e1.getMessage());
							SwtUtil.showMessageBox(shell, "出错了",
									e1.getMessage());
						}
					}
				}
			}
		});
		btn_upload.setBounds(471, 203, 80, 27);
		btn_upload.setText("\u4E0A\u4F20\u7167\u7247");
		// 下列列表中的数据动态加载进来的
		combo_1 = new Combo(com_addStu, SWT.NONE);
		combo_1.setBounds(220, 183, 107, 20);
		// 查看班级
		try {
			List<Map<String, Object>> classList = classDao.findAllClassInfo();
			for (Map<String, Object> map : classList) {
				combo_1.add(map.get("CLASS_ID").toString() + "--"
						+ map.get("CLASS_NAME").toString());
			}
			combo_1.select(0);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		label_name = new Label(com_addStu, SWT.NONE);
		label_name.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		label_name.setBounds(207, 111, 180, 12);

		com_main = new Composite(shell, SWT.NONE);
		com_main.setBackgroundImage(SWTResourceManager.getImage(Main.class,
				"/images/main_bg.png"));

		com_findStu = new Composite(shell, SWT.NONE);
		com_findStu.setBackgroundImage(SWTResourceManager.getImage(Main.class,
				"/images/bg.jpg"));

		table = new Table(com_findStu, SWT.BORDER | SWT.CHECK
				| SWT.FULL_SELECTION | SWT.MULTI);
		table.setBounds(44, 97, 708, 309);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		TableColumn tblclmnNewColumn = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn.setWidth(72);
		tblclmnNewColumn.setText("学号");

		TableColumn tblclmnNewColumn_1 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_1.setWidth(77);
		tblclmnNewColumn_1.setText("姓名");

		TableColumn tblclmnNewColumn_2 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_2.setWidth(62);
		tblclmnNewColumn_2.setText("性别");

		TableColumn tblclmnNewColumn_6 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_6.setWidth(82);
		tblclmnNewColumn_6.setText("班级");

		TableColumn tblclmnNewColumn_3 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_3.setWidth(143);
		tblclmnNewColumn_3.setText("地址");

		TableColumn tblclmnNewColumn_4 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_4.setWidth(165);
		tblclmnNewColumn_4.setText("入学时间");

		TableColumn tblclmnNewColumn_5 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_5.setWidth(100);
		tblclmnNewColumn_5.setText("学生状态");

		combo = new Combo(com_findStu, SWT.NONE);
		combo.add("查看所有");
		combo.add("根据学号");
		combo.add("根据班级");
		combo.select(0);
		combo.setBounds(116, 21, 87, 20);

		text_1 = new Text(com_findStu, SWT.BORDER);
		// 在根据条件查看学生信息时，失去焦点判断
		text_1.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				// 获取文本框数据
				String content = text_1.getText().trim();
				// 如果下拉列表选择的是 根据学号 判断文本框只能输入数字
				if ("根据学号".equals(combo.getText().trim())) {
					if (content.matches("^[0-9]{3,15}$")) {
						lab_find.setText("");
					} else {
						lab_find.setText("请输入正确的学号！！");
					}
				} else {
					lab_find.setText("");
				}
			}
		});
		text_1.setBounds(246, 23, 190, 18);

		Button btnNewButton_1 = new Button(com_findStu, SWT.NONE);
		// 查询
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String type = combo.getText().trim();
				if ("查看所有".equals(type)) {
					try {
						List<Map<String, Object>> list = studentDao
								.findAllStudent();
						table.removeAll();// 清除表格中所有数据
						if (null != list && list.size() > 0) {
							// 将数据显示在表格中
							for (Map<String, Object> map : list) {
								TableItem tableItem = new TableItem(table,
										SWT.NONE);
								tableItem.setText(new String[] {
										map.get("STU_ID").toString(),
										map.get("STU_NAME").toString(),
										map.get("STU_SEX").toString(),
										map.get("CLASS_NAME").toString(),
										map.get("STU_ADDR").toString(),
										map.get("SCHOOL_DATE").toString(),
										map.get("STATUS").toString() });
							}
						}

					} catch (SQLException e1) {

					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else if ("根据学号".equals(type)) {
					// 获取文本框的内容
					String stuId = text_1.getText();
					// 清除表格中的数据
					table.removeAll();
					if (!stuId.matches("^[0-9]{3,15}$")) {// 格式不合法，不能进行查询操作
						return;
					}

					try {
						// 根据学号查看学生信息
						Map<String, Object> map = studentDao
								.findStudentByID(Integer.parseInt(stuId));
						if (null != map && map.size() > 0) {
							TableItem tableItem = new TableItem(table, SWT.NONE);
							tableItem.setText(new String[] {
									map.get("STU_ID").toString(),
									map.get("STU_NAME").toString(),
									map.get("STU_SEX").toString(),
									map.get("CLASS_NAME").toString(),
									map.get("STU_ADDR").toString(),
									map.get("SCHOOL_DATE").toString(),
									map.get("STATUS").toString() });
						}
					} catch (NumberFormatException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				} else if ("根据班级".equals(type)) {
					String className = text_1.getText().trim();
					// 清除表格中的数据
					table.removeAll();
					// 根据班级名称查看学生信息
					try {
						List<Map<String, Object>> list = studentDao
								.findStudentByClassName(className);
						if (null != list && list.size() > 0) {
							// 将数据显示在表格中
							for (Map<String, Object> map : list) {
								TableItem tableItem = new TableItem(table,
										SWT.NONE);
								tableItem.setText(new String[] {
										map.get("STU_ID").toString(),
										map.get("STU_NAME").toString(),
										map.get("STU_SEX").toString(),
										map.get("CLASS_NAME").toString(),
										map.get("STU_ADDR").toString(),
										map.get("SCHOOL_DATE").toString(),
										map.get("STATUS").toString() });
							}
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnNewButton_1.setBounds(587, 21, 72, 22);
		btnNewButton_1.setText("查询");

		lab_find = new Label(com_findStu, SWT.NONE);
		lab_find.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		lab_find.setBounds(219, 47, 190, 12);

		Button btnNewButton_2 = new Button(com_findStu, SWT.NONE);
		btnNewButton_2.setBounds(104, 432, 72, 22);
		btnNewButton_2.setText("转学");

		Button btnNewButton_3 = new Button(com_findStu, SWT.NONE);
		// 毕业操作
		btnNewButton_3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// 获取表格中所有的项
				int count = table.getItemCount();
				
				// 根据选中的复选框获取数据
				for (int i = 0; i < count; i++) {
					// 判断此行是否被选中
					if (table.getItem(i).getChecked()) {
						String dateStr=table.getItem(i).getText(5);
						
						System.out.println("毕业"+table.getItem(i).getText(5));
					}
				}

			}
		});
		btnNewButton_3.setBounds(503, 432, 72, 22);
		btnNewButton_3.setText("毕业");
		// 打开查看面板时，自动加载所有在校学生，并显示在表格
		try {
			List<Map<String, Object>> list2 = studentDao.findAllStudent();
			if (null != list2 && list2.size() > 0) {
				// 将数据显示在表格中
				for (Map<String, Object> map : list2) {
					TableItem tableItem = new TableItem(table, SWT.NONE);
					tableItem.setText(new String[] {
							map.get("STU_ID").toString(),
							map.get("STU_NAME").toString(),
							map.get("STU_SEX").toString(),
							map.get("CLASS_NAME").toString(),
							map.get("STU_ADDR").toString(),
							map.get("SCHOOL_DATE").toString(),
							map.get("STATUS").toString() });
				}
			} else {
				SwtUtil.showMessageBox(shell, "错误提示", "该系统无在读学生记录，请先进行学生注册");
				// 无数据直接跳转到注册页面
				stackLayout.topControl = com_addStu;
				shell.layout();
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// TableItem tableItem = new TableItem(table, SWT.NONE);
		// tableItem.setText(new String[] {"1001", "zhangsan", "男", "湖南",
		// "2012-9-12", "在读"});
		//
		// TableItem tableItem_1 = new TableItem(table, SWT.NONE);
		// tableItem_1.setText(new String[] {"1002", "lisi", "男", "湖南", "",
		// "在读"});
		// 栈顶默认显示的面板
		stackLayout.topControl = com_main;

		Label lblNewLabel_4 = new Label(com_main, SWT.NONE);
		lblNewLabel_4.setBounds(25, 10, 36, 23);
		lblNewLabel_4.setText("欢迎：");

		Label label_user = new Label(com_main, SWT.NONE);
		label_user.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		label_user.setBounds(67, 10, 129, 23);
		if (null != TeacherUtil.map && TeacherUtil.map.size() > 0) {
			label_user.setText(TeacherUtil.map.get("TEA_NAME").toString()
					+ "--" + TeacherUtil.map.get("TNAME").toString());
		}
		//
		// 设置控件背景透明
		shell.setBackgroundMode(SWT.INHERIT_DEFAULT);

	}
}
