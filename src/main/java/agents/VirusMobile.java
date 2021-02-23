package agents;

import jade.content.lang.sl.SLCodec;
import jade.domain.FIPANames;
import jade.domain.mobility.MobilityOntology;

public class VirusMobile extends AgentMobile {


    @Override
    protected void beforeMove() {
        super.beforeMove();
    }


    @Override
    protected void afterMove() {
        super.afterMove();
        destination.contaminate();

        getContentManager().registerOntology(MobilityOntology.getInstance());
        getContentManager().registerLanguage(new SLCodec(), FIPANames.ContentLanguage.FIPA_SL0);
        addBehaviour( new GetAvailableLocationsBehaviour((AgentMobile) this) );
    }


}
