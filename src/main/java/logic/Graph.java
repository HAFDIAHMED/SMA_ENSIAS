package logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Graph {

    HashMap<String, List<String>> adjList;
    public HashMap<String, Node> nodes;

    public  Graph() {
        nodes = new HashMap<>();
        adjList = new HashMap<>();
    }


    public void addNode(Node node) {
        if (!nodes.containsKey(node.label) ){
            nodes.put(node.label, node);
            adjList.put(node.label, new ArrayList<String>());
        }
    }

    public void addNode(String label) {
        if (!nodes.containsKey(label) ){
            nodes.put(label, new Node(label));
            adjList.put(label, new ArrayList<String>());
        }
    }

    public void addEdge(String from, String to) {
        if ( ! nodes.get(from).isChild(to) ){
            nodes.get(from).addChild( nodes.get(to) );
            adjList.get(from).add(to);
            // because undirected
            addEdge(to, from);
        }
    }

    public List<String> dfs( String label, List<String> arr ){
        if( nodes.get(label).isVisited() ) {
            return arr;
        }
        arr.add(label);
        // System.out.println(label);
        nodes.get(label).visit();
        for ( String child: adjList.get(label)){
            arr =  dfs(child, arr) ;
        }
        return  arr;
    }

    public List<Node> dfs( Node root, List<Node> arr ){
        if( root.isVisited() ) {
            return arr;
        }
        root.visit();
        arr.add(root);
        for ( Node child: root.childs){
            arr = dfs(child, arr);
        }
        return arr;
    }

    public void bfs(String label) {
        if( nodes.get(label).childs.size() == 0){
            return;
        }
        LinkedList<Node> queue = new LinkedList<>();
        queue.add(nodes.get(label));

        while (!queue.isEmpty()) {
            Node currentFirst = queue.removeFirst();
            if (currentFirst.isVisited())
                continue;

            System.out.println(currentFirst.label);
            currentFirst.visit();
            List<Node> allNeighbors = nodes.get(currentFirst.label).childs;
            if (allNeighbors == null)
                continue;
            for (Node neighbor : allNeighbors) {
                if (!neighbor.isVisited()) {
                    queue.add(neighbor);
                }
            }
        }
    }


    public List<Node> bfs(Node root, List<Node> arr) {
        if( root.childs.size() == 0){
            return new ArrayList<Node>();
        }
        LinkedList<Node> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            Node currentFirst = queue.removeFirst();
            if (currentFirst.isVisited())
                continue;
            currentFirst.visit();
            arr.add(currentFirst);
            List<Node> allNeighbors = nodes.get(currentFirst.label).childs;
            if (allNeighbors == null)
                continue;
            for (Node neighbor : allNeighbors) {
                if (!neighbor.isVisited()) {
                    queue.add(neighbor);
                }
            }
        }

        return arr;
    }

    public void clear() {
        nodes.get("root").unVisit();
        for( int i=1; i< nodes.size(); i++){
            nodes.get("node-"+i).unVisit();
        }
    }

    public List<Node> getDFS(String start) {
        List<Node> itinéraire = this.dfs(nodes.get(start), new ArrayList<Node>());
        itinéraire.remove(0);
        this.clear();
        return itinéraire;
    }

    public List<Node> getBFS(String start) {
        List<Node> itinéraire = this.bfs(nodes.get(start), new ArrayList<Node>());
        itinéraire.remove(0);
        this.clear();
        return itinéraire;
    }




    public String getJson() {
        return  "";
    }

}
