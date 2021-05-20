import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;
import java.util.ArrayList;
import STAGData.ActionsTriggerData;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONFileParser {

    //Constructor to call parser
    public JSONFileParser(){
    }

    //Method to import the actions from the .json file
    public void importActions(String actionFilename, ArrayList<ActionsTriggerData> triggers){

        int i;
        JSONParser parser = new JSONParser();

        try{
            FileReader reader = new FileReader(actionFilename);
            JSONObject jsonObject = (JSONObject) parser.parse(reader);

            JSONArray actions = (JSONArray) jsonObject.get("actions");

            for (Object number : actions) {
                JSONObject jsonNumber = (JSONObject) number;
                JSONArray triggerList = (JSONArray) jsonNumber.get("triggers");
                JSONArray subjectList = (JSONArray) jsonNumber.get("subjects");
                JSONArray consumedList = (JSONArray) jsonNumber.get("consumed");
                JSONArray producedList = (JSONArray) jsonNumber.get("produced");
                String narration = (String) jsonNumber.get("narration");

                //Loop through triggers creating instances of STAGData.ActionsTriggerData
                for(i = 0; i < triggerList.size(); i++){

                    //Create a new instance of trigger data
                    ActionsTriggerData trig = new ActionsTriggerData(triggerList.get(i).toString(), narration);

                    //Add subjects, consumed and produced
                    trig.addSubject(subjectList);
                    trig.addConsumed(consumedList);
                    trig.addProduced(producedList);

                    //Add trigger data to arraylist of triggers
                    triggers.add(trig);
                }
            }

        }
        catch (FileNotFoundException fnfe) {
            System.out.println(fnfe);
        }
        catch (IOException ioe) {
            System.out.println(ioe);
        } catch (ParseException pe) {
            System.out.println(pe);
        }
    }
}
