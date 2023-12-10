package me.mskatking.crackedhub;

import io.papermc.paper.event.player.AsyncChatEvent;
import me.mskatking.crackedhub.util.ConfigHelper;
import me.mskatking.crackedhub.util.CrackedHubPlayer;
import me.mskatking.crackedhub.util.SQLProcessor;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.SubscribeEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.io.File;
import java.util.ArrayList;
import java.util.Optional;

public class DiscordListener implements Listener {

    private FileConfiguration config;
    private File f;

    public static ArrayList<LinkRequest> requests = new ArrayList<>();

    public DiscordListener() {
        config = ConfigHelper.getConfig("configs/discord", "config.yml");
        f = ConfigHelper.getFile("configs/discord", "config.yml");

        ConfigHelper.applyConfigDefaults(config, f, ConfigHelper.Configs.DISCORD_MAIN);
    }

    public void init() {
        if(!config.getString("channels.guildID").equals("-") && CrackedHub.bot.getGuildById(config.getString("channels.guildID")) != null) {
            CrackedHub.bot.getGuildById(config.getString("channels.guildID")).updateCommands().addCommands(
                    Commands.slash("link", "The command for linking MC accounts with Discord accounts!")
                            .addOption(OptionType.STRING, "linkid", "Only used if the request was started in MC."),
                    Commands.slash("unlink", "The command for unlinking MC and Discord accounts")
            ).queue();
        }
    }

    @SubscribeEvent
    public void onMessageRecieved(MessageReceivedEvent e) {
        if(e.getAuthor().isBot()) return;

        User author = e.getAuthor();
        Message message = e.getMessage();
        String content = message.getContentRaw();
        MessageChannel channel = e.getChannel();
        Member member = e.getMember();
        String nick = member.getNickname();
        Role role = e.getGuild().getPublicRole();
    }

    @EventHandler
    public void onChat(AsyncChatEvent e) {
        if(config.getString("channels.mcToDiscordChannel").equals("-") || config.getString("channels.guildID").equals("-") || !CrackedHub.alpha) return;

        TextChannel channel = CrackedHub.bot.getGuildById(config.getString("channels.guildID")).getTextChannelById(config.getString("channels.mcToDiscordChannel"));
        channel.sendMessage(PlainTextComponentSerializer.plainText().serialize(CrackedHubPlayer.findPlayer(e.getPlayer()).rank.prefix) + " " + e.getPlayer().getName() + ": " + PlainTextComponentSerializer.plainText().serialize(e.message())).queue();
    }

    @SubscribeEvent
    public void command(SlashCommandInteractionEvent e) {
        if(e.getUser().isBot() || config.getString("channels.listenForLinkCommand").equals("-")) return;
        if(e.getName().equals("link") && e.getChannelId().equals(config.getString("channels.listenForLinkCommand"))) {
            e.deferReply(true).queue();
            if(e.getOption("linkid") == null) {
                if(LinkRequest.containsRequest(e.getUser()).isPresent()) {
                    e.getHook().sendMessage("Whoops! Looks like there is already a request with your user!").queue();
                } else if(!SQLProcessor.contains(e.getUser().getId(), "USERID", SQLProcessor.Tables.DISCORD_LINKED.toString())) {
                    String id = CrackedHub.randomString(5);
                    requests.add(new LinkRequest(e.getUser(), id));
                    e.getHook().sendMessage("Alright! A request has been created! Please type this command on the Minecraft Server: /link " + id).queue();
                } else {
                    e.getHook().sendMessage("You already linked your accounts! Please contact an admin if you believe this is a mistake!").queue();
                }
            } else {
                String id = e.getOption("linkid").getAsString();
                if(LinkRequest.containsRequest(id).isPresent()) {
                    SQLProcessor.execute("INSERT INTO DISCORD_LINKED VALUES ('" + LinkRequest.containsRequest(id).get().initiatorP.getUniqueId() + "', " + e.getUser().getId() + ");");
                    requests.remove(LinkRequest.containsRequest(id).get());
                    e.getHook().sendMessage("Successfully linked your Minecraft and Discord accounts!").queue();
                } else {
                    e.getHook().sendMessage("Couldn't find a request with the id: '" + id + "'!").queue();
                }
            }
        } else if(e.getName().equals("unlink") && e.getChannelId().equals(config.getString("channels.listenForLinkCommand"))) {
            e.deferReply(true);
            if(SQLProcessor.contains(e.getUser().getId(), "USERID", SQLProcessor.Tables.DISCORD_LINKED.toString())) {
                SQLProcessor.execute("DELETE FROM DISCORD_LINKED WHERE USERID='" + e.getUser().getId() + ";");
                e.getHook().sendMessage("Successfully unlinked your accounts!");
            } else {
                e.getHook().sendMessage("Your accounts aren't linked!");
            }
        } else {
            e.reply("Could not find response for that command!").queue();
        }
    }

    public static class LinkRequest {
        public User initiatorU;
        public Player initiatorP;
        public String id;

        public LinkRequest(User initiatorU, String id) {
            this.initiatorU = initiatorU;
            this.id = id;
        }

        public LinkRequest(Player initiatorP, String id) {
            this.initiatorP = initiatorP;
            this.id = id;
        }

        public static Optional<LinkRequest> containsRequest(String id) {
            for(LinkRequest r : requests) {
                if(r.id.equals(id)) return Optional.of(r);
            }
            return Optional.empty();
        }

        public static Optional<LinkRequest> containsRequest(User user) {
            for(LinkRequest r : requests) {
                if(r.initiatorU.equals(user)) return Optional.of(r);
            }
            return Optional.empty();
        }
    }
}
