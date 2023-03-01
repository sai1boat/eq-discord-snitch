package com.sai1boat.eqdiscordsnitch;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class DiscordBot {


    private static JDA discord;
    protected static void notify(SellerMatchInfo seller) {
        if(discord == null) {
            discord = JDABuilder.createLight(Constants.token).build();
            try {
                discord.awaitReady();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        discord.getTextChannelsByName(Constants.channel, true)
                .stream()
                .findFirst()
                .get()
                .sendMessage(seller.toString())
                .queue();

    }
}
