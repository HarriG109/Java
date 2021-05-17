package STAGCommand;
import STAGData.LocationData;
import STAGData.PlayerData;

public class STAGGet extends STAGProcessCommand {

    public STAGGet(){
    }

    //Method to collectItemIfExists
    //TODO: Need to evaluate if people anything mentioned is like the item i.e. wooden spoon etc.
    //Handling two words could be problematic.
    public void collectItemIfExists(String[] commands, PlayerData currPlayer, LocationData currLoc){

        //Check command line for location artefact
        String getItem = commandIsObject(commands, currLoc.getArtefactList(false));

        checkArtefactInLocation(getItem, currLoc, currPlayer);
        if(!getItem.equals("NA")){
            setReturnString("You picked up an " + getItem);
        }
        else{
            setReturnString("Item doesn't exist in room");
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
