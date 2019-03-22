package lr_AgLinks;

import jade.core.AID;
import jade.core.Agent;
//import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
//import jade.lang.acl.MessageTemplate;
//import lr_AgLinks.Link;
//import lr_AgLinks.Setting;

public class SendRequest extends OneShotBehaviour {
    private Agent agent;
    private Setting set;
    private String endAg;
    private double weight;
    private String chain;
    private int transitions;
    private double weight0;
    private String chain0;
    private int transitions0;
    private String content;
    private String startAg;
    private AID sender;
    private Link sendReqTo;

    public SendRequest(Agent agent, Setting set, String endAg, Link sendReqTo, AID sender, String content) {
        this.agent = agent;
        this.set = set;
        this.endAg = endAg;
//        this.startAg = startAg;
        this.sender = sender;
        this.content = content;
        this.sendReqTo = sendReqTo;
    }

    @Override
    public void action() {
        AID aid = new AID(sendReqTo.getAgentName(), false);
        weight0 = Double.parseDouble((content.split(","))[0]);
        chain0 = (content.split(","))[1];
        transitions0 = Integer.parseInt((content.split(","))[2]);
//        System.out.println("CURRENT CHAIN IS " + chain0 + "    transitions is  = " + transitions0 + "   " + aid.getLocalName() );
        if (!aid.getLocalName().equals(sender.getLocalName())) {
            if (transitions0 < 6 && !agent.getLocalName().equals(endAg)) {
                weight = weight0 + sendReqTo.getWeight();;
                chain = (content.split(","))[1] + "-" + aid.getLocalName();
                transitions = Integer.parseInt((content.split(","))[2]) + 1;
                ACLMessage requestTo = new ACLMessage(ACLMessage.REQUEST);
                requestTo.setProtocol("request");
                requestTo.clearAllReceiver();
//                System.out.println(agent.getLocalName() + ": 'I'll send request to " + sendReqTo.getAgentName() + "'");
                requestTo.addReceiver(aid);
//                System.out.println(weight + " , " + chain + " , " + transitions);
                requestTo.setContent(weight + "," + chain + "," + (transitions));
                agent.send(requestTo);
                requestTo.clearAllReceiver();
            }
//            transitions++;
//            for (Link i: set.getLinks()){

        }else if(agent.getLocalName().equals(endAg) && transitions0<=6){
            ACLMessage answer = new ACLMessage(ACLMessage.CONFIRM);
            answer.setProtocol(chain0.replace(("-" + agent.getLocalName()),""));
//            System.out.println("CHAIN " + chain0 + " IS COOL, transitions = " + transitions0 + ", weight = " + weight0 + ". "
//                    + agent.getLocalName() + " WILL SEND CONFIRM TO " + sender.getLocalName() + " WITH PROTOCOL " + answer.getProtocol());
            answer.addReceiver(sender);
            answer.setContent(weight0 + "," + chain0 + "," + transitions0);
            agent.send(answer);
        }else if(transitions0>=6){
            ACLMessage answer = new ACLMessage(ACLMessage.REFUSE);
            answer.setProtocol(chain0.replace(("-" + agent.getLocalName()),""));
//            System.err.println("CHAIN " + chain0 + " IS DEAD END, transitions = " + transitions0 + ", weight = " + weight0 + ". "
//                    + agent.getLocalName() + " WILL SEND REFUSE TO " + sender.getLocalName() + " WITH PROTOCOL " + answer.getProtocol());
//            answer.clearAllReceiver();
            answer.setContent(weight0 + "," + chain0 + "," + transitions0);
            answer.addReceiver(sender);
            answer.setContent(10000 + "," + null + "," + transitions0);
            agent.send(answer);
//            answer.clearAllReceiver();
        }
     }
}