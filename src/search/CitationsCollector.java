package search;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CitationsCollector {
	private String bookName;
	
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
	
	public void detectASVe() 
	{
		PdfReaderHelper reader = new PdfReaderHelper(bookName);
		int totalNumberOfPages = reader.getTotalNumberOfPages();
		String text = "";
    	String message = "Per inciso, un ritratto di Girolamo Cappello è" + 
			"menzionato – senza indicazioni sull‟autore – nell‟inventario dei beni (1648) di suo" + 
			"nipote Vincenzo Cappello (ASV, Notarile Atti, notaio Claudio Paulini, b. 3457," +
			"protocollo 1648, III, cc. 1133r-1164v),";
//	    			"ASVe, 1700­1746. " +
//	    			"Cinque Savi alla Mercanzia, Lettere, Rettori, Zante, b. 569. \nbllbla";
//	    			"ASVe, 1704a, b. Senato, Dispacci, Provveditori da Terra e da Mar, Provveditori Generali da Mar, f. 951 (luglio 1702­luglio 1705): a) Dispaccio n. 85," +
//	    			" Provveditore Generale da Mar, Corfù, 6 dicembre; b)Disegno del fortino di Trapano [dicembre 1704].";
//	"ASVe, 1700­1746. Cinque Savi alla Mercanzia, Lettere, Rettori, Zante, b. 569. \n" +
//"ASVe, 1704a, b. Senato, Dispacci, Provveditori da Terra e da Mar, Provveditori Generali da Mar, f. 951" +
//"(luglio 1702­luglio 1705): a) Dispaccio n. 85, Provveditore Generale da Mar, Corfù, 6 dicembre; b)" +
//"Disegno del fortino di Trapano [dicembre 1704]." +
//	    	"ASVe, 1700­1746. Cinque Savi alla Mercanzia, Lettere, Rettori, Zante, b. 569. \n" + 
//"ASVe, 1704a, b. Senato, Dispacci, Provveditori da Terra e da Mar, Provveditori Generali da Mar, f. 951 (luglio 1702­luglio 1705): a) Dispaccio n. 85," +
//" Provveditore Generale da Mar, Corfù, 6 dicembre; b)Disegno del fortino di Trapano [dicembre 1704].";
	    	
    	// TO MATCH: 
//    	message = "ASVe, 1766g. Collegio, Risposte di fuori, cc. 512-456 rtj";
    	//??! NOT MATCHED  in the text-- "ASVe (Archivio di Stato di Venezia), 1693­1722. Cinque Savi alla Mercanzia, Lettere, Rettori, Cefalonia,    	b. 561";
    	Pattern p = Pattern.compile("(ASV[e]?(.\\(Archivio.di.Stato.di.Venezia\\))?,.+([fbcn]|(bb)|(cc))\\..[\\d-]+)");
//	    			"(ASV[e]?,[^(,\\)\\()]*,[^,]*,[^,]*,[^,]*,)");
//	    			+ " [a-zA-Z]\\. [\\d]+[^.]*.");
	    	if(totalNumberOfPages > 20)
	    		totalNumberOfPages = 20;
	    	for(int pageNb = 0; pageNb < totalNumberOfPages; ++pageNb)
	    	{
	    		//get text of page with number pageNb
	    		try {
	    			text = reader.getPageText(pageNb);
//	    	    	System.out.println("Text on page " + pageNb + ": " + text);
	    		} catch (IOException e) {
	    			// TODO Auto-generated catch block
	    			System.err.println("PdfReader error!!");
	    			e.printStackTrace();
	    		}
	    		//match regex in text
		    	Matcher matcher = p.matcher(//message);
						text);

				while(matcher.find()) {
					String result = matcher.group(); //!! consider length to avoid long wrong citation inside the text
//					System.out.println(result.length());
					System.out.println("<<" + result + ">>");
				}		
	    		
	    	}
	}

}
