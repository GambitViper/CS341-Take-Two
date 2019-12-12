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
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.LinkedList;
import java.util.Locale;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;

import Classes.Appointment;
import Classes.AppointmentCalendar;
import Classes.ComboItem;
import Classes.Database;
import Classes.Login;
import Classes.User;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

public class Dashboard {

	private JFrame frame;
	private JTextField txtFirstName;
	private JTextField txtLastName;
	private JTextField txtEmail;
	private JTextField txtPhoneNumber;
	private JPasswordField txtCurrentPass;
	private JPasswordField txtNewPass;
	private JPasswordField txtConfirmedPass;
	private JComboBox cboxAppointments;
	private JLabel lblAppDate;
	private JTextArea txtArea;

	private JLabel lblError;
	private JLabel lblMainMenuWelcome;

	private String userUsername;

	private JComboBox<ComboItem> month_cb;
	private JComboBox<ComboItem> day_cb;
	private JComboBox<ComboItem> year_cb;
	private LocalDate apptDate;
	private JComboBox<ComboItem> apptTime_cb;
	private JComboBox<ComboItem> emplSelect_cb;
	private JTextField apptType_field;

	private Appointment viewedApp;
	private final ButtonGroup buttonGroup = new ButtonGroup();

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

	/**
	 * Stores a local version of the Username after login
	 */
	public void setUser(String username) {
		userUsername = username.toLowerCase();

		User tmp;

		try {
			tmp = Login.findUserByUsername(userUsername);
		} catch (SQLException e) {
			e.printStackTrace();
			lblMainMenuWelcome.setText("Welcome " + userUsername);
			return;
		}
		lblMainMenuWelcome.setText("Welcome " + tmp.getFirstName() + " " + tmp.getLastName());
	}

	/**
	 * Helper method to fill profile of Edit Profile Screen
	 */
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
		frame.setBounds(dim.width / 2 - (1080 / 2), dim.height / 2 - (720 / 2), 1080, 720);
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
		pnlLogOut.setBounds(0, 621, 220, 72);
		pnlLogOut.setBorder(blackline);
		panel.add(pnlLogOut);
		pnlLogOut.setLayout(null);

		JLabel lblNewLabel = new JLabel("Log Out");
		lblNewLabel.setBackground(SystemColor.inactiveCaption);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(0, 0, 220, 74);
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
		
				JPanel pnlMainMenuContent = new JPanel();
				pnlMainMenuContent.setBounds(218, 0, 846, 681);
				frame.getContentPane().add(pnlMainMenuContent);
				pnlMainMenuContent.setLayout(null);
				
						lblMainMenuWelcome = new JLabel("Welcome " + userUsername);
						lblMainMenuWelcome.setHorizontalAlignment(SwingConstants.CENTER);
						lblMainMenuWelcome.setFont(new Font("Tahoma", Font.BOLD, 35));
						lblMainMenuWelcome.setBounds(0, 0, 846, 94);
						pnlMainMenuContent.add(lblMainMenuWelcome);
						
								JLabel lblWelcomeToDentask = new JLabel("Welcome to DenTask!");
								lblWelcomeToDentask.setFont(new Font("Tahoma", Font.BOLD, 20));
								lblWelcomeToDentask.setHorizontalAlignment(SwingConstants.CENTER);
								lblWelcomeToDentask.setBounds(10, 179, 826, 110);
								pnlMainMenuContent.add(lblWelcomeToDentask);
								
										JLabel lblSelectAnyOf = new JLabel("Select any of the panel's on the right menu to go to that page");
										lblSelectAnyOf.setHorizontalAlignment(SwingConstants.CENTER);
										lblSelectAnyOf.setFont(new Font("Tahoma", Font.PLAIN, 20));
										lblSelectAnyOf.setBounds(10, 348, 826, 64);
										pnlMainMenuContent.add(lblSelectAnyOf);
										
										JLabel lblTheUserManual = new JLabel("The User Manual is available at request");
										lblTheUserManual.setHorizontalAlignment(SwingConstants.CENTER);
										lblTheUserManual.setFont(new Font("Tahoma", Font.PLAIN, 20));
										lblTheUserManual.setBounds(10, 404, 826, 64);
										pnlMainMenuContent.add(lblTheUserManual);
										pnlMainMenuContent.setVisible(true);

