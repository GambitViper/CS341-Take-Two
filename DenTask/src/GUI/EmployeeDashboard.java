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
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.LinkedList;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import Classes.Appointment;
import Classes.AppointmentCalendar;
import Classes.ComboItem;
import Classes.Database;
import Classes.Login;
import Classes.User;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

public class EmployeeDashboard {

	private JFrame frame;
	private JTextField txtFirstName;
	private JTextField txtLastName;
	private JTextField txtEmail;
	private JTextField txtPhoneNumber;
	private JPasswordField txtCurrentPassword;
	private JPasswordField txtNewPassword;
	private JPasswordField txtConfirmedPassword;
	private JComboBox cboxViewedAppointment;
	private JTextArea txtAppDetails;

	private JComboBox<ComboItem> month_cb;
	private JComboBox<ComboItem> day_cb;
	private JComboBox<ComboItem> year_cb;
	private LocalDate apptDate;
	private JComboBox<ComboItem> apptTime_cb;
	private JComboBox<ComboItem> emplSelect_cb;
	private JTextField apptType_field;

	private JComboBox cbMondayS;
	private JComboBox cbMondayE;
	private JComboBox cbTuesdayS;
	private JComboBox cbTuesdayE;
	private JComboBox cbWednesdayS;
	private JComboBox cbWednesdayE;
	private JComboBox cbThursdayS;
	private JComboBox cbThursdayE;
	private JComboBox cbFridayS;
	private JComboBox cbFridayE;
	private JLabel lblError;
	private JLabel lblMainMenuWelcome;

	private String userUsername;
	private Appointment viewedApp;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EmployeeDashboard window = new EmployeeDashboard();
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
	public EmployeeDashboard() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
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

		JPanel pnlAvailability = new JPanel();
		pnlAvailability.setBorder(new LineBorder(new Color(0, 0, 0)));
		pnlAvailability.setBounds(0, 337, 220, 60);
		panel.add(pnlAvailability);
		pnlAvailability.setLayout(null);
		pnlAvailability.setBackground(SystemColor.activeCaption);

		JLabel lblAvailability = new JLabel("Availability");
		lblAvailability.setHorizontalAlignment(SwingConstants.CENTER);
		lblAvailability.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblAvailability.setBounds(0, 0, 220, 60);
		pnlAvailability.add(lblAvailability);

		JPanel pnlMainMenu = new JPanel();
		pnlMainMenu.setBackground(SystemColor.textHighlight);
		pnlMainMenu.setBounds(0, 160, 220, 60);
		pnlMainMenu.setBorder(blackline);
		panel.add(pnlMainMenu);
		pnlMainMenu.setLayout(null);

		JLabel lblMainMenu = new JLabel("Employee Dashboard");
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
		pnlEditProfile.setBounds(0, 395, 220, 60);
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

		JLabel lblLogOut = new JLabel("Log Out");
		lblLogOut.setBackground(SystemColor.inactiveCaption);
		lblLogOut.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblLogOut.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogOut.setBounds(0, 0, 220, 60);
		pnlLogOut.add(lblLogOut);

		JLabel lblCreatedBy = new JLabel("Created By:");
		lblCreatedBy.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCreatedBy.setHorizontalAlignment(SwingConstants.CENTER);
		lblCreatedBy.setBounds(0, 516, 220, 28);
		panel.add(lblCreatedBy);

		JLabel lblMattMilosTom = new JLabel("Matt Milos, Tom Jensen, Zach Baklund");
		lblMattMilosTom.setHorizontalAlignment(SwingConstants.CENTER);
		lblMattMilosTom.setBounds(0, 540, 220, 28);
		panel.add(lblMattMilosTom);

		JLabel lbldentask = new JLabel("\u00A9 DenTask 2019");
		lbldentask.setHorizontalAlignment(SwingConstants.CENTER);
		lbldentask.setBounds(0, 594, 220, 28);
		panel.add(lbldentask);
		pnlLogOut.setBorder(blackline);
		
