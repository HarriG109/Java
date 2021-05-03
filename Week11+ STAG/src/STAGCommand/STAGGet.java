package STAGCommand;
import STAGData.LocationData;
import STAGData.PlayerData;

public class STAGGet extends STAGProcessCommand {

    boolean itemExists;

    public STAGGet(){
    }

    //Method to collectItemIfExists
    //TODO: Need to evaluate if people anything mentioned is like the item i.e. wooden spoon etc.
    //Handling two words could be problematic.
    public void collectItemIfExists(String[] commands, PlayerData currPlayer, LocationData currLoc){

        setIndex(getIndex() + 1);

        itemExists = checkArtefactInLocation(commands[getIndex()], currLoc, currPlayer);
        if(itemExists){
            setReturnString("You picked up an " + commands[getIndex()]);
        }
        else{
            setReturnString("Item doesn't exist in room");
        }
    }

    //Check artefact is in location and remove it if it is
    //Note: Need to get descriptions and also the inventory should present descriptions maybe?
    public boolean checkArtefactInLocation(String commandLoc, LocationData currLoc, PlayerData currPlayer){
        int i;

        for(i = 0; i < currLoc.getArtefactList().size(); i++){
            if(currLoc.getArtefactList().get(i).get(0).equalsIgnoreCase(commandLoc)){

                //Add item to player inventory
                currPlayer.addPlayerInv(currLoc.getArtefactList().get(i).get(0), currLoc.getArtefactList().get(i).get(1));

                //Remove item from location
                currLoc.removeArtefact(i);
                return true;
            }
        }
        return false;
    }
}
