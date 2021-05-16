import STAGData.LocationData;
import com.alexmerz.graphviz.*;
import com.alexmerz.graphviz.objects.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class GraphParserExample {

    //Constructor to call parser
    public GraphParserExample(){
    }

    public void importEntities(String entityFilename, ArrayList<LocationData> location) {

        int i;

        try {
            Parser parser = new Parser();
            FileReader reader = new FileReader(entityFilename);
            parser.parse(reader);
            ArrayList<Graph> graphs = parser.getGraphs();
            ArrayList<Graph> subGraphs = graphs.get(0).getSubgraphs();
            for(Graph g : subGraphs){

                ArrayList<Graph> subGraphs1 = g.getSubgraphs();
                for (Graph g1 : subGraphs1){
                    ArrayList<Node> nodesLoc = g1.getNodes(false);
                    Node nLoc = nodesLoc.get(0);

                    //Create a new class in array for each location
                    LocationData loc = new LocationData(nLoc.getId().getId(), nLoc.getAttribute("description"));

                    ArrayList<Graph> subGraphs2 = g1.getSubgraphs();
                    for (Graph g2 : subGraphs2) {

                        ArrayList<Node> nodesEnt = g2.getNodes(false);
                        for (Node nEnt : nodesEnt) {

                            //Store the information into the respective location class
                            if(g2.getId().getId().equals("artefacts")){
                                loc.addArtefact(nEnt.getId().getId(), nEnt.getAttribute("description"));
                            }
                            else if(g2.getId().getId().equals("furniture")){
                                loc.addFurniture(nEnt.getId().getId(), nEnt.getAttribute("description"));
                            }
                            else if(g2.getId().getId().equals("characters")){
                                loc.addCharacter(nEnt.getId().getId(), nEnt.getAttribute("description"));
                            }
                        }
                    }

                    //Add location data to arraylist of locations
                    location.add(loc);
                }

                ArrayList<Edge> edges = g.getEdges();
                for (Edge e : edges){

                    //Look through locations and store the path if mentioned
                    for(i=0; i < location.size(); i++){
                        if(e.getSource().getNode().getId().getId().equals(location.get(i).getLoc())){
                            location.get(i).addPath(e.getTarget().getNode().getId().getId());
                        }
                    }
                }
            }

        } catch (FileNotFoundException fnfe) {
            System.out.println(fnfe);
        } catch (com.alexmerz.graphviz.ParseException pe) {
            System.out.println(pe);
        }
    }
}
