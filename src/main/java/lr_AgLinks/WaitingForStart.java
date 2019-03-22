package lr_AgLinks;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class WaitingForStart extends Behaviour {
    private Agent agent;
    private Setting set;
    private String endAg;
    private String startAg;
    private AID sender;
    private int count = 0;
    private Behaviour req;
    private Behaviour ans;
    private boolean done;
    private String content;
    private String protocol = "answer";

    public WaitingForStart(Agent agent, Setting set, String endAg, String startAg) {
//        super(agent,ParallelBehaviour.WHEN_ALL);
        this.agent = agent;
        this.set = set;
        this.endAg = endAg;
        this.startAg = startAg;
    }



    @Override
    public void action() {
        MessageTemplate mt = MessageTemplate.and(MessageTemplate.or(MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
                MessageTemplate.MatchPerformative(ACLMessage.INFORM)),
                MessageTemplate.MatchProtocol("request"));
        ACLMessage requestFrom = agent.receive(mt);
        if (requestFrom != null && requestFrom.getPerformative() == ACLMessage.REQUEST) {
            count++;
            sender = requestFrom.getSender();
            content = requestFrom.getContent();
            Behaviour ans = new WaitForAns(agent, set, startAg, endAg, sender, set.getLinks().size() - 1, content.split(",")[1], content);
            agent.addBehaviour(ans);
            for (Link i : set.getLinks()) {
//                System.out.println(agent.getLocalName() + ": 'I'll send request to " + i.getAgentName() + "'");
                Behaviour req = new SendRequest(agent, set, endAg, i, sender, content);
                agent.addBehaviour(req);
            }
        }else if(requestFrom != null && requestFrom.getPerformative() == ACLMessage.INFORM){
//            count--;
//            if (count == 0) {
//                System.err.println(agent.getLocalName() + " ended??????????");
                done = true;
            }
//                count++;
//            }else{
//            }
//        }
//        }
        else {
            block();
        }

    }
    @Override
    public boolean done() {
        return done;
    }
}
