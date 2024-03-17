package world.belto.ai.nlp;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;



public class ResourceLoader {
    
	public URL local_pdf = getClass().getClassLoader().getResource("sample.pdf");
//    public URL local_bin = getClass().getClassLoader().getResource("openlp.bin");
    
    InputStream openLpBinStream = getClass().getClassLoader().getResourceAsStream("en-sent.bin");
    
    public File pdf;
	
	public ResourceLoader() {
		
		try {
 
            // Open a connection to the URL
            InputStream inputStreamPDF = local_pdf.openStream();
            // Create a temporary file
            File tempFile = File.createTempFile("tempfile", ".pdf");
            

            // Write the contents of the InputStream to the temporary file
            OutputStream outputStream = new FileOutputStream(tempFile);
          
            byte[] buffer = new byte[4096];
            
            int bytesRead;
           
            while ((bytesRead = inputStreamPDF.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
           
            // Close the streams
            
            inputStreamPDF.close();
            
            outputStream.close();
           

            // Use the temporary file as needed
            System.out.println("Temporary file created: " + tempFile.length());
            this.pdf  = tempFile;
         
           
            if (openLpBinStream == null) {
                throw new IOException("openlp.bin file not found");
            }
           

        } catch (IOException e) {
            e.printStackTrace();
        }
		
		
		
	}


	public URL getFile() {
		return local_pdf;
	}
	
	
	 public InputStream getOpenLpBinStream() {
	        return openLpBinStream;
	    }
	 
	 public File getPDF() {
		 return pdf;
	 }



	public static void main(String args[]) {
		ResourceLoader rl = new ResourceLoader();
		
		if (rl.getOpenLpBinStream() != null) {
            System.out.println("openlp.bin InputStream is ready to use");
        }	
		
	}

}
