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
    public JSONFileParser(String actionFilename, ArrayList<ActionsTriggerData> triggers){
        //Call main on the input file
        main(actionFilename, triggers);
    }

    public void main(String actionFilename, ArrayList<ActionsTriggerData> triggers){

        int i, j, k, l;
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
                /*.out.println("\n");
                System.out.println(triggers);
                System.out.println("\n");
                System.out.println(subjects);
                System.out.println("\n");
                System.out.println(consumed);
                System.out.println("\n");
                System.out.println(produced);
                System.out.println("\n");
                System.out.println(narration);*/
                //Loop through triggers creating instances of STAGData.ActionsTriggerData
                for(i = 0; i < triggerList.size(); i++){

                    //Create a new instance of trigger data
                    ActionsTriggerData trig = new ActionsTriggerData(triggerList.get(i).toString(), narration);

                    //Loop through and add subjects
                    for(j = 0; j < subjectList.size(); j++){
                        trig.addSubject(subjectList.get(j).toString());
                    }
                    //Loop through and add consumed
                    for(k = 0; k < consumedList.size(); k++){
                        trig.addConsumed(consumedList.get(k).toString());
                    }
                    //Loop through and add produced
                    for(l = 0; l < producedList.size(); l++){
                        trig.addProduced(producedList.get(l).toString());
                    }

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
