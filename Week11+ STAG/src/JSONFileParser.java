import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONFileParser {

    //Constructor to call parser
    public JSONFileParser(String actionFilename){
        //Call main on the input file
        main(actionFilename);
    }

    public static void main(String actionFilename){

        int i;
        JSONParser parser = new JSONParser();

        try{
            FileReader reader = new FileReader(actionFilename);
            JSONObject jsonObject = (JSONObject) parser.parse(reader);

            JSONArray actions = (JSONArray) jsonObject.get("actions");
            System.out.println("\n");
            System.out.println(actions);
            //int length = actions.length();

            for (Object number : actions) {
                JSONObject jsonNumber = (JSONObject) number;
                JSONArray triggers = (JSONArray) jsonNumber.get("triggers");
                JSONArray subjects = (JSONArray) jsonNumber.get("subjects");
                JSONArray consumed = (JSONArray) jsonNumber.get("consumed");
                JSONArray produced = (JSONArray) jsonNumber.get("produced");
                String narration = (String) jsonNumber.get("narration");
                System.out.println("\n");
                System.out.println(triggers);
                System.out.println("\n");
                System.out.println(subjects);
                System.out.println("\n");
                System.out.println(consumed);
                System.out.println("\n");
                System.out.println(produced);
                System.out.println("\n");
                System.out.println(narration);
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
