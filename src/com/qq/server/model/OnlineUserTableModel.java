package com.qq.server.model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class OnlineUserTableModel extends AbstractTableModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5521821915507923161L;

	String[] tiTle = {
			"ÕËºÅ",
			"ÃÜÂë",
			"×´Ì¬",
	};
	
	ArrayList certificates = new ArrayList();
	
	public OnlineUserTableModel(){
		ArrayList certificate = new ArrayList<Comparable>();
		certificate.add("1070550684");
		certificate.add("asfasgsdgd");
		certificate.add("onLine");
		certificates.add(certificate);
	}
	
	public int getColumnCount() { 
		return tiTle.length; 
		} 

	public int getRowCount() { 
		return certificates.size(); 
		} 

	public String getColumnName(int col) { 
		return tiTle[col]; 
		} 

	public Object getValueAt(int row, int col) { 
		return ((ArrayList)certificates.get(row)).get(col); 
		} 
	
	public void setValueAt(Object value, int row, int col){
		((ArrayList)certificates.get(row)).set(col, value);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Class getColumnClass(int c) { 
		return getValueAt(0, c).getClass(); 
		}
	
	public boolean isCellEditable(int row, int column){
		return true;
	}
}