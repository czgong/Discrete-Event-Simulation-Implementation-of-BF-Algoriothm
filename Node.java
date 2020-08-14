public class Node implements Comparable<Node>{
    public int node;
    //initially the edge value, but will be reassigned to total cost from source
    public double edgeCost;
    public double processTime;
    public int lastNode;

    public Node(int node, double edgeCost, double processTime){
        this.node = node;
        this.edgeCost= edgeCost;
        this.processTime = processTime;
    }

    //compares total cost for priority queue
    public int compareTo(Node o) {
        if((processTime + edgeCost) < (o.processTime + o.edgeCost))
            return -1;
        if((processTime + edgeCost) > (o.processTime + o.edgeCost))
            return 1;
        return 0;
    }
}
