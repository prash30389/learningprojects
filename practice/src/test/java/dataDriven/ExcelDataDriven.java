package dataDriven;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;


public class ExcelDataDriven 
{
	
	
	@Test(priority = 1, dataProvider = "getdata", dataProviderClass = ConfigNumDataProviderDriven.class)
	
	public static void createNWrite(String num, String SheetName) throws EncryptedDocumentException, InvalidFormatException, IOException
	{
		File file = new File("./Excel/Report"+num +".xlsx");
		//FileInputStream fis = new FileInputStream(file);
		//Workbook wb =WorkbookFactory.create(fis);
			Workbook wb = new XSSFWorkbook(); //for create new workbook
				Sheet sh = wb.createSheet(SheetName + num);
					Row rw = sh.createRow(0);
						Cell Headercl = rw.createCell(0);
						Headercl.setCellValue("Company Name");
						Cell Headerc2 = rw.createCell(1);
						Headerc2.setCellValue("Location");
						Cell Headerc3 = rw.createCell(2);
						Headerc3.setCellValue("Employee Code");
						Cell Headerc4 = rw.createCell(3);
						Headerc4.setCellValue("Position");
						Cell Headerc5 = rw.createCell(4);
						Headerc5.setCellValue("Project");
					Row rw2 = sh.createRow(2);
						Cell cl = rw2.createCell(0);
						cl.setCellValue("360logica");
						Cell c2 = rw2.createCell(1);
						c2.setCellValue(5000);
						Cell c3 = rw2.createCell(2);
						Date date = new Date();
						c3.setCellValue(date);
						Cell c4 = rw2.createCell(3);
						c4.setCellValue("ASSOCIATE CONSULTANT");
						Cell c5 = rw2.createCell(4);
						c5.setCellValue("AEGON LIFE");
		FileOutputStream fos = new FileOutputStream(file);
			wb.write(fos);	
			System.out.println("Data Written");
	}
	@Test(priority =2, dataProvider ="getdata" , dataProviderClass = ConfigNumDataProviderDriven.class)
	public static void read(String num, String SheetName) throws EncryptedDocumentException, InvalidFormatException, IOException
	{
	File file = new File("./Excel/Report"+num+".xlsx");
	FileInputStream fis = new FileInputStream(file);
		Workbook wb =WorkbookFactory.create(fis);
			Sheet sh = wb.getSheet(SheetName + num);
			Row row = sh.getRow(2);
			int rowcount = sh.getLastRowNum();
			System.out.println("Total Number of Rows : " + rowcount);
				Cell c = row.getCell(0);
				String val = c.getStringCellValue();
				System.out.println(val);
			Cell c2 = row.getCell(1);
			double val2 = c2.getNumericCellValue();
			System.out.println(val2);
				Cell c3 = row.getCell(2);
				Date val3 = c3.getDateCellValue();
				System.out.println(val3);
			Cell c4 = row.getCell(3);
			String val4 = c4.getStringCellValue();
			System.out.println(val4);
				Cell c5 = row.getCell(4);
				String val5 = c5.getStringCellValue();
				int cellcount = row.getLastCellNum();
	System.out.println("row : " + rowcount + " total cell : " + cellcount);
	}
}
