package com.qq.server.tools;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class CustomizedTitleBorder {

	public static Border createTitleBorder(String string){
		
		return BorderFactory.createTitledBorder(new LineBorder(Colors.greyBorder),
				string,
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.TOP,
				Fonts.borderTitle,
				Color.blue
		);
	}
}
