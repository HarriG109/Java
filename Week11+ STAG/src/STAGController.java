import STAGData.PlayerData;

import java.util.ArrayList;

public class STAGController {

    String playerName;

    public STAGController(String command, ArrayList<PlayerData> players){

        //Get player name
        playerName = command.substring(0, command.indexOf(":"));

        //Add players to game
        addPlayerIfNew(playerName, players);
    }
    //Load location
    //i.e. start

    //Interpret command against triggers
    //Loop through triggers until a match and then analyse next command to see what comes of it.
    //Not just triggers though remember there are extra commands!

    //Method to add players
    public void addPlayerIfNew(String name, ArrayList<PlayerData> players){
        //If no players add immedietly
        if(players.size() == 0){

            //Create new instance of player
            PlayerData pd = new PlayerData(name);
            //Add to array of players
            players.add(pd);
        }
        //Else check the player exists and add if not
        else if(!checkPlayerExists(name, players)){

            //Create new instance of player
            PlayerData pd = new PlayerData(name);
            //Add to array of players
            players.add(pd);
        }
    }

    //Method to check current player exists
    public boolean checkPlayerExists(String name, ArrayList<PlayerData> players){
        int i;

        for(i = 0; i < players.size(); i++){
            if(players.get(i).getPlayer().equalsIgnoreCase(name)){
                return true;
            }
        }
        return false;
    }
}
