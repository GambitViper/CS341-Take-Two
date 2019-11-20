package GUI;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SignupScreen {

	private JFrame frame;
	private JTextField txtEmail;
	private JTextField txtLastName;
	private JTextField txtFirstName;
	private JTextField txtPhoneNumber;
	private JTextField txtUsername;
	private JPasswordField txtPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignupScreen window = new SignupScreen();
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
	public SignupScreen() {
		initialize();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setBounds(dim.width/2-(629/2), dim.height/2-(309/2), 629, 309);
		frame.setUndecorated(true);

		JLabel btnBack = new JLabel("<");
		btnBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				frame.dispose();
				LoginScreen login = new LoginScreen();
				login.setVisible(true);
			}
		});
		frame.getContentPane().setLayout(null);
		btnBack.setFont(new Font("Tahoma", Font.BOLD, 50));
		btnBack.setBounds(10, 252, 41, 46);
		frame.getContentPane().add(btnBack);
		
		JLabel Logo = new JLabel("");
		Logo.setBounds(26, 47, 230, 194);
		Logo.setIcon(new ImageIcon("logo.png"));
		frame.getContentPane().add(Logo);
		
		JLabel lblCreateANew = new JLabel("Create a new account");
		lblCreateANew.setHorizontalAlignment(SwingConstants.CENTER);
		lblCreateANew.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		lblCreateANew.setBounds(315, 11, 288, 28);
		frame.getContentPane().add(lblCreateANew);
		
		JButton btnNewAccount = new JButton("Create Account");
		btnNewAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
				LoginScreen login = new LoginScreen();
				login.setVisible(true);
			}
		});
		btnNewAccount.setBounds(315, 263, 288, 35);
		frame.getContentPane().add(btnNewAccount);
		
		JLabel lblFirstName = new JLabel("First Name");
		lblFirstName.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblFirstName.setBounds(315, 39, 86, 28);
		frame.getContentPane().add(lblFirstName);
		
		txtFirstName = new JTextField();
		txtFirstName.setColumns(10);
		txtFirstName.setBounds(315, 66, 114, 28);
		frame.getContentPane().add(txtFirstName);
		
		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblLastName.setBounds(489, 39, 86, 28);
		frame.getContentPane().add(lblLastName);
		
		txtLastName = new JTextField();
		txtLastName.setColumns(10);
		txtLastName.setBounds(489, 66, 114, 28);
		frame.getContentPane().add(txtLastName);
		
		JLabel lblUsername = new JLabel("Email");
		lblUsername.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblUsername.setBounds(315, 105, 86, 28);
		frame.getContentPane().add(lblUsername);
		
		txtEmail = new JTextField();
		txtEmail.setColumns(10);
		txtEmail.setBounds(315, 133, 114, 28);
		frame.getContentPane().add(txtEmail);
		
		JLabel lblPassword = new JLabel("Phone Number");
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPassword.setBounds(489, 105, 114, 28);
		frame.getContentPane().add(lblPassword);
		
		txtPhoneNumber = new JTextField();
		txtPhoneNumber.setColumns(10);
		txtPhoneNumber.setBounds(489, 133, 114, 28);
		frame.getContentPane().add(txtPhoneNumber);
		
		JLabel label_1 = new JLabel("Username");
		label_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		label_1.setBounds(315, 189, 86, 28);
		frame.getContentPane().add(label_1);
		
		txtUsername = new JTextField();
		txtUsername.setColumns(10);
		txtUsername.setBounds(315, 214, 114, 28);
		frame.getContentPane().add(txtUsername);
		
		JLabel lblPassword_1 = new JLabel("Password");
		lblPassword_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPassword_1.setBounds(489, 189, 86, 28);
		frame.getContentPane().add(lblPassword_1);
		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(489, 214, 114, 28);
		frame.getContentPane().add(txtPassword);
		frame.setBackground(new Color(255, 255, 255));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.getContentPane().setBackground(new Color(148, 227, 0));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void setVisible(boolean b) {
		frame.setVisible(b);
	}

}
