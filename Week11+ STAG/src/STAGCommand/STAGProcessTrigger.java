package STAGCommand;
import STAGData.ActionsTriggerData;
import STAGData.LocationData;
import STAGData.PlayerData;
import java.util.ArrayList;

public class STAGProcessTrigger extends STAGProcessCommand {

    public LocationData producedLoc;
    public String type;
    public STAGProcessTrigger(){
    }

    //Method to process a trigger
    public void processTrigger(String[] commands, LocationData currLoc,
                               PlayerData currPlayer, ArrayList<ActionsTriggerData> triggers,
                               ArrayList<LocationData> locations){

        ActionsTriggerData currTrigger = commandIsTrigger(commands, triggers);

        if(currTrigger != null){

            String subjectName = commandIsObject(commands, currTrigger.getSubjects());

            //Check the next command matches the trigger subject
            if(!subjectName.equals("NA")){

                //Check subjects for trigger match current inventory/furniture in current location
                if(allSubjectsHere(currTrigger.getSubjects(), currLoc, currPlayer)){

                    //Create the produced object
                    createProduced(currTrigger.getProduced(), currLoc, currPlayer, locations);
                    setReturnString(currTrigger.getNarr());

                    //Remove the consumed items
                    removeConsumed(currTrigger.getConsumed(), currLoc, currPlayer);
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

    //TODO: Just had a thought what if there are multiple subjects i.e. two trees
    //TODO: Just had a thought what if you pick up something that you already have?
    //Check all subjects are available in current location
    public boolean allSubjectsHere(ArrayList<String> subjects, LocationData currLoc, PlayerData currPlayer){
        int subjectCount = 0;

        for (String s: subjects) {
            if (checkStringInList(s, currLoc.getFurnitureList(false))) {
                subjectCount++;
            }
            if (checkStringInList(s, currLoc.getCharList(false))) {
                subjectCount++;
            }
            if (checkStringInList(s, currLoc.getArtefactList(false))) {
                subjectCount++;
            }
            if (checkStringInList(s, currPlayer.getPlayerInv(false))) {
                subjectCount++;
            }
        }

        if(subjectCount == subjects.size()){
            return true;
        }
        return false;
    }

    //Check if string exists in List
    public boolean checkStringInList(String stringToFind, ArrayList<String> stringList) {

        for (String s : stringList) {
            if (s.equalsIgnoreCase(stringToFind)) {
                if (s.equalsIgnoreCase(stringToFind)) {
                    return true;
                }
            }
        }
        return false;
    }

    //Get index if string exists in 2D arraylist of strings
    public int getIndexofStringinList(String stringToFind, ArrayList<String> stringList){
        int i;

        for(i = 0; i< stringList.size(); i++){
            if(stringList.get(i).equalsIgnoreCase(stringToFind)){
                return i;
            }
        }
        return -1;
    }

    //Remove consumed
    public void removeConsumed(ArrayList<String> consumed, LocationData currLoc, PlayerData currPlayer){

        for (String c: consumed) {
            if(c.equalsIgnoreCase("Health")){
                currPlayer.reduceHealth();
            }
            else if (checkStringInList(c, currLoc.getFurnitureList(false))) {
                //Remove from current location furniture if matched
                currLoc.removeFurniture(getIndexofStringinList(c, currLoc.getFurnitureList(false)));
            }
            else if (checkStringInList(c, currLoc.getCharList(false))) {
                //Remove from current location furniture if matched
                currLoc.removeCharacter(getIndexofStringinList(c, currLoc.getCharList(false)));
            }
            else if (checkStringInList(c, currPlayer.getPlayerInv(false))) {
                //Remove from current player inventory if matched
                currPlayer.removeInv(getIndexofStringinList(c, currPlayer.getPlayerInv(false)));
            }
        }
    }

    //Produce whats produced
    public void createProduced(ArrayList<String> produced, LocationData currLoc,
                               PlayerData currPlayer,
                               ArrayList<LocationData> locations){

        for(String p: produced) {
            if(p.equalsIgnoreCase("Health")){
                currPlayer.increaseHealth();
            }
            else {
                //Find in unplaced and return the match type
                handleProduced(p, currLoc, currPlayer, locations);
            }
        }
    }

    //Method to return the type of the unplaced object which is going to be produced
    public void handleProduced(String produced, LocationData currLoc, PlayerData currPlayer,
                               ArrayList<LocationData> locations){

        int i;

        //Get index of location the produced item resides
        findProducedInstance(produced, locations);

        if(getType().equals("L")){
            currLoc.addPath(produced);
        }
        else if(getType().equals("A")){
            i = getIndexofStringinList(produced, getProducedLoc().getArtefactList(false));

            //Use index to place object into player inventory and description;
            currPlayer.addPlayerInv(getProducedLoc().getArtefactList(false).get(i),
                    getProducedLoc().getArtefactList(true).get(i));

            //Remove furniture from unplaced
            getProducedLoc().removeArtefact(i);
        }
        else if(getType().equals("F")){
            //Get index of unplaced item
            i = getIndexofStringinList(produced, getProducedLoc().getFurnitureList(false));

            //Use index to place furniture into current location;
            currLoc.addFurniture(getProducedLoc().getFurnitureList(false).get(i),
                    getProducedLoc().getFurnitureList(true).get(i));

            //Remove furniture from unplaced
            getProducedLoc().removeFurniture(i);
        }
        else if(getType().equals("C")){

            //Get index of unplaced item
            i = getIndexofStringinList(produced, getProducedLoc().getCharList(false));

            //Use index to place character into current location;
            currLoc.addCharacter(getProducedLoc().getCharList(false).get(i),
                    getProducedLoc().getCharList(true).get(i));

            //Remove furniture from unplaced
            getProducedLoc().removeCharacter(i);
        }
    }

    public void setProducedLoc(LocationData location){
        producedLoc = location;
    }

    public LocationData getProducedLoc(){
        return producedLoc;
    }

    public void setType(String objectType){
        type = objectType;
    }

    public String getType(){
        return type;
    }

    //Method to find the produced item and set the location of it and the type of object
    public void findProducedInstance(String produced, ArrayList<LocationData> locations){

        for (LocationData l: locations) {
            if(l.getLoc().equals(produced)){
                setType("L");
                setProducedLoc(l);
            }
            for(String s: l.getFurnitureList(false)){
                if(s.equals(produced)){
                    setType("F");
                    setProducedLoc(l);
                }
            }
            for(String s: l.getArtefactList(false)){
                if(s.equals(produced)){
                    setType("A");
                    setProducedLoc(l);
                }
            }
            for(String s: l.getCharList(false)){
                if(s.equals(produced)){
                    setType("C");
                    setProducedLoc(l);
                }
            }
        }
    }

    //See if instance of word within tokens matches object in list
    public ActionsTriggerData commandIsTrigger(String[] commands, ArrayList<ActionsTriggerData> triggers){

        for(String s: commands){
            for(ActionsTriggerData t: triggers){
                if(s.equalsIgnoreCase(t.getTrig())){
                    return t;
                }
            }
        }
        return null;
    }
}