				JPanel pnlMainMenuContent = new JPanel();
				pnlMainMenuContent.setBounds(218, 0, 846, 681);
				frame.getContentPane().add(pnlMainMenuContent);
				pnlMainMenuContent.setLayout(null);
				
						lblMainMenuWelcome = new JLabel("Main Menu");
						lblMainMenuWelcome.setHorizontalAlignment(SwingConstants.CENTER);
						lblMainMenuWelcome.setFont(new Font("Tahoma", Font.BOLD, 35));
						lblMainMenuWelcome.setBounds(0, 0, 846, 94);
						pnlMainMenuContent.add(lblMainMenuWelcome);
						
								JLabel lblWelcomeToDentask = new JLabel("Welcome to DenTask!");
								lblWelcomeToDentask.setHorizontalAlignment(SwingConstants.CENTER);
								lblWelcomeToDentask.setFont(new Font("Tahoma", Font.BOLD, 20));
								lblWelcomeToDentask.setBounds(10, 160, 836, 94);
								pnlMainMenuContent.add(lblWelcomeToDentask);
								
										JLabel lblSelectAnyOf = new JLabel("Select any of the panel's on the right menu to go to that page");
										lblSelectAnyOf.setFont(new Font("Tahoma", Font.PLAIN, 20));
										lblSelectAnyOf.setHorizontalAlignment(SwingConstants.CENTER);
										lblSelectAnyOf.setBounds(10, 284, 826, 60);
										pnlMainMenuContent.add(lblSelectAnyOf);
										
										JLabel lblTheUserManual = new JLabel("The User Manual is available at request");
										lblTheUserManual.setHorizontalAlignment(SwingConstants.CENTER);
										lblTheUserManual.setFont(new Font("Tahoma", Font.PLAIN, 20));
										lblTheUserManual.setBounds(10, 339, 826, 60);
										pnlMainMenuContent.add(lblTheUserManual);
										pnlMainMenuContent.setVisible(true);

		JPanel pnlViewAppContent = new JPanel();
		pnlViewAppContent.setBounds(218, 0, 846, 681);
		frame.getContentPane().add(pnlViewAppContent);
		pnlViewAppContent.setLayout(null);

		JLabel lblViewAppointments_1 = new JLabel("View Appointments");
		lblViewAppointments_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblViewAppointments_1.setFont(new Font("Tahoma", Font.BOLD, 35));
		lblViewAppointments_1.setBounds(0, 0, 846, 96);
		pnlViewAppContent.add(lblViewAppointments_1);

		JLabel lblSelectAppoint = new JLabel("Select Appointment");
		lblSelectAppoint.setHorizontalAlignment(SwingConstants.CENTER);
		lblSelectAppoint.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblSelectAppoint.setBounds(12, 178, 826, 45);
		pnlViewAppContent.add(lblSelectAppoint);

		cboxViewedAppointment = new JComboBox();
		cboxViewedAppointment.setBounds(252, 236, 350, 38);
		pnlViewAppContent.add(cboxViewedAppointment);

		JLabel lblNewLabel = new JLabel("Patient");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setBounds(120, 292, 86, 38);
		pnlViewAppContent.add(lblNewLabel);

		JLabel lblDate = new JLabel("Date");
		lblDate.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblDate.setBounds(458, 292, 86, 38);
		pnlViewAppContent.add(lblDate);

		JLabel lblAppointmentType1 = new JLabel("Appointment Type");
		lblAppointmentType1.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblAppointmentType1.setBounds(120, 442, 220, 45);
		pnlViewAppContent.add(lblAppointmentType1);

		JLabel lblAppointmentDetails1 = new JLabel("Appointment Details");
		lblAppointmentDetails1.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblAppointmentDetails1.setBounds(458, 442, 220, 45);
		pnlViewAppContent.add(lblAppointmentDetails1);

		JLabel lblAppPatient = new JLabel("");
		lblAppPatient.setBounds(120, 351, 131, 38);
		pnlViewAppContent.add(lblAppPatient);

		JLabel lblAppDate = new JLabel("");
		lblAppDate.setBounds(458, 351, 131, 38);
		pnlViewAppContent.add(lblAppDate);

