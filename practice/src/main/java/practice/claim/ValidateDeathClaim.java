package practice.claim;


import io.restassured.response.Response;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import practice.testbase.IssuanceQueue;
import practice.testbase.PageObj;
import practice.testbase.TestBase;
import practice.utility.CreatePolicies;
import practice.utility.JSONUtils;
import practice.utility.PaymentConstants;
import practice.utility.Payment_Utility;
import practice.utility.ReadPaymentJSON;
import practice.utility.RestAPIUtils;


import java.util.concurrent.TimeUnit;

public class ValidateDeathClaim extends Endorsement_Nom_Contact {

    public static  String policyNumber="";
    public static String policyNumberForEventCheck="";
    private static String claimBaseUrl;
    public static String claimNumber ="";

    public static  void testClaimInitiatedStatusForPolicy() throws Exception {


  //      TestBase.init();
        claimBaseUrl = prop.getProperty("claim");
  //      Endorsement_Nom_Contact.Utility();
    	
        System.out.println(
                "\n******** Started execution of Test Scenario  :  API -> 'Claim Initiated' status updated in 'Policy Status Reason' ******\n");

        String sumAssurredAmount ="15000000";

        //Create policy and do payment
        PageObj.mockPolicy(sumAssurredAmount);
        policyNumber = PageObj.pnoMock();
        System.out.println(policyNumber);
        String uriPath = claimBaseUrl + "/internal/claims";

        //Initiate Claim
        int incomeBenefit=50,lumpSumBenefit=50; // declarartion & Intialisation

        // Calling updateClientInformation function
        postBody = ClaimUtility.updateClaimInformation(incomeBenefit,lumpSumBenefit,policyNumber);
        IssuanceQueue.waitSec(20);
        System.out.println("Headres>>>"+headers);
        // Send the Request
        response = RestAPIUtils.postOrPutResponse(uriPath, headers, bodyParams, postBody, "Post");
        IssuanceQueue.waitSec(10);
 //       Awaitility.await().atMost(10, TimeUnit.SECONDS).until(() -> response.getStatusCode() == 200);
        System.out.println(response.getBody());
        System.out.println("Waiting for 50 seconds");
        IssuanceQueue.waitSec(50);

        // Verify the status code
 //       Assert.assertEquals(response.getStatusCode(), 200);
 //       IssuanceQueue.waitSec(14);

        //Get Claim Number
         claimNumber= response.jsonPath().get("claimNumber");

        //Save Claim
        postBody = ClaimUtility.savedClaim(policyNumber,claimNumber);
        System.out.println("Waiting for 60 seconds");
        IssuanceQueue.waitSec(60);
        Response responseSave = RestAPIUtils.patchResponse(claimBaseUrl,"/internal/claims/additional-info",headers,postBody);
        System.out.println("Response after save claim:: "+responseSave.getBody().asString());
        //IssuanceQueue.waitSec(120);

 //       String baseURI = prop.getProperty("policy");
 //       Response responsePolicyDetail = RestAPIUtils.getResponse(baseURI,"/group/policies/"+policyNumber,headers);

        //Check updated value 'Claim Initiated'
 //       System.out.println("Before Assertion Execution");
 //       Assert.assertTrue(JSONUtils.verifyValueInResponse(responsePolicyDetail, "policyStatus", "reasonDescription", ConstantValues.CLAIM_INITIATED));

        System.out.println(
                "\n******** End execution of Test Scenario  :  API -> 'Claim Initiated' status updated in 'Policy Status Reason' ******\n");
        
        System.out.println("Claim Initiated Policy:"+policyNumber);
    }
//===========================================================================================
    
   
    public static void claimRegistered() throws Exception
    {
    	  postBody = ClaimUtility.registeredClaim(policyNumber,claimNumber);
          Response responseRegistered = RestAPIUtils.patchResponse(claimBaseUrl,"/internal/claims/register",headers,postBody); 
       //   responseSave.prettyPrint();

          //Awaitility.await().atMost(20, TimeUnit.SECONDS).until(() -> responseSave.getStatusCode() == 204); //5seconds
          IssuanceQueue.waitSec(120);

          String baseURI = prop.getProperty("policy");

          IssuanceQueue.waitSec(15);
          Response responsePolicyDetail = RestAPIUtils.getResponse(baseURI,"/group/policies/"+policyNumber,headers);

          //Check updated value 'Claim Registered'
          Assert.assertTrue(JSONUtils.verifyValueInResponse(responsePolicyDetail, "policyStatus", "reasonDescription", ConstantValues.CLAIM_REGISTERED));

          System.out.println(
                  "\n******** End execution of Test Scenario  :  API -> 'Claim Registered' status updated in 'Policy Status Reason' ******\n");
    }
    
