package org.tools.ui;

import java.io.IOException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JTabbedPane;

/**
 * This class constructs the main panel
 * @author Fabien Rica
 */
public class ApplicationPanelBuilder {

		
	public JComponent buildMainPanel() throws IOException {
		
		ApplicationLanguage lang = ApplicationLanguage.getInstance();
        tabbedPane = new JTabbedPane();
        tabbedPane.putClientProperty("jgoodies.noContentBorder", Boolean.TRUE);

        ImageIcon icon1 = createImageIcon("/icons/size_sort.png");
        tagSearchPanel = new TagSearchPanel();
        tabbedPane.addTab(lang.getText("Searchbytag"), icon1, tagSearchPanel);
        
        ImageIcon icon2 = createImageIcon("/icons/alphab_sort.png");
        textSearchPanel = new TextSearchPanel();
        tabbedPane.addTab(lang.getText("Searchbyname"), icon2, textSearchPanel);
        
        return tabbedPane;
    }
	
	/** Returns an ImageIcon, or null if the path was invalid. */
	private ImageIcon createImageIcon(String path) {
		
	    URL imgURL = getClass().getResource(path);
	    if (imgURL != null) {
	        return new ImageIcon(imgURL);
	    } else {
	        System.err.println("Couldn't find file: " + path);
	        return null;
	    }
	}
	
	private TagSearchPanel 	tagSearchPanel;
	private TextSearchPanel textSearchPanel;
	private JTabbedPane 	tabbedPane;
}
