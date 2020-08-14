import java.util.ArrayList;
import java.util.Scanner;

public class Tester {

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        Solver s;
        ArrayList<ArrayList<Node>> graph = new ArrayList<>();
        double[] processTimes;




        System.out.print("Num Vertices :");
        int vertices = scanner.nextInt();
        s = new Solver(vertices);
        processTimes = new double[vertices];

        for(int i = 0; i < vertices; i++){
            ArrayList<Node> listOfNeighbors = new ArrayList<>();
            graph.add(listOfNeighbors);
        }

        //initialize random processing times for each vertex
        for(int i = 0; i < processTimes.length; i++){
            processTimes[i] = (Math.random() * .5) +1;
        }

        System.out.print("Num Edges :");
        int edges = scanner.nextInt();

        //user inputs
        for(int i = 0; i < edges; i++){
            System.out.print("Start:");
            int start = scanner.nextInt();
            System.out.print("End:");
            int end = scanner.nextInt();
            System.out.print("Cost:");
            int cost = scanner.nextInt();
            graph.get(start).add(new Node(end, cost, processTimes[end]));
            graph.get(end).add(new Node(start, cost, processTimes[start]));

        }

        System.out.print("Starting Point: ");
        int startVertex =  scanner.nextInt();

        s.bellman(graph, startVertex, processTimes);
        System.out.println(s.allPaths());

        int[] newvalues = changeRoute();
        for(int i = 0; i < graph.get(newvalues[0]).size();i++){
            if(newvalues[1] == graph.get(newvalues[0]).get(i).node){
                graph.get(newvalues[0]).get(i).edgeCost = newvalues[2];
            }

        }
        s.detectEdgeCostChange(graph);

        System.out.println(s.allPaths());



    }

    public static int[] changeRoute(){
        Scanner scanner = new Scanner(System.in);
        int start = scanner.nextInt();
        int end = scanner.nextInt();
        int cost = scanner.nextInt();
        int[] v = {start, end, cost};
        return v;
    }
}