		JLabel lblAppType = new JLabel("");
		lblAppType.setBounds(120, 498, 131, 38);
		pnlViewAppContent.add(lblAppType);

		txtAppDetails = new JTextArea();
		txtAppDetails.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtAppDetails.setEditable(false);
		txtAppDetails.setBounds(458, 498, 271, 137);
		txtAppDetails.setBackground(new Color(240, 240, 240));
		txtAppDetails.setLineWrap(true);
		pnlViewAppContent.add(txtAppDetails);

		JButton btnCancelAppointment = new JButton("Cancel Appointment");
		btnCancelAppointment.setEnabled(false);
		btnCancelAppointment.setBounds(120, 577, 220, 58);
		pnlViewAppContent.add(btnCancelAppointment);

		JRadioButton radActive = new JRadioButton("Active");
		buttonGroup.add(radActive);
		radActive.setBounds(252, 126, 127, 25);
		radActive.setSelected(true);
		pnlViewAppContent.add(radActive);

		JRadioButton radCancelled = new JRadioButton("Cancelled");
		buttonGroup.add(radCancelled);
		radCancelled.setBounds(475, 126, 127, 25);
		pnlViewAppContent.add(radCancelled);
		pnlViewAppContent.setVisible(false);

		cboxViewedAppointment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				Object selected = cboxViewedAppointment.getSelectedItem();

				if (selected != null) {

					viewedApp = ((ComboItem) selected).getApp();

					User pat;
					try {
						pat = Login.findUserByUsername(viewedApp.getPatient());
					} catch (SQLException e1) {
						e1.printStackTrace();
						return;
					}

					lblAppPatient.setText(pat.getFirstName() + " " + pat.getLastName());
					lblAppDate.setText(viewedApp.getDate().toString() + " @ " + viewedApp.getTime());
					txtAppDetails.setText(viewedApp.getAppDetail());
					lblAppType.setText(viewedApp.getAppType());
					btnCancelAppointment.setEnabled(true);

				}

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

		JPanel pnlAvailabilityContent = new JPanel();
		pnlAvailabilityContent.setBounds(221, 0, 843, 681);
		frame.getContentPane().add(pnlAvailabilityContent);
		pnlAvailabilityContent.setLayout(null);
		pnlAvailabilityContent.setVisible(false);

		JLabel lblAvailability_1 = new JLabel("Set Availability");
		lblAvailability_1.setBounds(0, 0, 846, 62);
		pnlAvailabilityContent.add(lblAvailability_1);
		lblAvailability_1.setFont(new Font("Tahoma", Font.BOLD, 35));
		lblAvailability_1.setHorizontalAlignment(SwingConstants.CENTER);

		JComboBox cbMondayS_1 = new JComboBox();
		cbMondayS_1.setBounds(97, 213, 90, 33);
		pnlAvailabilityContent.add(cbMondayS_1);

		JComboBox cbMondayE_1 = new JComboBox();
		cbMondayE_1.setBounds(97, 291, 90, 33);
		pnlAvailabilityContent.add(cbMondayE_1);

		JComboBox cbTuesdayS_1 = new JComboBox();
		cbTuesdayS_1.setBounds(226, 213, 90, 33);
		pnlAvailabilityContent.add(cbTuesdayS_1);

		JComboBox cbWednesdayS_1 = new JComboBox();
		cbWednesdayS_1.setBounds(353, 213, 90, 33);
		pnlAvailabilityContent.add(cbWednesdayS_1);

		JComboBox cbThursdayS_1 = new JComboBox();
		cbThursdayS_1.setBounds(479, 213, 90, 33);
		pnlAvailabilityContent.add(cbThursdayS_1);

		JComboBox cbFridayS_1 = new JComboBox();
		cbFridayS_1.setBounds(612, 213, 90, 33);
		pnlAvailabilityContent.add(cbFridayS_1);

		JComboBox cbTuesdayE_1 = new JComboBox();
		cbTuesdayE_1.setBounds(226, 291, 90, 33);
		pnlAvailabilityContent.add(cbTuesdayE_1);

