package utils;

import java.io.InputStream;
import java.util.Properties;

public class Configuration {
    private static final Properties props = new Properties();

    static {
        try (InputStream in = 
                 Configuration.class.getClassLoader()
                                     .getResourceAsStream("config.properties")) {
            if (in == null) {
                throw new RuntimeException("config.properties not found on classpath");
            }
            props.load(in);
        } catch (Exception e) {
            throw new ExceptionInInitializerError("Failed to load config: " + e.getMessage());
        }
    }

    /** Get any property by key. */
    public static String get(String key) {
        return props.getProperty(key);
    }

    /** Convenience for ints (e.g. timeouts). */
    public static int getInt(String key) {
        return Integer.parseInt(props.getProperty(key));
    }
}
