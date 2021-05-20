package STAGData;
import java.util.ArrayList;

//Class which stores location data
public class LocationData {

    public ArrayList<String> location = new ArrayList<String>();
    public ArrayList<ArrayList<String>> characterList = new ArrayList<ArrayList<String>>();
    public ArrayList<ArrayList<String>> artefactList = new ArrayList<ArrayList<String>>();
    public ArrayList<ArrayList<String>> furnitureList = new ArrayList<ArrayList<String>>();
    public ArrayList<String> pathList = new ArrayList<String>();

    //Constructor to populate location information
    public LocationData(String locName, String desc){
        location.add(locName);
        location.add(desc);

        //Add two lists to each characters/artefact/furniture for the object and their descriptions
        characterList.add(new ArrayList<String>());
        characterList.add(new ArrayList<String>());
        artefactList.add(new ArrayList<String>());
        artefactList.add(new ArrayList<String>());
        furnitureList.add(new ArrayList<String>());
        furnitureList.add(new ArrayList<String>());
    }

    //Method to add characters to list
    public void addCharacter(String character, String desc){
        characterList.get(0).add(character);
        characterList.get(1).add(desc);
    }

    //Method to add artefacts to list
    public void addArtefact(String artefact, String desc){
        artefactList.get(0).add(artefact);
        artefactList.get(1).add(desc);
    }

    //Method to add furniture to list
    public void addFurniture(String furniture, String desc){
        furnitureList.get(0).add(furniture);
        furnitureList.get(1).add(desc);
    }

    //Method to add paths to list
    public void addPath(String path){
        pathList.add(path);
    }

    //Method to get location name
    public String getLoc(){
       return location.get(0);
    }

    //Method to get location desc
    public String getLocDesc(){
        return location.get(1);
    }

    //Method to get character list
    public ArrayList<String> getCharList(boolean desc){
        if(desc) {
            return characterList.get(1);
        }
        return characterList.get(0);
    }

    //Method to get artefact list
    public ArrayList<String> getArtefactList(boolean desc){
        if(desc) {
            return artefactList.get(1);
        }
        return artefactList.get(0);
    }

    //Method to get furniture list
    public ArrayList<String> getFurnitureList(boolean desc){
        if(desc) {
            return furnitureList.get(1);
        }
        return furnitureList.get(0);
    }

    //Method to get path list
    public ArrayList<String> getPaths(){
        return pathList;
    }

    //Method to remove artefact at specific index
    public void removeArtefact(int artIndex){
        artefactList.get(0).remove(artIndex);
        artefactList.get(1).remove(artIndex);
    }

    //Method to remove artefact at specific index
    public void removeCharacter(int charIndex){
        characterList.get(0).remove(charIndex);
        characterList.get(1).remove(charIndex);
    }

    //Method to remove artefact at specific index
    public void removeFurniture(int furnIndex){
        furnitureList.get(0).remove(furnIndex);
        furnitureList.get(1).remove(furnIndex);
    }
}
