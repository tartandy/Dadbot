package Service;

import DAO.FileHandler;
import Helpers.Helper;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.internal.utils.Helpers;

import java.util.ArrayList;
import java.util.Objects;

public class JokeNameHandler {

    private MessageReceivedEvent event;
    private JDA jda;
    private ArrayList<String> prefixes;
    private String botID;

    public JokeNameHandler(MessageReceivedEvent event, JDA jda) {
        prefixes = new ArrayList<>();
        prefixes.add("i'm ");
        prefixes.add("im ");
        prefixes.add("i am ");
        botID = "654015963506933791";
        this.jda = jda;
        this.event = event;
    }

    public void run(){
        String prefix, jokeName, message, originalName;

        //get prefix if exists
        prefix = getPrefix(event.getMessage());
        if(prefix != null){
            //update scoreboard
            FileHandler.addCount(event.getAuthor().getId());
            //get joke name
            jokeName = getDadJokeName(event.getMessage(), prefix);
            //check <50 chars
            assert jokeName != null;
            if(jokeName.length() > 50) return;
            //build message
            message = buildMessage(jokeName, event.getAuthor().getAsMention());

            //get original name
            originalName = FileHandler.getDefaultName(event.getAuthor().getId());
            //if original name has been set then change to nickname for time period
            if(originalName != null){
                //message is good to send
                sendMessage(message);
                //change nickname for 30s
                Objects.requireNonNull(event.getGuild().getMember(event.getAuthor())).modifyNickname(jokeName);
                try {
                    Thread.sleep(30000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Objects.requireNonNull(event.getGuild().getMember(event.getAuthor())).modifyNickname(originalName);
            } else {
                //warn user to set a default name before it can change names again
                sendMessage("I wan't to make the joke and all, but first we need ground rules. Set a default name, son. " +
                        "(Type ***!dadbot help*** if you're unsure how).");
            }

        }
    }

    //builds the message reply string
    private String buildMessage(String jokeName, String user) {
        //builds message to be sent
        //has a small chance to be evil dad and send a mean message
        String output;
        int random = Helper.randomWithRange(1, 400);
        switch (random){
            case 1:
                output = "I don't give a fuck what your name is, __***you're getting a beating***__";
                break;
            case 2:
                output = "Are you fucking retarded? Your name is " + Objects.requireNonNull(jda.getUserById(user)).getAsMention();
                break;
            case 3:
                output = "Well '" + jokeName + "', I'm going out for cigarettes, back uhh.... Later.";
                break;
            case 4:
                output = "Hi " + jokeName + " , I'm __***DADDY***__.";
                break;
            case 5:
                output = "lOoK aT mE, mY nAmE iS " + jokeName;
                break;
            case 6:
                output = "Listen '" + jokeName + "', if you don't go to bed right now I'm turning off the internet for the whole __***WEEK***__";
                break;
            case 7:
                output = "Doesn't matter what your name is kid, I'll always be disappointed in you.";
            default:
                output = "Hi " + jokeName + ", I'm " + Objects.requireNonNull(jda.getUserById(botID)).getAsMention() +"!";
                break;
        }
        return output;
    }

    //tries to get a prefix out of a message
    private String getPrefix(Message msg) {
        //check dad joke prefix and return true if found;
        for(String prefix : prefixes){
            if(msg.getContentRaw().toLowerCase().startsWith(prefix)) return prefix;
        }
        //check for dad joke mid string (adding additional space before prefix)
        for(String prefix: prefixes){
            if(msg.getContentRaw().toLowerCase().contains(" " + prefix)) return " " + prefix;
        }
        return null;
    }

    //gets the dad joke name from the message
    private String getDadJokeName(Message msg, String prefix) {
        //extract name from message
        String name = msg.getContentRaw().substring(msg.getContentRaw().toLowerCase().indexOf(prefix) + prefix.length());

        //trim name to punctuation
        if (name.contains(",")){
            name = name.substring(0, name.indexOf(","));
        }
        if (name.contains(".")){
            name = name.substring(0, name.indexOf("."));
        }
        //return null is name > 50 chars
        if (name.length() > 50) return null;
        return name;
    }

    private void sendMessage(String message){
        event.getChannel().sendMessage(message).queue();
    }

}
