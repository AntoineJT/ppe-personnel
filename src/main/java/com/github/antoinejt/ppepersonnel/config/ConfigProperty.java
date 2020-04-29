package com.github.antoinejt.ppepersonnel.config;

import com.moandjiezana.toml.Toml;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

class ConfigProperty {
	private static Toml toml = null;

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

	private final String value;

	ConfigProperty(String property, String defaultValue) {
		this.value = toml.getString(property, defaultValue);
	}

	ConfigProperty(String property, String defaultValue, Validator<String> validator) {
		this.value = toml.getString(property, defaultValue);
		try {
			validator.validate(value);
		} catch (Exception e) {
			e.printStackTrace();
		}
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

	@Override
	public String toString() {
		return value;
	}
}
