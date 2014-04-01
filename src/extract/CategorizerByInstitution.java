package extract;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CategorizerByInstitution {
	private final static String CITATIONS_FILE = //"testCategorizer";
			"/home/silvia/Dropbox/DH-project/DocsWithCitations/AllExtractedCitations";
	
	private final static String CATEGORIZED_CITATIONS_FILE = "/home/silvia/Dropbox/DH-project/DocsWithCitations/CategorizedCitations.txt";

	public void categorizeCitationsFromFile() {
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(CITATIONS_FILE));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String line;
		
//		Pattern pattern = Pattern.compile
//    			("(A[\\.]?S[\\.]?V[\\.]?[e]?(.(\\()?Archivio.di.Stato.di.Venezia(\\))?)?,.)([^,]+)");
		ArrayList<String> institutions = new ArrayList<String>(); //all kind of institutions found in citations
		
		try {
			line = in.readLine();
			while(line != null)
			{
//				System.out.println(line);
//				line = line.replaceAll("[\\.\\(\\d-–\\) ]+", " ");
				line = line.replaceAll("(\\([\\d–-]+[a-z]?(\\.)?\\)(\\.)?)|((\\d)+[a-z]?(\\.)?(,)?)", "");
//				line = line.replaceAll("((\\d)+[a-z]?(\\.)?)", "");
//				System.out.println(line);
				
				String[] tokens = line.split(",");
				String newInstitution = tokens[1].trim();
//				System.out.println(newInstitution);
				if(!institutions.contains(newInstitution))
					institutions.add(newInstitution);
//				Matcher matcher = pattern.matcher(line);
//				while(matcher.find()) {
//					String result = matcher.group();						
////					System.out.println("<" + result + ">");
//					String institutionString = result.replaceAll(matcher.group(1), "");
//					String[] tokens = institutionString.split(" ");
//					String institution = tokens[tokens.length - 1];
//					System.out.println("Institution:" + institution);//matcher.group(1));//institution);
//					
//				}
				line = in.readLine();
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(String inst : institutions)
			System.out.println(inst);

	}

}