		JComboBox cbWednesdayE_1 = new JComboBox();
		cbWednesdayE_1.setBounds(353, 291, 90, 33);
		pnlAvailabilityContent.add(cbWednesdayE_1);

		JComboBox cbThursdayE_1 = new JComboBox();
		cbThursdayE_1.setBounds(479, 291, 90, 33);
		pnlAvailabilityContent.add(cbThursdayE_1);

		JComboBox cbFridayE_1 = new JComboBox();
		cbFridayE_1.setBounds(612, 291, 90, 33);
		pnlAvailabilityContent.add(cbFridayE_1);

		JLabel lblMonday = new JLabel("Monday");
		lblMonday.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblMonday.setHorizontalAlignment(SwingConstants.CENTER);
		lblMonday.setBounds(97, 173, 90, 29);
		pnlAvailabilityContent.add(lblMonday);

		JLabel lblTuesday = new JLabel("Tuesday");
		lblTuesday.setHorizontalAlignment(SwingConstants.CENTER);
		lblTuesday.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblTuesday.setBounds(226, 173, 90, 29);
		pnlAvailabilityContent.add(lblTuesday);

		JLabel lblWednesday = new JLabel("Wednesday");
		lblWednesday.setHorizontalAlignment(SwingConstants.CENTER);
		lblWednesday.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblWednesday.setBounds(353, 173, 90, 29);
		pnlAvailabilityContent.add(lblWednesday);

		JLabel lblThursday = new JLabel("Thursday");
		lblThursday.setHorizontalAlignment(SwingConstants.CENTER);
		lblThursday.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblThursday.setBounds(479, 173, 90, 29);
		pnlAvailabilityContent.add(lblThursday);

		JLabel lblFriday = new JLabel("Friday");
		lblFriday.setHorizontalAlignment(SwingConstants.CENTER);
		lblFriday.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblFriday.setBounds(612, 173, 90, 29);
		pnlAvailabilityContent.add(lblFriday);

		JButton btnUpdateAvailability = new JButton("Set/Update Availability");
		btnUpdateAvailability.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnUpdateAvailability.setBounds(98, 422, 604, 51);
		pnlAvailabilityContent.add(btnUpdateAvailability);

		pnlLogOut.setBorder(blackline);

		JPanel pnlEditProfileContent = new JPanel();
		pnlEditProfileContent.setBounds(218, 0, 846, 681);
		frame.getContentPane().add(pnlEditProfileContent);
		pnlEditProfileContent.setLayout(null);

		JLabel lblEditProfile_1 = new JLabel("Edit Profile");
		lblEditProfile_1.setFont(new Font("Tahoma", Font.BOLD, 40));
		lblEditProfile_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditProfile_1.setBounds(10, 0, 826, 88);
		pnlEditProfileContent.add(lblEditProfile_1);

		JLabel lblFirstName = new JLabel("First Name");
		lblFirstName.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblFirstName.setBounds(84, 190, 140, 27);
		pnlEditProfileContent.add(lblFirstName);

		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblLastName.setBounds(427, 190, 140, 27);
		pnlEditProfileContent.add(lblLastName);

		JLabel lblCurrentPassword = new JLabel("Current Password");
		lblCurrentPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblCurrentPassword.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblCurrentPassword.setBounds(84, 421, 140, 36);
		pnlEditProfileContent.add(lblCurrentPassword);

		JLabel lblNewPassword = new JLabel("New Password");
		lblNewPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewPassword.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewPassword.setBounds(84, 480, 140, 34);
		pnlEditProfileContent.add(lblNewPassword);

		JLabel lblConfirmPassword = new JLabel("Confirm Password");
		lblConfirmPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblConfirmPassword.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblConfirmPassword.setBounds(84, 536, 140, 34);
		pnlEditProfileContent.add(lblConfirmPassword);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblEmail.setBounds(84, 295, 140, 27);
		pnlEditProfileContent.add(lblEmail);

		JLabel lblPhoneNumber = new JLabel("Phone Number");
		lblPhoneNumber.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblPhoneNumber.setBounds(427, 295, 140, 27);
		pnlEditProfileContent.add(lblPhoneNumber);

