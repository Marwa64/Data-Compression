import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class FloatingPoint {
    public static HashMap<Character, Double> rangeLow  = new HashMap<Character, Double>();
    public static HashMap<Character, Double> rangeHigh = new HashMap<Character, Double>();
    
    public static double compress(String text){
        double Lower = 0.0 , Upper = 1.0 , prevLower = Lower, prevUpper = Upper;
        char c = text.charAt(0);
        for(int i = 0 ; i < text.length() ; i++){
        	c = text.charAt(i);
            Lower = prevLower + (prevUpper-prevLower) * rangeLow.get(c);
            Upper = prevLower + (prevUpper-prevLower) * rangeHigh.get(c);
            prevLower = Lower;
            prevUpper = Upper;
        }
        if (Upper >= rangeLow.get(c) && Upper <= rangeHigh.get(c)) {
        	return round(Upper);
        } else {
        	return round(Lower);
        }
    }

    public static String decompress(double code, int num) {
    	String output = "";
    	double lower, upper;
    	for (int i = 0; i < num; i++) {
            for (char c : rangeLow.keySet()) {
                if(rangeLow.get(c) < code && rangeHigh.get(c) >= code){
                	output += c;
                    lower = rangeLow.get(c);
                    upper = rangeHigh.get(c);
                    code = (code - lower) / (upper - lower);
                    code = round(code);
                    break;
                }
            }
    	}
		return output;
    }
    
    public static double round(double code) {
    	double newCode;
    	String s = String.valueOf(code), newS = "";
    	int stopIndex = s.length()-1;
    	for (int i = s.length()-1; i <= 0; i--) {
    		if (i == s.length()-1 && !(s.charAt(i) == '1')) {
    			break;
    		} else if (i < s.length()-1 && !(s.charAt(i) == '0')) {
    			break;
    		} else {
    			stopIndex = i;
    		}
    	}
    	newS = s.substring(0, stopIndex);
    	newCode = Double.parseDouble(newS);
    	return newCode;
    }
    
    public static void ranges(ArrayList<String> prob) {
    	double low = 0, high = Double.parseDouble(prob.get(0).split(" ")[1]);
    	for (int i = 0; i < prob.size(); i++) {
        	char c = prob.get(i).split(" ")[0].charAt(0);
        	double p = Double.parseDouble(prob.get(i).split(" ")[1]);
    		rangeLow.put(c, low);
    		rangeHigh.put(c, high);
    		
        	if (i != prob.size()-1) {
        		double nextP = Double.parseDouble(prob.get(i+1).split(" ")[1]);
	    		double temp = high;
	    		high += nextP;
	    		low = temp;
        	}
    	}
    }
    
    
    public static void main(String[] args) {
    	
    	Scanner in = new Scanner(System.in);
    	ArrayList<String> probabilities = new ArrayList<String>();

    	System.out.println("Enter each symbol and it's probability (when you're done enter done): ");
    	String input = in.nextLine();
    	while (!input.equalsIgnoreCase("done")) {
    		probabilities.add(input);
    		input = in.nextLine();
    	}
    	ranges(probabilities);
    	
    	System.out.print("Would you like to: \n1-Compress \n2-Decompress \nYour Choice: ");
    	String choice = in.nextLine();
    	
    	if (choice.equals("1")) {
    		System.out.print("\nEnter the text you would like to compress: ");
    		String text = in.nextLine();
    		System.out.println("Result: " + compress(text)+"/"+text.length());
    	} else {
    		System.out.print("\nEnter the code you would like to decompress: ");
    		String text = in.nextLine();
    		double code = Double.parseDouble(text.split("/")[0]);
    		int num = Integer.parseInt(text.split("/")[1]);
    		System.out.println("Result: " + decompress(code, num));
    	}
    }
}
