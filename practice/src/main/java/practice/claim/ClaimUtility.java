package practice.claim;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.json.simple.JSONObject;

import io.restassured.response.Response;
import practice.testbase.IssuanceQueue;
import practice.testbase.TestBase;
import practice.utility.JSONUtils;
import practice.utility.RestAPIUtils;

public class ClaimUtility extends Endorsement_Nom_Contact {
    public static String projectPath = System.getProperty("user.dir");
    static String claimBaseUrl = TestBase.prop.getProperty("claim");

    public static void approveClaimRequest(String amount,String claimNumber,String policyID) throws Exception {
        Integer sumAssured =Integer.parseInt(amount);
        int approvalLvel=0;

        if(sumAssured<=2000000)
        {
            approvalLvel=1;
            approveLevelAsPerSumAssured(approvalLvel,claimNumber,policyID);

        }else if(sumAssured>2000000 && sumAssured <=5000000)
        {
            approvalLvel=2;
            approveLevelAsPerSumAssured(approvalLvel,claimNumber,policyID);

        }else if(sumAssured>5000000 && sumAssured <=7500000)
        {
            approvalLvel=2;
            approveLevelAsPerSumAssured(approvalLvel,claimNumber,policyID);

        }else{
            approvalLvel=4;
            approveLevelAsPerSumAssured(approvalLvel,claimNumber,policyID);
        }

    }

    public  static void approveLevelAsPerSumAssured(int level,String claimNumber,String policyNumber) throws Exception {
        HashMap<String,String> approvarDetails = userList();
        String jsonfile = projectPath + "/SchemeJson/acceptRequestLevel.json";

        JSONObject jsonObjectAcceptApprover = (JSONObject) JSONUtils.readJsonDataFromDefaltFolder(jsonfile);

        jsonObjectAcceptApprover.put("claimNumber", claimNumber);
        jsonObjectAcceptApprover.put("policyNumber", policyNumber);
        for(int i=1;i<=level;i++)
        {
            jsonObjectAcceptApprover.put("actionBy",approvarDetails.get("APPROVER"+i));
            jsonObjectAcceptApprover.put("actionByRole","APPROVER"+i);

            JSONObject summary = (JSONObject) jsonObjectAcceptApprover.get("claimCaseSummaryInfo");
            summary.put("claimNumber", claimNumber);
            summary.put("policyNumber", policyNumber);

            JSONObject action = (JSONObject) summary.get("actionDetails");
            action.put("userId",approvarDetails.get("APPROVER"+i));
            action.put("userRole","APPROVER"+i);
            action.put("actionTaken","Accepted");

            String postBody = jsonObjectAcceptApprover.toJSONString();
            IssuanceQueue.waitSec(5);
            Response responseAcceptApprover = RestAPIUtils.patchResponse(claimBaseUrl,"/internal/tasks/task-action",headers,postBody);
            System.out.println("YYYYYYY");
            responseAcceptApprover.prettyPrint();
            IssuanceQueue.waitSec(5);
  //          Awaitility.await().atMost(5, TimeUnit.SECONDS).until(() -> responseAcceptApprover.getStatusCode() == 200);
            responseAcceptApprover.prettyPrint();
        }

    }

    public static void rejectClaimRequest(String amount,String claimNumber,String policyID) throws Exception {
        Integer sumAssured =Integer.parseInt(amount);
        int approvalLvel=0;

        if(sumAssured<=1500000)
        {
            approvalLvel=1;
            rejectLevelAsPerSumAssured(approvalLvel,claimNumber,policyID);

        }else if(sumAssured>1500000 && sumAssured <=2000000)
        {
            approvalLvel=1;
            rejectLevelAsPerSumAssured(approvalLvel,claimNumber,policyID);

        }else if(sumAssured>2000000 && sumAssured <=2500000)
        {
            approvalLvel=3;

            rejectLevelAsPerSumAssured(approvalLvel,claimNumber,policyID);

        }else if(sumAssured>2500000 && sumAssured <=7500000)
        {
            approvalLvel=3;
            rejectLevelAsPerSumAssured(approvalLvel,claimNumber,policyID);

        }else{
            approvalLvel=3;
            rejectLevelAsPerSumAssured(approvalLvel,claimNumber,policyID);
        }

    }

