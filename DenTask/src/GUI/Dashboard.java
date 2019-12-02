package GUI;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.JLayeredPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JTable;

import Classes.Database;
import Classes.Login;
import Classes.User;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Dashboard {

	private JFrame frame;
	private JTextField txtFirstName;
	private JTextField txtLastName;
	private JTextField txtEmail;
	private JTextField txtPhoneNumber;
	private JPasswordField txtCurrentPass;
	private JPasswordField txtNewPass;
	private JPasswordField txtConfirmedPass;
	
	private JLabel lblError;
	private JLabel lblMainMenuWelcome;
	
	private String userUsername;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Dashboard window = new Dashboard();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void setUser(String username) {
		userUsername = username.toLowerCase();
		lblMainMenuWelcome.setText("Welcome " + userUsername);
	}
	
	public void profileFiller() {
		Database connector = new Database();
		connector.connect();
		
		User dummy = connector.getUser(userUsername); 
		txtFirstName.setText(dummy.getFirstName());
		txtLastName.setText(dummy.getLastName());
		txtEmail.setText(dummy.getEmail());
		txtPhoneNumber.setText(dummy.getPhoneNum());
		
		connector.disconnect();
	}
	
	public void burger() {

		pnlEditProfileContent.setVisible(false);
		pnlMainMenuContent.setVisible(true);
		pnlViewAppContent.setVisible(false);
		pnlMakeAppContent.setVisible(false);
		
		pnlEditProfile.setBackground(SystemColor.activeCaption);
		pnlMainMenu.setBackground(SystemColor.textHighlight);
		pnlViewApp.setBackground(SystemColor.activeCaption);
		pnlMakeApp.setBackground(SystemColor.activeCaption);
	}
	
	/**
	 * Create the application.
	 */
	public Dashboard() {
		
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(0, 51, 255));
		frame.setBounds(100, 100, 1080, 720);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
		
		JLabel lblMainMenu = new JLabel("Main Menu");
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
		
		JLabel lblMakeAppointment = new JLabel("Make Appointment");
		lblMakeAppointment.setHorizontalAlignment(SwingConstants.CENTER);
		lblMakeAppointment.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblMakeAppointment.setBounds(0, 0, 220, 60);
		pnlMakeApp.add(lblMakeAppointment);
		
		JPanel pnlViewApp = new JPanel();
		pnlViewApp.setBackground(SystemColor.activeCaption);
		pnlViewApp.setBounds(0, 278, 220, 60);
		pnlViewApp.setBorder(blackline);
		panel.add(pnlViewApp);
		pnlViewApp.setLayout(null);
		
		JLabel lblViewAppointments = new JLabel("View Appointments");
		lblViewAppointments.setHorizontalAlignment(SwingConstants.CENTER);
		lblViewAppointments.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblViewAppointments.setBounds(0, 0, 220, 60);
		pnlViewApp.add(lblViewAppointments);
		
		JPanel pnlEditProfile = new JPanel();
		pnlEditProfile.setBackground(SystemColor.activeCaption);
		pnlEditProfile.setBounds(0, 337, 220, 60);
		pnlEditProfile.setBorder(blackline);
		panel.add(pnlEditProfile);
		pnlEditProfile.setLayout(null);
		
		JLabel lblEditProfile = new JLabel("Edit Profile");
		lblEditProfile.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditProfile.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblEditProfile.setBounds(0, 0, 220, 60);
		pnlEditProfile.add(lblEditProfile);
		
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
		
		JPanel pnlEditProfileContent = new JPanel();
		pnlEditProfileContent.setBounds(218, 0, 846, 681);
		frame.getContentPane().add(pnlEditProfileContent);
		pnlEditProfileContent.setLayout(null);
		
		JLabel lblEditProfile_1 = new JLabel("Edit Profile");
		lblEditProfile_1.setFont(new Font("Tahoma", Font.BOLD, 40));
		lblEditProfile_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditProfile_1.setBounds(0, 0, 846, 86);
		pnlEditProfileContent.add(lblEditProfile_1);
		
		JLabel lblFirstName = new JLabel("First Name");
		lblFirstName.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblFirstName.setBounds(30, 126, 140, 27);
		pnlEditProfileContent.add(lblFirstName);
		
		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblLastName.setBounds(333, 126, 140, 27);
		pnlEditProfileContent.add(lblLastName);
		
		JLabel lblCurrentPassword = new JLabel("Current Password");
		lblCurrentPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblCurrentPassword.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblCurrentPassword.setBounds(30, 390, 140, 34);
		pnlEditProfileContent.add(lblCurrentPassword);
		
		JLabel lblNewPassword = new JLabel("New Password");
		lblNewPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewPassword.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewPassword.setBounds(333, 390, 140, 34);
		pnlEditProfileContent.add(lblNewPassword);
		
		JLabel lblConfirmPassword = new JLabel("Confirm Password");
		lblConfirmPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblConfirmPassword.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblConfirmPassword.setBounds(612, 391, 140, 33);
		pnlEditProfileContent.add(lblConfirmPassword);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblEmail.setBounds(30, 237, 140, 27);
		pnlEditProfileContent.add(lblEmail);
		
		JLabel lblPhoneNumber = new JLabel("Phone Number");
		lblPhoneNumber.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblPhoneNumber.setBounds(612, 126, 140, 27);
		pnlEditProfileContent.add(lblPhoneNumber);
		
		txtFirstName = new JTextField();
		txtFirstName.setBounds(30, 166, 214, 36);
		pnlEditProfileContent.add(txtFirstName);
		txtFirstName.setColumns(10);
		
		txtLastName = new JTextField();
		txtLastName.setColumns(10);
		txtLastName.setBounds(333, 166, 214, 36);
		pnlEditProfileContent.add(txtLastName);
		
		txtEmail = new JTextField();
		txtEmail.setColumns(10);
		txtEmail.setBounds(30, 288, 214, 36);
		pnlEditProfileContent.add(txtEmail);
		
		txtPhoneNumber = new JTextField();
		txtPhoneNumber.setColumns(10);
		txtPhoneNumber.setBounds(612, 166, 214, 36);
		pnlEditProfileContent.add(txtPhoneNumber);
		
		txtCurrentPass = new JPasswordField();
		txtCurrentPass.setBounds(30, 437, 214, 34);
		pnlEditProfileContent.add(txtCurrentPass);
		
		txtNewPass = new JPasswordField();
		txtNewPass.setBounds(343, 437, 214, 34);
		pnlEditProfileContent.add(txtNewPass);
		
		txtConfirmedPass = new JPasswordField();
		txtConfirmedPass.setBounds(612, 437, 214, 34);
		pnlEditProfileContent.add(txtConfirmedPass);
		
		JButton btnConfirm = new JButton("Save Changes");
		
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String currentPass = String.valueOf(txtCurrentPass.getPassword());
				String newPass = String.valueOf(txtNewPass.getPassword());
				String conPass = String.valueOf(txtConfirmedPass.getPassword());
				
				System.out.println(currentPass + " " + newPass + " " + conPass);
				
				if(currentPass.equals("")) {
					lblError.setText("Fill in current password to save changes");
					return;
				}
				try {
					if(!Login.loginUser(userUsername, currentPass).equals("1")) {
						lblError.setText("Current password doesn't match database");
						return;
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
					return;
				}
				
				if(newPass.equals("") && conPass.equals("")) {
					Login.updateUser(userUsername, null, txtFirstName.getText(), txtLastName.getText(), txtEmail.getText(), txtPhoneNumber.getText());
					return;
				}
				
				if(!newPass.equals(conPass)) {
					lblError.setText("New Passwords do not match");
					return;
				}
				
				if(currentPass.equals(newPass)) {
					lblError.setText("New password can not be the same as old password");
					return;
				}
				
				if(newPass.equals(conPass)) {
					Login.updateUser(userUsername, conPass, txtFirstName.getText(), txtLastName.getText(), txtEmail.getText(), txtPhoneNumber.getText());
					return;
				}
				
				lblError.setText("idk what u did bro");
				
			}
		});
		btnConfirm.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnConfirm.setBounds(30, 551, 796, 70);
		pnlEditProfileContent.add(btnConfirm);
		
		lblError = new JLabel("");
		lblError.setForeground(Color.RED);
		lblError.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblError.setHorizontalAlignment(SwingConstants.CENTER);
		lblError.setBounds(12, 484, 822, 54);
		pnlEditProfileContent.add(lblError);
		
				pnlEditProfileContent.setVisible(false);
		
		JPanel pnlMainMenuContent = new JPanel();
		pnlMainMenuContent.setBounds(218, 0, 846, 681);
		frame.getContentPane().add(pnlMainMenuContent);
		pnlMainMenuContent.setLayout(null);
		
		lblMainMenuWelcome = new JLabel("Welcome " + userUsername);
		lblMainMenuWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		lblMainMenuWelcome.setFont(new Font("Tahoma", Font.BOLD, 35));
		lblMainMenuWelcome.setBounds(0, 0, 846, 94);
		pnlMainMenuContent.add(lblMainMenuWelcome);
		pnlMainMenuContent.setVisible(true);
		
		JPanel pnlMakeAppContent = new JPanel();
		pnlMakeAppContent.setBounds(218, 0, 846, 681);
		frame.getContentPane().add(pnlMakeAppContent);
		pnlMakeAppContent.setLayout(null);
		
		JLabel lblMakeAppointment_1 = new JLabel("Make Appointment");
		lblMakeAppointment_1.setFont(new Font("Tahoma", Font.BOLD, 35));
		lblMakeAppointment_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblMakeAppointment_1.setBounds(0, 0, 846, 93);
		pnlMakeAppContent.add(lblMakeAppointment_1);
		pnlMakeAppContent.setVisible(false);
		
		JPanel pnlViewAppContent = new JPanel();
		pnlViewAppContent.setBounds(218, 0, 846, 681);
		frame.getContentPane().add(pnlViewAppContent);
		pnlViewAppContent.setLayout(null);
		
		JLabel lblViewAppointments_1 = new JLabel("View Appointments");
		lblViewAppointments_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblViewAppointments_1.setFont(new Font("Tahoma", Font.BOLD, 35));
		lblViewAppointments_1.setBounds(0, 0, 846, 96);
		pnlViewAppContent.add(lblViewAppointments_1);
		pnlViewAppContent.setVisible(false);
		

		/***************************
		 * THIS IS FOR MAIN MENU *
		 ***************************/

		lblMainMenu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				pnlEditProfileContent.setVisible(false);
				pnlMainMenuContent.setVisible(true);
				pnlViewAppContent.setVisible(false);
				pnlMakeAppContent.setVisible(false);
				
				pnlEditProfile.setBackground(SystemColor.activeCaption);
				pnlMainMenu.setBackground(SystemColor.textHighlight);
				pnlViewApp.setBackground(SystemColor.activeCaption);
				pnlMakeApp.setBackground(SystemColor.activeCaption);
			}
		});
		

		/********************************
		 * THIS IS FOR MAKE APPOINTMENT *
		 ********************************/

		lblMakeAppointment.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				pnlEditProfileContent.setVisible(false);
				pnlMainMenuContent.setVisible(false);
				pnlViewAppContent.setVisible(false);
				pnlMakeAppContent.setVisible(true);
				
				pnlEditProfile.setBackground(SystemColor.activeCaption);
				pnlMainMenu.setBackground(SystemColor.activeCaption);
				pnlViewApp.setBackground(SystemColor.activeCaption);
				pnlMakeApp.setBackground(SystemColor.textHighlight);
			}
		});

		/********************************
		 * THIS IS FOR VIEW APPOINTMENT *
		 ********************************/

		lblViewAppointments.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				pnlEditProfileContent.setVisible(false);
				pnlMainMenuContent.setVisible(false);
				pnlViewAppContent.setVisible(true);
				pnlMakeAppContent.setVisible(false);
				
				pnlEditProfile.setBackground(SystemColor.activeCaption);
				pnlMainMenu.setBackground(SystemColor.activeCaption);
				pnlViewApp.setBackground(SystemColor.textHighlight);
				pnlMakeApp.setBackground(SystemColor.activeCaption);
			}
		});
		
		/****************************
		 * THIS IS FOR EDIT PROFILE *
		 ****************************/

		lblEditProfile.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				profileFiller();
				pnlEditProfileContent.setVisible(true);
				pnlMainMenuContent.setVisible(false);
				pnlViewAppContent.setVisible(false);
				pnlMakeAppContent.setVisible(false);
				
				pnlEditProfile.setBackground(SystemColor.textHighlight);
				pnlMainMenu.setBackground(SystemColor.activeCaption);
				pnlViewApp.setBackground(SystemColor.activeCaption);
				pnlMakeApp.setBackground(SystemColor.activeCaption);
				
				
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
	}

	public void setVisible(boolean b) {
		frame.setVisible(b);
	}
}
