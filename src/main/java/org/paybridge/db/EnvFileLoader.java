package org.paybridge.db;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class EnvFileLoader {

    private static final Map<String, String> ENV_FILE_VALUES = loadEnvFile();

    private EnvFileLoader() {
    }

    public static String get(String key, String defaultValue) {
        String systemValue = System.getenv(key);
        if (systemValue != null && !systemValue.isBlank()) {
            return systemValue;
        }

        return ENV_FILE_VALUES.getOrDefault(key, defaultValue);
    }

    private static Map<String, String> loadEnvFile() {
        Map<String, String> values = new HashMap<>();
        Path envPath = Path.of(".env");

        if (!Files.exists(envPath)) {
            return values;
        }

        try {
            List<String> lines = Files.readAllLines(envPath);
            for (String rawLine : lines) {
                String line = rawLine.trim();
                if (line.isEmpty() || line.startsWith("#") || !line.contains("=")) {
                    continue;
                }

                String[] parts = line.split("=", 2);
                values.put(parts[0].trim(), parts[1].trim());
            }
        } catch (IOException ignored) {
            return values;
        }

        return values;
    }
}