   //*****************************************************************************************************************************8
   
    
    
    public static String testApprovedClaimStatusForInforcePolicy() throws Exception {

 //   	TestBase.init();
        claimBaseUrl = prop.getProperty("claim");
       
    	 System.out.println(
                "\n******** Started execution of Test Scenario  :  API -> status updated in 'Policy Status Reason' when approved claim of 'inforce' policy by claim approver ******\n");

        //headerSetup();
 //       PageObj.mockPolicy();
 //       policyNumber = PageObj.pnoMock();
        String sumAssurredAmount ="15000000";
        PageObj.mockPolicy(sumAssurredAmount);
        policyNumber = PageObj.pnoMock();
        System.out.println(policyNumber);

        String uriPath = claimBaseUrl + "/internal/claims";

        //Initiate Claim
        int incomeBenefit=50,lumpSumBenefit=50; // declarartion & Intialisation

        // calling updateClientInformation function
        postBody = ClaimUtility.updateClaimInformation(incomeBenefit,lumpSumBenefit,policyNumber);
        System.out.println("Waiting For 6 Second");
        IssuanceQueue.waitSec(6);

        response = RestAPIUtils.postOrPutResponse(uriPath, headers, bodyParams, postBody, "Post");
        IssuanceQueue.waitSec(5);
 //       Awaitility.await().atMost(5, TimeUnit.SECONDS).until(() -> response.getStatusCode() == 200);
 //       IssuanceQueue.waitSec(200);

        // Verify the status code
        Assert.assertEquals(response.getStatusCode(), 200);

        //Get Claim Number
        String claimNumber = response.jsonPath().get("claimNumber");
        String number = claimBenefit(claimBaseUrl,claimNumber);

        //Get claimBenefit amount
        int claimBenefit=(int)Double.parseDouble(number);

        //Save Claim
        postBody = ClaimUtility.savedClaim(policyNumber,claimNumber);
        System.out.println("Waiting For 120 Second");
        IssuanceQueue.waitSec(120);
        Response responseSave = RestAPIUtils.patchResponse(claimBaseUrl,"/internal/claims/additional-info",headers,postBody);

        // Send the Request for Registered Claim
        postBody = ClaimUtility.registeredClaim(policyNumber,claimNumber);
        Response responseRegistered = RestAPIUtils.patchResponse(claimBaseUrl,"/internal/claims/register",headers,postBody);

        // Awaitility.await().atMost(5, TimeUnit.SECONDS).until(() -> responseRegistered.getStatusCode() == 204);
  //      IssuanceQueue.waitSec(120);
        System.out.println("Waiting For 5 Second");
        IssuanceQueue.waitSec(5);

        //Decision from Ops User
        postBody = ClaimUtility.opsUserAcceptAction(policyNumber,claimNumber);
        System.out.println("Waiting For 6 Second");
        IssuanceQueue.waitSec(6);
        Response responseAccept = RestAPIUtils.patchResponse(claimBaseUrl,"/internal/tasks/task-action",headers,postBody);

        // Awaitility.await().atMost(5, TimeUnit.SECONDS).until(() -> responseAccept.getStatusCode() == 200);

        String baseURI = prop.getProperty("policy");
        ClaimUtility.approveClaimRequest(String.valueOf(claimBenefit),claimNumber,policyNumber);

        System.out.println("Waiting For 6 Second");
        IssuanceQueue.waitSec(15);
        Response responsePolicyDetail = RestAPIUtils.getResponse(baseURI,"/group/policies/"+policyNumber,headers);


        //Check updated value
  //      Assert.assertTrue(JSONUtils.verifyValueInResponse(responsePolicyDetail, "", "substandard", ConstantValues.subStandard));
  //      Assert.assertTrue(JSONUtils.verifyValueInResponse(responsePolicyDetail, "policyStatus", "reasonDescription", ConstantValues.CLAIM_PAID));
   //     Assert.assertTrue(JSONUtils.verifyValueInResponse(responsePolicyDetail, "policyStatus", "statusDescription", ConstantValues.TERMINATED));

        System.out.println(
                "\n******** End execution of Test Scenario  :  API -> status updated in 'Policy Status Reason' when approved claim of 'inforce' policy by claim approver  ******\n");
		return policyNumber;
    }
    
