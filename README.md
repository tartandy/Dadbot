# Dadbot
This is a discord bot creating using JDA, which has several features. I run it locally on my raspberry pi for a few servers that I'm in, but don't have the resources nor incentive to making the bot publically accessable.

If you wish to take this code and run the bot for your own servers, by all means. But you'll need to make your own BOT on discord and provide your own token.

## The Classic Joke
It's used for the terrible joke we all love, when it finds that you've said "I am [something]" then it will respond saying "Hi [something], I'm Dadbot!". 

I'm and Im also get picked up, and the name cuts off at full stops or commas to stop long messages containing "I am" to restult in a long name. It also just outright ignores messages containing I am if the "name" it determines ifs over 50 chars long. This just cuts down on the bot becomming too spammy.

There's also a very small chance he can say something unique instead of his usual message, but finding them out on their own is funnier than me listing them all here.

## Changing Nicknmaes
Another feature of dadbot is whatever he sees as your name when he makes the joke, he will try and change your nickname to that for a small amount of time (30s by default). Discord's nickname feature requires a max of 32 char names, so dadbot will automatically trim names that are over this limit down to the nearest full word.

To have this feature however you need to set yourself a default name for him to change back to, which is detailed more in the section below.

## Default Names
In order for dadbot to change your nickname, he needs to know what to return your name to after it. Having a default name avoids him changing your name to the wrong thing if you spam him too many times in close succession...

Dadbot has commands to get and set your default name, more info below.

## Scoreboard
What bot would be a true bot without having a fun scoreboard? This one keeps a count of how many times it catches people out on your server, just for funsies. If you're a member of multiple servers, your score is combined across the two. This is by design.

## Help & Commands
If you get stuck with anything above, there's a help command built into him by typing **!dadbot help**. His command prefix is !dadbot if you hadn't already guessed.

# Setup of your own Dadbot
I'm assuming you know how to create your own bot on discords API, so from here it's just a matter of downloading the JAR file in this project under **/Build/Lib/**. I run it by typing `java -cp [path_to_jar] Main` in cmd.

Other option is to clone this repo and run it yourself in your IDE of choice. Perhaps stating the obvious here but the project uses gradle...
