package lr_AgLinks;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class Start extends OneShotBehaviour {
    private Agent agent;
    private Setting set;
    private double weight = 0;
    private String chain;
    private int transitions;
    private boolean beginner;
    private String protocol;
    private String endAg;

    public Start(Agent agent, Setting set, String endAg){
        this.agent = agent;
        this.set = set;
        this.endAg = endAg;
    }
    @Override
    public void action(){
        System.out.println(agent.getLocalName() + ": 'Let's start'");
        beginner = true;
        protocol = "start";
        for (Link i:set.getLinks()){
//            System.out.println(agent.getLocalName() + ": ' first of all I'll send request to " + i.getAgentName() + "'");
            AID aid = new AID(i.getAgentName(), false);
            ACLMessage start = new ACLMessage(ACLMessage.REQUEST);
            start.clearAllReceiver();
            start.setProtocol("request");
//            System.out.println(agent.getLocalName() + ": 'will add " + aid.getLocalName() + "'");
            start.addReceiver(aid);
            chain = agent.getLocalName() + "-" + aid.getLocalName();
            weight = i.getWeight();
            transitions = 1;
            start.setContent(weight + "," + chain + "," + transitions);
            agent.send(start);
        }
        agent.addBehaviour(new WaitForAns(agent, set, set.getLinks().size(), (String)(weight + "," + chain + "," + transitions), endAg));
    }
}