    public  static void rejectLevelAsPerSumAssured(int level,String claimNumber,String policyNumber) throws Exception {
        HashMap<String,String> approvarDetails = userList();
        String jsonfile = projectPath + "/SchemeJson/acceptRequestLevel.json";

        JSONObject jsonObjectAcceptApprover = (JSONObject) JSONUtils.readJsonDataFromDefaltFolder(jsonfile);

        jsonObjectAcceptApprover.put("claimNumber", claimNumber);
        jsonObjectAcceptApprover.put("policyNumber", policyNumber);
        jsonObjectAcceptApprover.put("decision", "REJECT");
        jsonObjectAcceptApprover.put("subReason", "REJECT_INVALID_CLAIMANT");
        for(int i=1;i<=level;i++)
        {
            jsonObjectAcceptApprover.put("actionBy",approvarDetails.get("APPROVER"+i));
            jsonObjectAcceptApprover.put("actionByRole","APPROVER"+i);

            JSONObject summary = (JSONObject) jsonObjectAcceptApprover.get("claimCaseSummaryInfo");
            summary.put("claimNumber", claimNumber);
            summary.put("policyNumber", policyNumber);

            JSONObject action = (JSONObject) summary.get("actionDetails");
            action.put("userId",approvarDetails.get("APPROVER"+i));
            action.put("userRole","APPROVER"+i);
            action.put("actionTaken","Rejected");

            String postBody = jsonObjectAcceptApprover.toJSONString();
            IssuanceQueue.waitSec(5);
            Response responseAcceptApprover = RestAPIUtils.patchResponse(claimBaseUrl,"/internal/tasks/task-action",headers,postBody);
            System.out.println("YYYYYYY");
            IssuanceQueue.waitSec(5);
//            Awaitility.await().atMost(5, TimeUnit.SECONDS).until(() -> responseAcceptApprover.getStatusCode() == 200);
        }

    }

    public static void repudiateClaimRequest(String amount,String claimNumber,String policyID) throws Exception {
        Integer sumAssured =Integer.parseInt(amount);
        int approvalLvel=0;

        if(sumAssured<=2000000)
        {
            approvalLvel=3;
            repudiateLevelAsPerSumAssured(approvalLvel,claimNumber,policyID);

        }else if(sumAssured>2000000 && sumAssured <=5000000)
        {
            approvalLvel=3;
            repudiateLevelAsPerSumAssured(approvalLvel,claimNumber,policyID);

        }else if(sumAssured>5000000 && sumAssured <=7500000)
        {
            approvalLvel=3;

            repudiateLevelAsPerSumAssured(approvalLvel,claimNumber,policyID);

        }else{
            approvalLvel=3;
            repudiateLevelAsPerSumAssured(approvalLvel,claimNumber,policyID);
        }

    }

    public  static void repudiateLevelAsPerSumAssured(int level,String claimNumber,String policyNumber) throws Exception {
        HashMap<String,String> approvarDetails = userList();
        String jsonfile = projectPath + "/SchemeJson/acceptRequestLevel.json";

        JSONObject repo = (JSONObject) JSONUtils.readJsonDataFromDefaltFolder(jsonfile);

        repo.put("claimNumber", claimNumber);
        repo.put("policyNumber", policyNumber);
        repo.put("decision", "REPUDIATE");
        repo.put("subReason", "AGE_MISSTATEMENT");
        for(int i=1;i<=level;i++)
        {
            repo.put("actionBy",approvarDetails.get("APPROVER"+i));
            repo.put("actionByRole","APPROVER"+i);

            JSONObject summary = (JSONObject) repo.get("claimCaseSummaryInfo");
            summary.put("claimNumber", claimNumber);
            summary.put("policyNumber", policyNumber);

            JSONObject action = (JSONObject) summary.get("actionDetails");
            action.put("userId",approvarDetails.get("APPROVER"+i));
            action.put("userRole","Approver"+i);
            action.put("actionTaken","Decision : Repudiate , Sub Reason : Age Misstatement");

            String postBody = repo.toJSONString();
            IssuanceQueue.waitSec(5);
            Response responseAcceptApprover = RestAPIUtils.patchResponse(claimBaseUrl,"/internal/tasks/task-action",headers,postBody);
            System.out.println("YYYYYYY");
            responseAcceptApprover.prettyPrint();
            IssuanceQueue.waitSec(5);
 //           Awaitility.await().atMost(5, TimeUnit.SECONDS).until(() -> responseAcceptApprover.getStatusCode() == 200);
            responseAcceptApprover.prettyPrint();
        }

    }

