import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Collections;

public class Solver {
    private ArrayList<Integer> pathIds;
    private double pathLength;

    private Node head, target;



    Solver() {
        this.pathIds = new ArrayList<> ();
        this.pathLength = 0;
        this.head = null;
        this.target = null;
    }

    public void setHead(Node head) {
        this.head = head;
    }

    public void setTarget(Node target) {
        this.target = target;
    }

    public ArrayList<Integer> getPathIds() {
        return pathIds;
    }

    public boolean aStar(){
        if(head == null || target == null) return false;

        PriorityQueue<Node> closedList = new PriorityQueue<>();
        PriorityQueue<Node> openList = new PriorityQueue<>();

        head.setF(head.getG() + head.getH());
        openList.add(head);

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

    public void fillPath(){
        Node n = target;

        if(n == null)
            return;

        while(n.getParent() != null){
            pathIds.add(n.getId());
            n = n.getParent();
        }
        pathIds.add(n.getId());
        Collections.reverse(pathIds);
    }

    public void printPathLength() {
        System.out.println("\nPath length: " + pathLength + "\n");
    }

}
