package com.pthfndr;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Collections;

public class Solver {
    private ArrayList<Integer> pathIds;
    private double pathWeight;
    private double pathLength;

    private Node head, target;



    Solver() {
        this.pathIds = new ArrayList<> ();
        this.pathWeight = 0;
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

    public double getPathWeight() {
        return this.pathWeight;
    }

    public double getPathLength() {
        return pathLength;
    }

    public boolean aStar(){
        if(head == null || target == null) return false;
        System.out.println("Solution init");
        pathIds.clear();
        pathWeight = 0;
        pathLength = 0;

        PriorityQueue<Node> closedList = new PriorityQueue<>();
        PriorityQueue<Node> openList = new PriorityQueue<>();

        head.setF(head.getG() + head.getH());
        openList.add(head);

        while(!openList.isEmpty()){
            Node n = openList.peek();
            if(n == target){
                pathWeight = n.getG();
                pathLength = n.getL();
                return true;
            }

            for(Node.Edge edge : n.getNeighbours()){
                Node m = edge.getNode();
                double totalWeight = n.getG() + edge.getWeight();
                double totalLength = n.getL() + edge.getLength();


                if(!openList.contains(m) && !closedList.contains(m)){
                    m.setParent(n);
                    m.setG(totalWeight);
                    m.setL(totalLength);
                    m.setF(m.getG() + m.getH());
                    openList.add(m);
                } else {
                    if(totalWeight < m.getG()){
                        m.setParent(n);
                        m.setG(totalWeight);
                        m.setL(totalLength);
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

    /*public boolean dijkstra() {
        if(head == null || target == null) return false;


    }*/

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

    public void printPathWeight() {
        System.out.println("\nPath weight: " + pathWeight + "\n");
    }

    public void printPathLength() {
        System.out.println("\nPath length: " + pathLength + "\n");
    }

}
