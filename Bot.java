import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;

public class Bot extends ListenerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(Bot.class);
    private static JDA jdaInstance;

    public static void main(String[] args) {
        try {
            // Replace "YOUR_BOT_TOKEN" with your bot's token
            JDABuilder jdaBuilder = JDABuilder.createDefault("YOUR_BOT_TOKEN");

            // Set a status for your bot
            jdaBuilder.setActivity(Activity.playing("Type !ping"));

            // Add an event listener to your bot
            jdaBuilder.addEventListeners(new Bot());

            // Build the JDA instance
            jdaInstance = jdaBuilder.build();
            logger.info("Bot is up and running!");

        } catch (LoginException e) {
            logger.error("Failed to login to Discord", e);
        } catch (Exception e) {
            logger.error("An unexpected error occurred", e);
        }
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        // Ensure the message is not from a bot
        if (event.getAuthor().isBot()) return;

        // Get the content of the message
        String message = event.getMessage().getContentRaw();

        try {
            // Respond to "!ping" command
            if (message.equalsIgnoreCase("!ping")) {
                long time = System.currentTimeMillis();
                event.getChannel().sendMessage("Calculating ping...").queue(response -> {
                    response.editMessageFormat("Ping: %d ms", System.currentTimeMillis() - time).queue();
                });
            }

        } catch (Exception e) {
            logger.error("An error occurred while processing a message", e);
            event.getChannel().sendMessage("Oops! Something went wrong.").queue();
        }
    }
}
