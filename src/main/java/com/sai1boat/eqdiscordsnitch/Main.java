package com.sai1boat.eqdiscordsnitch;


import java.io.*;

public class Main {

    static PriceTarget[] priceTargets = new PriceTarget[]{ new PriceTarget("Granite Face Grinder",8000),
                                     new PriceTarget("Fungus Covered Scale Tunic", 40000),
                                     new PriceTarget("Fungi Covered Great Staff", 8000)};


    private static void searchForTargets(String line) {
        for(PriceTarget t : priceTargets){
            final SellerMatchInfo seller = t.search(line);
            if(seller != null) {
                System.out.println(seller.toString());
                DiscordBot.notify(seller);
            }
        }

    }

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
                searchForTargets(line);
            }
        }
    }
}