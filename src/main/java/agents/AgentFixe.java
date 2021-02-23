package agents;

import jade.content.lang.sl.SLCodec;
import jade.core.Agent;
import jade.domain.FIPANames;
import jade.domain.mobility.MobilityOntology;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import logic.Node;

import java.util.List;

public class AgentFixe extends Agent {

    public void setup() {

        // register the SL0 content language and the mobility ontology
        getContentManager().registerLanguage(new SLCodec(),
                FIPANames.ContentLanguage.FIPA_SL0);
        getContentManager().registerOntology(MobilityOntology.getInstance());


        Object args[] = this.getArguments() ;
        if ( args.length == 3 ){
            try {
                AgentController mobileAgent = this.getContainerController().createNewAgent(
                        "MPolice", "agents.PoliceMobile",new Object []{ (List<Node>)args[0], args[1].toString(), (int) args[2]}) ;
                mobileAgent.start();
            } catch (StaleProxyException e) {
                e.printStackTrace();
            }
        }

    }

}
