package practice.dynamodb;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;

/**
 * #############################################
 * 
 * @author SHREYAS PATIL
 * 
 * ##############################################
 */

public class Events {

	static Map<String, Integer> hashmapStandard = new HashMap<String, Integer>();
	static Map<String, Integer> hashmapDynamodb = new HashMap<String, Integer>();
	static EventSets eventSets = new EventSets();


	public static void checkEvents_PaymentRegister(String pno) throws IOException, ParseException
		{
			CommonUtility.loadConfigFile();
			CommonUtility.establishConnectionToTable(pno);
			eventSets.paymentEventRegisterSuccess();
			CommonUtility.compareFreekookAndEndorsementEvents();
			
		}
		
		
		public static void checkEvents_CR_RP_AR_PaymentRegister(String pno) throws IOException, ParseException
		{
			CommonUtility.loadConfigFile();
			CommonUtility.establishConnectionToTable(pno);
			eventSets.paymentEvent_CR_RP_AR_RegisterSuccess();
			CommonUtility.compareFreekookAndEndorsementEvents();
		}
		
		public static void checkEvents_Payout(String pno) throws IOException, ParseException
		{
			CommonUtility.loadConfigFile();
			CommonUtility.establishConnectionToTable(pno);
			eventSets.payoutEvent();
			CommonUtility.compareFreekookAndEndorsementEvents();
		}

		public static void checkEvents_InitiatePaymentPayin(String pno) throws IOException, ParseException, InterruptedException {
			CommonUtility.loadConfigFile();
			CommonUtility.establishConnectionToTable(pno);
			eventSets.initiatePayInEvent();
			CommonUtility.compareFreekookAndEndorsementEvents();
		}
		
		public static void checkEvents_InitiatePaymentPayinForCO(String pno) throws IOException, ParseException, InterruptedException {
			CommonUtility.loadConfigFile();
			CommonUtility.establishConnectionToTable(pno);
			eventSets.initiatePayInForCOEvent();
			CommonUtility.compareFreekookAndEndorsementEvents();
		}
	
}
