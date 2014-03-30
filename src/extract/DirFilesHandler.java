package extract;

import java.io.File;
import java.util.ArrayList;

public class DirFilesHandler {

	private String dirPath = "/home/silvia/Dropbox/DH-project/DocsWithCitations";
	private ArrayList<String> allFiles = new ArrayList<String>();
	
	private void listFilesForFolder(File folder) {
		
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	          // System.out.println("Reading files under the folder "+folder.getAbsolutePath());
    	  	  listFilesForFolder(fileEntry);
	        } else {
	    	    if (fileEntry.isFile()) {
	    		    String temp = fileEntry.getName();
	    		    if ((temp.substring(temp.lastIndexOf('.') + 1,
	    			  	  temp.length()).toLowerCase()).equals("pdf"))
	    		    {
	    		    	allFiles.add(folder.getAbsolutePath()+ "/" + fileEntry.getName());
//    			  	  	System.out.println("File= " + folder.getAbsolutePath()+ "/" + fileEntry.getName());
	    		    }
	          }
	       }
       }
	    
	}
	
	public ArrayList<String> getAllFiles() {
		File dir = new File(dirPath);
		allFiles = new ArrayList<String>();
		listFilesForFolder(dir);
		return allFiles;
	}
}
