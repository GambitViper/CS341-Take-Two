package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JTextField;
import javax.swing.UIManager;

import Classes.Login;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;

import javax.swing.SwingConstants;
import javax.swing.JPasswordField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class LoginScreen extends JFrame{

	/**
	 * Local Variables for the Login Screen
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private JTextField txtUsername;
	private JPasswordField txtPassword;
	private JLabel lblError;

	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginScreen window = new LoginScreen();
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
	public LoginScreen() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setBounds(dim.width/2-(629/2), dim.height/2-(309/2), 629, 309);
		frame.setUndecorated(true);
		frame.isResizable();
		frame.getContentPane().setBackground(new Color(148, 227, 0));
		frame.getContentPane().setLayout(null);
		
		txtUsername = new JTextField();
		txtUsername.setColumns(10);
		txtUsername.setBounds(376, 63, 185, 35);
		frame.getContentPane().add(txtUsername);
		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(376, 133, 185, 33);
		frame.getContentPane().add(txtPassword);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setBounds(376, 202, 185, 30);
		frame.getContentPane().add(btnLogin);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblUsername.setBounds(265, 63, 101, 35);
		frame.getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblPassword.setBounds(265, 131, 101, 35);
		frame.getContentPane().add(lblPassword);
		
		JLabel closeButton = new JLabel("X");
		closeButton.setFont(new Font("Tahoma", Font.BOLD, 50));
		closeButton.setBounds(10, 254, 40, 44);
		frame.getContentPane().add(closeButton);
		
		JLabel lblClickToCreate = new JLabel("Click to create new account");
		lblClickToCreate.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblClickToCreate.setHorizontalAlignment(SwingConstants.CENTER);
		lblClickToCreate.setBounds(376, 267, 185, 31);
		frame.getContentPane().add(lblClickToCreate);

		JLabel Logo = new JLabel("");
		Logo.setBounds(25, 49, 230, 194);
		Logo.setIcon(new ImageIcon("logo.png"));
		frame.getContentPane().add(Logo);
		
		JLabel lblWelcome = new JLabel("Welcome");
		lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcome.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblWelcome.setBounds(376, 17, 185, 35);
		frame.getContentPane().add(lblWelcome);
		
		lblError = new JLabel("");
		lblError.setForeground(Color.RED);
		lblError.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblError.setHorizontalAlignment(SwingConstants.CENTER);
		lblError.setBounds(337, 239, 264, 22);
		frame.getContentPane().add(lblError);
		frame.setBackground(new Color(255, 255, 255));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		/************************
		 * LOGIN BUTTON TRIGGER *
		 ************************/
		btnLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				
				String myPass = String.valueOf(txtPassword.getPassword());
				String confirmation = "didnt return";
				System.out.println(myPass);
				System.out.println(txtUsername.getText());
				int uType = 3;
				
				
				try {
					confirmation = Login.loginUser(txtUsername.getText().toLowerCase(), myPass);
					uType = Login.getUserType(txtUsername.getText().toLowerCase());
					if(confirmation.equals("This user has been removed")) {
						lblError.setText("User was deleted");
						return;
					}
					if(confirmation.equals("1") && uType == 3) {
						Dashboard dash = new Dashboard();
						dash.setUser(txtUsername.getText());
						dash.setVisible(true);
						frame.dispose();
						return;
					} else if(confirmation.equals("1") && (uType == 2 || uType == 1)) {
						frame.dispose();
						EmployeeDashboard edash = new EmployeeDashboard();
						edash.setVisible(true);
						return;
					} else if(confirmation.equals("1") && (uType == 0)) {
						frame.dispose();
						AdminDashboard adash = new AdminDashboard();
						adash.setVisible(true);	
						return;
					}
					
					lblError.setText("Username or Password Invalid!");
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		/**************************
		 * CREATE ACCOUNT TRIGGER *
		 **************************/
		lblClickToCreate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				frame.dispose();
				SignupScreen signup = new SignupScreen();
				signup.setVisible(true);
			}
		});

		/*********************
		 * ENTER KEY TRIGGER *
		 *********************/
		txtPassword.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent evt) {
				if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
					String myPass = String.valueOf(txtPassword.getPassword());
					String confirmation = "didnt return";
					System.out.println(myPass);
					System.out.println(txtUsername.getText());
					int uType = 3;
					
					
					try {
						confirmation = Login.loginUser(txtUsername.getText().toLowerCase(), myPass);
						uType = Login.getUserType(txtUsername.getText().toLowerCase());
						
						if(confirmation.equals("1") && uType == 3) {
							Dashboard dash = new Dashboard();
							dash.setUser(txtUsername.getText());
							dash.setVisible(true);
							frame.dispose();
							return;
						} else if(confirmation.equals("1") && (uType == 2 || uType == 1)) {
							EmployeeDashboard edash = new EmployeeDashboard();
							edash.setUser(txtUsername.getText());
							edash.setVisible(true);
							frame.dispose();
							return;
						} else if(confirmation.equals("1") && (uType == 0)) {
							AdminDashboard adash = new AdminDashboard();
							adash.setVisible(true);	
							frame.dispose();
							return;
						}
						
						lblError.setText("Username or Password Invalid!");
						
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});

		/************************
		 * CLOSE BUTTON TRIGGER *
		 ************************/
		closeButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				frame.dispose();
			}
		});
	}

	/*************************
	 * Sets frame to visible *
	 *************************/
	public void setVisible(boolean b) {
		frame.setVisible(b);
	}
}
