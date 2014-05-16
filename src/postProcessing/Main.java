package postProcessing;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class Main {
	
	static final String FOND = "Fond";
	static final String SERIES = "Series";
		
	public static void main(String args[]) { 
		//dictionaries with fonds/series and their frequencies
		TreeMap<String, Integer> fonds = new TreeMap<String, Integer>();
		TreeMap<String, Integer> series = new TreeMap<String, Integer>();		

		BufferedReader inCorpus = null;
		BufferedReader inAnnot = null;
		try {
			inCorpus = new BufferedReader(new FileReader("Corpus.txt"));
			inAnnot = new BufferedReader(new FileReader("corpus_predicted_labels.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String citation;
		String annotation;
		Boolean fond = false, series1 = false;
		String newFond = "";
		String newSeries = "";
		try {
			citation = inCorpus.readLine();
			annotation = inAnnot.readLine();
			while(citation != null) {
				String[] toks = annotation.split("\t");
				citation.trim();
				citation = citation.replaceAll(" ", "");
				citation = citation.replaceAll(" ", "");
				if(toks.length == 2)
				{
//					System.out.println(toks.length + "  " + toks[1]);
					if(toks[1].equals(FOND))
					{
						if(!fond) //no previous token labeled with fond
						{
							newFond = "";
							fond = true;
						}
						else 
						{
							newFond += " ";
						}
						newFond += citation;//.substring(0, citation.length()-2);
					}
					else if(toks[1].equals(SERIES))
					{
						if(!series1) //no previous token labeled with series
						{
							newSeries = "";
							series1 = true;
						}
						else 
						{
							newSeries += " ";
						}

						newSeries += citation;//citation.substring(0, citation.length()-2);
					}
					else {
						
						if(fond)
						{
//							System.out.println("Fond: =" + newFond + "="); 
							String[] fondDefs = newFond.split(",");
//							System.out.println(newFond);
							for(String f:fondDefs) {
//								System.out.println("F:: +" + f + "+");
								if(f == "") break;
								if(fonds.containsKey(f))
								{
									Integer val = fonds.get(f);
									fonds.put(f, val+1);
								}
								else {
									fonds.put(f, 1);
								}
								
							}
							fond = false;
						}
						if(series1)
						{
							String[] seriesDefs = newSeries.split(",");
							for(String s:seriesDefs)
							{
//								System.out.println("S: =" + s + "=");
								if(series.containsKey(s))
								{
									Integer val = series.get(s);
									series.put(s, val+1);
								}
								else
									series.put(s, 1);
							}
							series1 = false;
						}
						
					}
				}
				citation = inCorpus.readLine();
				annotation = inAnnot.readLine();				
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(Map.Entry<String, Integer> entry:fonds.entrySet()) {
			System.out.println(entry.getKey() + "-->" + entry.getValue() );
		}

		System.out.println("SERIES");
		for(Map.Entry<String, Integer> entry:series.entrySet()) {
			System.out.println(entry.getKey() + "-->" + entry.getValue() );
		}

	}
}
