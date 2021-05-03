import STAGData.PlayerData;
import java.util.ArrayList;

public class STAGHandleIncomingCommand {

    int currentPlayerIndex;
    String currentPlayerName;
    String actualCommand;
    public String[] tokenArray;

    public STAGHandleIncomingCommand(String command){
        //Get player name
        currentPlayerName = command.substring(0, command.indexOf(":"));

        //Get just the command part for splitting into tokens by removing player name
        actualCommand = command.substring(command.indexOf(":") + 1).trim();

        //Split into token array for better handling
        setToken(actualCommand);
    }

    //Method to add players
    public void addPlayerIfNew(ArrayList<PlayerData> players){

        //If no players add immediately
        if(players.size() == 0){

            //Create new instance of player
            PlayerData pd = new PlayerData(currentPlayerName);
            //Add to array of players
            players.add(pd);
            //Set current player index
            setCurrentPlayerIndex(0);
        }
        //Else check the player exists and add if not then add
        else if(!checkPlayerExists(currentPlayerName, players)){

            //Create new instance of player
            PlayerData pd = new PlayerData(currentPlayerName);
            //Add to array of players
            players.add(pd);
        }
    }

    //Method to check current player exists
    public boolean checkPlayerExists(String name, ArrayList<PlayerData> players){
        int i;

        for(i = 0; i < players.size(); i++){
            if(players.get(i).getPlayer().equalsIgnoreCase(name)){
                //Set current player index to found player number
                setCurrentPlayerIndex(i);
                return true;
            }
        }
        //Set new added player index (before adding player so element number is correct)
        setCurrentPlayerIndex(players.size());
        return false;
    }

    //Set current player index
    public void setCurrentPlayerIndex(int ind){
        currentPlayerIndex = ind;
    }

    //Get current player index
    public int getCurrentPlayerIndex(){
        return currentPlayerIndex;
    }

    //Set token
    public void setToken(String splitString){
        //Split into token array for better handling
        tokenArray = splitString.split("\\s+");
    }

    //Get token
    public String[] getTokenArray(){
        return tokenArray;
    }
}
