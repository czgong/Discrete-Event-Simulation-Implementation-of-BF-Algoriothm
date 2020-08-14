import java.util.ArrayList;
import java.util.PriorityQueue;
import java.text.DecimalFormat;

public class Solver {
    ArrayList<ArrayList<Node>> myGraph = new ArrayList<>();
    double[] distances;
    int startNode;
    double time = 0;
    double[] processTimes;
    boolean[] visited;
    int[] previous;

    public Solver(int vertices){
        distances = new double[vertices];
        for(int i = 0; i < vertices; i++){
            distances[i] = Integer.MAX_VALUE;
        }

    }

    private static DecimalFormat df = new DecimalFormat("0.00");


    public void bellman(ArrayList<ArrayList<Node>> graph, int s, double[] processTimes){
        myGraph = graph;
        startNode = s;
        distances[s] = 0;
        this.processTimes = processTimes;
        PriorityQueue<Node> PQ = new PriorityQueue<>();
        visited = new boolean[graph.size()];
        previous = new int[graph.size()];


        //run it for V - 1 times
        for(int v = 0; v < myGraph.size() - 1; v++) {
            int start;
            for (int l = 0; l < myGraph.size(); l++) {
                start = l;
                //for each edge in the graph
                for (int i = 0; i < myGraph.get(l).size(); i++) {
                    PQ.add(myGraph.get(l).get(i));
                }

                while(PQ.size() != 0) {
                    Node currentNode = PQ.remove();
                    if (!visited[currentNode.node]) {
                        processMessage(currentNode, l);
                        if (distances[start] + currentNode.edgeCost < distances[currentNode.node]) {
                            distances[currentNode.node] = distances[start] + currentNode.edgeCost + processTimes[currentNode.node];
                            previous[currentNode.node] = start;
                        }
                    }
                }

                visited[start] = true;
            }

        }

    }

    //add cost to send and recieve message to total time
    public void processMessage(Node destination, int start){
        System.out.println("Vertex " + destination.node + " processing from vertex: " + start +  ". Current Time: " + df.format(time) + " ms");
        time += destination.edgeCost;
        time += destination.processTime;
        System.out.println("Edge Cost: " + destination.edgeCost + " ms");
        System.out.println("Vertex Process Time: " + df.format(destination.processTime) + " ms");
        System.out.println("Processed. Current Time: " + df.format(time) + " ms");
    }

    public String allPaths(){
        String a = "";
        for(int i = 0; i < distances.length; i++){
            if(distances[i] < -10000)
                return "Error: Negative Edge Cycle Detected";
        }
        for(int i = 0; i < distances.length; i++){
            a = a + "\n" + startNode + " to " + i + " shortest distance: " + df.format(distances[i]) + " milliseconds." + " Previous vertex: " + previous[i];
        }

        return a;
    }

    public void detectEdgeCostChange(ArrayList<ArrayList<Node>> g){
        double[] oldDistances = distances.clone();

        for(int i = 0; i < visited.length; i++) {
            visited[i] = false;
            distances[i] = Integer.MAX_VALUE;
        }
        distances[startNode] = 0;

        time = 0;
        bellman(g, startNode, processTimes);

        for(int i = 0; i < distances.length; i++){
            if(oldDistances[i] != distances[i]){
                double difference = oldDistances[i] - distances[i];
                if(difference > 0) {
                    System.out.println("Edge value decrease detected from vertex " + previous[i] + " to vertex " + i);
                }
                else{
                    System.out.println("Edge value increase detected from vertex " + previous[i] + " to vertex " + i);
                }
                System.out.println("Total path time has changed by: " + " ");
                System.out.println(df.format(Math.abs(difference)) + " ms");
            }
        }

    }
}
