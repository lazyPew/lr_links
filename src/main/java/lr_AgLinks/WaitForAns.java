package lr_AgLinks;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.ArrayList;
import java.util.List;

public class WaitForAns extends Behaviour {
    private Agent agent;
    private Setting set;
    private String endAg;
    private String startAg;
    private String content;
    private AID sender;
    private boolean done;
    private int transitions;
    private double weight0;
    private String chain0;
    private int transitions0;
    private String bestChain;
    private double bestWeight = 100000;
    private int size;
    private boolean beginner;
    private String protocol;

    public WaitForAns(Agent agent, Setting set, String startAg, String endAg, AID sender, int size, String protocol, String content){
        this.agent = agent;
        this.set = set;
        this.startAg = startAg;
        this.endAg = endAg;
        this.sender = sender;
        this.size = size;
        this.protocol = protocol;
//        beginner = false;
        this.content = content;
    }

    public WaitForAns(Agent agent, Setting set, int size, String content, String endAg){//, String protocol){
        this.agent = agent;
        this.set = set;
//        this.startAg = startAg;
        this.size = size;
//        this.beginner = beginner;
        this.content = content;
        this.protocol = agent.getLocalName();
        this.endAg = endAg;
    }

    @Override
    public void onStart() {
//        System.out.println("PROTOCOL FOR ANSWER FOR " + startAg + " IS EQUAL " + protocol + " SIZE OF LINKS EXCEPT FOR SENDER = " + content);
        super.onStart();
    }

    @Override
    public void action() {
        if (!agent.getLocalName().equals(endAg) && Integer.parseInt(content.split(",")[2])<6){
            MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchProtocol(protocol),
                    MessageTemplate.or(MessageTemplate.MatchPerformative(ACLMessage.REFUSE),
                            MessageTemplate.MatchPerformative(ACLMessage.CONFIRM)));
            ACLMessage answerFrom = agent.receive(mt);
            if (answerFrom != null) {
//                System.out.println();
                weight0 = Double.parseDouble((answerFrom.getContent().split(","))[0]);
                chain0 = (answerFrom.getContent().split(","))[1];
                transitions0 = Integer.parseInt((answerFrom.getContent().split(","))[2]);
                if (answerFrom.getPerformative() == ACLMessage.CONFIRM) {
                    if (weight0 < bestWeight) {
                        bestWeight = weight0;
                        bestChain = chain0;
                    }
                }
                ACLMessage answerTo = answerFrom.createReply();
                answerTo.setProtocol(chain0.split("-" + agent.getLocalName())[0]);
                answerTo.clearAllReceiver();
                answerTo.setContent(bestWeight + "," + bestChain + "," + (transitions0-1));
                answerTo.addReceiver(sender);
                if (bestChain == null) {
                    answerTo.setPerformative(ACLMessage.REFUSE);
                } else {
                    answerTo.setPerformative(ACLMessage.CONFIRM);
                }agent.send(answerTo);
                size--;
            } else {
                block();
            }
        }else if(agent.getLocalName().equals(endAg) || Integer.parseInt(content.split(",")[2]) >= 6){
//
            done = true;
        }
        if(size == 0){
            if(protocol==agent.getLocalName()){
//                System.err.println("FOUND BEST CHAIN FOR START FROM " + agent.getLocalName() + " AND END WITH " + endAg + ": " + bestWeight + "," + bestChain);
            }
            done = true;
        }
    }
    @Override
    public boolean done() {
        return done;
    }

    @Override
    public int onEnd() {
        if(protocol==agent.getLocalName()) {
            System.out.println("            IN THE END FOUND BEST CHAIN FOR " + agent.getLocalName() + " AND END WITH " + endAg + ": " + bestWeight + "," + bestChain);
            ACLMessage ending = new ACLMessage(ACLMessage.INFORM);
            ending.setProtocol("request");

            DFAgentDescription dfd = new DFAgentDescription();
            ServiceDescription sd = new ServiceDescription();
            sd.setType("Linkers");
            dfd.addServices(sd);
            DFAgentDescription[] foundservices = null;
            List<AID> receivers = new ArrayList<AID>();
            try{
                foundservices = DFService.search(agent,dfd);
            }catch(FIPAException e){
                e.printStackTrace();
            }
            for (int i=0; i<foundservices.length; i++){
                receivers.add(foundservices[i].getName());
                ending.addReceiver(foundservices[i].getName());
            }

            ending.addReceiver(agent.getAID());
            agent.send(ending);
//            System.err.println("          " + agent.getLocalName() + " WILL SEND ENDING TO " + receivers);
//            agent.send(ending);        }else{
//            System.out.println("DONE FOUND BEST CHAIN FOR " + agent.getLocalName() + " IN CHAIN " + protocol +" AND END WITH " + endAg + ": " + bestWeight + "," + bestChain);
        }
        return super.onEnd();
    }
}
