package agents;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.util.ExtendedProperties;
import jade.util.leap.Properties;
import jade.wrapper.ContainerController;

public class MainContainer {


    private ContainerController mainContainer;

    public MainContainer() {
        Runtime rt = Runtime.instance();
        Properties p = new ExtendedProperties() ; //fixer quelques propriétés
        p.setProperty("gui","true") ;
        ProfileImpl profile = new ProfileImpl(p);
        mainContainer = rt.createMainContainer(profile); //créer le main-container
    }

    public ContainerController getContainer(){ return mainContainer; }

}
