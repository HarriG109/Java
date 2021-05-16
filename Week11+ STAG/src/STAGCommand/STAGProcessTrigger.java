package STAGCommand;
import STAGData.ActionsTriggerData;
import STAGData.LocationData;
import STAGData.PlayerData;

import javax.xml.stream.Location;
import java.util.ArrayList;

public class STAGProcessTrigger extends STAGProcessCommand {

    public ActionsTriggerData currTrigger;
    public LocationData producedLoc;
    public String type;
    public STAGProcessTrigger(){
    }

    //Method to process a trigger
    public void processTrigger(String[] commands, LocationData currLoc,
                               PlayerData currPlayer, ArrayList<ActionsTriggerData> triggers,
                               ArrayList<LocationData> locations){

        if(setCurrentTriggerIfExists(commands[getIndex()], triggers)){

            //TODO: Could be any word in the sentence, need to rethink incrementing
            //Increment
            setIndex(getIndex() + 1);

            //Check the next command matches the trigger subject
            if(doesCommandMatchSubject(commands[getIndex()], getCurrTrigger().getSubjects())){

                //Check subjects for trigger match current inventory/furniture in current location
                if(allSubjectsHere(getCurrTrigger().getSubjects(), currLoc, currPlayer)){

                    //Create the produced object
                    createProduced(getCurrTrigger().getProduced(), currLoc, currPlayer, locations);
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

    //Set the current trigger
    public void setCurrentTrigger(ActionsTriggerData trigger){
        currTrigger = trigger;
    }

    //Get current trigger
    public ActionsTriggerData getCurrTrigger(){
        return currTrigger;
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

    //Remove consumed
    public void removeConsumed(ArrayList<String> consumed, LocationData currLoc, PlayerData currPlayer){

        for (String c: consumed) {
            if(c.equalsIgnoreCase("Health")){
                currPlayer.reduceHealth();
            }
            else if (checkStringIn2DArrayList(c, currLoc.getFurnitureList())) {
                //Remove from current location furniture if matched
                currLoc.getFurnitureList().remove(getIndexofStringin2DArrayList(c, currLoc.getFurnitureList()));
            }
            else if (checkStringIn2DArrayList(c, currLoc.getCharList())) {
                //Remove from current location furniture if matched
                currLoc.getCharList().remove(getIndexofStringin2DArrayList(c, currLoc.getCharList()));
            }
            else if (checkStringIn2DArrayList(c, currPlayer.getPlayerInv())) {
                //Remove from current player inventory if matched
                currPlayer.getPlayerInv().remove(getIndexofStringin2DArrayList(c, currPlayer.getPlayerInv()));
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
            i = getIndexofStringin2DArrayList(produced, getProducedLoc().getArtefactList());

            //Use index to place object into player inventory and description;
            currPlayer.addPlayerInv(getProducedLoc().getArtefactList().get(i).get(0),
                    getProducedLoc().getArtefactList().get(i).get(1));

            //Remove furniture from unplaced
            getProducedLoc().removeArtefact(i);
        }
        else if(getType().equals("F")){
            //Get index of unplaced item
            i = getIndexofStringin2DArrayList(produced, getProducedLoc().getFurnitureList());

            //Use index to place furniture into current location;
            currLoc.addFurniture(getProducedLoc().getFurnitureList().get(i).get(0),
                    getProducedLoc().getFurnitureList().get(i).get(1));

            //Remove furniture from unplaced
            getProducedLoc().removeFurniture(i);
        }
        else if(getType().equals("C")){

            //Get index of unplaced item
            i = getIndexofStringin2DArrayList(produced, getProducedLoc().getCharList());

            //Use index to place character into current location;
            currLoc.addCharacter(getProducedLoc().getCharList().get(i).get(0),
                    getProducedLoc().getCharList().get(i).get(1));

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
            for(ArrayList<String> s: l.getFurnitureList()){
                if(s.get(0).equals(produced)){
                    setType("F");
                    setProducedLoc(l);
                }
            }
            for(ArrayList<String> s: l.getArtefactList()){
                if(s.get(0).equals(produced)){
                    setType("A");
                    setProducedLoc(l);
                }
            }
            for(ArrayList<String> s: l.getCharList()){
                if(s.get(0).equals(produced)){
                    setType("C");
                    setProducedLoc(l);
                }
            }
        }
    }
}
