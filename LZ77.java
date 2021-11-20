import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class LZ77 {
	
	public static boolean compress(String inputPath, String outputPath) throws IOException {
		String text = Files.readString(Path.of(inputPath));
		String searchWindow = "", output = "", temp = "";
		for (int i = 0; i < text.length(); i++) {
			int position = 0, length = 0;
			boolean match_start = false;
			String next = "";
			searchWindow = text.substring(0, i);
			
			if (i > 0) {
				int currentCount = 0, temp_start = 0, start = 0, cursor = i;
				for (int j = 0; j < searchWindow.length(); j++) {
					if (text.charAt(cursor) == searchWindow.charAt(j)) {
						if (match_start == false) {
							temp_start = j;
							match_start = true;
						}
						currentCount++;
						if (cursor < text.length()-1) {
							cursor++;
						} else {
							currentCount = 0;
							cursor = i;
							if (match_start) {
								j--;
								match_start = false;
							}
						}
					} else {
						currentCount = 0;
						cursor = i;
						if (match_start) {
							j--;
							match_start = false;
						}
					}
					if (currentCount >= length) {
						length = currentCount;
						start = temp_start;
					}
				}
				next = Character.toString(text.charAt(i + length));
				if (length > 0) {
					position = i - start;
				}
				i += length;
			} else {
				next = Character.toString(text.charAt(0));
			}
			temp = "<" + position + "," + length + "," + next + ">";
			output += temp;
		}

	    try {
	        FileWriter myWriter = new FileWriter(outputPath + "\\CompressedFile_lZ77.txt");
	        myWriter.write(output);
	        myWriter.close();
	        return true;
	      } catch (IOException e) {
	        System.out.println("An error occurred.");
	        e.printStackTrace();
	        return false;
	      }
	}
	
    public static boolean decompress(String inputPath, String outputPath) throws IOException{
    	String text = Files.readString(Path.of(inputPath));
        String output = "";
        
        boolean start_tag = false;
        for (int i = 0; i < text.length(); i++) {
     	   if (text.charAt(i) == '<') {
     		   start_tag = true;
     		   continue;
     	   } else if (text.charAt(i) == '>') {
     		   start_tag = false;
     		   continue;
     	   }
     	   
     	   if (start_tag) {
     		   int position = Character.getNumericValue(text.charAt(i));
     		   i += 2;
     		   int length = Character.getNumericValue(text.charAt(i));
     		   i += 2;
     		   char next = text.charAt(i);
     		   if (length > 0) {
     			   int start = output.length() - position;
     			   String symbols = output.substring(start, start + length);
     			   output+= symbols;
     		   }
     		   output += next;
     	   }
        }

	    try {
	        FileWriter myWriter = new FileWriter(outputPath + "\\DecompressedFile_LZ77.txt");
	        myWriter.write(output);
	        myWriter.close();
	        return true;
	      } catch (IOException e) {
	        System.out.println("An error occurred.");
	        e.printStackTrace();
	        return false;
	      }
     }
}
