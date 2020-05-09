

import java.io.File;
import java.io.IOException;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.JOptionPane;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;

public class ReadExcel {

  private String inputFile = null;
  private BudgetCreator budgetCreator = null;
  
  //private Vector<Vector> array =null;
  
  private Map<Integer,Double> lastFinYrRetail = null;
  private Map<Integer,Double> lastFinYrSbdy = null;
  private Map<Integer,Double> newFinYrRetail = null;
  private Map<Integer,Double> newFinYrSbdy = null;
  private Map<Integer,Double> budgetData = null;
  
  private int lstYrsRFirmCode = 0;
  private double lstYrsRValue = 0;
 
  private int lstYrsSFirmCode = 0;
  private double lstYrsSValue = 0;

  
  private int newYrsRFirmCode = 0;
  private double newYrsRValue = 0;

  private int newYrsSFirmCode = 0;
  private double newYrsSValue = 0;

  
  private int budgetFirmCode = 0;
  private double budgetValue = 0;


  public void setInputFile(String inputFile) {
    this.inputFile = inputFile;
  }

  public void read() throws IOException  {
	  budgetCreator = new BudgetCreator();
	 // array = new Vector<Vector>();
	  lastFinYrRetail = new HashMap<Integer, Double>();
	  lastFinYrSbdy= new HashMap<Integer, Double>();
	  newFinYrRetail = new HashMap<Integer, Double>();
	  newFinYrSbdy= new HashMap<Integer, Double>();
	  budgetData= new HashMap<Integer, Double>();
    File inputWorkbook = new File(inputFile);
    Workbook w;
    try {
      w = Workbook.getWorkbook(inputWorkbook);

      Sheet sheet = w.getSheet("Sheet1");

      
     int rowCount = sheet.getRows();
     int colCount = 10;
    	  
    	  for(int row=0; row<rowCount; row++){
    		  
    		  for(int col=0;col<colCount;col++){
    		  
    		  Cell cell = sheet.getCell(col, row);
    		  CellType type = cell.getType();
    		  String value = cell.getContents();
    		  switch (col) {
			case 0:
				lstYrsRFirmCode = budgetCreator.getNameFirmCode(value);
				break;
			case 1:
				if(lstYrsRFirmCode>0&&(type==CellType.NUMBER)){
					lstYrsRValue = Double.parseDouble(value);
    				lastFinYrRetail.put(lstYrsRFirmCode, lstYrsRValue/100000);  // first retails input
				}
				break;
			case 2:
				lstYrsSFirmCode = budgetCreator.getNameFirmCode(value);
				break;
			case 3:
				if(lstYrsSFirmCode>0&&(type==CellType.NUMBER)){
					lstYrsSValue = Double.parseDouble(value);
    				lastFinYrSbdy.put(lstYrsSFirmCode, lstYrsSValue/100000); // first sbdy input
				}
				break;
				
			case 4:
				newYrsRFirmCode = budgetCreator.getNameFirmCode(value);
				break;
			case 5:
				if(newYrsRFirmCode>0&&(type==CellType.NUMBER)){
					newYrsRValue = Double.parseDouble(value);
    				newFinYrRetail.put(newYrsRFirmCode, newYrsRValue/100000); // new retail input
				}
				break;
				
			case 6:
				newYrsSFirmCode = budgetCreator.getNameFirmCode(value);
				break;
			case 7:
				if(newYrsSFirmCode>0&&(type==CellType.NUMBER)){
					newYrsSValue = Double.parseDouble(value);
    				newFinYrSbdy.put(newYrsSFirmCode, newYrsSValue/100000); // new sbdy input
				}
				break;
				
			case 8:
				budgetFirmCode = budgetCreator.getNameFirmCode(value);
				break;
			case 9:
				if((budgetFirmCode>0)&&(type==CellType.NUMBER)){  //add recently
					budgetValue = Double.parseDouble(value);
    				budgetData.put(budgetFirmCode, budgetValue); // budget value
				}
				break;

			default:
				break;
			}
    		  
    	 }
    	
    	  
      }

    	  
    	  Vector<Vector> array =budgetCreator.calculateBudget(lastFinYrRetail,lastFinYrSbdy,newFinYrRetail,newFinYrSbdy,budgetData);
    	  WriteExcel test = new WriteExcel();
         test.setOutputFile("BUDGET_REPORT.xls",array);
    	 //test.setOutputFile("BUDGET_REPORT.xls",array);
          test.write();
          JOptionPane.showMessageDialog(null, "Excel file : BUDGET_REPORT created !!! ");
          
    } catch (BiffException e) {
      e.printStackTrace();
      JOptionPane.showMessageDialog(null, "Err"+e.getMessage());
    } catch (WriteException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		JOptionPane.showMessageDialog(null, "Err"+e.getMessage());
	}catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		JOptionPane.showMessageDialog(null, "Err"+e.getMessage());
	}
  }
} 