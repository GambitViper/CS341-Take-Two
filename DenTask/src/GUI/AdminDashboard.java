package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import Classes.ComboItem;
import Classes.Database;
import Classes.Login;
import Classes.User;

import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.JRadioButton;
import javax.swing.JList;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;

public class AdminDashboard {

	private JFrame frame;
	private JTextField txtFirstName;
	private JTextField txtLastName;
	private String[] types = {"Dentist", "Hygienist", "Patient"};
	private JTextField txtEmail;
	private JTextField txtPhoneNumber;
	private JTextField txtUsername;
	private JPasswordField txtPassword;
	private JPasswordField txtPasswordConfirmed;
	private JLabel lblError;
	private JRadioButton radDentist, radHygienist;
	private JComboBox cboxUsers;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminDashboard window = new AdminDashboard();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AdminDashboard() {
		initialize();
		/***************************
		 * THIS IS FOR BURGER MENU *
		 ***************************/
		Border blackline = BorderFactory.createLineBorder(Color.black);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setBounds(dim.width/2-(1080/2), dim.height/2-(720/2), 1080, 720);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(148, 227, 0));
		panel.setBounds(0, 0, 220, 681);
		panel.setBorder(blackline);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel Logo = new JLabel("");
		Logo.setBounds(10, 11, 210, 138);
		panel.add(Logo);
		Logo.setIcon(new ImageIcon("logo.png"));
		
		JPanel pnlMainMenu = new JPanel();
		pnlMainMenu.setBackground(SystemColor.textHighlight);
		pnlMainMenu.setBounds(0, 160, 220, 60);
		pnlMainMenu.setBorder(blackline);
		panel.add(pnlMainMenu);
		pnlMainMenu.setLayout(null);
		
		JLabel lblMainMenu = new JLabel("Admin Dashboard");
		lblMainMenu.setHorizontalAlignment(SwingConstants.CENTER);
		lblMainMenu.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblMainMenu.setBounds(0, 0, 220, 60);
		pnlMainMenu.add(lblMainMenu);
		
		JPanel pnlMakeApp = new JPanel();
		pnlMakeApp.setBackground(SystemColor.activeCaption);
		pnlMakeApp.setBounds(0, 219, 220, 60);
		pnlMakeApp.setBorder(blackline);
		panel.add(pnlMakeApp);
		pnlMakeApp.setLayout(null);
		
		JLabel lblViewAppointment = new JLabel("View Appointments");
		lblViewAppointment.setHorizontalAlignment(SwingConstants.CENTER);
		lblViewAppointment.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblViewAppointment.setBounds(0, 0, 220, 60);
		pnlMakeApp.add(lblViewAppointment);
		
		JPanel pnlViewApp = new JPanel();
		pnlViewApp.setBackground(SystemColor.activeCaption);
		pnlViewApp.setBounds(0, 278, 220, 60);
		pnlViewApp.setBorder(blackline);
		panel.add(pnlViewApp);
		pnlViewApp.setLayout(null);
		
		JLabel lblMakeProfiles = new JLabel("Make Profiles");
		lblMakeProfiles.setHorizontalAlignment(SwingConstants.CENTER);
		lblMakeProfiles.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblMakeProfiles.setBounds(0, 0, 220, 60);
		pnlViewApp.add(lblMakeProfiles);
		
		JPanel pnlEditProfile = new JPanel();
		pnlEditProfile.setBackground(SystemColor.activeCaption);
		pnlEditProfile.setBounds(0, 337, 220, 60);
		pnlEditProfile.setBorder(blackline);
		panel.add(pnlEditProfile);
		pnlEditProfile.setLayout(null);
		
		JLabel lblDeleteProfiles = new JLabel("Delete Profiles");
		lblDeleteProfiles.setHorizontalAlignment(SwingConstants.CENTER);
		lblDeleteProfiles.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblDeleteProfiles.setBounds(0, 0, 220, 60);
		pnlEditProfile.add(lblDeleteProfiles);
		
		JPanel pnlLogOut = new JPanel();
		pnlLogOut.setBackground(SystemColor.activeCaption);
		pnlLogOut.setBounds(0, 621, 220, 60);
		pnlLogOut.setBorder(blackline);
		panel.add(pnlLogOut);
		pnlLogOut.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Log Out");
		lblNewLabel.setBackground(SystemColor.inactiveCaption);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(0, 0, 220, 60);
		pnlLogOut.add(lblNewLabel);
		
		JLabel lblCreatedBy = new JLabel("Created By:");
		lblCreatedBy.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCreatedBy.setHorizontalAlignment(SwingConstants.CENTER);
		lblCreatedBy.setBounds(0, 466, 220, 28);
		panel.add(lblCreatedBy);
		
