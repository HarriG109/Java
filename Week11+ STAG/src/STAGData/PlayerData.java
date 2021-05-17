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
        //Starting Health will be 3
        playerHealth = 3;

        //Add two lists to the player inventory one for objects and one for descriptions
        playerInv.add(new ArrayList<String>());
        playerInv.add(new ArrayList<String>());
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
        playerInv.get(0).add(artefact);
        playerInv.get(1).add(desc);
    }

    //Method to get player inventory arraylist
    public ArrayList<String> getPlayerInv(boolean desc){
        if(desc) {
            return playerInv.get(1);
        }
        return playerInv.get(0);
    }

    //Method to remove artefact from inventory at specific index
    public void removeInv(int artIndex){
        playerInv.get(0).remove(artIndex);
        playerInv.get(1).remove(artIndex);
    }

    //Method to wipe entire inventory (For death)
    public void wipeInv(){
        int i;

        for(i=0; i< playerInv.size(); i++){
            playerInv.remove(i);
        }
    }

    //Method to get player health
    public int getHealth(){
        return playerHealth;
    }

    //Method to set player health
    public void setHealth(int health){
        playerHealth = health;
    }

    //Method to reduce player health
    public void reduceHealth(){
        setHealth(getHealth() - 1);
    }

    //Method to increase player health
    public void increaseHealth(){
        setHealth(getHealth() + 1);
    }
}
