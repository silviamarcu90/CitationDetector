package search;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import com.itextpdf.text.io.RandomAccessSourceFactory;
import com.itextpdf.text.pdf.PRTokeniser;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.RandomAccessFileOrArray;

public class Main {
    
	private static String INPUTFILE = "test1.pdf";
	private static String INPUTFILE2 = "/home/silvia/Documents/DH-project/BooksWithCitations/Delorenzi_955425_tesi.pdf.pdf";
	private static String INPUTFILE3 = "/home/silvia/Documents/DH-project/BooksWithCitations/collectionFromStoriadivenezia/barbierato_immaginarsilaguerra.pdf";
	private static String OUTPUTFILE = "Text1.txt";
	private static String OUTPUTFILE2 = "Text2.txt";
	
	public static void testPdfReader()
	{
		PdfReaderHelper reader = new PdfReaderHelper(INPUTFILE);
		reader.getFileContent();
	}
	public static void testPdfReader2()
	{
		PdfReaderHelper reader = new PdfReaderHelper(INPUTFILE3);
//		reader.extractText2();
		try {
			int pageNb = 4;
			String text = reader.getPageText(pageNb);
	    	System.out.println("Text on page " + pageNb + ": " + text);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("PdfReader error!!");
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{
//		Main.testPdfReader2();
		CitationsDetector detector = new CitationsDetector(INPUTFILE);
		detector.detectASVe();
	}
	
	
	/**
     * Parses the PDF using PRTokeniser
     * @param src  the path to the original PDF file
     * @param dest the path to the resulting text file
     * @throws IOException
     */
    public static void parsePdf(String src, String dest) throws IOException {
        PdfReader reader = new PdfReader(src);
        // we can inspect the syntax of the imported page
        byte[] streamBytes = reader.getPageContent(1);
        PRTokeniser tokenizer = new PRTokeniser(new RandomAccessFileOrArray
        						(new RandomAccessSourceFactory().createSource(streamBytes)));
        PrintWriter out = new PrintWriter(new FileOutputStream(dest));
        while (tokenizer.nextToken()) {
            if (tokenizer.getTokenType() == PRTokeniser.TokenType.STRING) {
                out.print(tokenizer.getStringValue());
            }
        }
        out.flush();
        out.close();
        reader.close();
    }
	
}