package STAGData;

import java.util.ArrayList;

public class PlayerData {

    public String player;
    public int playerLocationIndex;
    public ArrayList<ArrayList<String>> playerInv = new ArrayList<ArrayList<String>>();
    public int playerHealth;

    public PlayerData(String name){

        //Create initial values for player
        //NAME:
        player = name;
        //LOCATION:
        //Start should be first location from entities file i.e. first instance in entities
        setPlayerLocIndex(0);
        playerHealth = 3;
    }

    //Method to get player name
    public String getPlayer(){
        return player;
    }

    //Method to set location name
    public void setPlayerLocIndex(int loc){
        playerLocationIndex = loc;
    }

    //Method to get location name
    public int getPlayerLocIndex(){
        return playerLocationIndex;
    }

    //Method to add to player inventory arraylist
    public void addPlayerInv(String artefact, String desc){
        playerInv.add(new ArrayList<String>());
        playerInv.get(playerInv.size() - 1).add(artefact);
        playerInv.get(playerInv.size() - 1).add(desc);
    }

    //Method to get player inventory arraylist
    public ArrayList<ArrayList<String>> getPlayerInv(){
        return playerInv;
    }

    //Method to remove artefact from inventory at specific index
    public void removeInv(int artIndex){
        playerInv.remove(artIndex);
    }

    //Method to get player health
    public int health(){
        return playerHealth;
    }
}
