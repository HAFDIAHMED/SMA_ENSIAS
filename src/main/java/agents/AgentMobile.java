package agents;

import jade.content.lang.sl.SLCodec;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.FIPANames;
import jade.domain.mobility.MobilityOntology;
import logic.Node;

import java.util.Iterator;
import java.util.List;

public class AgentMobile extends Agent {


    protected List<Node> itineraire ;
    protected Node destination;


    public void foundIntrus( String loc) {
        System.out.println("Intrus found in :" + loc);
    }


    public void setup() {

        Object args[] = this.getArguments() ;
        if (args == null || args.length < 1) {
            System.out.println("Usage : <itineraire>");
            doDelete() ; //appelle takeDown()
        }
        else if ( args.length == 3) {
            System.out.println("name: " + getLocalName() );
            itineraire = (List<Node>) args[0];

            int time = (int) args[2];

            addBehaviour(new TickerBehaviour(this, time) {
                public void onTick() {

                    Iterator<Node> iterator = itineraire.listIterator();
                    try{
                        // Move to next container
                        destination =iterator.next();
                        iterator.remove();

                        //beforeMove
                        myAgent.doMove(destination.loc);
                        //afterMove

                    } catch (Exception e) {
                        myAgent.doDelete();
                    }
                }
            });

        }
    }


}
