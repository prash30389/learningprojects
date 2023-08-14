package handleExcelFileNApachePOIMethodology;

import java.io.File;
import java.io.FileInputStream;

import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;


public class ReadStringValue {

	public static void main(String[] args) throws IOException 
	{
		File file = new File("F:/Excel sheets/ExcelData.xlsx");
		FileInputStream fis = new FileInputStream(file);

			Workbook wb = WorkbookFactory.create(fis);
			Sheet sh = wb.getSheet("Sheet1");
			Row rw = sh.getRow(0);
			Cell cl = rw.getCell(0);
			String value = cl.getStringCellValue();
			System.out.println(value);
			
	}

}
