package lr_AgLinks;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class Parallel8 extends ParallelBehaviour {
//    private Behaviour req;
//    private Behaviour ans;
    private Agent agent;
    private Setting set;
    private String startAg;
    private String endAg;
    private String content;
    private AID sender;
    private int count;

    public Parallel8(Agent agent, Setting set, String endAg, String startAg, AID sender, String content){
        super(agent,ParallelBehaviour.WHEN_ALL);
        this.agent = agent;
        this.set = set;
        this.endAg = endAg;
        this.startAg = startAg;
        this.sender = sender;
        this.content = content;


    }

    @Override
    public void onStart() {
        super.onStart();
//        MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
//                MessageTemplate.MatchProtocol("request"));
//        ACLMessage requestFrom = agent.receive(mt)
        for(Link i: set.getLinks()){
//            Behaviour ans = new WaitForAns(agent, set, endAg, startAg, sender, content);
//            Behaviour req = new SendRequest(agent, set, endAg, i, sender, content);
            count++;
//            addSubBehaviour(req);
//            addSubBehaviour(ans);
        }
//        if(ans.done)

    }


    @Override
    public int onEnd() {
        return super.onEnd();
    }
}