		JLabel lblMattMilosTom = new JLabel("Matt Milos, Tom Jensen, Zach Baklund");
		lblMattMilosTom.setHorizontalAlignment(SwingConstants.CENTER);
		lblMattMilosTom.setBounds(0, 495, 220, 28);
		panel.add(lblMattMilosTom);
		
		JLabel lbldentask = new JLabel("\u00A9 DenTask 2019");
		lbldentask.setHorizontalAlignment(SwingConstants.CENTER);
		lbldentask.setBounds(0, 594, 220, 28);
		panel.add(lbldentask);
		
		JPanel pnlMakeProfiles = new JPanel();
		pnlMakeProfiles.setBounds(218, 0, 846, 681);
		frame.getContentPane().add(pnlMakeProfiles);
		pnlMakeProfiles.setLayout(null);
		
		JLabel lblViewAppointments_1 = new JLabel("Make Profile");
		lblViewAppointments_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblViewAppointments_1.setFont(new Font("Tahoma", Font.BOLD, 35));
		lblViewAppointments_1.setBounds(0, 0, 846, 96);
		pnlMakeProfiles.add(lblViewAppointments_1);
		
		txtFirstName = new JTextField();
		txtFirstName.setBounds(75, 167, 214, 36);
		pnlMakeProfiles.add(txtFirstName);
		txtFirstName.setColumns(10);
		
		txtLastName = new JTextField();
		txtLastName.setColumns(10);
		txtLastName.setBounds(503, 167, 214, 36);
		pnlMakeProfiles.add(txtLastName);
		
		JLabel lblFirstName = new JLabel("First Name");
		lblFirstName.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblFirstName.setBounds(74, 142, 95, 24);
		pnlMakeProfiles.add(lblFirstName);
		
		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblLastName.setBounds(503, 142, 95, 24);
		pnlMakeProfiles.add(lblLastName);
		
		txtEmail = new JTextField();
		txtEmail.setColumns(10);
		txtEmail.setBounds(75, 277, 214, 36);
		pnlMakeProfiles.add(txtEmail);
		
		txtPhoneNumber = new JTextField();
		txtPhoneNumber.setColumns(10);
		txtPhoneNumber.setBounds(503, 277, 214, 36);
		pnlMakeProfiles.add(txtPhoneNumber);
		
		txtUsername = new JTextField();
		txtUsername.setColumns(10);
		txtUsername.setBounds(75, 385, 214, 36);
		pnlMakeProfiles.add(txtUsername);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblEmail.setBounds(75, 253, 95, 24);
		pnlMakeProfiles.add(lblEmail);
		
		JLabel lblPhoneNumber = new JLabel("Phone Number");
		lblPhoneNumber.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblPhoneNumber.setBounds(503, 253, 165, 24);
		pnlMakeProfiles.add(lblPhoneNumber);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblUsername.setBounds(74, 360, 95, 24);
		pnlMakeProfiles.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblPassword.setBounds(74, 461, 95, 24);
		pnlMakeProfiles.add(lblPassword);
		
		JLabel lblConfirmPassword = new JLabel("Confirm Password");
		lblConfirmPassword.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblConfirmPassword.setBounds(503, 461, 165, 24);
		pnlMakeProfiles.add(lblConfirmPassword);
		
		JLabel lblAccountType = new JLabel("Account Type");
		lblAccountType.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblAccountType.setBounds(503, 360, 165, 24);
		pnlMakeProfiles.add(lblAccountType);
		
		radDentist = new JRadioButton("Dentist");
		radDentist.setBounds(499, 392, 109, 23);
		pnlMakeProfiles.add(radDentist);
		
		radHygienist = new JRadioButton("Hygienist");
		radHygienist.setBounds(620, 392, 109, 23);
		pnlMakeProfiles.add(radHygienist);
		
		JButton btnCreateAccount = new JButton("Create Account");
		btnCreateAccount.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnCreateAccount.setBounds(75, 576, 642, 43);
		pnlMakeProfiles.add(btnCreateAccount);
		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(75, 496, 214, 36);
		pnlMakeProfiles.add(txtPassword);
		
		txtPasswordConfirmed = new JPasswordField();
		txtPasswordConfirmed.setBounds(503, 496, 214, 36);
		pnlMakeProfiles.add(txtPasswordConfirmed);
		
		lblError = new JLabel("");
		lblError.setForeground(Color.RED);
		lblError.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblError.setHorizontalAlignment(SwingConstants.CENTER);
		lblError.setBounds(75, 84, 642, 47);
		pnlMakeProfiles.add(lblError);
		
