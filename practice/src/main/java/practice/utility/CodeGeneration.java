package practice.utility;

import practice.testbase.IssuanceQueue;
import practice.testbase.PageObj;
import practice.testbase.TestBase;

public class CodeGeneration {
	
	String FreelookCancellation;
	
	public void FreelookCancellation() throws Exception {
		TestBase.init();
		IssuanceQueue.getToken_ops();
		PageObj.mockPolicy();
		FreelookCancellation = PageObj.pnoMock();
		IssuanceQueue.waitSec(4);

	}

}
