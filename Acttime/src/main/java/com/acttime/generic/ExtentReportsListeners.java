package com.acttime.generic;

import java.io.File;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Protocol;
import com.aventstack.extentreports.reporter.configuration.Theme;



public class ExtentReportsListeners implements IReporter {
	private ExtentReports extent;
	static ExtentTest test;

	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites,
			String outputDirectory) {
		ExtentSparkReporter htmlReporter = new ExtentSparkReporter(outputDirectory + File.separator
				+ "LatestHighTechReport.html");
		htmlReporter.config().setDocumentTitle("actTime APP");
		htmlReporter.config().setReportName("Test Report");
		htmlReporter.config().setTimeStampFormat("MMM dd, yyyy hh:mm:ss a");

		// htmlReporter.config().setTestViewChartLocation(ChartLocation.BOTTOM); //This

		htmlReporter.config().setTheme(Theme.STANDARD);
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
		for (ISuite suite : suites) {
			Map<String, ISuiteResult> result = suite.getResults();

			for (ISuiteResult r : result.values()) 
			{
				ITestContext context = r.getTestContext();

				buildTestNodes(context.getPassedTests(), Status.PASS);
				buildTestNodes(context.getFailedTests(), Status.FAIL);
				buildTestNodes(context.getSkippedTests(), Status.SKIP);
			}
		}

		extent.flush();
		
	}

	private void buildTestNodes(IResultMap tests, Status status) {
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
				@SuppressWarnings("unused")
				String sample = result.getTestContext().getCurrentXmlTest().getSuite().getName();

			//	test = extent.createTest(result.getMethod().getDescription());
				
				Object[] value1=result.getParameters();
				String arrValue="[";
				if(value1.length>0){
					for(Object ob:value1){
						arrValue= arrValue+" "+ob+" ";
					}
					test = extent.createTest(result.getMethod().getDescription()+":Data Set: "+arrValue+"]");
				}else {
					test = extent.createTest(result.getMethod().getDescription());
				}

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