		JButton btnConfirm = new JButton("Save Changes");
		btnConfirm.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnConfirm.setBounds(267, 606, 214, 50);
		pnlEditProfileContent.add(btnConfirm);

		txtFirstName = new JTextField();
		txtFirstName.setBounds(84, 229, 214, 36);
		pnlEditProfileContent.add(txtFirstName);
		txtFirstName.setColumns(10);

		txtLastName = new JTextField();
		txtLastName.setColumns(10);
		txtLastName.setBounds(427, 228, 214, 36);
		pnlEditProfileContent.add(txtLastName);

		txtEmail = new JTextField();
		txtEmail.setColumns(10);
		txtEmail.setBounds(84, 333, 214, 36);
		pnlEditProfileContent.add(txtEmail);

		txtPhoneNumber = new JTextField();
		txtPhoneNumber.setColumns(10);
		txtPhoneNumber.setBounds(427, 333, 214, 36);
		pnlEditProfileContent.add(txtPhoneNumber);

		txtCurrentPassword = new JPasswordField();
		txtCurrentPassword.setBounds(267, 423, 214, 36);
		pnlEditProfileContent.add(txtCurrentPassword);

		txtNewPassword = new JPasswordField();
		txtNewPassword.setBounds(267, 480, 214, 36);
		pnlEditProfileContent.add(txtNewPassword);

		txtConfirmedPassword = new JPasswordField();
		txtConfirmedPassword.setBounds(267, 534, 214, 36);
		pnlEditProfileContent.add(txtConfirmedPassword);

		lblError = new JLabel("");
		lblError.setHorizontalAlignment(SwingConstants.CENTER);
		lblError.setForeground(Color.RED);
		lblError.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblError.setBounds(20, 88, 816, 65);
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
				pnlAvailabilityContent.setVisible(false);

				pnlEditProfile.setBackground(SystemColor.activeCaption);
				pnlMainMenu.setBackground(SystemColor.textHighlight);
				pnlViewApp.setBackground(SystemColor.activeCaption);
				pnlMakeApp.setBackground(SystemColor.activeCaption);
				pnlAvailability.setBackground(SystemColor.activeCaption);
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
				pnlAvailabilityContent.setVisible(false);

