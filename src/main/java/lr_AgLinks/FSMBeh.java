package lr_AgLinks;

import jade.core.Agent;
import jade.core.behaviours.FSMBehaviour;
import lr_AgLinks.Endings.End;
import lr_AgLinks.Endings.Fail;
import lr_AgLinks.Endings.Success;

public class FSMBeh extends FSMBehaviour {
    private Agent agent;
    private Setting set;
    private double weight;
    private String startAg = "Agent2";
    private String endingAg = "Agent10";
    private int limit = 6;
//    private AID start = startAg;

//    private Price prices;
    private final static String START = "start";
    private final static String WAIT = "wait";
    private final static String SUCCESS = "success";
    private final static String FAIL = "fail";
    private final static String END = "END";

    public FSMBeh(Agent agent, Setting set){
        this.agent = agent;
        this.set = set;

        if (agent.getLocalName().equals(startAg)){
            System.out.println(agent.getLocalName() + ": 'Program will start from me'");
            registerFirstState(new Start(agent, set,endingAg), START);
            registerState(new WaitingForStart(agent,set,endingAg,startAg),WAIT);
//            registerState(new Parallel8(agent, new SendRequest(agent,set,endingAg,startAg),
//                    new WaitForAns(agent,set,endingAg,startAg)), WAIT);
            registerState(new Success(agent), SUCCESS);
            registerState(new Fail(agent),FAIL);
            registerLastState(new End(agent), END);

            registerDefaultTransition(START,WAIT);
            registerTransition(WAIT, SUCCESS, 1);
            registerTransition(WAIT, FAIL, 0);
        }
        else{
//            System.out.println(agent.getLocalName() + ": 'I'll be right behind'");
            registerFirstState(new WaitingForStart(agent,set,endingAg,startAg),WAIT);
//            registerState(new Parallel8(agent, new SendRequest(agent,set,endingAg,startAg),
//                    new WaitForAns(agent,set,endingAg,startAg)), WAIT);
            registerState(new Success(agent), SUCCESS);
            registerState(new Fail(agent),FAIL);
            registerLastState(new End(agent), END);

            registerTransition(WAIT, SUCCESS, 1);
            registerTransition(WAIT, FAIL, 0);
        }
        registerDefaultTransition(SUCCESS,END);
        registerDefaultTransition(FAIL,END);

    }
}
