package org.tools.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * This class implements the About dialog box
 * @author Fabien Rica
 *
 */
public class AboutBoxDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;

	public AboutBoxDialog(JFrame parent, String title, boolean modal) {
		
		super(parent, title, modal);
		
		ApplicationLanguage lang = ApplicationLanguage.getInstance();
		
		setBackground(Color.black);
		JPanel p1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		StringBuffer text = new StringBuffer();
		text.append(lang.getText("Developer") +": Fabien Rica\n");
		text.append("Version: 1.0\n");
		text.append("Data dictionary: DICOM 2009 (dcm4che 2.0.21)");
		JTextArea jtAreaAbout = new JTextArea(4, 21);
		jtAreaAbout.setText(text.toString());
		jtAreaAbout.setFont(new Font("Arial", Font.PLAIN, 13));
		jtAreaAbout.setEditable(false);
		p1.add(jtAreaAbout);

		getContentPane().add(p1, BorderLayout.CENTER);
		JPanel p2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		jbnOk = new JButton(" OK ");
		jbnOk.addActionListener(this);
		p2.add(jbnOk);
		getContentPane().add(p2, BorderLayout.SOUTH);
		//setLocation(408, 270);
		
		setLocationRelativeTo(null);
		setResizable(false);
		addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				Window aboutDialog = e.getWindow();
				aboutDialog.dispose();
			}
		});
		
		pack();
	}
	
	JButton jbnOk;

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jbnOk) {
			this.dispose();
		}
	}
}
	
