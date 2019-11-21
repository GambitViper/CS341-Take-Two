package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.SwingConstants;
import javax.swing.JPasswordField;

public class LoginScreen extends JFrame{

	private JFrame frame;
	private JTextField txtUsername;
	private JPasswordField txtPassword;

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
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setBounds(dim.width/2-(629/2), dim.height/2-(309/2), 629, 309);
		frame.setUndecorated(true);

		txtUsername = new JTextField();
		txtUsername.setColumns(10);
		txtUsername.setBounds(376, 63, 185, 35);
		frame.getContentPane().add(txtUsername);
		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(376, 133, 185, 33);
		frame.getContentPane().add(txtPassword);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				frame.dispose();
				Dashboard dash = new Dashboard();
				dash.setVisible(true);
			}
		});
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
		closeButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				frame.dispose();
			}
		});
		closeButton.setFont(new Font("Tahoma", Font.BOLD, 50));
		closeButton.setBounds(10, 254, 40, 44);
		frame.getContentPane().add(closeButton);
		
		JLabel lblClickToCreate = new JLabel("Click to create new account");
		lblClickToCreate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				frame.dispose();
				SignupScreen signup = new SignupScreen();
				signup.setVisible(true);
			}
		});
		lblClickToCreate.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblClickToCreate.setHorizontalAlignment(SwingConstants.CENTER);
		lblClickToCreate.setBounds(376, 254, 185, 31);
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
		frame.setBackground(new Color(255, 255, 255));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(148, 227, 0));
		frame.getContentPane().setLayout(null);
		
	}

	public void setVisible(boolean b) {
		frame.setVisible(b);
	}
}