package boraproj.evaluation;

import java.io.FileOutputStream;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class PrintEvaluationResults {
	
	private Evaluation_2 evaluation;
	
	public PrintEvaluationResults(ArrayList<String> model_under_construction, ArrayList<String> relevant_repo, ArrayList<String> suggestion ) {
		
		this.evaluation = new Evaluation_2(model_under_construction, relevant_repo, suggestion);
	}

	public void print() {
		
		try   
		{  
		//declare file name to be create   
		String filename = "C:\\Users\\Admin\\Desktop\\EvaluationAuto.xlsx";  
		//creating an instance of HSSFWorkbook class  
		HSSFWorkbook workbook = new HSSFWorkbook();  
		//invoking creatSheet() method and passing the name of the sheet to be created   
		HSSFSheet sheet = workbook.createSheet("Evaluation");   
		//creating the 0th row using the createRow() method  
		HSSFRow rowhead = sheet.createRow((short)0);  
		//creating cell by using the createCell() method and setting the values to the cell by using the setCellValue() method  
		rowhead.createCell(0).setCellValue("Recommendations.");  
		rowhead.createCell(1).setCellValue("True positives");  
		rowhead.createCell(2).setCellValue("False positives");  
		rowhead.createCell(3).setCellValue("Precision");  
		rowhead.createCell(4).setCellValue("Recall");
		rowhead.createCell(5).setCellValue("F1");
		rowhead.createCell(6).setCellValue("Recall-repo");
		rowhead.createCell(7).setCellValue("Reused");
		//creating the 1st row  
		HSSFRow row = sheet.createRow((short)1);  
		//inserting data in the first row  
		row.createCell(0).setCellValue(""+evaluation.getSuggestions());  
		row.createCell(1).setCellValue(""+evaluation.getTP());  
		row.createCell(2).setCellValue(""+evaluation.getFP());  
		row.createCell(3).setCellValue(""+evaluation.precision());  
		row.createCell(4).setCellValue(""+evaluation.recall());
		row.createCell(5).setCellValue(""+evaluation.fMeaure());
		row.createCell(6).setCellValue(""+evaluation.recallRepo());
		row.createCell(7).setCellValue(""+evaluation.reused());
		//creating the 2nd row  
//		HSSFRow row1 = sheet.createRow((short)2);  
//		//inserting data in the second row  
//		row1.createCell(0).setCellValue("2");  
//		row1.createCell(1).setCellValue("Mathew Parker");  
//		row1.createCell(2).setCellValue("22222222");  
//		row1.createCell(3).setCellValue("parker.mathew@gmail.com");  
//		row1.createCell(4).setCellValue("200000.00");  
		
		
		FileOutputStream fileOut = new FileOutputStream(filename);  
		workbook.write(fileOut);  
		//closing the Stream  
		fileOut.close();  
		//closing the workbook  
		workbook.close();  
		//prints the message on the console  
		System.out.println("Excel file has been generated successfully.");  
		}   
		catch (Exception e)   
		{  
		e.printStackTrace();  
		}  
		}  
		
	
}
