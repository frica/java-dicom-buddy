package org.tools.application;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;

import org.tools.dicom.DicomDictionary;
import org.tools.ui.AboutBoxDialog;
import org.tools.ui.ApplicationLanguage;
import org.tools.ui.ApplicationPanelBuilder;
import org.tools.ui.Settings;

import com.jgoodies.looks.Options;
import com.jgoodies.looks.plastic.PlasticLookAndFeel;

/**
 * Main class of the application
 * @author Fabien Rica
 *
 */

@SuppressWarnings("deprecation")
public class DicomBuddy extends JFrame implements ActionListener, ItemListener {
	
	private static final long serialVersionUID = 1L;
	
	/** 
	 * Constructor
	 */
	public DicomBuddy() throws IOException {
		
		initApplicationSettings();
		initDicomDictionary();
		setVisible(true);
	}

	/** 
	 * Initialize Dicom Dictionary from {@link DicomDictionary}
	 */
	private void initDicomDictionary() throws IOException {
		
		// initialize unique instance
		DicomDictionary.getInstance();
	}

	/** 
	 * Create panel from {@link ApplicationPanelBuilder}
	 */ 
	private void buildUI() throws IOException {
		
		builder = new ApplicationPanelBuilder();
		add(builder.buildMainPanel());
	}

	/** 
	 * Initialize menus
	 */
	private void initMenu() {
		
		ApplicationLanguage lang = ApplicationLanguage.getInstance();
		
		appMenu = new JMenuBar();
		setJMenuBar(appMenu);
		
		JMenu fileMenu = new JMenu(lang.getText("File"));
		JMenu aboutMenu = new JMenu(lang.getText("Help"));
		
		exitItem = new JMenuItem(lang.getText("Exit"), 'X');
		aboutItem = new JMenuItem(lang.getText("About"), 'A');
		
		fileMenu.add(exitItem);
		aboutMenu.add(aboutItem);
		
		appMenu.add(fileMenu);
		appMenu.add(aboutMenu);
		
		exitItem.addActionListener(this);
		aboutItem.addActionListener(this);
	}

	/** 
	 * Initialize application UI
	 */
	private void initApplicationSettings() throws IOException {
		
		setTitle(APPLICATION_TITLE);
		setSize(PREFERRED_SIZE);
		
		// to center frame
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		initUserInterfaceStyle();
		initMenu();
		buildUI();
	}

	/** 
	 * Initialize UI style
	 * 
	 */
	private void initUserInterfaceStyle() {
		Settings settings = Settings.createDefault();
		
		// Set font options    
		UIManager.put(
				Options.USE_SYSTEM_FONTS_APP_KEY,
				settings.isUseSystemFonts());
		Options.setUseNarrowButtons(settings.isUseNarrowButtons());

		// Global options
		Options.setTabIconsEnabled(settings.isTabIconsEnabled());
		UIManager.put(Options.POPUP_DROP_SHADOW_ENABLED_KEY, 
				settings.isPopupDropShadowEnabled());

		LookAndFeel selectedLaf = settings.getSelectedLookAndFeel();
		if (selectedLaf instanceof PlasticLookAndFeel) {
			PlasticLookAndFeel.setCurrentTheme(settings.getSelectedTheme());
			//PlasticLookAndFeel.setTabStyle(settings.getPlasticTabStyle());
			//PlasticLookAndFeel.setHighContrastFocusColorsEnabled(
			//		settings.isPlasticHighContrastFocusEnabled());
		} else if (selectedLaf.getClass() == MetalLookAndFeel.class) {
			MetalLookAndFeel.setCurrentTheme(new DefaultMetalTheme());
		}
		        
		try {
			UIManager.setLookAndFeel(selectedLaf);
		} catch (Exception e) {
			System.err.println("Can't change L&F: " + e);
		}
		// NOTE Too bad the "com.sun.java.swing.plaf.gtk.GTKLookAndFeel is full of bugs
	}
		
	
	 public void actionPerformed(ActionEvent e) {
	
		 Object source = e.getSource();
		 if (source == exitItem) {
			 System.out.println("Exiting Application");
			 System.exit(0);
		 }
			
		 if (source == aboutItem) {
			 AboutBoxDialog aboutDlg = new AboutBoxDialog(this, lang.getText("AboutEx"), true);
			 aboutDlg.setVisible(true);
		 }
	}	 
	 
	 /** 
	  * Never used
	  */
	 public void itemStateChanged(ItemEvent e) {
	 }

	 private 	JMenuBar 	appMenu;
	 private	JMenuItem 	exitItem;
	 private 	JMenuItem  	aboutItem;
	 private	ApplicationPanelBuilder builder;

	 private static ApplicationLanguage lang = null;
	 private static final Dimension PREFERRED_SIZE = new Dimension(310, 320);
	 private static final String APPLICATION_TITLE = "Java Dicom Buddy";

	 /** Describes optional settings of the JGoodies Looks. */
	 @SuppressWarnings("unused")
	 private final Settings settings = null;


	 public static void main(String s[ ]) throws IOException {
		 
		 try {
			 lang = ApplicationLanguage.getInstance();
			 System.out.println("LOCALE is " + lang.getLocale().toString());
			 //lang.setLocale(new Locale("en", "EN")); //"fr", "FR"));
	
			 DicomBuddy app = new DicomBuddy();
	
		 } catch (RuntimeException e) {
			 System.err.println("Could not launch application!");
		 }
	 }
}