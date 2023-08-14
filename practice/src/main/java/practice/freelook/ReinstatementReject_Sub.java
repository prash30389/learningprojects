package practice.freelook;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import practice.testbase.IssuanceQueue;
import practice.testbase.PageObj;
import practice.testbase.TestBase;
import practice.utility.Reinstatement;

public class ReinstatementReject_Sub extends TestBase {

	public static String initiateReinstatement() throws Exception {

		TestBase.init();
		PageObj.mockBackdatedPolicy();
		String pno = PageObj.pnoMock();

		Reinstatement.ebaotoken();
		Reinstatement.ebaoLapse(pno);
		IssuanceQueue.waitSec(10);

		IssuanceQueue.waitSec(4);
		st = new SoftAssert();
		Reinstatement.initiateReinstatement(pno);

		IssuanceQueue.waitSec(10);

		Reinstatement.Reject(pno);
		IssuanceQueue.waitSec(10);

		return pno;

	}

}
