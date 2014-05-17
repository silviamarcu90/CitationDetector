package postProcessing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

class ValueComparator implements Comparator<String> {

    TreeMap<String, Integer> base;
    public ValueComparator(TreeMap<String, Integer> base) {
        this.base = base;
    }

    // Note: this comparator imposes orderings that are inconsistent with equals.    
    public int compare(String a, String b) {
        if (base.get(a) >= base.get(b)) {
            return -1;
        } else {
            return 1;
        } // returning 0 would merge keys
    }
}

public class FondsAndSeriesExtractor {
	
	static final String FOND = "Fond";
	static final String SERIES = "Series";
		
	static void writeDictionary(String filename, TreeMap<String, Integer> map) {
		BufferedWriter out = null;
		
		try {
			out = new BufferedWriter(new FileWriter(filename));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(Map.Entry<String, Integer> entry:map.entrySet()) {
			try {
				out.write(entry.getKey() + "," + entry.getValue() + "\n" );
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try {
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]) throws Exception{ 
		
		
		//dictionaries with fonds/series and their frequencies
		TreeMap<String, Integer> fonds = new TreeMap<String, Integer>();
		TreeMap<String, Integer> series = new TreeMap<String, Integer>();		
        ValueComparator comp_f =  new ValueComparator(fonds);
        ValueComparator comp_s =  new ValueComparator(series);

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
						newFond += citation;
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

						newSeries += citation;
					}
					else {
						
						if(fond)
						{
							String[] fondDefs = newFond.split(",");
							for(String f:fondDefs) {
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
		
		//sorted dictionaries
		TreeMap<String, Integer> sortedFonds = new TreeMap<String, Integer>(comp_f);
		TreeMap<String, Integer> sortedSeries = new TreeMap<String, Integer>(comp_s);		
		sortedFonds.putAll(fonds);
		sortedSeries.putAll(series);
		
//		for(Map.Entry<String, Integer> entry:sortedFonds.entrySet()) {
//			System.out.println(entry.getKey() + "-->" + entry.getValue() );
//		}
//		System.out.println("SERIES");
//		for(Map.Entry<String, Integer> entry:series.entrySet()) {
//			System.out.println(entry.getKey() + "-->" + entry.getValue() );
//		}
		
		FondsAndSeriesExtractor.writeDictionary("Fonds", sortedFonds);
		FondsAndSeriesExtractor.writeDictionary("Series", sortedSeries);

	}
	
}