		JPanel pnlViewAppContent = new JPanel();
		pnlViewAppContent.setBounds(218, 0, 846, 681);
		frame.getContentPane().add(pnlViewAppContent);
		pnlViewAppContent.setLayout(null);

		JLabel lblViewAppointments_1 = new JLabel("View Appointments");
		lblViewAppointments_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblViewAppointments_1.setFont(new Font("Tahoma", Font.BOLD, 35));
		lblViewAppointments_1.setBounds(10, 0, 826, 96);
		pnlViewAppContent.add(lblViewAppointments_1);

		cboxAppointments = new JComboBox();

		cboxAppointments.setBounds(154, 230, 578, 49);
		pnlViewAppContent.add(cboxAppointments);

		JLabel lblSelectAnAppointment = new JLabel("Select an appointment to view details");
		lblSelectAnAppointment.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblSelectAnAppointment.setHorizontalAlignment(SwingConstants.CENTER);
		lblSelectAnAppointment.setBounds(10, 179, 826, 38);
		pnlViewAppContent.add(lblSelectAnAppointment);

		JLabel lblNewLabel_1 = new JLabel("Employee");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_1.setBounds(154, 314, 104, 38);
		pnlViewAppContent.add(lblNewLabel_1);

		JLabel lblAppEmployee = new JLabel("");
		lblAppEmployee.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblAppEmployee.setBounds(154, 363, 137, 38);
		pnlViewAppContent.add(lblAppEmployee);

		JLabel lblDate = new JLabel("Date");
		lblDate.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblDate.setBounds(481, 314, 104, 38);
		pnlViewAppContent.add(lblDate);

		JLabel lblAppointmentType_1 = new JLabel("Appointment Type");
		lblAppointmentType_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblAppointmentType_1.setBounds(154, 446, 197, 38);
		pnlViewAppContent.add(lblAppointmentType_1);

		JLabel lblDetails = new JLabel("Details");
		lblDetails.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblDetails.setBounds(481, 446, 197, 38);
		pnlViewAppContent.add(lblDetails);

		lblAppDate = new JLabel("");
		lblAppDate.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblAppDate.setBounds(481, 363, 137, 38);
		pnlViewAppContent.add(lblAppDate);

		JLabel lblAppType = new JLabel("");
		lblAppType.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblAppType.setBounds(154, 499, 137, 38);
		pnlViewAppContent.add(lblAppType);

		txtArea = new JTextArea();
		txtArea.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtArea.setEditable(false);
		txtArea.setBounds(479, 505, 246, 149);
		txtArea.setBackground(new Color(240, 240, 240));
		txtArea.setLineWrap(true);
		pnlViewAppContent.add(txtArea);

		JButton btnCancelAppointment = new JButton("Cancel Appointment");
		btnCancelAppointment.setEnabled(false);
		btnCancelAppointment.setBounds(154, 592, 232, 59);
		pnlViewAppContent.add(btnCancelAppointment);

		JRadioButton radActive = new JRadioButton("Active");
		buttonGroup.add(radActive);
		radActive.setBounds(259, 132, 127, 25);
		pnlViewAppContent.add(radActive);
		radActive.setSelected(true);

		JRadioButton radCancelled = new JRadioButton("Cancelled");
		buttonGroup.add(radCancelled);
		radCancelled.setBounds(458, 132, 127, 25);

		pnlViewAppContent.add(radCancelled);
		pnlViewAppContent.setVisible(false);

		btnCancelAppointment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				Database db = new Database();
				db.connect();

