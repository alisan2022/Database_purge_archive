package org.example;

import org.apache.commons.cli.*;

import java.sql.Connection;

public class App {
    public static void main(String[] args) {
        Options options = getOptions();

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            System.out.println("Error error.");
            return;
        }

        Config config = Config.getInstance();
        config.loadConfigFromCmd(cmd);

        /*
        if (!config.checker()) {
            System.exit(1);
        }
        */
        config.checker();


        try (Connection connection = config.getConnection()) {
            if (connection != null) {
                System.out.println("Database connection established successfully.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Output configuration details
        System.out.println("Host: " + config.getHost());
        System.out.println("Username: " + config.getUsername());
        System.out.println("Password: " + config.getPassword());
        System.out.println("Port: " + config.getPort());
        System.out.println("Purge: " + config.isPurge());
    }

    private static Options getOptions() {
        Options options = new Options();

        Option host = new Option("h", "host", true, "database host");
        options.addOption(host);

        Option username = new Option("u", "username", true, "database username");
        options.addOption(username);

        Option password = new Option("p", "password", true, "database password");
        options.addOption(password);

        Option port = new Option("P", "port", true, "database port");
        options.addOption(port);

        Option purge = new Option("g", "purge", true, "purge database (true/false)");
        options.addOption(purge);

        return options;
    }
}
