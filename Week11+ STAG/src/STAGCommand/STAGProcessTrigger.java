package STAGCommand;
import STAGData.ActionsTriggerData;
import STAGData.LocationData;
import STAGData.PlayerData;
import java.util.ArrayList;

public class STAGProcessTrigger extends STAGProcessCommand {

    public ActionsTriggerData currTrigger;

    public STAGProcessTrigger(){
    }

    //Method to process a trigger
    public void processTrigger(String[] commands, LocationData currLoc,
                               PlayerData currPlayer, ArrayList<ActionsTriggerData> triggers,
                               LocationData unplacedLoc, ArrayList<LocationData> locations){

        if(setCurrentTriggerIfExists(commands[getIndex()], triggers)){

            //TODO: Could be any word in the sentence, need to rethink incrementing
            //Increment
            setIndex(getIndex() + 1);

            //Check the next command matches the trigger subject
            if(doesCommandMatchSubject(commands[getIndex()], getCurrTrigger().getSubjects())){


                //Check subjects for trigger match current inventory/furniture in current location
                if(allSubjectsHere(getCurrTrigger().getSubjects(), currLoc, currPlayer)){


                    //Create the produced object
                    createProduced(getCurrTrigger().getProduced(), currLoc, currPlayer, unplacedLoc, locations);
                    setReturnString(getCurrTrigger().getNarr());

                    //Remove the consumed items
                    removeConsumed(getCurrTrigger().getConsumed(), currLoc, currPlayer);
                }
                else{
                    setReturnString("Missing required items or object is not in this location");
                }
            }
            else{
                setReturnString("Unknown Object");
            }
        }
        else{
            setReturnString("Unknown Action");
        }
    }

    //Check if trigger exists
    public boolean setCurrentTriggerIfExists(String command, ArrayList<ActionsTriggerData> triggers){
        //int i;

        for (ActionsTriggerData a: triggers) {
            if(a.getTrig().equalsIgnoreCase(command)){
                setCurrentTrigger(a);
                return true;
            }
        }
        return false;
    }

    //Check if subject exists
    public boolean doesCommandMatchSubject(String command, ArrayList<String> subjects){

        for (String s: subjects) {
            if(s.equals(command)){
                return true;
            }
        }
        return false;
    }

    //TODO: Just had a thought what if there are multiple subjects i.e. two trees
    //TODO: Just had a thought what if you pick up something that you already have?
    //Check all subjects are available in current location
    public boolean allSubjectsHere(ArrayList<String> subjects, LocationData currLoc, PlayerData currPlayer){
        int subjectCount = 0;

        for (String s: subjects) {
            if (checkStringIn2DArrayList(s, currLoc.getFurnitureList())) {
                subjectCount++;
            }
            if (checkStringIn2DArrayList(s, currLoc.getCharList())) {
                subjectCount++;
            }
            if (checkStringIn2DArrayList(s, currPlayer.getPlayerInv())) {
                subjectCount++;
            }
        }

        if(subjectCount == subjects.size()){
            return true;
        }
        return false;
    }

    //Check if string exists in 2D arraylist of strings
    public boolean checkStringIn2DArrayList(String stringToFind, ArrayList<ArrayList<String>> stringList){

        for(ArrayList<String> row: stringList){
            if(row.get(0).equalsIgnoreCase(stringToFind)){
                return true;
            }
        }
        return false;
    }

    //Get index if string exists in 2D arraylist of strings
    public int getIndexofStringin2DArrayList(String stringToFind, ArrayList<ArrayList<String>> stringList){
        int i;

        for(i = 0; i< stringList.size(); i++){
            if(stringList.get(i).get(0).equalsIgnoreCase(stringToFind)){
                return i;
            }
        }
        return -1;
    }

    //Set the current trigger
    public void setCurrentTrigger(ActionsTriggerData trigger){
        currTrigger = trigger;
    }