    public static String savedClaim(String policy,String claimNumber) throws Exception {
        String jsonfile = projectPath + "/SchemeJson/saveForClaimRegister.json";
        JSONObject jsonOb = (JSONObject) JSONUtils.readJsonDataFromDefaltFolder(jsonfile);

        jsonOb.put("claimNumber", claimNumber);
        JSONObject summrySec = (JSONObject) jsonOb.get("claimCaseSummaryInfo");
        summrySec.put("claimNumber", claimNumber);
        summrySec.put("policyNumber", policy);

        String eventDate = getAccidentRelatedInfo();
        JSONObject actionDetail = (JSONObject) summrySec.get("actionDetails");
        actionDetail.put("actionTakenDate",eventDate);

        return jsonOb.toJSONString();
    }

    public static String registeredClaim(String policy,String claimNumber) throws Exception {
        String jsonfile = projectPath + "/SchemeJson/saveBenefit.json";

        JSONObject jsonObjectSave = (JSONObject) JSONUtils.readJsonDataFromDefaltFolder(jsonfile);

        jsonObjectSave.put("claimNumber", claimNumber);
        jsonObjectSave.put("policyNumber", policy);

        JSONObject summrySec = (JSONObject) jsonObjectSave.get("claimCaseSummaryInfo");
        summrySec.put("claimNumber", claimNumber);
        summrySec.put("policyNumber", policy);

        String eventDate = getAccidentRelatedInfo();
        JSONObject actionDetail = (JSONObject) summrySec.get("actionDetails");
        actionDetail.put("actionTakenDate",eventDate);

        return jsonObjectSave.toJSONString();
    }

    public static String opsUserRepudiateAction(String policyNumber,String claimNumber) throws Exception {
        String jsonfile = projectPath + "/SchemeJson/acceptLevel1.json";

        JSONObject jsonObjectRepudiate = (JSONObject) JSONUtils.readJsonDataFromDefaltFolder(jsonfile);

        jsonObjectRepudiate.put("claimNumber", claimNumber);
        jsonObjectRepudiate.put("policyNumber", policyNumber);
        jsonObjectRepudiate.put("decision", "REPUDIATE");
        jsonObjectRepudiate.put("subReason", "AGE_MISSTATEMENT");
        JSONObject summary = (JSONObject) jsonObjectRepudiate.get("claimCaseSummaryInfo");
        summary.put("claimNumber", claimNumber);
        summary.put("policyNumber", policyNumber);

        String eventDate = getAccidentRelatedInfo();
        JSONObject actionDetail = (JSONObject) summary.get("actionDetails");
        actionDetail.put("actionTakenDate",eventDate);

        return jsonObjectRepudiate.toJSONString();
    }

    public static String opsUserRejectAction(String policyNumber,String claimNumber) throws Exception {
        String jsonfile = projectPath + "/SchemeJson/acceptLevel1.json";

        JSONObject jsonObjectReject = (JSONObject) JSONUtils.readJsonDataFromDefaltFolder(jsonfile);

        jsonObjectReject.put("claimNumber", claimNumber);
        jsonObjectReject.put("policyNumber", policyNumber);
        jsonObjectReject.put("decision", "REJECT");
        jsonObjectReject.put("subReason", "REJECT_INVALID_CLAIMANT");
        JSONObject summary = (JSONObject) jsonObjectReject.get("claimCaseSummaryInfo");
        summary.put("claimNumber", claimNumber);
        summary.put("policyNumber", policyNumber);

        return jsonObjectReject.toJSONString();
    }

