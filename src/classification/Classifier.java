package classification;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import search.PdfReaderHelper;

public class Classifier {
	private String bookName;
	
	public Classifier(String filename)
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
	
	public boolean detectASVe() 
	{
		PdfReaderHelper reader = new PdfReaderHelper(bookName);
		int totalNumberOfPages = reader.getTotalNumberOfPages();
		String text = "";
    
    	Pattern p = Pattern.compile("((A|a)(R|r)(C|c)(H|h)(I|i)(V|v)(I|i)(O|o) " +
    			"(D|d)(I|i) (S|s)(T|t)(A|a)(T|t)(O|o) (D|d)(I|i) (V|v)(E|e)(N|n)(E|e)(Z|z)(I|i)(A|a))"
    			+ "|(ASV[e]?)");

    	if(totalNumberOfPages > 20)	    	
    		totalNumberOfPages = 20;
    	
    	for(int pageNb = 0; pageNb < totalNumberOfPages; ++pageNb)
    	{
    		//get text of page with number pageNb
    		try {
    			text = reader.getPageText(pageNb);
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			System.err.println("PdfReader error!!");
    			e.printStackTrace();
    		}
    		//match regex in text
	    	Matcher matcher = p.matcher(text);

			while(matcher.find()) {
				String result = "";
				result = matcher.group();
				if(!result.equals(""))
				{
					System.out.println("<<" + result + ">>");
					return true;
				}
			}			    		
    	}
    	return false;
	}

}
