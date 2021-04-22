import java.util.ArrayList;

public class LocationData {

    public ArrayList<String> location = new ArrayList<String>();
    public ArrayList<ArrayList<String>> characterList = new ArrayList<ArrayList<String>>();
    public ArrayList<ArrayList<String>> artefactList = new ArrayList<ArrayList<String>>();
    public ArrayList<ArrayList<String>> furnitureList = new ArrayList<ArrayList<String>>();
    public ArrayList<String> pathList = new ArrayList<String>();

    public LocationData(String locName, String desc){
        location.add(locName);
        location.add(desc);
    }

    //Method to add characters to list
    public void addCharacter(String character, String desc){
        characterList.add(new ArrayList<String>());
        characterList.get(characterList.size() - 1).add(character);
        characterList.get(characterList.size() - 1).add(desc);
    }

    //Method to add artefacts to list
    public void addArtefact(String artefact, String desc){
        artefactList.add(new ArrayList<String>());
        artefactList.get(artefactList.size() - 1).add(artefact);
        artefactList.get(artefactList.size() - 1).add(desc);
    }

    //Method to add furniture to list
    public void addFurniture(String furniture, String desc){
        furnitureList.add(new ArrayList<String>());
        furnitureList.get(furnitureList.size() - 1).add(furniture);
        furnitureList.get(furnitureList.size() - 1).add(desc);
    }

    //Method to add paths to list
    public void addPath(String path){
        pathList.add(path);
    }

    //Method to get location name
    public String getLoc(){
       return location.get(0);
    }
}
