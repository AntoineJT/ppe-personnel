package com.github.antoinejt.ppepersonnel.config;

public enum Config {
    TYPE_PASSERELLE(new ConfigProperty("type_passerelle", "SERIALIZATION", validateTypePasserelle())),
    DB_DRIVER(new ConfigProperty("database.driver", "mysql")),
    DB_DRIVER_CLASSNAME(new ConfigProperty("database.driver_classname", "com.mysql.cj.jdbc.Driver", validateDriverExists())),
    DB_HOST(new ConfigProperty("database.host", "localhost")),
    DB_PORT(new ConfigProperty("database.port", "3306", validatePort())),
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

    private static ExceptionValidator<String, InvalidConfigException> validateTypePasserelle() {
        return new ExceptionValidator<>(
                passerelle -> passerelle.equals("JDBC") || passerelle.equals("SERIALIZATION"),
                new ExceptionThrower<>(InvalidConfigException.class,
                        "Le type de passerelle est invalide ! Utilisation de la valeur par défaut : SERIALIZATION"));
    }

    private static boolean isSerializationSelected() {
        return !Config.TYPE_PASSERELLE.toString().equals("JDBC");
    }

    private static void exit(String message, long delay) {
        System.err.println(String.format("%s%nArrêt du programme...", message));
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(1);
    }

    private static ActionValidator<String> validateDriverExists() {
        return new ActionValidator<>(
                driver -> {
                    if (isSerializationSelected()) {
                        return true;
                    }
                    try {
                        Class.forName(driver);
                    } catch (ClassNotFoundException e) {
                        return false;
                    }
                    return true;
                },
                () -> {
                    exit("Le driver spécifié n'existe pas dans le classpath !", 2000);
                }
        );
    }

    private static ActionValidator<String> validatePort() {
        return new ActionValidator<>(
                port -> {
                    if (isSerializationSelected()) {
                        return true;
                    }
                    try {
                        int portNumber = Integer.parseInt(port);
                        return portNumber > 0 && portNumber <= 65535;
                    } catch (NumberFormatException e) {
                        return false;
                    }
                }, () -> exit("Le port spécifié est invalide ! Il doit être un nombre entier compris entre 0 et 65535 inclus !", 3000));
    }
}
