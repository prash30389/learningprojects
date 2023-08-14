package handleExcelFileNApachePOIMethodology;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class CurrentSystemTimeStamp {

	public static void main(String[] args) throws EncryptedDocumentException, IOException 
	{
		File file = new File("F:/Excel sheets/ExcelData.xlsx");
		FileInputStream fis = new FileInputStream(file);

			Workbook wb = WorkbookFactory.create(fis);
			Sheet sh = wb.getSheet("Sheet1");
			Row rw = sh.getRow(1);
			Cell cl = rw.getCell(1);
			Date date = new Date();
			System.out.println(date);
			
			Date dateValue = cl.getDateCellValue();
			System.out.println(dateValue);
	
	SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy");
	String value = sdf.format(dateValue);
	System.out.println(value);

//or
	
	
	
	}

}
