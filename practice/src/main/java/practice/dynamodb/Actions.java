package practice.dynamodb;

/**###########################################
 * 
 * @author SHREYAS PATIL
 * 
 *##############################################
 */
public interface Actions {

	public abstract void paymentEventRegisterSuccess();
	public abstract void paymentEvent_CR_RP_AR_RegisterSuccess();
	public abstract void initiatePayInEvent();
	public abstract void initiatePayInForCOEvent();
	public abstract void payoutEvent();
		
}
