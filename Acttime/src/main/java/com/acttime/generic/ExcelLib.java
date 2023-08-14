package com.acttime.generic;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelLib 
{
String FilePath;

public ExcelLib(String FilePath)
{
	this.FilePath=FilePath;
}

public String GetData(String sheetName, int row, int cell)
	{
		
	String value = null;
		
		try
		{
			
		FileInputStream fis = new FileInputStream(new File(FilePath));
		Workbook wb = WorkbookFactory.create(fis);
		Cell cl = wb.getSheet(sheetName).getRow(row).getCell(cell);
		
		switch(cl.getCellType())
		{
		
		case STRING:
		value = cl.getStringCellValue();
		break;
		
		case NUMERIC:
			if (DateUtil.isCellDateFormatted(cl))		//dob
			{
				SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
				value = sdf.format(cl.getDateCellValue());
				
			}
			else
			{
				long num = (long) cl.getNumericCellValue();
				value = "" + num;	
			}  		//ph, cvv,
			
			break;
		case  BOOLEAN:
			value = "" + cl.getBooleanCellValue();
			break;
			
			default:
				break;
		}
	}
		catch (EncryptedDocumentException e) 
		{
			e.printStackTrace();
		}
		
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		
		return value;
		
	}

}
