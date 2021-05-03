package STAGCommand;
import STAGData.LocationData;

public class STAGLook {
    String location;

    public STAGLook(LocationData locD){
        setLookString(getLocInfo(locD));
    }

    //Method to get current location from player
    public String getLookString(){
        return location;
    }

    //Method to set current location for player
    public void setLookString(String loc){
        location = loc;
    }

    //Method to return string of location data
    public String getLocInfo(LocationData locD){

        //With brackets and commas
        StringBuilder sb = new StringBuilder();

        sb.append("Location Description: " + locD.getLocDesc() + "\n");
        sb.append(currLocCharsAsString(locD) + "\n");
        sb.append(currLocArtAsString(locD) + "\n");
        sb.append(currLocFurnAsString(locD) + "\n");
        sb.append(currLocPathAsString(locD) + "\n");

        return sb.toString();
    }

    //TODO: These methods are too similar
    //Method to return string of characters in current location
    public String currLocCharsAsString(LocationData locD){

        //With brackets and commas
        int i;
        StringBuilder sb = new StringBuilder();

        sb.append("Characters: ");

        for (i = 0; i < locD.getCharList().size(); i++) {
            sb.append(locD.getCharList().get(i).get(0));
            sb.append(" ");
        }

        return sb.toString();
    }

    //Method to return string of artefacts in current location
    public String currLocArtAsString(LocationData locD){

        //With brackets and commas
        int i;
        StringBuilder sb = new StringBuilder();

        sb.append("Artefacts: ");

        for (i = 0; i < locD.getArtefactList().size(); i++) {
            sb.append(locD.getArtefactList().get(i).get(0));
            sb.append(" ");
        }

        return sb.toString();
    }

    //Method to return string of furniture in current location
    public String currLocFurnAsString(LocationData locD){

        //With brackets and commas
        int i;
        StringBuilder sb = new StringBuilder();

        sb.append("Furniture: ");

        for (i = 0; i < locD.getFurnitureList().size(); i++) {
            sb.append(locD.getFurnitureList().get(i).get(0));
            sb.append(" ");
        }

        return sb.toString();
    }

    //Method to return string of paths in current location
    public String currLocPathAsString(LocationData locD){

        //With brackets and commas
        int i;
        StringBuilder sb = new StringBuilder();

        sb.append("Paths: ");

        for (i = 0; i < locD.getPaths().size(); i++) {
            sb.append(locD.getPaths().get(i));
            sb.append(" ");
        }

        return sb.toString();
    }
}
