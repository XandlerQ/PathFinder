import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Collections;

public class Solver {
    public ArrayList<Integer> pathIds;
    public double pathLength;

    Solver() {
        pathIds = new ArrayList<> ();
        pathLength = 0;
    }

    public boolean aStar(Node start, Node target){
        PriorityQueue<Node> closedList = new PriorityQueue<>();
        PriorityQueue<Node> openList = new PriorityQueue<>();

        start.setF(start.getG() + start.getH());
        openList.add(start);

        while(!openList.isEmpty()){
            Node n = openList.peek();
            if(n == target){
                pathLength = n.getG();
                return true;
            }

            for(Node.Edge edge : n.getNeighbours()){
                Node m = edge.getNode();
                double totalWeight = n.getG() + edge.getWeight();

                if(!openList.contains(m) && !closedList.contains(m)){
                    m.setParent(n);
                    m.setG(totalWeight);
                    m.setF(m.getG() + m.getH());
                    openList.add(m);
                } else {
                    if(totalWeight < m.getG()){
                        m.setParent(n);
                        m.setG(totalWeight);
                        m.setF(m.getG() + m.getH());

                        if(closedList.contains(m)){
                            closedList.remove(m);
                            openList.add(m);
                        }
                    }
                }
            }

            openList.remove(n);
            closedList.add(n);
        }
        return false;
    }

    public void fillPath(Node target){
        Node n = target;

        if(n==null)
            return;

        while(n.getParent() != null){
            pathIds.add(n.getId());
            n = n.getParent();
        }
        pathIds.add(n.getId());
        Collections.reverse(pathIds);
    }

    public void printPath() {
        for(int id : pathIds){
            System.out.print(id + " ");
        }
        System.out.println("\nPath length: " + pathLength + "\n");

    }

}
