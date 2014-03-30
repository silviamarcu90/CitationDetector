package extract;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import search.PdfReaderHelper;

public class CitationsCollector {
	private String bookName;
	private final static String CITATIONS_FILE =
			"/home/silvia/Dropbox/DH-project/DocsWithCitations/ExtractedCitations.txt";
	
	public CitationsCollector(String filename)
	{
		setBookName(filename);
	}

	public String getBookName()
	{
		return bookName;
	}

	public void setBookName(String bookName)
	{
		this.bookName = bookName;
	}
	
	public void collectASVe() 
	{
		PdfReaderHelper reader = new PdfReaderHelper(bookName);
		int totalNumberOfPages = reader.getTotalNumberOfPages();
		String text = "";
    	String message = "Per inciso, un ritratto di Girolamo Cappello è" + 
			"menzionato – senza indicazioni sull‟autore – nell‟inventario dei beni (1648) di suo" + 
			"nipote Vincenzo Cappello (ASV, Notarile Atti, notaio Claudio Paulini, b. 3457," +
			"protocollo 1648, III, cc. 1133r-1164v),";
//"ASVe, 1704a, b. Senato, Dispacci, Provveditori da Terra e da Mar, Provveditori Generali da Mar, f. 951 (luglio 1702­luglio 1705): a) Dispaccio n. 85," +
//" Provveditore Generale da Mar, Corfù, 6 dicembre; b)Disegno del fortino di Trapano [dicembre 1704].";
	    	
    	// TO MATCH: 
//    	message = "ASVe, 1766g. Collegio, Risposte di fuori, cc. 512-456 rtj";
	//??! NOT MATCHED  in the text-- "ASVe (Archivio di Stato di Venezia), 1693­1722. Cinque Savi alla Mercanzia, Lettere, Rettori, Cefalonia,    	b. 561";
    
    	Pattern p = Pattern.compile
    			("(ASV[e]?(.\\(Archivio.di.Stato.di.Venezia\\))?,.+([fbcn]|(bb)|(cc))\\..[\\d-]+)");
    			//filza|busta -- full words not included
    	
//    	if(totalNumberOfPages > 20)
//    		totalNumberOfPages = 20;
		int lenBookPath = bookName.length();
		String outputFile = bookName.substring(0, lenBookPath - 3) + "txt";
		BufferedWriter out = null, outAppend = null;
		try {
			out = new BufferedWriter(new FileWriter(outputFile));
			outAppend = new BufferedWriter(new FileWriter(CITATIONS_FILE, true));
		} catch (IOException e1) {
			System.err.println("Error while opening a file for writing");
			e1.printStackTrace();
		}

    	for(int pageNb = 0; pageNb < totalNumberOfPages; ++pageNb)
    	{
    		//get text of page with number pageNb
    		try {
    			text = reader.getPageText(pageNb);
//	    	    	System.out.println("Text on page " + pageNb + ": " + text);
    		} catch (IOException e) {
    			System.err.println("PdfReader error!!");
    			e.printStackTrace();
    		}
    		//match regex in text
	    	Matcher matcher = p.matcher(//message);
					text);

			while(matcher.find()) {
				String result = matcher.group(); //!! consider length to avoid long wrong citation inside the text
				System.out.println("<<" + result + ">>");
				try {
					out.write(result + "\n");
					outAppend.write(result + "\n");
				} catch (IOException e) {
					System.err.println("Error while writing");
					e.printStackTrace();
				}
			}   		
    	}
    	
    	try {
			out.close();
			outAppend.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
