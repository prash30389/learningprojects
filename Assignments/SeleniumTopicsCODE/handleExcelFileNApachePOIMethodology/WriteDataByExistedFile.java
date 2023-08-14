package handleExcelFileNApachePOIMethodology;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class WriteDataByExistedFile {

	public static void main(String[] args) throws IOException
	{
		File file = new File("./reports/ExcelInput.xlsx");
		FileInputStream fis = new FileInputStream(file);
		
		Workbook wb = WorkbookFactory.create(fis);
		Sheet sh = wb.getSheet("selenium report");
		Row rw = sh.createRow(1);
		
		Cell cl = rw.createCell(0);
		cl.setCellValue("1.1");
		
		Cell cl2 = rw.createCell(1);
		cl2.setCellValue("Delhi Election wins aap");
		

		FileOutputStream fos = new FileOutputStream(file);
		wb.write(fos);
		System.out.println("Data Written");
	}

	}


