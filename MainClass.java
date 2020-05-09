import java.io.IOException;

import javax.swing.JOptionPane;

/**
 * 
 */

/**
 * @author nijesh
 *
 */
public class MainClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	    ReadExcel test = new ReadExcel();
	   test.setInputFile("BUDGET.xls");
	  // test.setInputFile("BUDGET.xls");
	    try {
			test.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Err"+e.getMessage());
		}

	}

}
