import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.JInternalFrame;
import java.awt.Dialog.ModalExclusionType;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;

public class GUI {

	private JFrame frmDentaskV;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frmDentaskV.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmDentaskV = new JFrame();
		frmDentaskV.setResizable(false);
		frmDentaskV.setTitle("DenTask v2.0");
		frmDentaskV.setBounds(100, 100, 1000, 600);
		frmDentaskV.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDentaskV.getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("630px"),
				ColumnSpec.decode("56px"),},
			new RowSpec[] {
				RowSpec.decode("210px"),
				RowSpec.decode("16px"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));
		
		JLabel label = new JLabel("New label");
		frmDentaskV.getContentPane().add(label, "2, 6, center, center");
	}

}
