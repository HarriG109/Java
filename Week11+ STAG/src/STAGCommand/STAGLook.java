package STAGCommand;
import STAGData.LocationData;
import STAGData.PlayerData;
import java.util.ArrayList;

//Class allowing player to look in a location
public class STAGLook extends STAGProcessCommand {

    //Method to return string of location data
    public String getLocInfo(PlayerData currPlayer,
                             ArrayList<PlayerData> players, ArrayList<LocationData> location){

        //Re-create location in case location changes between actions
        LocationData locD = location.get(currPlayer.getPlayerLocIndex());
        StringBuilder sb = new StringBuilder();

        sb.append("You are in " + locD.getLocDesc() + ". You can see: \n");
        sb.append(currLocPlayersAsString(locD, currPlayer, players, location));
        sb.append(currLocCharsAsString(locD));
        sb.append(currLocArtAsString(locD));
        sb.append(currLocFurnAsString(locD));
        sb.append(currLocPathAsString(locD));

        return sb.toString();
    }


    //Method to return string of characters in current location
    public String currLocCharsAsString(LocationData locD){

        int i;
        StringBuilder sb = new StringBuilder();

        for (i = 0; i < locD.getCharList(false).size(); i++) {
            sb.append(locD.getCharList(true).get(i));
            sb.append("\n");
        }

        return sb.toString();
    }

    //Method to return string of artefacts in current location
    public String currLocArtAsString(LocationData locD){

        int i;
        StringBuilder sb = new StringBuilder();

        for (i = 0; i < locD.getArtefactList(false).size(); i++) {
            sb.append(locD.getArtefactList(true).get(i));
            sb.append("\n");
        }

        return sb.toString();
    }

    //Method to return string of furniture in current location
    public String currLocFurnAsString(LocationData locD){

        int i;
        StringBuilder sb = new StringBuilder();

        for (i = 0; i < locD.getFurnitureList(false).size(); i++) {
            sb.append(locD.getFurnitureList(true).get(i));
            sb.append("\n");
        }

        return sb.toString();
    }

    //Method to return string of paths in current location
    public String currLocPathAsString(LocationData locD){

        int i;
        StringBuilder sb = new StringBuilder();

        for (i = 0; i < locD.getPaths().size(); i++) {
            if(i == 0){
                sb.append("You can access from here:\n");
            }
            sb.append("A " + locD.getPaths().get(i));
            sb.append("\n");
        }

        return sb.toString();
    }

    //Method to see other player
    public String currLocPlayersAsString(LocationData locD, PlayerData currPlayer,
                                         ArrayList<PlayerData> players, ArrayList<LocationData> locations){

        int i;
        StringBuilder sb = new StringBuilder();

        for (i = 0; i < players.size(); i++) {
            if(locations.get(players.get(i).getPlayerLocIndex()).getLoc().equals(locD.getLoc()) &&
               !currPlayer.getPlayer().equals(players.get(i).getPlayer())) {
                sb.append("Another player called " + players.get(i).getPlayer());
                sb.append("\n");
            }
        }

        return sb.toString();
    }
}
