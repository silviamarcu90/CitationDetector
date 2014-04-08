package classification;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class TestClassifier {
    
	private static String RESULTS = "ClassificationResults";
	private static String INPUTFILE = "test1.pdf";
	private static String INPUTFILE2 = "/home/silvia/Documents/DH-project/BooksWithCitations/Delorenzi_955425_tesi.pdf.pdf";
	private static String INPUTFILE3 = "/home/silvia/Documents/DH-project/BooksWithCitations/collectionFromStoriadivenezia/barbierato_immaginarsilaguerra.pdf";
	
	public static void computePrecisionAndRecall() {
		String allfilesPath = "/home/silvia/Dropbox/DH-project/DocsWithCitations/allFiles.txt";
		BufferedReader br = null;
		
		try {
			br = new BufferedReader(new FileReader(allfilesPath));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String line = "";
		int tp = 0; // true-positive
		int fp = 0; // false-positive
		int fn = 0; //false-negative
		try {
			while( (line = br.readLine()) != null) {
				String[] tokens = line.split(" ");
				String inputFile = "";
				inputFile = line.substring(0, line.lastIndexOf(" "));
				int score = Integer.parseInt(tokens[tokens.length - 1]);
				Classifier classifier = new Classifier(inputFile);
				boolean hasCitations = classifier.detectASVe();
				if(hasCitations) {
					if(score == 1)
						tp ++;
					else fp ++;
				}
				if(!hasCitations && score == 0)
					fn ++;				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Precision = " + tp*1.0 / (tp + fp));
		System.out.println("Recall = " + tp*1.0 / (tp + fn));
	}	
	
	public static void main(String[] args)
	{
		boolean hasCitations = false;
		PrintWriter out = null;
		String inputFile = INPUTFILE3;
//		Classifier classifier = new Classifier(inputFile);
//		hasCitations = classifier.detectASVe();
//
//		//write the results in a file
//		try {
//		    out = new PrintWriter(new BufferedWriter(new FileWriter(RESULTS, true)));
//		    String x = hasCitations?"1":"0";
//		    out.println(inputFile + " " + x);
//		}catch (IOException e) {
//		    System.err.println(e);
//		}finally{
//		    if(out != null){
//		        out.close();
//		    }
//		} 
		
		computePrecisionAndRecall();
	}
	
}
