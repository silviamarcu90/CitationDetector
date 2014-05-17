package postProcessing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
 
import org.w3c.dom.Document;
import org.w3c.dom.Element;
 
public class WriteXMLFile {
	
	static final String ASVE = "Archive";
	static final String XML_FILENAME = "AllCitations.xml";
	
	public static void reconstructCitations() throws Exception{
		String categ = ASVE;
		int k = 0;	  
		
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
 
		// root element
		Document doc = docBuilder.newDocument();
		Element mainRoot = doc.createElement("Citations");
		doc.appendChild(mainRoot);
	
		//root of one element
		Element rootElement = null;
		
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
		String prevCateg = "Archive";
		String newFond = "";
		try {
			citation = inCorpus.readLine();
			annotation = inAnnot.readLine();
			while(citation != null) {
				String[] toks = annotation.split("\t");
				citation.trim();
				citation = citation.replaceAll(" ", "");
				citation = citation.replaceAll(" ", "");
					
				if(toks.length == 2) //else it is finished this citation!
				{
					String currentCateg = toks[1];
					if(!currentCateg.equals(prevCateg)) //no previous token labeled with fond
					{
						String[] fondDefs = newFond.split(",");
						for(String f:fondDefs) {
							if(f == "") break;
							//different parts separated by comma
							Element token = doc.createElement(categ);
							token.appendChild(doc.createTextNode(f));
							rootElement.appendChild(token);
						}
						//init new fond
						newFond = "";
						categ = toks[1];

					}
					else 
					{
						newFond += " ";
					}
					newFond += citation;//.substring(0, citation.length()-2);
					prevCateg = currentCateg;
					if(currentCateg.equals(ASVE) )
					{
						k++;
						rootElement = doc.createElement("citation");
						mainRoot.appendChild(rootElement);
						rootElement.setAttribute("id", "" + k);
					}
				}
				citation = inCorpus.readLine();
				annotation = inAnnot.readLine();				
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//for the last token extracted
		String[] fondDefs = newFond.split(",");
		for(String f:fondDefs) {
			if(f == "") break;
			//different parts separated by comma
			Element token = doc.createElement(categ);
			token.appendChild(doc.createTextNode(f));
			rootElement.appendChild(token);
		}

		
		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(XML_FILENAME));
 
		// Output to console for testing
		// StreamResult result = new StreamResult(System.out);
 
		transformer.transform(source, result);
 
		System.out.println("File " + XML_FILENAME +  " saved!");

	}
	
	public static void main(String[] argv) throws Exception {
		WriteXMLFile.reconstructCitations();
	}
}