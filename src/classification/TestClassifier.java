package classification;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class TestClassifier {
    
	private static String RESULTS = "ClassificationResults";
	private static String INPUTFILE = "test1.pdf";
	private static String INPUTFILE2 = "/home/silvia/Documents/DH-project/BooksWithCitations/Delorenzi_955425_tesi.pdf.pdf";
	private static String INPUTFILE3 = "/home/silvia/Documents/DH-project/BooksWithCitations/collectionFromStoriadivenezia/barbierato_immaginarsilaguerra.pdf";
	
	public static void main(String[] args)
	{
		boolean hasCitations = false;
		PrintWriter out = null;
		String inputFile = INPUTFILE3;
		Classifier classifier = new Classifier(inputFile);
		hasCitations = classifier.detectASVe();

		//write the results in a file
		try {
		    out = new PrintWriter(new BufferedWriter(new FileWriter(RESULTS, true)));
		    String x = hasCitations?"1":"0";
		    out.println(inputFile + " " + x);
		}catch (IOException e) {
		    System.err.println(e);
		}finally{
		    if(out != null){
		        out.close();
		    }
		} 
	}
	
}
