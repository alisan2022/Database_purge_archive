package org.example;

import org.apache.commons.cli.CommandLine;

import java.sql.*;

public class Config {
    private String host;
    private String username;
    private String password;
    private int port;
    private boolean purge;
    private static Config instance = null;
    private final String driver = "com.mysql.cj.jdbc.Driver";

    private Config() {}

    static {
        try {
            Class.forName(getInstance().driver);
        } catch (ClassNotFoundException e) {
            System.out.println("Woah class not found sad :(");
            throw new RuntimeException(e);
        }
    }

    public static Config getInstance() {
        if (instance == null) {
            instance = new Config();
        }
        return instance;
    }

    public void loadConfigFromCmd(CommandLine cmd) {
        if (cmd.hasOption("host")) {
            this.host = cmd.getOptionValue("host");
        }

        if (cmd.hasOption("username")) {
            this.username = cmd.getOptionValue("username");
        }

        if (cmd.hasOption("password")) {
            this.password = cmd.getOptionValue("password");
        }

        if (cmd.hasOption("port")) {
            this.port = Integer.parseInt(cmd.getOptionValue("port"));
        }

        if (cmd.hasOption("purge")) {
            this.purge = Boolean.parseBoolean(cmd.getOptionValue("purge"));
        }
    }

    public void checker() {
        if (host == null) {
            //System.out.println("Give me a god damn host.");
            throw new RuntimeException("Give me a god damn host.");
        }

        if (username == null || username.isBlank()) {
            //System.out.println("Where's the username idiot ?");
            throw new RuntimeException("Where's the username idiot ?");
        }

        if (password == null || password.trim().isEmpty()) {
            //System.out.println("You forgot the password"); //throw new exception try catch in app
            throw new RuntimeException("You forgot the password");}

        if (port < 1000 || port > 9999) {
            //System.out.println("Invalid port length. Amount of your stupidness beyond my compute.");
            throw new RuntimeException("Invalid port length. Amount of your stupidness beyond my compute.");
        }

    }

    Connection getConnection() {
        Connection connection = null;
        try {
            String url = "jdbc:mysql://" + host + ":" + port + "/dbconfig31";
            connection = DriverManager.getConnection(url, "alisan", "");
            String sql = "INSERT INTO dbconfig32 (dbport, dbhost, dbusername, dbpassword, dbpurge) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setInt(1, port);
                pstmt.setString(2, host);
                pstmt.setString(3, username);
                pstmt.setString(4, password);
                pstmt.setBoolean(5, purge);
                try (ResultSet rs = pstmt.executeQuery()) {
                    rs.getArray(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public String getHost() {
        return host;
    }

    public Config setHost(String host) {
        this.host = host;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public Config setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Config setPassword(String password) {
        this.password = password;
        return this;
    }

    public int getPort() {
        return port;
    }

    public Config setPort(int port) {
        this.port = port;
        return this;
    }

    public boolean isPurge() {
        return purge;
    }

    public Config setPurge(boolean purge) {
        this.purge = purge;
        return this;
    }
}
