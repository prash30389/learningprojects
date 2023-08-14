package practice.dynamodb;

import java.util.ArrayList;

/**###########################################
 * 
 * @author SHREYAS PATIL
 * 
 *##############################################
 */
public class EventSets implements Actions {

	static ArrayList<String> dynamodbEvents = new ArrayList<String>();
	static ArrayList<String> standardEvents = new ArrayList<String>();
	static ArrayList<String> commonEvents = new ArrayList<String>();

		public void paymentEventRegisterSuccess() {
			// TODO Auto-generated method stub
			standardEvents.add("com.aegonlife.payment.mandate.registration.initiated");
			standardEvents.add("com.aegonlife.payment.mandate.registered");
			standardEvents.add("com.aegonlife.payment.registered");
		}
		public void paymentEvent_CR_RP_AR_RegisterSuccess() {
			
			standardEvents.add("com.aegonlife.payment.mandate.registration.initiated");
			standardEvents.add("com.aegonlife.payment.mandate.registration.initiated");
			standardEvents.add("com.aegonlife.payment.mandate.registered");
			standardEvents.add("com.aegonlife.payment.mandate.registered");
			standardEvents.add("com.aegonlife.payment.registered");
			standardEvents.add("com.aegonlife.payment.registered");
		}
		public void payoutEvent(){
			standardEvents.add("com.aegonlife.payment.payout.successful");
		}
		
		
		public void initiatePayInEvent() {
			standardEvents.add("com.aegonlife.payment.received");
			standardEvents.add("com.aegonlife.payment.mandate.registration.initiated");
		}
		
		public void initiatePayInForCOEvent() {
			standardEvents.add("com.aegonlife.payment.received");
		}
	
}
