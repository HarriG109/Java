package STAGCommand;
import STAGData.PlayerData;

public class STAGInv extends STAGProcessCommand {

    public String invString;

    public STAGInv(PlayerData currPlayer) {
        setInvString(returnInvAsString(currPlayer));
    }

    //Method to return string to console
    public String returnInvAsString(PlayerData currPlayer){

        //With brackets and commas
        int i;
        StringBuilder sb = new StringBuilder();

        sb.append("Your collection from your travels: ");

        for (i = 0; i < currPlayer.getPlayerInv(false).size(); i++) {
            sb.append(currPlayer.getPlayerInv(true).get(i));
            sb.append("\n");
        }
        if(i == 0){
            sb.append("\nEmpty");
        }

        return sb.toString();
    }

    //Method to set inv string
    public void setInvString(String inv){
        invString = inv;
    }

    //Method to get inv string
    public String getInvString(){
        return invString;
    }
}