    public static String opsUserAcceptAction(String policyNumber,String claimNumber) throws Exception {
        String jsonfile = projectPath + "/SchemeJson/acceptLevel1.json";

        JSONObject jsonObjectAccept = (JSONObject) JSONUtils.readJsonDataFromDefaltFolder(jsonfile);

        jsonObjectAccept.put("claimNumber", claimNumber);
        jsonObjectAccept.put("policyNumber", policyNumber);
        jsonObjectAccept.put("decision", "ACCEPT");
        jsonObjectAccept.put("subReason", "ACCEPT");
        JSONObject summary = (JSONObject) jsonObjectAccept.get("claimCaseSummaryInfo");
        summary.put("claimNumber", claimNumber);
        summary.put("policyNumber", policyNumber);

        return jsonObjectAccept.toJSONString();
    }
    public static HashMap<String,String> userList()
    {
        HashMap<String,String> approvarDetails = new HashMap<String,String>();
        approvarDetails.put("APPROVER1","8509");
        approvarDetails.put("APPROVER2","99162");
        approvarDetails.put("APPROVER3","8509");
        approvarDetails.put("APPROVER4","99162");
        approvarDetails.put("APPROVER5","8509");

        return approvarDetails;
    }

    public static String getAccidentRelatedInfo() throws Exception
    {
        DateFormat  dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -2);
        Date todate = cal.getTime();
        String eventDate = dateFormat.format(todate);
         return eventDate;
    }

    public  static String updateClaimInformation(int incomeBenefit,int lumpSumBenefit,String policyNumber) throws Exception
    {

        String jsonfile = projectPath + "/SchemeJson/claimInitiate.json";
        JSONObject jsonObject = (JSONObject) JSONUtils.readJsonDataFromDefaltFolder(jsonfile);

        String eventDate = getAccidentRelatedInfo();

        jsonObject.put("policyNumber", policyNumber);
        JSONObject claimDetails = (JSONObject) jsonObject.get("claimDetail");
        claimDetails.put("dateOfIntimation", eventDate);
        claimDetails.put("dateOfDeath",eventDate);
        claimDetails.put("eventDate",eventDate);
        claimDetails.put("dateOfAccident",eventDate);
        claimDetails.put("incomeBenefit", incomeBenefit);
        claimDetails.put("lumpSumBenefit", lumpSumBenefit);
        JSONObject claimSummaryInfo = (JSONObject) jsonObject.get("claimCaseSummaryInfo");
        JSONObject actionDetails = (JSONObject) claimSummaryInfo.get("actionDetails");
        actionDetails.put("actionTakenDate",eventDate);
        return jsonObject.toJSONString();
    }

    public  static String updateClaimInitiateMulti(int incomeBenefit,int lumpSumBenefit,String policyNumber) throws Exception
    {

        String jsonfile = projectPath + "/SchemeJson/claimInitiateMulti.json";
        JSONObject jsonObject = (JSONObject) JSONUtils.readJsonDataFromDefaltFolder(jsonfile);

        String eventDate = getAccidentRelatedInfo();

        jsonObject.put("policyNumber", policyNumber);
        JSONObject claimDetails = (JSONObject) jsonObject.get("claimDetail");
        claimDetails.put("dateOfIntimation", eventDate);
        claimDetails.put("dateOfDeath",eventDate);
        claimDetails.put("eventDate",eventDate);
        claimDetails.put("dateOfAccident",eventDate);
        claimDetails.put("incomeBenefit", incomeBenefit);
        claimDetails.put("lumpSumBenefit", lumpSumBenefit);

        return jsonObject.toJSONString();
    }
}
