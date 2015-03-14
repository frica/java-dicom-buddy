package org.tools.ui;

import java.awt.Event;
import java.util.EventListener;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;


public class ApplicationLanguage {

	private ApplicationLanguage(){
		setLocale(/*new Locale("en", "EN")*/Locale.getDefault());
	}
	
	private static ApplicationLanguage language = null;
	
	public static ApplicationLanguage getInstance() {
        if (language == null) {
        	language = new ApplicationLanguage();
        }
        return language;
    }
	
	ResourceBundle bundle = null;
	Locale currentLocale = null;
	
	public static final String MISSING_RESOURCE = "Missing Resource";
	
    public interface Listener extends EventListener {
        public void languageChanged(Event event);
    }
	
    /**
     * Return the current Locale.
     * 
     * @return The current Locale.
     */
    public Locale getLocale() {
        return currentLocale;
    }
	
	public void setLocale(Locale locale) {
        currentLocale = locale;
        Locale.setDefault(locale);
        bundle = ResourceBundle.getBundle("lang/i18n", currentLocale);
    }
	
    public String getText(String key) {
        try {
            return bundle.getString(key);
        } catch (MissingResourceException e) {
            return MISSING_RESOURCE + " '" + key + "'";
        }
    };
}
