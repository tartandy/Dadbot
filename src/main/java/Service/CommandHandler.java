package Service;

import DAO.FileHandler;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import Helpers.Helper;

import java.util.*;

class CommandHandler {

    private MessageReceivedEvent event;
    private JDA jda;
    private String helpText;

    CommandHandler(MessageReceivedEvent event, JDA jda){
        this.event = event;
        this.jda = jda;
        helpText = "> **__Dadbot Command Help__**\n" +
                "> \n" +
                "> **Get Scoreboard:** dadbot keeps a score of how many times he catches you out!    ```!dadbot scoreboard```\n" +
                "> **Set your default name:** saving a default name will help dadbot change your name back correctly!```!dadbot name set [name here]```\n" +
                "> **Set your default name:** lets you check your current default name!```!dadbot name get```";
    }

    void run(){
        //split command into individual words, stripping markup
        String[] split = event.getMessage().getContentStripped().toLowerCase().split(" ");
        //return if bad command
        if(split.length < 2){
            sendMessage("What!? If you're going to use my command word at least tell me what you want. Use ***!dadbot help*** if you're unsure.");
            return;
        }
        switch (split[1]){
            case "scoreboard":
                sendMessage(parseScoreboard());
                break;
            case "help":
                sendMessage(helpText);
                break;
            case "name":
                sendMessage(handleNameCommand(split));
                break;
            default:
                sendMessage("Sorry son I can't understand you, try again or type ***!dadbot help*** for commands.");
        }

    }

    //main handler for name type commands
    private String handleNameCommand(String[] split) {
        //return if bad command
        if(split.length < 3){
            return"Sorry kiddo that's a bad name command, check ***!dadbot help*** for proper syntax.";
        }

        //check whether to get or set default name
        if(split[2].equals("get")){
            return processNameGet(split);
        } else if (split[2].equals("set")){
            return processNameSet(split);
        }
        //return default bad command response if neither
        return"Sorry kiddo that's a bad name command, check ***!dadbot help*** for proper syntax.";
    }

    //process name set requests
    private String processNameSet(String[] split) {
        StringBuilder nameBuilder = new StringBuilder();
        String name;
        //processes name to set as default
        for(int i = 3; i < split.length ; i++){
            nameBuilder.append(event.getMessage().getContentStripped().split(" ")[i]).append(" ");
        }
        name = nameBuilder.toString().trim();

        //remove ; from name if present
        if(name.contains(";")){
            name = name.replace(";", "");
            sendMessage("lmaoooo were you trying to break me?? Don't worry I removed the ; from your name.");
        }

        //remove \n from name if present
        if(name.contains("\n")){
            name = name.replace("\n", "");
            sendMessage("lmaoooo were you trying to break me?? Don't worry I removed the line break from your name.");
        }

        //check name is not empty, otherwise return bad name
        if(name.isEmpty()) return "I can't set your default name to be empty, idiot. Try again.";

        //throw error if over 32 char nickname limit
        if(name.length() > 32) return "Your nickname is " + (name.length() - 32) + " characters too long.";

        //give nickname to file handler to add to file
        if (FileHandler.setDefaultName(event.getAuthor().getId(), name)){
            return "Your default name has been set!";
        }
        return "There was an error storing your name, sorry son.";

    }

    private String processNameGet(String[] split) {
        //get nickname from file
        String name = FileHandler.getDefaultName(event.getAuthor().getId());
        //return error if no name is stored, else return name
        if(name == null) return "I don't love you enough to have a nickname for you. Check ***!dadbot help*** to learn how.";
        return event.getAuthor().getAsMention() + ", your default name is: *" + name + "*.";
    }

    //gets the scoreboard and parses the output
    private String parseScoreboard() {
        //get scoreboard and sort
        HashMap<String, Integer> scoreboard = FileHandler.loadCounter();
        Map<String, Integer> sortedScoreboard = Helper.sortByValue(scoreboard);

        //begin parsing scoreboard
        StringBuilder output = new StringBuilder("Dadjoke Highscores:");
        //for each entry, check user is in guild and add to output if true
        for (Map.Entry<String, Integer> entry : sortedScoreboard.entrySet()) {
            if(Helper.isUserInGuild(event.getGuild(), entry.getKey()))
                output.append("\n")
                        .append(Objects.requireNonNull(jda.getUserById(entry.getKey())).getName())
                        .append(": **")
                        .append(entry.getValue())
                        .append("**");
        }
        return output.toString();
    }

    private void sendMessage(String text){
        //sends pre-prepared message to event channel
        event.getMessage().getChannel().sendMessage(text).queue();
    }
}
