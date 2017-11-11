package kn156danilenko.gui;

import java.awt.Component;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.mockobjects.dynamic.Mock;

import junit.extensions.jfcunit.JFCTestCase;
import junit.extensions.jfcunit.JFCTestHelper;
import junit.extensions.jfcunit.eventdata.JTableMouseEventData;
import junit.extensions.jfcunit.eventdata.MouseEventData;
import junit.extensions.jfcunit.eventdata.StringEventData;
import junit.extensions.jfcunit.finder.DialogFinder;
import junit.extensions.jfcunit.finder.NamedComponentFinder;
import kn156danilenko.User;
import kn156danilenko.db.DaoFactory;
import kn156danilenko.db.MockDaoFactory;
import kn156danilenko.util.Messages;

public class MainFrameTest extends JFCTestCase {

	private MainFrame mainFrame;

    private Mock mockUserDao;

    private List users;
    
    protected void setUp() throws Exception {
        super.setUp();
        try {
            Properties properties = new Properties();
            properties.setProperty("dao.factory", MockDaoFactory.class.getName());
            DaoFactory.init(properties );
            mockUserDao = ((MockDaoFactory) DaoFactory.getInstance()).getMockUserDao();
            User expectedUser = new User(new Long(1000), "George", "Bush", new Date());
            users = Collections.singletonList(expectedUser);
            mockUserDao.expectAndReturn("findAll", users);
            setHelper(new JFCTestHelper());
            mainFrame = new MainFrame(); 
        } catch (Exception e) {
            e.printStackTrace();
        }
        mainFrame.setVisible(true);
    }

