package practice.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.model.Category;

import com.aventstack.extentreports.reporter.ExtentSparkReporter;
//import com.aventstack.extentreports.reporter.configuration.Cha
import com.aventstack.extentreports.reporter.configuration.Protocol;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.*;
import org.testng.xml.XmlSuite;
import java.util.*;

public class ExtentReport implements IReporter {

	static String projectPath = System.getProperty("user.dir");
	private static final String OUTPUT_FOLDER = "/Report/";
	private static final String FILE_NAME = "AutomationPayment.html";

	private ExtentReports extent;
	static ExtentTest test;

	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
		try {
			init();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		for (ISuite suite : suites) {
			Map<String, ISuiteResult> result = suite.getResults();

			for (ISuiteResult r : result.values()) {
				ITestContext context = r.getTestContext();

				try {
					buildTest(context.getPassedTests(), Status.PASS);
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					buildTest(context.getFailedTests(), Status.FAIL);
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					buildTest(context.getSkippedTests(), Status.SKIP);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}

		for (String s : Reporter.getOutput()) {
			extent.addTestRunnerOutput(s);
		}
		extent.flush();

	}

	private void init() throws Exception {

		ExtentSparkReporter htmlReporter = new ExtentSparkReporter(projectPath + OUTPUT_FOLDER + FILE_NAME);

		// ExtentSparkReporter htmlReporter = new ExtentSparkReporter (projectPath +
		// OUTPUT_FOLDER + FILE_NAME); ////This code works for V4

		htmlReporter.config().setDocumentTitle("Aegon API Report");
		htmlReporter.config().setReportName("Aegon API Report");
		htmlReporter.config().setTimeStampFormat("MMM dd, yyyy hh:mm:ss a");

		// htmlReporter.config().setTestViewChartLocation(ChartLocation.BOTTOM); //This

		htmlReporter.config().setTheme(Theme.DARK);
		htmlReporter.config().setProtocol(Protocol.HTTPS);

				// Remove Timeline
		String css = "div#timeline-chart { display: none; }";
		String css1 = "div#charts-row>div:last-child { display: none; }";
		htmlReporter.config().setCss(css);
		htmlReporter.config().setCss(css1);
		htmlReporter.config().setJs("document.querySelector('a[view=dashboard-view]').click()");


		extent = new ExtentReports();
		extent.setSystemInfo("Author", "QA Team");
		extent.setSystemInfo("Platform", System.getProperty("os.name"));
		extent.setSystemInfo("Machine's User", System.getProperty("user.name"));
		extent.setSystemInfo("Java Version", System.getProperty("java.version"));
		extent.setSystemInfo("Environment", " QA ");

		extent.attachReporter(htmlReporter);
		extent.setReportUsesManualConfiguration(false);

	}

	private void buildTest(IResultMap tests, Status status) throws Exception {

		if (tests.size() > 0) {

			List<ITestResult> resultList = new LinkedList<ITestResult>(tests.getAllResults());

			class ResultComparator implements Comparator<ITestResult> {
				public int compare(ITestResult r1, ITestResult r2) {
					return getTime(r1.getStartMillis()).compareTo(getTime(r2.getStartMillis()));
				}
			}

			Collections.sort(resultList, new ResultComparator());

			for (ITestResult result : resultList) {

			//	String sample = result.getTestContext().getCurrentXmlTest().getSuite().getName();
				String sample = result.getTestContext().getCurrentXmlTest().getSuite().getName();

				test = extent.createTest(result.getMethod().getDescription());
				


				for (String group : result.getMethod().getGroups()) {
					test.getModel().setStartTime(getTime(result.getStartMillis()));
					test.assignCategory(group);
					test.getModel().setEndTime(getTime(result.getEndMillis()));
				}

				if (result.getStatus() == ITestResult.FAILURE) {
					test.fail(MarkupHelper.createLabel(result.getName() + " Test Case is FAILED", ExtentColor.RED));
					test.getModel().setStartTime(getTime(result.getStartMillis()));
					test.getModel().setEndTime(getTime(result.getEndMillis()));
					test.fail(result.getThrowable());

				} else if (result.getStatus() == ITestResult.SKIP) {
					test.skip(MarkupHelper.createLabel(result.getName() + " Test Case is SKIPPED", ExtentColor.YELLOW));
					test.getModel().setStartTime(getTime(result.getStartMillis()));
					test.getModel().setEndTime(getTime(result.getEndMillis()));
					test.skip(result.getThrowable());
				} else if (result.getStatus() == ITestResult.SUCCESS) {
					test.pass(MarkupHelper.createLabel(result.getMethod().getDescription() + " got "
							+ status.toString().toLowerCase() + "ed", ExtentColor.GREEN));
					test.getModel().setStartTime(getTime(result.getStartMillis()));
					test.getModel().setEndTime(getTime(result.getEndMillis()));
				
				}

			}
		}

	}

	private Date getTime(long millis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return calendar.getTime();
	}

}