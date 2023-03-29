import java.io.File;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class ReceiptReader extends PDFTextStripper{
    //Loading an existing document
    File file = new File("D://Sample.pdf");
    PDDocument document = PDDocument.load(file);
    //Instantiate PDFTextStripper class
    PDFTextStripper pdfStripper = new PDFTextStripper();

    public ReceiptReader() throws IOException {
        //Retrieving text from PDF document
        String text = pdfStripper.getText(document);
        System.out.println(text);
        //Closing the document
        document.close();
    }


}
