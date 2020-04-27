package com.github.antoinejt.ppepersonnel.config;

public enum Config {
    TYPE_PASSERELLE(new ConfigProperty("type_passerelle", "SERIALIZATION", validateTypePasserelle())),
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

    private static Validator<String, InvalidConfigException> validateTypePasserelle() {
        return new Validator<>(
                passerelle -> passerelle.equals("JDBC") || passerelle.equals("SERIALIZATION"),
                new ExceptionThrower<>(InvalidConfigException.class,
                        "Le type de passerelle est invalide ! Utilisation de la valeur par défaut : SERIALIZATION"));
    }
}