    //Get current trigger
    public ActionsTriggerData getCurrTrigger(){
        return currTrigger;
    }

    //Remove consumed
    public void removeConsumed(ArrayList<String> consumed, LocationData currLoc, PlayerData currPlayer){

        for (String c: consumed) {
            if (checkStringIn2DArrayList(c, currLoc.getFurnitureList())) {
                //Remove from current location furniture if matched
                currLoc.getFurnitureList().remove(getIndexofStringin2DArrayList(c, currLoc.getFurnitureList()));
            }
            if (checkStringIn2DArrayList(c, currPlayer.getPlayerInv())) {
                //Remove from current player inventory if matched
                currPlayer.getPlayerInv().remove(getIndexofStringin2DArrayList(c, currPlayer.getPlayerInv()));
            }
        }
    }

    //Produce whats produced
    public void createProduced(ArrayList<String> produced, LocationData currLoc,
                               PlayerData currPlayer, LocationData unplacedLoc,
                               ArrayList<LocationData> locations){

        for(String p: produced) {

            //Find in unplaced and return the match type
            handleProduced(p, unplacedLoc, currLoc, currPlayer, locations);
        }
    }

    //Method to return the type of the unplaced object which is going to be produced
    public void handleProduced(String produced, LocationData unplacedLoc,
                               LocationData currLoc, PlayerData currPlayer,
                               ArrayList<LocationData> locations){
        int i;

        if(checkStringIn2DArrayList(produced, unplacedLoc.getArtefactList())){
            //Get index of unplaced item
            i = getIndexofStringin2DArrayList(produced, unplacedLoc.getArtefactList());

            //Use index to place object into player inventory and description;
            currPlayer.addPlayerInv(unplacedLoc.getArtefactList().get(i).get(0),
                    unplacedLoc.getArtefactList().get(i).get(1));

            //Remove artefact from unplaced
            unplacedLoc.getArtefactList().remove(produced);
        }
        else if(checkStringIn2DArrayList(produced, unplacedLoc.getFurnitureList())){
            //Get index of unplaced item
            i = getIndexofStringin2DArrayList(produced, unplacedLoc.getFurnitureList());

            //Use index to place object into player inventory and description;
            currLoc.addFurniture(unplacedLoc.getFurnitureList().get(i).get(0),
                    unplacedLoc.getFurnitureList().get(i).get(1));

            //Remove artefact from unplaced
            unplacedLoc.getFurnitureList().remove(produced);
        }
        else if(checkStringIn2DArrayList(produced, unplacedLoc.getCharList())){
            //Get index of unplaced item
            i = getIndexofStringin2DArrayList(produced, unplacedLoc.getCharList());

            //Use index to place object into player inventory and description;
            currLoc.addCharacter(unplacedLoc.getCharList().get(i).get(0),
                    unplacedLoc.getCharList().get(i).get(1));

            //Remove artefact from unplaced
            unplacedLoc.getCharList().remove(produced);
        }
        else if(checkStringIn2DArrayList(produced, unplacedLoc.getCharList())){
            //Get index of unplaced item
            i = getIndexofStringin2DArrayList(produced, unplacedLoc.getCharList());

            //Use index to place object into player inventory and description;
            currLoc.addCharacter(unplacedLoc.getCharList().get(i).get(0),
                    unplacedLoc.getCharList().get(i).get(1));

            //Remove artefact from unplaced
            unplacedLoc.getCharList().remove(produced);
        }
        else if(getLocIndex(produced, locations) != -1){
            currLoc.addPath(produced);
        }
    }

    //Get new index of location based on comparison with string
    public int getLocIndex(String commandLoc, ArrayList<LocationData> locations){
        int i;

        for(i = 0; i < locations.size(); i++){
            if(locations.get(i).getLoc().equals(commandLoc)){
                return i;
            }
        }
        //Should never hit this due to pathExist
        return -1;
    }
}
