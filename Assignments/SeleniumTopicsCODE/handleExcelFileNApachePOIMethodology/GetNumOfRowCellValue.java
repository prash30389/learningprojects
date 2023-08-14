package handleExcelFileNApachePOIMethodology;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class GetNumOfRowCellValue {

	public static void main(String[] args) throws IOException 
	{
		File file = new File("F:/Excel sheets/ExcelData.xlsx");
		FileInputStream fis = new FileInputStream(file);

			Workbook wb = WorkbookFactory.create(fis);
			Sheet sh = wb.getSheet("Sheet1");
			int rowcount = sh.getLastRowNum();			// give one less because it does not count header row.
		
		System.out.println("Total Number of Rows : " + rowcount);
		
			for(int i =0; i<=rowcount; i++)
			{
				Row rw = sh.getRow(i);
				int cellcount = rw.getLastCellNum();
				System.out.println("row : " + i + " total cell : " + cellcount);
				
				for(int j =0; j<cellcount; j++)
				{
					Cell cl = rw.getCell(j);
					String value = cl.getStringCellValue();
					System.out.println("cell value : " + value);
				}
				
			}
	}

}
