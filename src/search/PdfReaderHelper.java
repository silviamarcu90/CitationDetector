package search;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.ContentByteUtils;
import com.itextpdf.text.pdf.parser.PdfContentStreamProcessor;
import com.itextpdf.text.pdf.parser.RenderListener;
import com.snowtide.pdf.OutputTarget;
import com.snowtide.pdf.PDFTextStream;
import com.snowtide.pdf.Page;

public class PdfReaderHelper {

	private String pdfFilename;
	final String textFile = "textFile.txt";
	final int nbOfPagesLimit = 30;
	private int totalNumberOfPages;

	public PdfReaderHelper(String fn) {
		setFilename(fn);
		//get total number of pages
    	PDFTextStream stream = new PDFTextStream(pdfFilename);
    	setTotalNumberOfPages(stream.getPageCnt());
    	stream.close();
	}

	public String getFilename() {
		return pdfFilename;
	}

	public void setFilename(String filename) {
		this.pdfFilename = filename;
	}

	public int getTotalNumberOfPages() {
		return totalNumberOfPages;
	}

	public void setTotalNumberOfPages(int totalNumberOfPages) {
		this.totalNumberOfPages = totalNumberOfPages;
	}

	public String getFileContent() {
		String content = "";
		try {
			extractText();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			BufferedReader br = new BufferedReader(new FileReader(textFile));
			String line = "";
			while ((line = br.readLine()) != null)
				content += line + "\n";
		} catch (FileNotFoundException e) {
			System.err.println("Exception while reading...");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return content;
	}
    /**
     * Extracts text from a PDF document.
     * @throws IOException
     */
    public void extractText() throws IOException {
        PrintWriter out = new PrintWriter(new FileOutputStream(textFile));
        PdfReader reader = new PdfReader(pdfFilename);
        RenderListener listener = new MyTextRenderListener(out);
        PdfContentStreamProcessor processor = new PdfContentStreamProcessor(listener);
        int numberOfPages = Math.min(nbOfPagesLimit, reader.getNumberOfPages());
        for(int i = 1; i <= numberOfPages; ++i)
        {
        	PdfDictionary pageDic = reader.getPageN(1);
	        PdfDictionary resourcesDic = pageDic.getAsDict(PdfName.RESOURCES);
	        processor.processContent(ContentByteUtils.getContentBytesForPage(reader, i), resourcesDic);
        }
        out.flush();
        out.close();
        reader.close();
    }

    public void extractText2() {
        String pdfFilePath = pdfFilename;
        PDFTextStream pdfts = new PDFTextStream(pdfFilePath); 
        StringBuilder text = new StringBuilder(1024);
        pdfts.pipe(new OutputTarget(text));
        pdfts.close();
        System.out.printf("The text extracted from %s is:", pdfFilePath);
        System.out.println(text);
    }
    
    public String getPageText(int pageNumber) throws IOException {
    	PDFTextStream stream = new PDFTextStream(pdfFilename);
    	StringBuffer sb = new StringBuffer(1024);
    	OutputTarget tgt = new OutputTarget(sb);
    	Page fifthPage = stream.getPage(pageNumber); // page numbers are 0-based
    	fifthPage.pipe(tgt);
    	stream.close();
    	return sb.toString();
	}
    
	public String getFirstThreePagesText () throws IOException {
		PDFTextStream stream = new PDFTextStream(pdfFilename);
		StringBuffer sb = new StringBuffer(1024);
		OutputTarget tgt = OutputTarget.forBuffer(sb);
		for (int pagenum = 0; pagenum < 3; pagenum++) {
			Page page = stream.getPage(pagenum);
			page.pipe(tgt);
		}
		stream.close();
		return sb.toString();
	}
	
}
