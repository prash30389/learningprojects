package handleExcelFileNApachePOIMethodology; //was to find total no of sheets present inside the workbook and sheet name print also

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class CountSheetName {

	public static void main(String[] args) throws IOException 
	{
		File file = new File("F:/testing study/Excel sheets/ExcelData.xlsx");
		FileInputStream fis = new FileInputStream(file);

			Workbook wb = WorkbookFactory.create(fis);
			int sheetcount = wb.getNumberOfSheets();
			System.out.println("Number of sheets : " + sheetcount);
		
		for(int i=0; i<sheetcount; i++)
		{
			Sheet sh = wb.getSheetAt(i);
			String sheetname = sh.getSheetName();
			System.out.println("Sheet Name : " + sheetname);
		}

	}

}
