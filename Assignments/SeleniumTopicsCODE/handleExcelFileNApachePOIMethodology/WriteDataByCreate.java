package handleExcelFileNApachePOIMethodology;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WriteDataByCreate {

	public static void main(String[] args) throws IOException 
	{
		File file = new File("./reports/ExcelInput.xlsx");
		//System.out.println(file.exists());
		//file.createNewFile();
		//System.out.println(file.exists());
		@SuppressWarnings("resource")
		Workbook wb = new XSSFWorkbook();
		Sheet sh = wb.createSheet("selenium report");
		Row rw = sh.createRow(0);
		Cell cl = rw.createCell(0);
		cl.setCellValue("Sl.NO");
		Cell cl2 = rw.createCell(1);
		cl2.setCellValue("Name");

		FileOutputStream fos = new FileOutputStream(file);
		wb.write(fos);
		System.out.println("Data Written");
	}

}
