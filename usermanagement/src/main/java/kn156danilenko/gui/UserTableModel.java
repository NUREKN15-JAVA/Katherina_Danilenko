package kn156danilenko.gui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import kn156danilenko.User;
import kn156danilenko.util.Messages;

public class UserTableModel extends AbstractTableModel {
	

	
    private static final String[] COLUMN_NAMES = {Messages.getString("UserTableModel.id"),Messages.getString("UserTableModel.first_name"),Messages.getString("UserTableModel.last_name")}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    private List users = null;
	private static final Class[] COLUMN_CLASSES = {Long.class, String.class,String.class};
	
	 public UserTableModel(Collection users) {
			this.users = new ArrayList(users);
		}
	
	public int getRowCount() {
		return users.size();
	}

	
	public int getColumnCount() {
		return COLUMN_NAMES.length;
	}

	
	public Object getValueAt(int rowIndex, int columnIndex) {
		User user=(User) users.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return user.getId();
		case 1:
			return user.getFirstName();
		case 2:
			return user.getLastName();
		}
		return null;
	}

	
	public String getColumnName(int column) {
		return COLUMN_NAMES[column];
	}

	
	public Class getColumnClass(int columnIndex) {
		return COLUMN_CLASSES[columnIndex];
	}
	
	//
	
	 public User getUser(int index) {
	        return (User) users.get(index);
	    }
	 
	  public void addUsers(Collection users) {
	        this.users.addAll(users);
	        
	    }

	    public void clearUsers() {
	        this.users = new ArrayList();
	    }

}
