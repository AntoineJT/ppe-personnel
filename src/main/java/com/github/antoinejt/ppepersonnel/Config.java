package com.github.antoinejt.ppepersonnel;

import com.moandjiezana.toml.Toml;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public enum Config {
    TYPE_PASSERELLE(new ConfigProperty("type_passerelle", "SERIALIZATION")),
    DB_DRIVER(new ConfigProperty("database.driver", "mysql")),
    DB_DRIVER_CLASSNAME(new ConfigProperty("database.driver_classname", "com.mysql.cj.jdbc.Driver")),
    DB_HOST(new ConfigProperty("database.host", "localhost")),
    DB_PORT(new ConfigProperty("database.port", "3306")),
    DB_NAME(new ConfigProperty("database.name", "personnel")),
    DB_USER(new ConfigProperty("database.user", "root")),
    DB_PASSWORD(new ConfigProperty("database.password", ""));

    private final String value;

    Config(ConfigProperty property) {
        this.value = property.toString();
    }

    @Override
    public String toString() {
        return value;
    }
}

class ConfigProperty {
    private static Toml toml = null;
    private final String value;

    ConfigProperty(String property, String defaultValue) {
        this.value = toml.getString(property, defaultValue);
    }

    @Override
    public String toString() {
        return value;
    }

    static {
        File cfgFile = new File("config.toml");
        if (!cfgFile.exists()) {
            try {
                assert cfgFile.createNewFile();
                extractFileFromInside("/config.toml", cfgFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        toml = new Toml().read(cfgFile);
    }

    private static void extractFileFromInside(String insidePath, File file) throws IOException {
        String content = "";
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(Config.class.getResourceAsStream(insidePath)))) {
            content = reader.lines().collect(Collectors.joining("\n"));
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(content);
        }
    }
}