		JPanel pnlDeleteProfile = new JPanel();
		pnlDeleteProfile.setBounds(218, 0, 846, 681);
		frame.getContentPane().add(pnlDeleteProfile);
		pnlDeleteProfile.setLayout(null);
		
		JLabel lblEditProfile_1 = new JLabel("Delete Profile");
		lblEditProfile_1.setFont(new Font("Tahoma", Font.BOLD, 40));
		lblEditProfile_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditProfile_1.setBounds(10, 0, 826, 88);
		pnlDeleteProfile.add(lblEditProfile_1);
		
		JButton btnDeleteUser = new JButton("Delete User");
		btnDeleteUser.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnDeleteUser.setBounds(145, 324, 571, 80);
		pnlDeleteProfile.add(btnDeleteUser);
		
		cboxUsers = new JComboBox();
		cboxUsers.setBounds(262, 223, 320, 60);
		pnlDeleteProfile.add(cboxUsers);
		
		JLabel lblSelectUser = new JLabel("Select User");
		lblSelectUser.setHorizontalAlignment(SwingConstants.CENTER);
		lblSelectUser.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblSelectUser.setBounds(262, 155, 320, 57);
		pnlDeleteProfile.add(lblSelectUser);
		
				pnlDeleteProfile.setVisible(false);
		
		JPanel pnlMainMenuContent = new JPanel();
		pnlMainMenuContent.setBounds(218, 0, 846, 681);
		frame.getContentPane().add(pnlMainMenuContent);
		pnlMainMenuContent.setLayout(null);
		
		JLabel lblMainMenu_1 = new JLabel("Main Menu");
		lblMainMenu_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblMainMenu_1.setFont(new Font("Tahoma", Font.BOLD, 35));
		lblMainMenu_1.setBounds(0, 0, 846, 94);
		pnlMainMenuContent.add(lblMainMenu_1);
		pnlMainMenuContent.setVisible(true);
		
		JPanel pnlViewAppointments = new JPanel();
		pnlViewAppointments.setBounds(218, 0, 846, 681);
		frame.getContentPane().add(pnlViewAppointments);
		pnlViewAppointments.setLayout(null);
		
		JLabel lblMakeAppointment_1 = new JLabel("Make Appointment");
		lblMakeAppointment_1.setFont(new Font("Tahoma", Font.BOLD, 35));
		lblMakeAppointment_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblMakeAppointment_1.setBounds(0, 0, 846, 93);
		pnlViewAppointments.add(lblMakeAppointment_1);
		pnlViewAppointments.setVisible(false);
		

		/***************************
		 * THIS IS FOR MAIN MENU *
		 ***************************/

		lblMainMenu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				pnlDeleteProfile.setVisible(false);
				pnlMainMenuContent.setVisible(true);
				pnlMakeProfiles.setVisible(false);
				pnlViewAppointments.setVisible(false);
				