    protected void tearDown() throws Exception {
    	
        try {
            mainFrame.setVisible(false);
            getHelper().cleanUp(this);   
            super.tearDown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private Component find(Class componentClass, String name) {
        NamedComponentFinder finder;
        finder = new NamedComponentFinder(componentClass, name);
        finder.setWait(0);
        Component component = finder.find(mainFrame, 0);
        assertNotNull("Could not find component '" + name + "'", component);
        return component;
    }
    
    public void testBrowseControls() {
        find(JPanel.class, "browsePanel");
        JTable table = (JTable) find(JTable.class, "userTable");
        assertEquals(3, table.getColumnCount());
        assertEquals(Messages.getString("UserTableModel.id"), table.getColumnName(0));
        assertEquals(Messages.getString("UserTableModel.first_name"), table.getColumnName(1));
        assertEquals(Messages.getString("UserTableModel.last_name"), table.getColumnName(2));

        find(JButton.class, "addButton");
        find(JButton.class, "editButton");
        find(JButton.class, "deleteButton");
        find(JButton.class, "detailsButton");        
    }
    
    public void testAddUser() {
        try {
            String firstName = "John";
            String lastName = "Doe";
            Date now = new Date();

            User user = new User(firstName, lastName, now);
            
            User expectedUser = new User(new Long(1), firstName, lastName, new Date());
            mockUserDao.expectAndReturn("create", user, expectedUser);
            
            ArrayList users = new ArrayList(this.users);
            users.add(expectedUser);
            mockUserDao.expectAndReturn("findAll", users);
           
            
            JTable table = (JTable) find(JTable.class, "userTable");
            assertEquals(1, table.getRowCount());
            
            JButton addButton = (JButton) find(JButton.class, "addButton");
            getHelper().enterClickAndLeave(new MouseEventData(this, addButton)); 
            
            find(JPanel.class, "addPanel");

            fillField(firstName, lastName, now);

            JButton okButton = (JButton) find(JButton.class, "okButton");
            getHelper().enterClickAndLeave(new MouseEventData(this, okButton));

            find(JPanel.class, "browsePanel");
            table = (JTable) find(JTable.class, "userTable");
            assertEquals(2, table.getRowCount());
            
            mockUserDao.verify();
        } catch (Exception e) {
            fail(e.toString());
        }
    }
    public void testCancelAddUser() {
        try {
            String firstName = "John";
            String lastName = "Doe";
            Date now = new Date();

            ArrayList users = new ArrayList(this.users);
            mockUserDao.expectAndReturn("findAll", users);
            
            JTable table = (JTable) find(JTable.class, "userTable");
            assertEquals(1, table.getRowCount());

            JButton addButton = (JButton) find(JButton.class, "addButton");
            getHelper().enterClickAndLeave(new MouseEventData(this, addButton));

            find(JPanel.class, "addPanel");

            fillField(firstName, lastName, now);

            JButton cancelButton = (JButton) find(JButton.class, "cancelButton");
            getHelper().enterClickAndLeave(new MouseEventData(this, cancelButton));

            find(JPanel.class, "browsePanel");
            table = (JTable) find(JTable.class, "userTable");
            assertEquals(1, table.getRowCount());
            
            mockUserDao.verify();
        } catch (Exception e) {
            fail(e.toString());
        }
    }

    public void testCancelEditUser() {
        try {
            String firstName = "John";
            String lastName = "Doe";
            Date now = new Date();

            User expectedUser = new User(new Long(1), firstName, lastName, new Date());
            List users = new ArrayList(this.users);
            users.add(expectedUser);
            
            mockUserDao.expectAndReturn("findAll", users);
            
            JTable table = (JTable) find(JTable.class, "userTable");
            assertEquals(1, table.getRowCount());

            JButton editButton = (JButton) find(JButton.class, "editButton");
            getHelper().enterClickAndLeave(new MouseEventData(this, editButton));

            String title = "Edit user";
            findDialog(title);
            
            getHelper().enterClickAndLeave(new JTableMouseEventData(this, table, 0, 0, 1));
            getHelper().enterClickAndLeave(new MouseEventData(this, editButton));
            
            find(JPanel.class, "editPanel");
            
            JTextField firstNameField = (JTextField) find(JTextField.class,
                    "firstNameField");
            JTextField lastNameField = (JTextField) find(JTextField.class,
                    "lastNameField");
            JTextField dateOfBirthField = (JTextField) find(JTextField.class,
                    "dateOfBirthField");
            
            assertEquals("George", firstNameField.getText());
            assertEquals("Bush", lastNameField.getText());
            
            getHelper().sendString(
                    new StringEventData(this, firstNameField, firstName));
            getHelper().sendString(
                    new StringEventData(this, lastNameField, lastName));
            DateFormat formatter = DateFormat.getDateInstance();
            String date = formatter.format(now);
            getHelper().sendString(
                    new StringEventData(this, dateOfBirthField, date));

            JButton cancelButton = (JButton) find(JButton.class, "cancelButton");
            getHelper().enterClickAndLeave(new MouseEventData(this, cancelButton));

            find(JPanel.class, "browsePanel");
            table = (JTable) find(JTable.class, "userTable");
            assertEquals(2, table.getRowCount());
            mockUserDao.verify();
            
        } catch (Exception e) {
            fail(e.toString());
        }
    }

    public void testEditUser() {
        try {       
        	        	
            //User expectedUser = new User(new Long(1000), "GeorgeG","BushB", new Date());
            User expectedUser = (User) users.get(0);
        	System.out.println(expectedUser);
            mockUserDao.expect("update", expectedUser);

            List users = Collections.singletonList(expectedUser);
            mockUserDao.expectAndReturn("findAll", users);
            
                                    
            JTable table = (JTable) find(JTable.class, "userTable");
            assertEquals(1, table.getRowCount());
            JButton editButton = (JButton) find(JButton.class, "editButton");
            getHelper().enterClickAndLeave(new JTableMouseEventData(this, table, 0, 0, 1));
            getHelper().enterClickAndLeave(new MouseEventData(this, editButton));
            
            find(JPanel.class, "editPanel");

            JTextField firstNameField = (JTextField) find(JTextField.class,
                    "firstNameField");
            JTextField lastNameField = (JTextField) find(JTextField.class,
                    "lastNameField");
            JTextField dateOfBirthField = (JTextField) find(JTextField.class,
                    "dateOfBirthField");
            
            getHelper().sendString(
                    new StringEventData(this, firstNameField, "G"));
            getHelper().sendString(
                    new StringEventData(this, lastNameField, "B"));

            JButton okButton = (JButton) find(JButton.class, "okButton");
            getHelper().enterClickAndLeave(new MouseEventData(this, okButton));

            find(JPanel.class, "browsePanel");
            table = (JTable) find(JTable.class, "userTable");
            assertEquals(1, table.getRowCount());
            mockUserDao.verify();
            
        } catch (Exception e) {
            fail(e.toString());
        }
    }
   
      public void testDeleteUser() {
        try {
            User expectedUser = new User(new Long(1000), "George", "Bush", new Date());
            mockUserDao.expect("delete", expectedUser);

            List users = new ArrayList();
            mockUserDao.expectAndReturn("findAll", users);
            
            JTable table = (JTable) find(JTable.class, "userTable");
            assertEquals(1, table.getRowCount());
            JButton deleteButton = (JButton) find(JButton.class, "deleteButton");
            getHelper().enterClickAndLeave(new JTableMouseEventData(this, table, 0, 0, 1));
            getHelper().enterClickAndLeave(new MouseEventData(this, deleteButton));
            
            find(JPanel.class, "browsePanel");
            table = (JTable) find(JTable.class, "userTable");
            assertEquals(0, table.getRowCount());
            mockUserDao.verify();
            
        } catch (Exception e) {
            fail(e.toString());
        }
    }

    private void findDialog(String title) {
        JDialog dialog;
        DialogFinder dFinder = new DialogFinder(title);
        dialog = (JDialog) dFinder.find();
        assertNotNull("Could not find dialog '" + title + "'", dialog);
        getHelper().disposeWindow( dialog, this );
    }

    private void fillField(String firstName, String lastName, Date now) {
        JTextField firstNameField = (JTextField) find(JTextField.class,
                "firstNameField");
        JTextField lastNameField = (JTextField) find(JTextField.class,
                "lastNameField");
        JTextField dateOfBirthField = (JTextField) find(JTextField.class,
                "dateOfBirthField");
        getHelper().sendString(
                new StringEventData(this, firstNameField, firstName));
        getHelper().sendString(
                new StringEventData(this, lastNameField, lastName));
        DateFormat formatter = DateFormat.getDateInstance();
        String date = formatter.format(now);
        getHelper().sendString(
                new StringEventData(this, dateOfBirthField, date));
    }
    
    public void testDetailsUser() {
		try {
			
			JTable table = (JTable) find(JTable.class, "userTable");
			JButton detailsButton = (JButton) find(JButton.class, "detailsButton");
			getHelper().enterClickAndLeave(new JTableMouseEventData(this, table, 0, 0, 1));
			getHelper().enterClickAndLeave(new MouseEventData(this, detailsButton));
			find(JPanel.class, "detailsPanel");
			JLabel firstNameField = (JLabel) find(JLabel.class, "firstNameField");
			JLabel lastNameField = (JLabel) find(JLabel.class, "lastNameField");
			JLabel AgeField = (JLabel) find(JLabel.class, "ageField");
			DateFormat formatter = DateFormat.getDateInstance();
			assertEquals("George", firstNameField.getText());
			assertEquals("Bush", lastNameField.getText());
			assertEquals("0", AgeField.getText());
			JButton okButton = (JButton) find(JButton.class, "okButton");
			mockUserDao.expectAndReturn("findAll", users);
			getHelper().enterClickAndLeave(new MouseEventData(this, okButton));

			find(JPanel.class, "browsePanel");
			table = (JTable) find(JTable.class, "userTable");
			assertEquals(1, table.getRowCount());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
 }



