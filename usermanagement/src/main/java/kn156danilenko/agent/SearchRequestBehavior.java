package kn156danilenko.agent;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;


public class SearchRequestBehavior extends Behaviour {

	private AID[] aids;
	private String firstName;
	private String lastName;
	
	public SearchRequestBehavior(AID[] aids, String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.aids = aids;
	}

	public void action() {
		if(aids != null) {
			ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
			message.setContent(firstName + "," + lastName);
			for(int i = 0; i < aids.length; i++) {
				message.addReceiver(aids[i]);
			}
			myAgent.send(message);
		}
	}

	@Override
	public boolean done() {
		return true;
	}

}
