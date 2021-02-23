package main;

import agents.JadeContainer;
import agents.MainContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import logic.Graph;
import logic.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainApp {

    Graph graph = new Graph();

    private ContainerController mainContainer ;
    private AgentController mobileAgent, fixAgent, mobileAgent2;
    private List<Node> itinéraire;
    private List<Node> itinéraire2;
    private HashMap<String, ContainerController> containers = new HashMap<>();

    public MainApp() {
        mainContainer = new MainContainer().getContainer();

        // define contaners
        containers.put("root",  new JadeContainer("root").getContainer());
        for (int i = 1; i < 7; i++) {
            containers.put("node-"+i,  new JadeContainer("node-"+i).getContainer());
        }

        // define nodes
        graph.addNode(new Node("root"));
        for( int i =1; i<6; i++ ){
            graph.addNode(new Node( "node-" + i ));
        }
        // defining edges
        graph.addEdge("root", "node-1");
        graph.addEdge("root", "node-2");
        graph.addEdge("node-2", "node-3");
        graph.addEdge("node-2", "node-4");
        graph.addEdge("node-2", "node-5");
        //graph.addEdge("node-4", "node-6");
        //graph.addEdge("node-4", "node-7");
        //graph.addEdge("node-4", "node-8");
        // define intrus
        graph.nodes.get("node-2").contaminate();


        itinéraire = graph.getDFS("root");
        itinéraire2 = graph.getBFS("node-2");
        try {

            fixAgent = containers.get("root").createNewAgent("FPolice", "agents.AgentFixe", new Object[]{itinéraire, "police", 1000});
            fixAgent.start();

            mobileAgent2 = containers.get("node-2").createNewAgent(
                    "Virus", "agents.VirusMobile",new Object []{itinéraire2, "sick", 2000}) ;
            mobileAgent2.start();
        } catch (StaleProxyException e) { e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new MainApp();
    }

}
