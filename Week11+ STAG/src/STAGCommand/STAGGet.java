package STAGCommand;
import STAGData.LocationData;
import STAGData.PlayerData;

//Class to allow current player to pick up item
public class STAGGet extends STAGProcessCommand {

    //Method to collectItemIfExists
    public void collectItemIfExists(String[] commands, PlayerData currPlayer, LocationData currLoc){

        //Check command line for location artefact
        String getItem = commandIsObject(commands, currLoc.getArtefactList(false));

        checkArtefactInLocation(getItem, currLoc, currPlayer);
        if(!getItem.equals("NA")){
            setReturnString("You picked up a " + getItem);
        }
        else{
            setReturnString("Item doesn't exist in location");
        }
    }

    //Check artefact is in location and remove it if it is
    //Note: Need to get descriptions and also the inventory should present descriptions maybe?
    public void checkArtefactInLocation(String commandLoc, LocationData currLoc, PlayerData currPlayer){
        int i;

        for(i = 0; i < currLoc.getArtefactList(false).size(); i++){
            if(currLoc.getArtefactList(false).get(i).equalsIgnoreCase(commandLoc)){

                //Add item to player inventory
                currPlayer.addPlayerInv(currLoc.getArtefactList(false).get(i),
                        currLoc.getArtefactList(true).get(i));

                //Remove item from location
                currLoc.removeArtefact(i);
            }
        }
    }
}
