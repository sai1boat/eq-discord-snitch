package com.sai1boat.eqdiscordsnitch;


import java.io.*;

public class Main {


    public static void main(String[] args) {
        final String inputFile;
        if (args == null || args.length ==0){
            inputFile = Constants.inputFile;
        }
        else {
            inputFile = args[0];
        }

        boolean keepReading = true;
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(inputFile));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String line;
        while (keepReading) {
            try {
                line = reader.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (line == null) {
                //wait until there is more of the file for us to read
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            else {
                DiscordBot.searchForTargets(line);
            }
        }
    }
}