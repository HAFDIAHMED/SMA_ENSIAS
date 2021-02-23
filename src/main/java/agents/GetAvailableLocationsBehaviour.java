package agents;

import jade.content.lang.sl.SLCodec;
import jade.content.onto.basic.Action;
import jade.content.onto.basic.Result;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.FIPANames;
import jade.domain.JADEAgentManagement.QueryAgentsOnLocation;
import jade.domain.mobility.MobilityOntology;
import jade.lang.acl.ACLMessage;
import jade.proto.SimpleAchieveREInitiator;
import jade.util.leap.Iterator;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;

import java.util.ArrayList;
import java.util.List;

public class GetAvailableLocationsBehaviour extends SimpleAchieveREInitiator {


    private ACLMessage request;
    public GetAvailableLocationsBehaviour(Agent a) {
        super(a, new ACLMessage(ACLMessage.REQUEST));
        request = (ACLMessage)getDataStore().get(REQUEST_KEY);

        // fills all parameters of the request ACLMessage
        request.clearAllReceiver();
        request.addReceiver(a.getAMS());
        request.setLanguage(FIPANames.ContentLanguage.FIPA_SL0);
        request.setOntology(MobilityOntology.NAME);
        request.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);

        // creates the content of the ACLMessage
        try {
            Action action = new Action();
            action.setActor(a.getAMS());
            QueryAgentsOnLocation q = new QueryAgentsOnLocation();
            q.setLocation(this.getAgent().here());
            action.setAction(q);
            a.getContentManager().fillContent(request, action);
        }
        catch(Exception e) { e.printStackTrace() ;}
        myAgent.send(request);
        // reset(request);
    }



    protected void handleInform(ACLMessage inform) {
        try {
            myAgent.getContentManager().registerLanguage(new SLCodec(), FIPANames.ContentLanguage.FIPA_SL0);
            myAgent.getContentManager().registerOntology(MobilityOntology.getInstance());
            Result sitesDisponibles = (Result)myAgent.getContentManager().extractContent(inform);
            Iterator iterator = sitesDisponibles.getItems().iterator();

            ArrayList<String> arr = new ArrayList<String>();

            while(iterator.hasNext()){
                AID id = (AID) iterator.next();
                arr.add(id.getLocalName());
            }

            if ( myAgent.getLocalName().equals("Virus") ){
                System.out.println("\t\t\t\t\t\t\t\t\t Virus: ----> to : " + ((AgentMobile)this.myAgent).destination.loc.getName());
                System.out.println("\t\t\t\t\t\t\t\t\t agents disponibles :");
                System.out.println("\t\t\t\t\t\t\t\t\t" + arr);
            }else{
                System.out.println("Police ----> to : " + ((AgentMobile)this.myAgent).destination.loc.getName());
                System.out.println("agents disponibles :");
                System.out.println(arr);
            }

            if ( ( arr.contains("FPolice") || arr.contains("MPolice")) && arr.contains("Virus")){

                System.out.println("\t\t\t\t#######################");
                System.out.println("\t\t\t\t-------- KILL ---------");
                AgentContainer container =(AgentContainer) myAgent.getContainerController();
                AgentController victim = container.getAgent("Virus");
                //victim.kill();
                System.out.println("\t\t\t\t-------- KILLED -------");
                System.out.println("\t\t\t\t######################");
            }
            System.out.println("\n");
        }
        catch(Exception e) {
            e.printStackTrace();
        }

    }
}
