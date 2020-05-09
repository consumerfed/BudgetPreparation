

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Vector;

import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.UnderlineStyle;
import jxl.write.Formula;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;


public class WriteExcel {

  private WritableCellFormat timesBoldUnderline;
  private WritableCellFormat times;
  private String inputFile;
  private Vector<Vector> array = null;


  public void setOutputFile(String inputFile) {
	  this.inputFile = inputFile;
  }

  public void setOutputFile(String inputFile, Vector<Vector> array) {
	  this.inputFile = inputFile;
	  this.array = array;	
	}
  
  public void write() throws IOException, WriteException {
    File file = new File(inputFile);
    WorkbookSettings wbSettings = new WorkbookSettings();

    wbSettings.setLocale(new Locale("en", "EN"));

    WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
    workbook.createSheet("Report", 0);
    WritableSheet excelSheet = workbook.getSheet("Report");
    createLabel(excelSheet);
    createContent(excelSheet);

    workbook.write();
    workbook.close();
  }

  private void createLabel(WritableSheet sheet)
      throws WriteException {
    // Lets create a times font
    WritableFont times10pt = new WritableFont(WritableFont.TIMES, 10);
    // Define the cell format
    times = new WritableCellFormat(times10pt);
    // Lets automatically wrap the cells
    times.setWrap(true);

    // Create create a bold font with unterlines
    WritableFont times10ptBoldUnderline = new WritableFont(WritableFont.TIMES, 10, WritableFont.BOLD, false,
        UnderlineStyle.SINGLE);
    timesBoldUnderline = new WritableCellFormat(times10ptBoldUnderline);
    // Lets automatically wrap the cells
    timesBoldUnderline.setWrap(true);

    CellView cv = new CellView();
    cv.setFormat(times);
    cv.setFormat(timesBoldUnderline);
    cv.setAutosize(false);

    // Write a few headers
    addCaption(sheet, 0, 0, "SL No");
    addCaption(sheet, 1, 0, "Branch Name");
    addCaption(sheet, 2, 0, "Retail");
    addCaption(sheet, 3, 0, "Sbdy");
    addCaption(sheet, 4, 0, "Retail");
    addCaption(sheet, 5, 0, "Sbdy");
    addCaption(sheet, 6, 0, "Variation");
    addCaption(sheet, 7, 0, "Budget");
    addCaption(sheet, 8, 0, "Comparison");
    

  }

  private void createContent(WritableSheet sheet) throws WriteException,
      RowsExceededException {
	  
	  int size = array.size();
	  sheet.setColumnView(1, 50);
      for(int row = 0; row < size; row ++){
		  sheet.setRowView(row, 400);
		  Vector val = array.get(row);
		  for(int col =0; col<9;col++){
			  addValues(sheet,col, row, val.get(col));
		  }
		}
	  
	  

  }

  private void addCaption(WritableSheet sheet, int column, int row, String s)
      throws RowsExceededException, WriteException {
    Label label;
    label = new Label(column, row, s, timesBoldUnderline);
    sheet.addCell(label);
  }

  private void addNumber(WritableSheet sheet, int column, int row,
      Integer integer	) throws WriteException, RowsExceededException {
	 
    Number number;
    number = new Number(column, row, integer, times);
    sheet.addCell(number);
    
	
  }
  
  private void addValues(WritableSheet sheet, int column, int row, Object obj)
	      throws WriteException, RowsExceededException {
	    Label label;
	    label = new Label(column, row, obj.toString(), times);
	    sheet.addCell(label);
	  }

  private void addLabel(WritableSheet sheet, int column, int row, String s)
      throws WriteException, RowsExceededException {
    Label label;
    label = new Label(column, row, s, times);
    sheet.addCell(label);
  }




} 