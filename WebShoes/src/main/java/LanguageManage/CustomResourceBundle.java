package LanguageManage;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public class CustomResourceBundle extends ResourceBundle {
    private final Properties properties;

    public CustomResourceBundle(String baseName, Locale locale) {
        properties = new Properties();
        try (InputStreamReader reader = new InputStreamReader(
                getClass().getResourceAsStream(baseName + "_" + locale.getLanguage() + ".properties"), "UTF-8")) {
            properties.load(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Object handleGetObject(String key) {
        return properties.get(key);
    }

    @Override
    public Enumeration<String> getKeys() {
        return (Enumeration<String>) properties.propertyNames();
    }
}
