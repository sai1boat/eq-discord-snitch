package com.sai1boat.eqdiscordsnitch;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Constants {

    static String token_default = "You can hardcode your discord bot token here";
    static String channel_default = "You can hardcode the name of your channel here";
    static String inputFile_default = "You can hardcode the path to your log file here.";
    static String token = "Alternatively, create a Constants.properties file in " +
            "resources with a token=<your token>";
    static String channel = "Alternatively, Create a Constants.properties file in " +
            "resources with a channel=<your channel>";
    static String inputFile = "Alternatively, create a Constants.properties file in " +
            "resource with a inputFile=<path to your log file>";

    static {
        Properties p = new Properties();
        try {
            InputStream input = Constants.class.getClassLoader()
                    .getResourceAsStream("Constants.properties");
            p.load(input);
            token = p.getProperty("token", token_default);
            channel = p.getProperty("channel", channel_default);
            inputFile = p.getProperty("inputFile", inputFile_default);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
