package com.sai1boat.eqdiscordsnitch;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.HashSet;

public class DiscordBot {


    private static JDA discord;


    static HashSet<PriceTarget> priceTargets = new HashSet<>();
    static {
        priceTargets.add(new PriceTarget("Granite Face Grinder",8000));
        priceTargets.add(new PriceTarget("Fungus Covered Scale Tunic", 40000));
        priceTargets.add(new PriceTarget("Fungi Covered Great Staff", 8000));
    }


    protected static void searchForTargets(String line) {
        for(PriceTarget t : priceTargets){
            final SellerMatchInfo seller = t.search(line);
            if(seller != null) {
                System.out.println(seller.toString());
                DiscordBot.notify(seller);
            }
        }

    }
    protected static void notify(SellerMatchInfo seller) {

        initDiscord();

        discord.getTextChannelsByName(Constants.channel, true)
                .stream()
                .findFirst()
                .get()
                .sendMessage(seller.toString())
                .queue();

    }


    private static void initDiscord() {
        if(discord == null) {
            discord = JDABuilder.createLight(Constants.token).build();
            try {
                discord.awaitReady();
                discord.addEventListener(new ListenerAdapter() {
                    @Override
                    public void onReady(ReadyEvent event)
                    {
                        System.out.println("I am ready to go!");
                    }

                    @Override
                    public void onMessageReceived(MessageReceivedEvent event)
                    {
                        handleMessage(event);
                    }

                    private void handleMessage(MessageReceivedEvent event) {

                        final String msg = event.getMessage().getContentDisplay();
                        final String[] msgWords = msg.split("\"");

                        switch(msgWords[0].trim()) {
                            case "help":
                            case "?":
                                respond(getHelpMessage(), event);
                                break;
                            case "list":
                            case "targets":
                                respond(getTargetsMessage(), event);
                                break;
                            case "add":
                                if (msgWords.length == 2) {
                                    if (priceTargets.add(new PriceTarget(msgWords[1], Integer.MAX_VALUE))) {
                                        respond("Added item " + msgWords[1] + " to price targets.",
                                                event);
                                    } else {
                                        respond("Could not add item " + msgWords[1] + " to price targets.",
                                                event);
                                    }
                                } else if (msgWords.length == 3) {

                                    priceTargets.add(new PriceTarget(msgWords[1],
                                            Integer.parseInt(msgWords[2].trim())));
                                } else {
                                    respond("add command requires 1 or 2 parameters but no more or less.",
                                            event);
                                }
                                break;
                            case "remove":
                                if(priceTargets.remove(new PriceTarget(msgWords[1], Integer.MAX_VALUE))) {
                                    respond("Removed item "+msgWords[1]+" from price targets.",
                                            event);
                                } else {
                                    respond("Could not remove item "+msgWords[1]+" from price targets.",
                                            event);
                                }
                                break;
                            default:
                                break;
                        }
                    }

                    private void respond(String msg, MessageReceivedEvent event) {
                        event.getChannel().sendMessage(msg).queue();
                    }

                    private String getHelpMessage() {
                        return "Available commands :\n" +
                                "1) help/? This message\n" +
                                "2) list/targets - print all price targets.\n" +
                                "3) add \"Item Name in Quotes\" <price(optional)> \n" +
                                "4) remove \"Item Name in Quotes\"";
                    }

                    private String getTargetsMessage() {
                        StringBuilder sb = new StringBuilder();
                        for (PriceTarget p : priceTargets) {
                            sb.append("WTB "+p._itemName+" for less than "+p._desiredPrice+"p\n");
                        }
                        return sb.toString();
                    }
                });
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } //end init == null
    }
}
