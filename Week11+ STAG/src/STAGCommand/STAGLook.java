package STAGCommand;
import STAGData.LocationData;
import STAGData.PlayerData;
import java.util.ArrayList;

public class STAGLook extends STAGProcessCommand {

    public STAGLook(){
    }

    //Method to return string of location data
    public String getLocInfo(LocationData locD, ArrayList<PlayerData> players, ArrayList<LocationData> location){

        StringBuilder sb = new StringBuilder();

        sb.append("Location Description: " + locD.getLocDesc() + "\n");
        sb.append(currLocPlayersAsString(locD, players, location) + "\n");
        sb.append(currLocCharsAsString(locD) + "\n");
        sb.append(currLocArtAsString(locD) + "\n");
        sb.append(currLocFurnAsString(locD) + "\n");
        sb.append(currLocPathAsString(locD) + "\n");

        return sb.toString();
    }

    //TODO: These methods are too similar
    //Method to return string of characters in current location
    public String currLocCharsAsString(LocationData locD){

        int i;
        StringBuilder sb = new StringBuilder();

        sb.append("Characters: ");

        for (i = 0; i < locD.getCharList(false).size(); i++) {
            sb.append(locD.getCharList(false).get(i));
            sb.append(" ");
        }

        return sb.toString();
    }

    //Method to return string of artefacts in current location
    public String currLocArtAsString(LocationData locD){

        int i;
        StringBuilder sb = new StringBuilder();

        sb.append("Artefacts: ");

        for (i = 0; i < locD.getArtefactList(false).size(); i++) {
            sb.append(locD.getArtefactList(false).get(i));
            sb.append(" ");
        }

        return sb.toString();
    }

    //Method to return string of furniture in current location
    public String currLocFurnAsString(LocationData locD){

        int i;
        StringBuilder sb = new StringBuilder();

        sb.append("Furniture: ");

        for (i = 0; i < locD.getFurnitureList(false).size(); i++) {
            sb.append(locD.getFurnitureList(false).get(i));
            sb.append(" ");
        }

        return sb.toString();
    }

    //Method to return string of paths in current location
    public String currLocPathAsString(LocationData locD){

        int i;
        StringBuilder sb = new StringBuilder();

        sb.append("Paths: ");

        for (i = 0; i < locD.getPaths().size(); i++) {
            sb.append(locD.getPaths().get(i));
            sb.append(" ");
        }

        return sb.toString();
    }

    //Method to see other player
    public String currLocPlayersAsString(LocationData locD, ArrayList<PlayerData> players
            , ArrayList<LocationData> locations){

        int i;
        StringBuilder sb = new StringBuilder();

        sb.append("Players: ");

        for (i = 0; i < players.size(); i++) {
            if(locations.get(players.get(i).getPlayerLocIndex()).getLoc().equals(locD.getLoc())) {
                sb.append(players.get(i).getPlayer());
                sb.append(" ");
            }
        }

        return sb.toString();
    }
}
