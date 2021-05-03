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

        sb.append("Inventory: ");

        for (i = 0; i < currPlayer.getPlayerInv().size(); i++) {
            sb.append(currPlayer.getPlayerInv().get(i).get(0));
            sb.append(" ");
        }
        if(i == 0){
            sb.append("Empty");
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