				pnlEditProfile.setBackground(SystemColor.activeCaption);
				pnlMainMenu.setBackground(SystemColor.activeCaption);
				pnlViewApp.setBackground(SystemColor.activeCaption);
				pnlMakeApp.setBackground(SystemColor.textHighlight);
				pnlAvailability.setBackground(SystemColor.activeCaption);
			}
		});

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
				pnlAvailabilityContent.setVisible(false);

				pnlEditProfile.setBackground(SystemColor.activeCaption);
				pnlMainMenu.setBackground(SystemColor.activeCaption);
				pnlViewApp.setBackground(SystemColor.textHighlight);
				pnlMakeApp.setBackground(SystemColor.activeCaption);
				pnlAvailability.setBackground(SystemColor.activeCaption);
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
				pnlAvailabilityContent.setVisible(false);

				pnlEditProfile.setBackground(SystemColor.textHighlight);
				pnlMainMenu.setBackground(SystemColor.activeCaption);
				pnlViewApp.setBackground(SystemColor.activeCaption);
				pnlMakeApp.setBackground(SystemColor.activeCaption);
				pnlAvailability.setBackground(SystemColor.activeCaption);
			}
		});

		/****************************
		 * THIS IS FOR AVAILABILITY *
		 ****************************/

		lblAvailability.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {

				String timeFormatted;
				String timeActual;

				cbMondayS_1.removeAllItems();
				cbMondayE_1.removeAllItems();
				cbTuesdayS_1.removeAllItems();
				cbTuesdayE_1.removeAllItems();
				cbWednesdayS_1.removeAllItems();
				cbWednesdayE_1.removeAllItems();
				cbThursdayS_1.removeAllItems();
				cbThursdayE_1.removeAllItems();
				cbFridayS_1.removeAllItems();
				cbFridayE_1.removeAllItems();

				for (int i = 8; i < 18; i++) {

					if (i < 12) {
						timeFormatted = (i + ":00 AM");
					} else if (i == 12) {
						timeFormatted = (12 + ":00 PM");
					} else {
						timeFormatted = ((i - 12) + ":00 PM");
					}

					timeActual = i + "00";

					cbMondayS_1.addItem(new ComboItem(timeFormatted, timeActual));
					cbTuesdayS_1.addItem(new ComboItem(timeFormatted, timeActual));
					cbWednesdayS_1.addItem(new ComboItem(timeFormatted, timeActual));
					cbThursdayS_1.addItem(new ComboItem(timeFormatted, timeActual));
					cbFridayS_1.addItem(new ComboItem(timeFormatted, timeActual));
				}

				pnlEditProfileContent.setVisible(false);
				pnlMainMenuContent.setVisible(false);
				pnlViewAppContent.setVisible(false);
				pnlMakeAppContent.setVisible(false);
				pnlAvailabilityContent.setVisible(true);

				pnlEditProfile.setBackground(SystemColor.activeCaption);
				pnlMainMenu.setBackground(SystemColor.activeCaption);
				pnlViewApp.setBackground(SystemColor.activeCaption);
				pnlMakeApp.setBackground(SystemColor.activeCaption);
				pnlAvailability.setBackground(SystemColor.textHighlight);
			}
		});

		/**********************
		 * THIS IS FOR LOGOUT *
		 **********************/

		lblLogOut.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				frame.dispose();
				LoginScreen login = new LoginScreen();
				login.setVisible(true);
			}
		});

		/****************************
		 * THIS IS FOR SAVE PROFILE *
		 ****************************/

		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String currentPass = String.valueOf(txtCurrentPassword.getPassword());
				String newPass = String.valueOf(txtNewPassword.getPassword());
				String conPass = String.valueOf(txtConfirmedPassword.getPassword());

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
					txtCurrentPassword.setText(null);
					txtNewPassword.setText(null);
					txtConfirmedPassword.setText(null);
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
					txtCurrentPassword.setText(null);
					txtNewPassword.setText(null);
					txtConfirmedPassword.setText(null);
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
		btnCancelAppointment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Database db = new Database();
				db.connect();

				db.deleteAppointment(viewedApp.getPatient(), viewedApp.getEmployee(), viewedApp.getDate().toString(),
						viewedApp.getTime());

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
		});

		/*********************
		 * SAVE AVAILABILITY *
		 *********************/
		btnUpdateAvailability.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Database tmp = new Database();
				tmp.connect();

				Object tmp1 = cbMondayS_1.getSelectedItem();
				String valMS = ((ComboItem) tmp1).getValue();

				Object tmp2 = cbMondayE_1.getSelectedItem();
				String valME = ((ComboItem) tmp1).getValue();

				tmp.updateDailyAvailability(userUsername, "Monday", valMS, valME);

				Object tmp3 = cbTuesdayS_1.getSelectedItem();
				String valTS = ((ComboItem) tmp3).getValue();

				Object tmp4 = cbTuesdayE_1.getSelectedItem();
				String valTE = ((ComboItem) tmp4).getValue();

				tmp.updateDailyAvailability(userUsername, "Tuesday", valTS, valTE);

				Object tmp5 = cbWednesdayS_1.getSelectedItem();
				String valWS = ((ComboItem) tmp5).getValue();

				Object tmp6 = cbWednesdayE_1.getSelectedItem();
				String valWE = ((ComboItem) tmp6).getValue();

				tmp.updateDailyAvailability(userUsername, "Wednesday", valWS, valWE);

				Object tmp7 = cbThursdayS_1.getSelectedItem();
				String valTHS = ((ComboItem) tmp7).getValue();

				Object tmp8 = cbThursdayE_1.getSelectedItem();
				String valTHE = ((ComboItem) tmp8).getValue();

				tmp.updateDailyAvailability(userUsername, "Thursday", valTHS, valTHE);

				Object tmp9 = cbFridayS_1.getSelectedItem();
				String valFS = ((ComboItem) tmp9).getValue();

				Object tmp10 = cbFridayE_1.getSelectedItem();
				String valFE = ((ComboItem) tmp10).getValue();

				tmp.updateDailyAvailability(userUsername, "Friday", valFS, valFE);

			}
		});
		/*********************************
		 * ITEM CHANGED FOR AVAILABILITY *
		 *********************************/
		cbMondayS_1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				String tf;
				String ta;

				cbMondayE_1.removeAllItems();
				for (int i = cbMondayS_1.getSelectedIndex() + 9; i < 18; i++) {

					if (i < 12) {
						tf = (i + ":00 AM");
					} else if (i == 12) {
						tf = (12 + ":00 PM");
					} else {
						tf = ((i - 12) + ":00 PM");
					}

					ta = i + "00";

					cbMondayE_1.addItem(new ComboItem(tf, ta));
				}

			}
		});
		cbTuesdayS_1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				String tf;
				String ta;

				cbTuesdayE_1.removeAllItems();
				for (int i = cbTuesdayS_1.getSelectedIndex() + 9; i < 18; i++) {

					if (i < 12) {
						tf = (i + ":00 AM");
					} else if (i == 12) {
						tf = (12 + ":00 PM");
					} else {
						tf = ((i - 12) + ":00 PM");
					}

					ta = i + "00";

					cbTuesdayE_1.addItem(new ComboItem(tf, ta));
				}
			}
		});
		cbWednesdayS_1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				String tf;
				String ta;

				cbWednesdayE_1.removeAllItems();
				for (int i = cbWednesdayS_1.getSelectedIndex() + 9; i < 18; i++) {

					if (i < 12) {
						tf = (i + ":00 AM");
					} else if (i == 12) {
						tf = (12 + ":00 PM");
					} else {
						tf = ((i - 12) + ":00 PM");
					}

					ta = i + "00";

					cbWednesdayE_1.addItem(new ComboItem(tf, ta));
				}
			}
		});
		cbThursdayS_1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				String tf;
				String ta;

				cbThursdayE_1.removeAllItems();
				for (int i = cbThursdayS_1.getSelectedIndex() + 9; i < 18; i++) {

					if (i < 12) {
						tf = (i + ":00 AM");
					} else if (i == 12) {
						tf = (12 + ":00 PM");
					} else {
						tf = ((i - 12) + ":00 PM");
					}

					ta = i + "00";

					cbThursdayE_1.addItem(new ComboItem(tf, ta));
				}
			}
		});
		cbFridayS_1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				String tf;
				String ta;

				cbFridayE_1.removeAllItems();
				for (int i = cbFridayS_1.getSelectedIndex() + 9; i < 18; i++) {

					if (i < 12) {
						tf = (i + ":00 AM");
					} else if (i == 12) {
						tf = (12 + ":00 PM");
					} else {
						tf = ((i - 12) + ":00 PM");
					}

					ta = i + "00";

					cbFridayE_1.addItem(new ComboItem(tf, ta));
				}
			}
		});

		/**
		 * Fills in appointment details based on selected appointment
		 */
		cboxViewedAppointment.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {

				LinkedList<Appointment> updatedApps;

				if (radCancelled.isSelected()) {

					updatedApps = popAllApps();

					cboxViewedAppointment.removeAllItems();

					for (int i = 0; i < updatedApps.size(); i++) {
						if (updatedApps.get(i).getResult() != null
								&& updatedApps.get(i).getResult().contains("Cancelled")) {

							String name = (updatedApps.get(i).getDate() + " at " + updatedApps.get(i).getTime());
							Appointment tmp = updatedApps.get(i);
							System.out.println(tmp.toString());

							cboxViewedAppointment.addItem(new ComboItem(name, tmp));
						}
					}
				} else {

					updatedApps = popApps();
					cboxViewedAppointment.removeAllItems();

					for (int i = 0; i < updatedApps.size(); i++) {
						String name = (updatedApps.get(i).getDate() + " at " + updatedApps.get(i).getTime());
						Appointment tmp = updatedApps.get(i);
						System.out.println(tmp.toString());

						cboxViewedAppointment.addItem(new ComboItem(name, tmp));
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
