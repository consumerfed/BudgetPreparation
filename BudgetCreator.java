
import java.text.DecimalFormat;
import java.util.Map;
import java.util.Vector;

/**
 * 
 */

/**
 * @author nijesh
 *
 */
public class BudgetCreator {
	
	private UnitsMap unitMap = null;
	private Vector<Vector> array = null; //output array
	DecimalFormat decimalFormat = null;
	
	public BudgetCreator(){
		
	}

	public int getNameFirmCode(String value) {
		int firmCode = 0;
		String str = value;
		if(str.contains("(")&& str.contains(")")){
			int startIndex = str.indexOf("(");
			int endIndex = str.indexOf(")");
			//System.out.println(true);
			//System.out.println(startIndex + "--" +endIndex);
			String sub = str.substring(startIndex+1, endIndex).trim();
			sub = sub.trim();
			//System.out.println(sub);
			firmCode = Integer.parseInt(sub);
			//System.out.println(" firmcode :"+firmCode);
		}
		return firmCode;
	}

	

	public void viewData(Map<Integer, Double> budgetData) {
		// TODO Auto-generated method stub
		System.out.println(" ------- Report -----------");
		System.out.println(budgetData.size());
		System.out.println(budgetData);
	}

	public Vector<Vector> calculateBudget(Map<Integer, Double> lastFinYrRetail,
			Map<Integer, Double> lastFinYrSbdy,
			Map<Integer, Double> newFinYrRetail,
			Map<Integer, Double> newFinYrSbdy, Map<Integer, Double> budgetData) {
		// TODO Auto-generated method stub
		unitMap = new UnitsMap();
		array = new Vector<Vector>();
		
		Vector heading = new Vector();
		heading.add("SLNO");
		heading.add("BRANCH NAME");
		heading.add("RETAIL");
		heading.add("SBDY");
		heading.add("RETAIL");
		heading.add("SBDY");
		heading.add("VARIATION");
		heading.add("BUDGET");
		heading.add("COMPARISON");
		array.add(heading);
		
		decimalFormat = new DecimalFormat("0.00");
		Map map = unitMap.getUnitMap();
		Vector data = null;
		int serialNo = 1;
		Double oldRetailTotal = 0.00;
		Double oldSbdyTotal = 0.00;
		Double newRetailTotal = 0.00;
		Double newSbdyTotal = 0.00;
		Double variationTotal = 0.00;
		Double budgetTotal = 0.00;
		Double comparisonTotal = 0.00;
		for (Object key :map.keySet()){
			data = new Vector();
			int firmCode = Integer.parseInt(key.toString());
			
			Double newRetail = newFinYrRetail.containsKey(firmCode) ? newFinYrRetail.get(firmCode) : 0.00;
			Double newSbdy = newFinYrSbdy.containsKey(firmCode) ? newFinYrSbdy.get(firmCode) : 0.00;
			Double oldRetail = lastFinYrRetail.containsKey(firmCode) ? lastFinYrRetail.get(firmCode) : 0.00;
			Double oldSbdy = lastFinYrSbdy.containsKey(firmCode) ? lastFinYrSbdy.get(firmCode) : 0.00;
			Double budgetValue = budgetData.containsKey(firmCode) ? budgetData.get(firmCode) : 0.00;
			
			//System.out.println(firmCode +"--->"+budgetValue);
			
			Double variation = (newRetail + newSbdy) - (oldRetail + oldSbdy);
			Double comparison = budgetValue!=0 ? (newRetail + newSbdy)*100/budgetValue : 0.00;
			
			//inserting all values to array
			data.add(serialNo); //1
			data.add(map.get(firmCode)); //2
			data.add(Double.parseDouble(decimalFormat.format(oldRetail))); //3
			data.add(Double.parseDouble(decimalFormat.format(oldSbdy))); //4
			data.add(Double.parseDouble(decimalFormat.format(newRetail))); //5
			data.add(Double.parseDouble(decimalFormat.format(newSbdy))); //6
			data.add(Double.parseDouble(decimalFormat.format(variation))); //7
			data.add(budgetValue); //8
			data.add(decimalFormat.format(comparison)); //9
			
			//Grand Total calculation
			oldRetailTotal =  oldRetailTotal + Double.parseDouble(decimalFormat.format(oldRetail));
			oldSbdyTotal =  oldSbdyTotal + Double.parseDouble(decimalFormat.format(oldSbdy));
			newRetailTotal = newRetailTotal + Double.parseDouble(decimalFormat.format(newRetail));
			newSbdyTotal =  newSbdyTotal + Double.parseDouble(decimalFormat.format(newSbdy));
			variationTotal = variationTotal + Double.parseDouble(decimalFormat.format(variation));
			budgetTotal = budgetTotal + budgetValue;
			//System.out.println(" String : "+comparison);
			comparisonTotal = comparisonTotal + Double.parseDouble(decimalFormat.format(comparison));
			
			//System.out.println(map.get(key));
			//System.out.println(" firmcode :"+ firmCode +" variation : "+Variation + "comparison : "+comparison);
			serialNo = serialNo +1;
			array.add(data);
		}
		
		Vector footer = new Vector();
		footer.add(" ");
		footer.add(" GRAND TOTAL ");
		footer.add(oldRetailTotal);
		footer.add(oldSbdyTotal);
		footer.add(newRetailTotal);
		footer.add(newSbdyTotal);
		footer.add(variationTotal);
		footer.add(budgetTotal);
		footer.add(comparisonTotal);
		array.add(footer);
		
		return array;
	}
		


	
}
