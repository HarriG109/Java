package STAGData;
import org.json.simple.JSONArray;
import java.util.ArrayList;

//Class which stores triggers
public class ActionsTriggerData {

    String trigger;
    String narration;
    public ArrayList<String> subjectsList = new ArrayList<String>();
    public ArrayList<String> consumedList = new ArrayList<String>();
    public ArrayList<String> producedList = new ArrayList<String>();

    //Constructor to populate trigger information
    public ActionsTriggerData(String triggerName, String narr){
        trigger = triggerName;
        narration = narr;
    }

    //Method to add subject to arraylist
    public void addSubject(JSONArray subject){
        subjectsList.addAll(subject);
    } //Not understanding this, Intellij says method is not used but it is within JSONFileParser

    //Method to add consumed info to arraylist
    public void addConsumed(JSONArray consume){
        consumedList.addAll(consume);
    } //Not understanding this, Intellij says method is not used but it is within JSONFileParser

    //Method to add produced info to arraylist
    public void addProduced(JSONArray produce){
        producedList.addAll(produce);
    } //Not understanding this, Intellij says method is not used but it is within JSONFileParser

    //Method to get trigger name
    public String getTrig(){
        return trigger;
    }

    //Method to get narration name
    public String getNarr(){
        return narration;
    }

    //Method to get narration name
    public ArrayList<String> getSubjects(){
        return subjectsList;
    }

    //Method to get narration name
    public ArrayList<String> getProduced(){
        return producedList;
    }

    //Method to get narration name
    public ArrayList<String> getConsumed(){
        return consumedList;
    }
}
