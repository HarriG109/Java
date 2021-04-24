package STAGData;

import java.util.ArrayList;

public class PlayerData {

    public String player;
    public String playerLocation;
    public ArrayList<String> playerInv = new ArrayList<String>();
    public int playerHealth;

    public PlayerData(String name){

        //Create initial values for player
        player = name;
        playerLocation = "start";
        playerHealth = 3;
    }

    //Method to add items to inventory
    public void addItem(String item){
        playerInv.add(item);
    }

    //Method to get player name
    public String getPlayer(){
        return player;
    }

    //Method to get location name
    public String getPlayerLoc(){
        return playerLocation;
    }

    //Method to get player inventory at index
    public String getPlayerInv(int index){
        return playerInv.get(index);
    }

    //Method to get player health
    public int health(){
        return playerHealth;
    }
}
