package me.mskatking.crackedhub;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.SubscribeEvent;
import org.bukkit.event.Listener;

public class DiscordListener implements Listener {

    @SubscribeEvent
    public void onMessageRecieved(MessageReceivedEvent e) {
        e.getChannel().sendMessage(e.getMessage().getContentRaw());
    }
}
