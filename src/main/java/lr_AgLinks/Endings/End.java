package lr_AgLinks.Endings;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

public class End extends OneShotBehaviour {
    private Agent agent;

    public End(Agent agent){
        this.agent = agent;
    }
    @Override
    public void action() {
        System.out.println(agent.getLocalName() + " FINISHED");
    }
}
