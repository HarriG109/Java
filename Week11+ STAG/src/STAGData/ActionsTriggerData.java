package STAGData;
import org.json.simple.JSONArray;
import STAGExceptions.IndexDoesntExistException;

import java.util.ArrayList;

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
    }

    //Method to add consumed info to arraylist
    public void addConsumed(JSONArray consume){
        consumedList.addAll(consume);
    }

    //Method to add produced info to arraylist
    public void addProduced(JSONArray produce){
        producedList.addAll(produce);
    }

    //Method to get trigger name
    public String getTrig(){
        return trigger;
    }

    //Method to get narration name
    public String getNarr(){
        return narration;
    }

    //Method to get subjects at specific index
    public String getSubject(int index) throws IndexDoesntExistException {

        if(index >= subjectsList.size()){
            IndexDoesntExistException idee = new IndexDoesntExistException();
            throw idee;
        }
        else{
            return subjectsList.get(index);
        }
    }

    //Method to get consumed at specific index
    public String getConsumed(int index) throws IndexDoesntExistException {

        if(index >= consumedList.size()){
            IndexDoesntExistException idee = new IndexDoesntExistException();
            throw idee;
        }
        else{
            return consumedList.get(index);
        }
    }

    //Method to get produced at specific index
    public String getProduced(int index) throws IndexDoesntExistException {

        if(index >= producedList.size()){
            IndexDoesntExistException idee = new IndexDoesntExistException();
            throw idee;
        }
        else{
            return producedList.get(index);
        }
    }
}
