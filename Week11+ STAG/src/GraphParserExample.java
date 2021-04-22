import com.alexmerz.graphviz.*;
import com.alexmerz.graphviz.objects.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class GraphParserExample {

    //Constructor to call parser
    public GraphParserExample(String entityFilename, ArrayList<LocationData> location){
        //Call main on the input file
        main(entityFilename, location);
    }

    public static void main(String entityFilename, ArrayList<LocationData> location) {
        try {
            Parser parser = new Parser();
            FileReader reader = new FileReader(entityFilename);
            parser.parse(reader);
            ArrayList<Graph> graphs = parser.getGraphs();
            ArrayList<Graph> subGraphs = graphs.get(0).getSubgraphs();
            for(Graph g : subGraphs){
                System.out.printf("id = %s\n",g.getId().getId());

                ArrayList<Graph> subGraphs1 = g.getSubgraphs();
                for (Graph g1 : subGraphs1){
                    ArrayList<Node> nodesLoc = g1.getNodes(false);
                    Node nLoc = nodesLoc.get(0);
                    System.out.printf("\tid = %s, name = %s\n, description = %s\n",g1.getId().getId(), nLoc.getId().getId(), nLoc.getAttribute("description"));

                    //Create a new class in array for each location
                    LocationData loc = new LocationData(nLoc.getId().getId(), nLoc.getAttribute("description"));

                    ArrayList<Graph> subGraphs2 = g1.getSubgraphs();
                    for (Graph g2 : subGraphs2) {
                        System.out.printf("\t\tid = %s\n", g2.getId().getId());
                        ArrayList<Node> nodesEnt = g2.getNodes(false);
                        for (Node nEnt : nodesEnt) {
                            System.out.printf("\t\t\tid = %s, description = %s\n", nEnt.getId().getId(), nEnt.getAttribute("description"));
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
                    location.add(loc);
                    //Issue is how do we add paths if it's separate to the creation of the location?
                }

                ArrayList<Edge> edges = g.getEdges();
                for (Edge e : edges){
                    System.out.printf("Path from %s to %s\n", e.getSource().getNode().getId().getId(), e.getTarget().getNode().getId().getId());
                }
            }

        } catch (FileNotFoundException fnfe) {
            System.out.println(fnfe);
        } catch (com.alexmerz.graphviz.ParseException pe) {
            System.out.println(pe);
        }
    }
}
