package practice.utility;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.github.fge.jsonschema.main.cli.Main;
/**
 * @author SHREYAS
 *
 */
public class ReadPaymentJSON {

	public static Object object =new Object();
	public static JSONParser parser = new JSONParser();
	
	public static String mobikwik="";
	public static String paytm="";
	public static String cointab="";
	public static String onemg="";
	public static String flipkart="";
	public static String firstcry="";
	public static String fundsindia="";
	public static String indialends="";
	public static String policybazaar="";
	public static String razorpay="";
	
	public static String paymentStatus="";
	public static String payinTypeNB="";
	public static String payinTypeReinstatement="";
	public static String payinTypeRenewal="";
	public static String payinTypeCounterOffer="";
	
	public static String payoutStatus="";
	public static String payoutSuspense="";
	public static String payoutFreelook="";
	public static String payoutReject="";
	public static String payoutSurrender="";
	public static String payoutRiderSurrender="";
	public static String payoutReinstatementReject="";
	public static String payoutNullAndVoid="";
	public static String payoutRiderFreelook="";
	public static double suspenseAmountAfterPayout ;
	public static String payoutMode="";
	
	public static String suspenseRenewal="";
	public static String suspenseNB="";
	public static String suspenseReindtatement="";
	public static long statusCode=0;
	
	public static String offlinePatch="";
	public static String emptyPaymentMode="";
	public static String emptyVendorCode="";
	public static String paymentAmountNullMessage="";
	public static String mismatchPaymentType="";
	public static String incorrectDueDate="";
	public static String dueNotFound="";
	public static String emptyMobileAndEmail="";
	public static String emptySuspenseType="";
	public static String invalidSuspenseType="";
	public static String noSuspenseBalance="";
	
	public static void getExpectedDetailsFromJson() throws Exception
	{
		FileReader reader =null;
		
		try {
			reader  = new FileReader(System.getProperty("user.dir")+"/constant/payment.json");
			object   = (JSONObject) parser.parse(reader);

			JSONObject jsonObject      = new JSONObject();
			jsonObject                 = (JSONObject) ((HashMap) object).get("vendorCode");
			mobikwik                   = (String) jsonObject.get("mobikwik");
			paytm 		               = (String) jsonObject.get("paytm");
			cointab                    = (String) jsonObject.get("cointab");
			onemg                      = (String) jsonObject.get("onemg");
			flipkart                   = (String) jsonObject.get("flipkart");
			firstcry                   = (String) jsonObject.get("firstcry");
			fundsindia                 = (String) jsonObject.get("fundsIndia");
			indialends                 = (String) jsonObject.get("indialends");
			policybazaar               = (String) jsonObject.get("policyBazaar");
			razorpay                   = (String) jsonObject.get("razorpay");
			jsonObject                 = (JSONObject) ((HashMap) object).get("payin");
			paymentStatus              = (String) jsonObject.get("paymentStatus");
			payinTypeNB                = (String) jsonObject.get("paymentTypeNewBusiness");
			payinTypeReinstatement     = (String) jsonObject.get("paymentTypeReinstatement");
			payinTypeRenewal           = (String) jsonObject.get("paymentTypeRenewal");
			payinTypeCounterOffer      = (String) jsonObject.get("paymentTypeCounterOffer");
			jsonObject                 = (JSONObject) ((HashMap) object).get("payout");
			payoutStatus               = (String) jsonObject.get("payoutStatus");
			payoutSuspense             = (String) jsonObject.get("payoutTypeSuspense");
			payoutFreelook             = (String) jsonObject.get("payoutTypeFreelook");
			payoutReject               = (String) jsonObject.get("payoutTypeReject");
			payoutSurrender            = (String) jsonObject.get("payoutTypeSurrender");
			payoutRiderSurrender       = (String) jsonObject.get("payoutTypeRiderSurrender");
			payoutReinstatementReject  = (String) jsonObject.get("payoutTypeReinstatementReject");
			payoutNullAndVoid          = (String) jsonObject.get("payoutNullAndVoid");
			payoutRiderFreelook        = (String) jsonObject.get("payoutTypeRiderFreelook");
			suspenseAmountAfterPayout  = (double) jsonObject.get("suspenseAmountAfterPayout");
			offlinePatch               = (String) jsonObject.get("patchApiStatus");
			payoutMode				   = (String) jsonObject.get("payoutMode");
			jsonObject                 = (JSONObject) jsonObject.get("suspense");
			suspenseRenewal            = (String) jsonObject.get("suspenseTypeRenewal");
			suspenseNB                 = (String) jsonObject.get("suspenseTypeNewBusiness");
			suspenseReindtatement      = (String) jsonObject.get("suspenseTypeReinstatement");
			jsonObject                 = (JSONObject) ((HashMap) object).get("payinValidations");
			emptyVendorCode            = (String) jsonObject.get("emptyVendorCode");
			emptyPaymentMode           = (String) jsonObject.get("emptyPaymentMode");
			paymentAmountNullMessage   = (String) jsonObject.get("paymentAmountZero");
			mismatchPaymentType        = (String) jsonObject.get("mismatchPaymentType");
			incorrectDueDate           = (String) jsonObject.get("incorrectDueDate");
			dueNotFound                = (String) jsonObject.get("dueNotFound");
			emptyMobileAndEmail        = (String) jsonObject.get("emptyMobileAndEmail");
			jsonObject                 = (JSONObject) ((HashMap) object).get("payoutValidations");
			emptySuspenseType          = (String) jsonObject.get("emptySuspenseType");
			invalidSuspenseType        = (String) jsonObject.get("invalidSuspenseType");
			noSuspenseBalance          = (String) jsonObject.get("noSuspenseBalance");
			jsonObject                 = (JSONObject) ((HashMap) object).get("ResponseStatusCode");
			statusCode                 = (long) jsonObject.get("statusCode");
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			reader.close();
		}
	}
}