				db.deleteAppointment(viewedApp.getPatient(), viewedApp.getEmployee(), viewedApp.getDate().toString(),
						viewedApp.getTime());

			}
		});

		JPanel pnlMakeAppContent = new JPanel();
		pnlMakeAppContent.setBounds(218, 0, 846, 681);
		frame.getContentPane().add(pnlMakeAppContent);
		pnlMakeAppContent.setLayout(null);
		JLabel lblMakeAppointment_1 = new JLabel("Make Appointment");
		lblMakeAppointment_1.setFont(new Font("Tahoma", Font.BOLD, 35));
		lblMakeAppointment_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblMakeAppointment_1.setBounds(0, 0, 846, 93);
		pnlMakeAppContent.add(lblMakeAppointment_1);

		JLabel lblAppointmentDate = new JLabel("Appointment Date");
		lblAppointmentDate.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblAppointmentDate.setBounds(345, 87, 149, 30);
		pnlMakeAppContent.add(lblAppointmentDate);

		JLabel lblMonth = new JLabel("Month");
		lblMonth.setBounds(297, 114, 46, 14);
		pnlMakeAppContent.add(lblMonth);

		month_cb = new JComboBox<>();
		month_cb.setBounds(297, 128, 93, 20);
		pnlMakeAppContent.add(month_cb);

		JLabel lblDay = new JLabel("Day");
		lblDay.setHorizontalAlignment(SwingConstants.CENTER);
		lblDay.setBounds(400, 114, 46, 14);
		pnlMakeAppContent.add(lblDay);

		day_cb = new JComboBox<>();
		day_cb.setBounds(400, 128, 46, 20);
		pnlMakeAppContent.add(day_cb);

		JLabel lblYear = new JLabel("Year");
		lblYear.setHorizontalAlignment(SwingConstants.RIGHT);
		lblYear.setBounds(492, 114, 46, 14);
		pnlMakeAppContent.add(lblYear);

		year_cb = new JComboBox<>();
		year_cb.setBounds(456, 128, 82, 20);
		pnlMakeAppContent.add(year_cb);

		JLabel lblAppointmentTime = new JLabel("Appointment Time");
		lblAppointmentTime.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblAppointmentTime.setBounds(345, 159, 149, 30);
		pnlMakeAppContent.add(lblAppointmentTime);

		apptTime_cb = new JComboBox();
		apptTime_cb.setBounds(308, 200, 220, 20);
		pnlMakeAppContent.add(apptTime_cb);

		JLabel lblDentistHygienist = new JLabel("Dentist / Hygienist");
		lblDentistHygienist.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblDentistHygienist.setBounds(345, 231, 149, 30);
		pnlMakeAppContent.add(lblDentistHygienist);

		emplSelect_cb = new JComboBox<>();
		emplSelect_cb.setBounds(308, 272, 220, 20);
		pnlMakeAppContent.add(emplSelect_cb);

		JLabel lblAppointmentType = new JLabel("Appointment Type");
		lblAppointmentType.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblAppointmentType.setBounds(345, 303, 149, 30);
		pnlMakeAppContent.add(lblAppointmentType);

		apptType_field = new JTextField();
		apptType_field.setBounds(308, 344, 220, 20);
		pnlMakeAppContent.add(apptType_field);
		apptType_field.setColumns(10);

		JLabel lblAppointmentDetails = new JLabel("Appointment Details");
		lblAppointmentDetails.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblAppointmentDetails.setBounds(340, 375, 164, 30);
		pnlMakeAppContent.add(lblAppointmentDetails);

		JTextArea apptDetail_txt = new JTextArea();
		apptDetail_txt.setBounds(260, 416, 314, 93);
		pnlMakeAppContent.add(apptDetail_txt);

		JButton btnMakeAppointment = new JButton("Make Appointment");
		btnMakeAppointment.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnMakeAppointment.setBounds(324, 533, 183, 40);
		pnlMakeAppContent.add(btnMakeAppointment);
		pnlMakeAppContent.setVisible(false);

		month_cb.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg) {
				ComboItem selectedMonth = (ComboItem) month_cb.getSelectedItem();
				ComboItem selectedDay = (ComboItem) day_cb.getSelectedItem();
				ComboItem selectedYear = (ComboItem) year_cb.getSelectedItem();
				ComboItem selectedTime = (ComboItem) apptTime_cb.getSelectedItem();

				// Populates the appropriate number of days for selected year and month
				fillDays(selectedYear, selectedMonth);

				// Populate employeeSelect list
				fillEmployeeSelect(selectedMonth, selectedDay, selectedYear, selectedTime);
			}
		});

		day_cb.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg) {
				ComboItem selectedMonth = (ComboItem) month_cb.getSelectedItem();
				ComboItem selectedDay = (ComboItem) day_cb.getSelectedItem();
				ComboItem selectedYear = (ComboItem) year_cb.getSelectedItem();
				ComboItem selectedTime = (ComboItem) apptTime_cb.getSelectedItem();

				// Populate employeeSelect list
				fillEmployeeSelect(selectedMonth, selectedDay, selectedYear, selectedTime);
			}
		});

		year_cb.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg) {
				ComboItem selectedMonth = (ComboItem) month_cb.getSelectedItem();
				ComboItem selectedDay = (ComboItem) day_cb.getSelectedItem();
				ComboItem selectedYear = (ComboItem) year_cb.getSelectedItem();
				ComboItem selectedTime = (ComboItem) apptTime_cb.getSelectedItem();

				// Populates the appropriate number of days for selected year and month
				fillDays(selectedYear, selectedMonth);

				// Populate employeeSelect list
				fillEmployeeSelect(selectedMonth, selectedDay, selectedYear, selectedTime);
			}
		});

		apptTime_cb.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg) {
				ComboItem selectedMonth = (ComboItem) month_cb.getSelectedItem();
				ComboItem selectedDay = (ComboItem) day_cb.getSelectedItem();
				ComboItem selectedYear = (ComboItem) year_cb.getSelectedItem();
				ComboItem selectedTime = (ComboItem) apptTime_cb.getSelectedItem();

				// Populate employeeSelect list
				fillEmployeeSelect(selectedMonth, selectedDay, selectedYear, selectedTime);
			}
		});

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
				AppointmentCalendar.fillDentists();
				AppointmentCalendar.fillHygienists();
				AppointmentCalendar.fillAppointments();

				month_cb.removeAllItems();

				String[] months = { "January", "February", "March", "April", "May", "June", "July", "August",
						"September", "October", "November", "December" };
				for (int i = 0; i < months.length; i++) {
					String monthLabel = months[i];
					String monthValue = "" + (i + 1);
					month_cb.addItem(new ComboItem(monthLabel, monthValue));
				}
				month_cb.setSelectedIndex(0);

				day_cb.removeAllItems();

				for (int i = 0; i < 31; i++) {
					String day = "" + (i + 1);
					day_cb.addItem(new ComboItem(day, day));
				}

				year_cb.removeAllItems();
				String[] years = { "2019", "2020" };
				for (String year : years) {
					year_cb.addItem(new ComboItem(year, year));
				}
				year_cb.setSelectedIndex(0);

				apptTime_cb.removeAllItems();

				String[] times = { "8:00 - 9:00", "9:00 - 10:00", "10:00 - 11:00", "11:00 - 12:00", "12:00 - 13:00",
						"13:00 - 14:00", "14:00 - 15:00", "15:00 - 16:00", "16:00 - 17:00" };
				for (int i = 0; i < times.length; i++) {
					String timeLabel = times[i];
					String timeValue = "" + (i + 8);
					apptTime_cb.addItem(new ComboItem(timeLabel, timeValue));
				}
				apptTime_cb.setSelectedIndex(0);

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

				radActive.setSelected(true);
				radCancelled.setSelected(false);

				btnCancelAppointment.setEnabled(false);
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

				txtCurrentPass.setText(null);
				txtNewPass.setText(null);
				txtConfirmedPass.setText(null);
				lblError.setText("");

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

		/****************************************
		 * THIS IS FOR FILLING APPOINTMENT INFO *
		 ****************************************/
		cboxAppointments.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {

				Object selected = cboxAppointments.getSelectedItem();

				if (selected != null) {

					viewedApp = ((ComboItem) selected).getApp();

					User dh;

					try {
						dh = Login.findUserByUsername(viewedApp.getEmployee());
					} catch (SQLException e1) {
						e1.printStackTrace();
						return;
					}

					if (dh != null) {
						lblAppEmployee.setText(dh.getFirstName() + " " + dh.getLastName());
					} else {
						lblAppEmployee.setText("User was deleted");
					}
					lblAppDate.setText(viewedApp.getDate().toString() + " @ " + viewedApp.getTime());
					txtArea.setText(viewedApp.getAppDetail());
					lblAppType.setText(viewedApp.getAppType());
					btnCancelAppointment.setEnabled(true);

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

		/*****************************************
		 * THIS IS FOR EDIT PROFILE CONFIRMATION *
		 *****************************************/
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String currentPass = String.valueOf(txtCurrentPass.getPassword());
				String newPass = String.valueOf(txtNewPass.getPassword());
				String conPass = String.valueOf(txtConfirmedPass.getPassword());

				System.out.println(currentPass + " " + newPass + " " + conPass);

				if (currentPass.equals("")) {
					lblError.setText("Fill in current password to save changes");
					return;
				}
				try {
					if (!Login.loginUser(userUsername, currentPass).equals("1")) {
						lblError.setText("Current password doesn't match database");
						return;
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
					return;
				}

				if (newPass.equals("") && conPass.equals("")) {
					Login.updateUser(userUsername, null, txtFirstName.getText(), txtLastName.getText(),
							txtEmail.getText(), txtPhoneNumber.getText());
					txtCurrentPass.setText(null);
					txtNewPass.setText(null);
					txtConfirmedPass.setText(null);
					lblError.setText("");

					pnlEditProfileContent.setVisible(false);
					pnlMainMenuContent.setVisible(true);
					pnlViewAppContent.setVisible(false);
					pnlMakeAppContent.setVisible(false);

					pnlEditProfile.setBackground(SystemColor.activeCaption);
					pnlMainMenu.setBackground(SystemColor.textHighlight);
					pnlViewApp.setBackground(SystemColor.activeCaption);
					pnlMakeApp.setBackground(SystemColor.activeCaption);
					return;
				}

				if (!newPass.equals(conPass)) {
					lblError.setText("New Passwords do not match");
					return;
				}

				if (currentPass.equals(newPass)) {
					lblError.setText("New password can not be the same as old password");
					return;
				}

				if (newPass.equals(conPass)) {
					Login.updateUser(userUsername, conPass, txtFirstName.getText(), txtLastName.getText(),
							txtEmail.getText(), txtPhoneNumber.getText());
					txtCurrentPass.setText(null);
					txtNewPass.setText(null);
					txtConfirmedPass.setText(null);
					lblError.setText("");

					pnlEditProfileContent.setVisible(false);
					pnlMainMenuContent.setVisible(true);
					pnlViewAppContent.setVisible(false);
					pnlMakeAppContent.setVisible(false);

					pnlEditProfile.setBackground(SystemColor.activeCaption);
					pnlMainMenu.setBackground(SystemColor.textHighlight);
					pnlViewApp.setBackground(SystemColor.activeCaption);
					pnlMakeApp.setBackground(SystemColor.activeCaption);
					return;
				}

				lblError.setText("idk what u did bro");

			}
		});

		/**********************************
		 * THIS IS FOR CANCEL APPOINTMENT *
		 **********************************/

		/***************************************
		 * TRIGGER TO SHOW APPOINTMENT DETAILS *
		 ***************************************/

		/**
		 * Make appointment button trigger
		 */
		btnMakeAppointment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg) {
				ComboItem selectedMonth = (ComboItem) month_cb.getSelectedItem();
				ComboItem selectedDay = (ComboItem) day_cb.getSelectedItem();
				ComboItem selectedYear = (ComboItem) year_cb.getSelectedItem();
				ComboItem selectedTime = (ComboItem) apptTime_cb.getSelectedItem();
				ComboItem selectedEmployee = (ComboItem) emplSelect_cb.getSelectedItem();

//				makeAppointment(String patient, String employee, String type, String detail, String date, int time)
				if (selectedMonth != null && selectedDay != null && selectedYear != null && selectedTime != null
						&& selectedEmployee != null) {
					String result = AppointmentCalendar.makeAppointment(userUsername, selectedEmployee.getValue(),
							apptType_field.getText(), apptDetail_txt.getText(), apptDate.toString(),
							Integer.parseInt(selectedTime.getValue()));
					if (result.equals("Successfully created")) {
						pnlEditProfileContent.setVisible(false);
						pnlMainMenuContent.setVisible(true);
						pnlViewAppContent.setVisible(false);
						pnlMakeAppContent.setVisible(false);

						pnlEditProfile.setBackground(SystemColor.activeCaption);
						pnlMainMenu.setBackground(SystemColor.textHighlight);
						pnlViewApp.setBackground(SystemColor.activeCaption);
						pnlMakeApp.setBackground(SystemColor.activeCaption);
					}
					return;
				}

			}
		});

		/**
		 * 
		 */
		cboxAppointments.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {

				LinkedList<Appointment> updatedApps;

				if (radCancelled.isSelected()) {

					updatedApps = popAllApps();

					cboxAppointments.removeAllItems();

					for (int i = 0; i < updatedApps.size(); i++) {
						if (updatedApps.get(i).getResult() != null && updatedApps.get(i).getResult().contains("Cancelled")) {

							String name = (updatedApps.get(i).getDate() + " at " + updatedApps.get(i).getTime());
							Appointment tmp = updatedApps.get(i);
							System.out.println(tmp.toString());

							cboxAppointments.addItem(new ComboItem(name, tmp));
						}
					}
				} else {

					updatedApps = popApps();
					cboxAppointments.removeAllItems();

					for (int i = 0; i < updatedApps.size(); i++) {
						String name = (updatedApps.get(i).getDate() + " at " + updatedApps.get(i).getTime());
						Appointment tmp = updatedApps.get(i);
						System.out.println(tmp.toString());

						cboxAppointments.addItem(new ComboItem(name, tmp));
					}
				}

			}
		});
	}

	private void fillEmployeeSelect(ComboItem selectedMonth, ComboItem selectedDay, ComboItem selectedYear,
			ComboItem selectedTime) {
		if (selectedMonth != null && selectedDay != null && selectedYear != null && selectedTime != null) {
			String month = selectedMonth.getValue();
			String day = selectedDay.getValue();
			String year = selectedYear.getValue();
			String time = selectedTime.getValue();

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
			apptDate = LocalDate.parse(month + "/" + day + "/" + year, formatter); // LocalDate = yyyy-mm-dd
			DayOfWeek dow = apptDate.getDayOfWeek(); // Extracts a `DayOfWeek` enum object.
			String output = dow.getDisplayName(TextStyle.FULL, Locale.US); // String = Tue
			System.out.println(apptDate.toString() + " " + output + " @ " + time);

//			LinkedList<User> employeeSelectList(String dayOfWeek, String date, int time)
			LinkedList<User> emplSelected = AppointmentCalendar.employeeSelectList(output, apptDate.toString(),
					Integer.parseInt(time));

			emplSelect_cb.removeAllItems();

			for (User user : emplSelected) {
				String emplLabel = "";
				// Check if employee is Dentist
				if (user.getUserType() == 1) {
					emplLabel += "Dr. ";
				}
				emplLabel += user.getFirstName() + ", " + user.getLastName();
				emplSelect_cb.addItem(new ComboItem(emplLabel, user.getUsername()));
			}
		}
	}

	private void fillDays(ComboItem selectedYear, ComboItem selectedMonth) {
		if (selectedMonth != null && selectedYear != null) {
			String month = selectedMonth.getKey();
			String year = selectedYear.getValue();

			day_cb.removeAllItems();

			int numDays = YearMonth.of(Integer.parseInt(year), Month.valueOf(month.toUpperCase())).lengthOfMonth();
			for (int i = 0; i < numDays; i++) {
				String day = "" + (i + 1);
				day_cb.addItem(new ComboItem(day, day));
			}
		}
	}

	/**
	 * Helper method to fill a LinkedList of appointments of a given user
	 */
	private LinkedList<Appointment> popApps() {
		Database db = new Database();
		db.connect();

		System.out.println(db.getAppointments(userUsername, true).toString());

		return db.getAppointments(userUsername, true);

	}

	/**
	 * Helper method to fill a LinkedList of appointments of a given user Cancelled
	 * or Not
	 */
	private LinkedList<Appointment> popAllApps() {
		Database db = new Database();
		db.connect();

		return db.getAppointments(userUsername, false);

	}

	/**
	 * Set Frame Visible Helper
	 */
	public void setVisible(boolean b) {
		frame.setVisible(b);
	}
}
