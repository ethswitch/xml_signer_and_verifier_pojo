package org.ips.xml.signer.xmlsigner.configs;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppConfigCache {

    private static final ConcurrentHashMap<String, String> cache = new ConcurrentHashMap<>();
    private static final String DEFAULT_PROPERTIES_FILE = "application.properties";
    private static final String PROFILE_PROPERTY = "spring.profiles.active";
    private static final String PLACEHOLDER_REGEX = "\\$\\{([^}]+)\\}";

     static {
         try {
             loadProperties();
         } catch (IOException e) {
             throw new RuntimeException(e);
         }
     }
    // Load properties and handle profiles
    public static void loadProperties() throws IOException {
        loadPropertiesFromFile(DEFAULT_PROPERTIES_FILE);

        String activeProfile = getProperty(PROFILE_PROPERTY);
        if (StringUtils.isNotBlank(activeProfile)) {
            loadPropertiesFromFile("application-" + activeProfile + ".properties");
        }
    }

    // Load properties from the specified file into cache
    private static void loadPropertiesFromFile(String fileName) throws IOException {
        Properties properties = new Properties();

        try (InputStream inputStream = AppConfigCache.class.getClassLoader().getResourceAsStream(fileName)) {
            if (inputStream == null) {
                throw new IOException("Properties file not found in classpath: " + fileName);
            }
            properties.load(inputStream);
        }

        // Add all properties to the cache
        properties.stringPropertyNames().forEach(key -> cache.put(key, properties.getProperty(key).trim()));
    }

    // Get property from cache and resolve any placeholders
    public static String getProperty(String key) {
        String value = cache.get(key);
        return value != null ? replacePlaceholdersWithMap(value, cache) : null;
    }

    // Check if cache contains a property
    public static boolean containsKey(String key) {
        return cache.containsKey(key);
    }

    // Add or update a property in the cache
    public static void setProperty(String key, String value) {
        cache.put(key, value);
    }

    // Remove a property from the cache
    public static void removeProperty(String key) {
        cache.remove(key);
    }

    // Replace placeholders in the form ${...} with values from the map
    private static String replacePlaceholdersWithMap(String original, Map<String, String> replacements) {
        if (original == null || original.isEmpty()) {
            return original;
        }

        Pattern pattern = Pattern.compile(PLACEHOLDER_REGEX);
        Matcher matcher = pattern.matcher(original);
        StringBuffer resultString = new StringBuffer();

        // Iterate over all matches
        while (matcher.find()) {
            String placeholderKey = matcher.group(1); // Extract key from ${key}
            String replacement = replacements.getOrDefault(placeholderKey, matcher.group(0)); // Replace with value or keep placeholder
            matcher.appendReplacement(resultString, replacement);
        }

        matcher.appendTail(resultString); // Append the rest of the original string
        return resultString.toString();
    }
}
