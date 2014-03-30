package search;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import classification.Classifier;

import com.itextpdf.text.io.RandomAccessSourceFactory;
import com.itextpdf.text.pdf.PRTokeniser;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.RandomAccessFileOrArray;

import extract.CitationsCollector;
import extract.DirFilesHandler;

public class Main {
    
	private static String INPUTFILE = "test1.pdf";
	private static String INPUTFILE2 = "/home/silvia/Documents/DH-project/BooksWithCitations/Delorenzi_955425_tesi.pdf.pdf";
	private static String INPUTFILE3 = "/home/silvia/Documents/DH-project/BooksWithCitations/collectionFromStoriadivenezia/barbierato_immaginarsilaguerra.pdf";
	private static String INPUTFILE4 = "/home/silvia/Documents/DH-project/BooksWithCitations/"
				+ "RHISE Vol. 2 - Albini et al.pdf";
	private static String INPUTFILE5 = "/home/silvia/Dropbox/DH-project/DocsWithCitations/docsGiovanni/"
//				+ "111-Mueller.pdf";
//			+ "NIS2013.pdf";
			+ "Sclosa_955397_tesi.pdf.pdf";
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
	
	public static void collectCitationsFromFiles(ArrayList<String> allFiles) {
		for(String inputfile : allFiles) {
			Classifier classifier = new Classifier(inputfile);
			if( classifier.detectASVe() ) //has citations
			{	
				CitationsCollector cc = new CitationsCollector(inputfile);
				cc.collectASVe();
			}
			System.out.println("_________________________________________________________");
		}
	}
	
	public static void main(String[] args)
	{
//		Main.testPdfReader2();
//		LightASVeDetector detector = new LightASVeDetector(INPUTFILE);
//		detector.detectASVe();
		
//		CitationsCollector cc = new CitationsCollector(INPUTFILE5);
//		cc.collectASVe();

		DirFilesHandler handler = new DirFilesHandler();
		ArrayList<String> allFiles = handler.getAllFiles();
//		System.out.println(allFiles.size());
//		for(String f : allFiles)
//			System.out.println(f);
		Main.collectCitationsFromFiles(allFiles);
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
