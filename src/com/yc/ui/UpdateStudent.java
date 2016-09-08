package com.yc.ui;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;

public class UpdateStudent extends Composite {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public UpdateStudent(Composite parent, int style) {
		super(parent, style);
		setBackground(SWTResourceManager.getColor(SWT.COLOR_CYAN));
		
		Label lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setBounds(118, 76, 54, 12);
		lblNewLabel.setText("修改学生信息");

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