				pnlEditProfile.setBackground(SystemColor.activeCaption);
				pnlMainMenu.setBackground(SystemColor.textHighlight);
				pnlViewApp.setBackground(SystemColor.activeCaption);
				pnlMakeApp.setBackground(SystemColor.activeCaption);
			}
		});
		

		/********************************
		 * THIS IS FOR VIEW APPOINTMENT *
		 ********************************/

		lblViewAppointment.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				pnlDeleteProfile.setVisible(false);
				pnlMainMenuContent.setVisible(false);
				pnlMakeProfiles.setVisible(false);
				pnlViewAppointments.setVisible(true);
				
				pnlEditProfile.setBackground(SystemColor.activeCaption);
				pnlMainMenu.setBackground(SystemColor.activeCaption);
				pnlViewApp.setBackground(SystemColor.activeCaption);
				pnlMakeApp.setBackground(SystemColor.textHighlight);
			}
		});

		/****************************
		 * THIS IS FOR MAKE PROFILE *
		 ****************************/

		lblMakeProfiles.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				txtFirstName.setText(null);
				txtLastName.setText(null);
				txtEmail.setText(null);
				txtPhoneNumber.setText(null);
				txtPassword.setText(null);
				txtPasswordConfirmed.setText(null);
				txtUsername.setText(null);
				radDentist.setSelected(false);
				radHygienist.setSelected(false);
				
				pnlDeleteProfile.setVisible(false);
				pnlMainMenuContent.setVisible(false);
				pnlMakeProfiles.setVisible(true);
				pnlViewAppointments.setVisible(false);
				
				pnlEditProfile.setBackground(SystemColor.activeCaption);
				pnlMainMenu.setBackground(SystemColor.activeCaption);
				pnlViewApp.setBackground(SystemColor.textHighlight);
				pnlMakeApp.setBackground(SystemColor.activeCaption);
			}
		});
		
		/***********************************
		 * THIS IS FOR DELETE PROFILE MENU *
		 ***********************************/

		lblDeleteProfiles.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				LinkedList<User> userList;
				
				userList = popUsers();

				cboxUsers.removeAllItems();
				
				for( int i = 0; i < userList.size(); i++) {
					
					String name = (userList.get(i).getFirstName() + " " + userList.get(i).getLastName());
					String username = userList.get(i).getUsername();
					System.out.println(userList.get(i).getUsername());
					if(!username.equals("admin")) {
						cboxUsers.addItem(new ComboItem(name, username));
					}
				}
				pnlDeleteProfile.setVisible(true);
				pnlMainMenuContent.setVisible(false);
				pnlMakeProfiles.setVisible(false);
				pnlViewAppointments.setVisible(false);
				
				pnlEditProfile.setBackground(SystemColor.textHighlight);
				pnlMainMenu.setBackground(SystemColor.activeCaption);
				pnlViewApp.setBackground(SystemColor.activeCaption);
				pnlMakeApp.setBackground(SystemColor.activeCaption);
			}
		});

		/******************************
		 * THIS IS FOR DELETE PROFILE *
		 ******************************/
		
		btnDeleteUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Object item = cboxUsers.getSelectedItem();
				String val = ((ComboItem)item).getValue();
				LinkedList<User> userList;
				
				Database db = new Database();
				db.connect();
				
				db.deleteUser(val);
				
				db.disconnect();
				
				userList = popUsers();
				System.out.println(userList);
				cboxUsers.removeAllItems();
				
				for( int i = 0; i < userList.size(); i++) {
					
					String name = (userList.get(i).getFirstName() + " " + userList.get(i).getLastName());
					String username = userList.get(i).getUsername();
					System.out.println(userList.get(i).getUsername());
					if(!username.equals("admin")) {
						cboxUsers.addItem(new ComboItem(name, username));
					}
				}
			}
		});
		/**********************
		 * THIS IS FOR LOGOUT *
		 **********************/

		lblNewLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				frame.dispose();
				LoginScreen login = new LoginScreen();
				login.setVisible(true);
			}
		});
		
		/************************************
		 * THIS IS FOR CREATING NEW ACCOUNT *
		 ************************************/

		btnCreateAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				login();

				txtFirstName.setText(null);
				txtLastName.setText(null);
				txtEmail.setText(null);
				txtPhoneNumber.setText(null);
				txtPassword.setText(null);
				txtPasswordConfirmed.setText(null);
				txtUsername.setText(null);
				radDentist.setSelected(false);
				radHygienist.setSelected(false);
				
				pnlDeleteProfile.setVisible(false);
				pnlMainMenuContent.setVisible(true);
				pnlMakeProfiles.setVisible(false);
				pnlViewAppointments.setVisible(false);
				
				pnlEditProfile.setBackground(SystemColor.activeCaption);
				pnlMainMenu.setBackground(SystemColor.textHighlight);
				pnlViewApp.setBackground(SystemColor.activeCaption);
				pnlMakeApp.setBackground(SystemColor.activeCaption);
			}
		});
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private LinkedList<User> popUsers() {
		Database db = new Database();
		db.connect();
		
		LinkedList<User> users = new LinkedList<User>();

		System.out.println(db.getAllUsers(true).toString());
		
		return db.getAllUsers(true);
		
	}
	
	private boolean login() {
		
		String pass1 = String.valueOf(txtPassword.getPassword());
		String pass2 = String.valueOf(txtPasswordConfirmed.getPassword());
		int empType = 3;
		
		User dummy = null;
		try {
			dummy = Login.findUserByUsername(txtUsername.getText());
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if(!pass1.equals(pass2)) {
			lblError.setText("PASSWORDS DO NOT MATCH");
			return false;
		} else if(dummy != null){
			lblError.setText("USERNAME IS TAKEN");
			return false;
		} else {
			
				if(radDentist.isSelected()) {
					try {
						Login.createUser(
								txtUsername.getText(),
								String.valueOf(txtPassword.getPassword()), 
								txtFirstName.getText(),
								txtLastName.getText(),
								txtEmail.getText(),
								txtPhoneNumber.getText(),
								1);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return true;
				} else if(radHygienist.isSelected()) {

					try {
						Login.createUser(
								txtUsername.getText(),
								String.valueOf(txtPassword.getPassword()), 
								txtFirstName.getText(),
								txtLastName.getText(),
								txtEmail.getText(),
								txtPhoneNumber.getText(),
								2);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return true;
				} else {
					lblError.setText("Select a account type!");
				}
		}
		return false;
	}
	
	
	public void setVisible(boolean b) {
		frame.setVisible(b);
	}
}
