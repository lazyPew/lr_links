package lr_AgLinks;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;

import java.util.ArrayList;

public class AgLinkers extends Agent {
    private Agent agent;
    private ArrayList<AID> listOfLinks;

    public AgLinkers(){
//        Setting set = new Setting();
    }

    @Override
    protected void setup() {
        super.setup();
        Setting set = WorkWithConfigGFiles.unMarshalAny(Setting.class, getLocalName() + ".xml");
        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {registerAgent();}});
        addBehaviour(new FSMBeh(this, set));
    }
    private void registerAgent(){
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("Linkers");
        sd.setName(getLocalName() + "-linker");
        dfd.addServices(sd);
        try{
            DFService.register(this, dfd);
        }catch(FIPAException e){
            e.printStackTrace();
        }
    }
}