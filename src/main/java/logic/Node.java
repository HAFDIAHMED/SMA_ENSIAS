package logic;

import jade.core.ContainerID;
import jade.util.leap.Serializable;

import java.util.ArrayList;
import java.util.List;

public class Node implements Serializable {

    public ContainerID loc;
    public String label;
    List<Node> childs;
    private boolean contaminated;
    private boolean visited;


    public Node(String label){
        this.label = label;
        contaminated =false;
        visited = false;
        childs = new ArrayList<Node>();
        setContainerId(label);
    }

    public boolean isVisited() {
        return this.visited;
    }
    public void visit() {
        this.visited = true;
    }



    public boolean isChild(String label) {
        for ( Node node: childs){
            if ( node.label.equals(label)){
                return  true;
            }
        }
        return false;
    }

    public void addChild(Node node) {
        if ( !isChild(node.label)) {
            childs.add(node);
        }
    }


    public void setContainerId( String name ) {
        loc = new ContainerID();
        loc.setName(name);
        loc.setAddress("localhost");
    }

    public void unVisit(){
        this.visited = false;
    }

    public void contaminate(){
        this.contaminated = true;
    }
    public void unContaminate() {this.contaminated = false; }
    public boolean isContaminated(){
        return  contaminated;
    }
}