 //#####################################################################################################################
    
    public static String testClaimRegisteredStatusForGraceTypePolicy() throws Exception {

        System.out.println(
                "\n******** Started execution of Test Scenario  :  API -> 'Claim Registered' status updated in 'Policy Status Reason'  for Grace type of policy ******\n");

       String gracePolicy= CreatePolicies.gracePolicy_Claim();
       IssuanceQueue.waitSec(6);
       Payment_Utility.getDueAmountAndDueDate(gracePolicy,PaymentConstants.paymentTypeRenewal);
       Payment_Utility.registerPayment_RP_CR_AR(gracePolicy,PaymentConstants.paymentTypeRenewal,ReadPaymentJSON.policybazaar);
//       Payment_Utility.getFinalPaymentStatus();
       System.out.println(gracePolicy);
        String uriPath = claimBaseUrl + "/internal/claims";

        //Initiate Claim
        int incomeBenefit=50,lumpSumBenefit=50; // declarartion & Intialisation

        // Calling updateClientInformation function
        postBody = ClaimUtility.updateClaimInformation(incomeBenefit,lumpSumBenefit,gracePolicy);
        IssuanceQueue.waitSec(6);

        // Send the Request
        response = RestAPIUtils.postOrPutResponse(uriPath, headers, bodyParams, postBody, "Post");
        IssuanceQueue.waitSec(5);
//        Awaitility.await().atMost(5, TimeUnit.SECONDS).until(() -> response.getStatusCode() == 200);
        IssuanceQueue.waitSec(200);

        // Verify the status code
        Assert.assertEquals(response.getStatusCode(), 200);

        //Get Claim Number
        String claimNumber = response.jsonPath().get("claimNumber");

        //Save Claim
        postBody = ClaimUtility.savedClaim(gracePolicy,claimNumber);
        IssuanceQueue.waitSec(120);
        Response responseSave = RestAPIUtils.patchResponse(claimBaseUrl,"/internal/claims/additional-info",headers,postBody);

        // Send the Request for Registered Claim
        postBody = ClaimUtility.registeredClaim(gracePolicy,claimNumber);
        Response responseRegistered = RestAPIUtils.patchResponse(claimBaseUrl,"/internal/claims/register",headers,postBody);

        IssuanceQueue.waitSec(120);

        String baseURI = prop.getProperty("policy");

        IssuanceQueue.waitSec(15);
        Response responsePolicyDetail = RestAPIUtils.getResponse(baseURI,"/group/policies/"+gracePolicy,headers);

        //Check updated value 'Claim Registered'
        Assert.assertTrue(JSONUtils.verifyValueInResponse(responsePolicyDetail, "policyStatus", "reasonDescription", ConstantValues.CLAIM_REGISTERED));

        System.out.println(
                "\n******** End execution of Test Scenario  :  API -> 'Claim Registered' status updated in 'Policy Status Reason' for Grace type of policy ******\n");
		return gracePolicy;
    }
    
    
 //#################################################################################################################  
    public static String getPolicyNumber()
    {
    	String policy=policyNumber;
		return policy;
    	
    }
    
    
    public static String claimBenefit(String claimBaseUrl,String claimNumber)
    {
        try {
            do {
                response = RestAPIUtils.getResponse(claimBaseUrl, "/internal/claims/info?claimNumber=" + claimNumber, headers);
                IssuanceQueue.waitSec(2);
              } while (response.jsonPath().get("messages[0]").toString().contains("argument \"content\" is null") ||
                    response.jsonPath().get("messages[0]").toString().contains("Claim Not Found "));
        }catch(Exception e){}

        String number =String.valueOf(response.jsonPath().getDouble("reviewDetail.benefitsCalculationDetail.grossClaimBenefitPayable"));
        return number;
    }
    
    
    public static void main(String[] args) throws Exception {
   // 	testClaimInitiatedStatusForPolicy();
   // 	claimRegistered();
   // 	testApprovedClaimStatusForInforcePolicy();
    	testClaimRegisteredStatusForGraceTypePolicy();
    	String p = getPolicyNumber();
    	System.out.println("***"+p+"***");
	}

}
