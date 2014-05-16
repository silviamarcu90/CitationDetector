package trainingPrep;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;

public class Main {

	static final String CITATIONS_FILE = "ExtractedCitations26April.txt";
	static final String FILE_TO_ANNOTATE1 = "CorpusToAnnotate1.txt";
	static final String FILE_TO_ANNOTATE2 = "CorpusToAnnotate2.txt";
	static final String FILE_TO_ANNOTATE3 = "CorpusToAnnotate3.txt";
	static final String CORPUS = "Corpus.txt";

	public static void writeCitationsTokenized(String outputFilePath,
			ArrayList<String> allCitations, int offset) throws Exception
	{
		BufferedWriter out = new BufferedWriter(new FileWriter(outputFilePath));
		for(int i = 0 + offset; i < allCitations.size()/*100 + offset*/; ++i) {
			String citation = allCitations.get(i);
			String[] tokens = citation.split(" ");
			for(String tok : tokens) {
				out.write(tok + " \n");
//				System.out.println(tok);
			}
			out.write("\n");
//			System.out.println();
		}
		out.close();

	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception
	{
		ArrayList<String> allCitations = new ArrayList<String>();
		//read file
		BufferedReader br = null;
		br = new BufferedReader(new FileReader(CITATIONS_FILE));
		
		String line = br.readLine();
		
		while(line != null) 
		{
			allCitations.add(line);
			line = br.readLine();
		}
		br.close();
		
//		Collections.shuffle(allCitations); //shuffle - to be in a random order
		
//		for(String l: allCitations) {
//			System.out.println(l);
//		}
		
		//take first 300 citations and put them in a file -- split by white space
		
//		Main.writeCitationsTokenized(FILE_TO_ANNOTATE1, allCitations, 0);
//		Main.writeCitationsTokenized(FILE_TO_ANNOTATE2, allCitations, 100);
//		Main.writeCitationsTokenized(FILE_TO_ANNOTATE3, allCitations, 200);

		Main.writeCitationsTokenized(CORPUS, allCitations, 0);
	}

}
