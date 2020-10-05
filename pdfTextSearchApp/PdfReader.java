package pdfTextSearchApp;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripperByArea;

import java.util.*;

import java.awt.geom.Rectangle2D;

public class PdfReader {

    public static void main(String args[]){
        new PdfReader(1, "../sample/");

    }


    // Read in a file
    public PdfReader(int slidesPerPage, String directoryName){
        try{
            File folder = new File(directoryName);
            File[] files = folder.listFiles();
            String fileName;
            List<String> fileText;
            for (File file : files){
                if (file.isFile()){
                    if (file.getName().endsWith(".pdf")){
                        fileName = file.getName();
                        fileText = readFile(slidesPerPage, directoryName + "/" + fileName);
                        new PdfFile(fileName, fileText);
                    }

                    
                }
            }
        }
        catch(IOException e){
            System.out.println(e);
        }

    }

    // Read a files contents
    public List<String> readFile(int slidesPerPage, String fileName) throws IOException {
        File file = new File(fileName);
        System.out.println(fileName);
        PDDocument document = PDDocument.load(file);

        float pageHeight = document.getPage(0).getMediaBox().getHeight();
        float pageWidth = document.getPage(0).getMediaBox().getWidth();

        List<String> slides = new ArrayList<String>();

        String[] currentSlides = new String[slidesPerPage];

        PDFTextStripperByArea pdfStripper = new PDFTextStripperByArea();
        setupRegions(pdfStripper, slidesPerPage, pageHeight, pageWidth);
        int numberOfPages = document.getNumberOfPages();

        // loop through the pages
        for (int pageNumber = 0; pageNumber < numberOfPages; pageNumber++){

            pdfStripper.extractRegions(document.getPage(pageNumber));

            for (int i = 0; i < slidesPerPage; i++){
                currentSlides[i] = pdfStripper.getTextForRegion( "r"+i ).replace(
                                    "The picture can't be displayed",
                                    "CONTAINS UNDISPLAYABLE IMAGE");
            }

            for (int slideNum = 0; slideNum < slidesPerPage; slideNum++){
                slides.add(currentSlides[slideNum]);
            }
        }
        document.close();

        return slides;
    }


    public void setupRegions(PDFTextStripperByArea pdfStripper, int slidesPerPage, float pageHeight, float pageWidth){

        Rectangle2D region;

        if (slidesPerPage == 1){
            region = new Rectangle2D.Double(0, 0, pageWidth, pageHeight);
            pdfStripper.addRegion("r0", region);
        }else if (slidesPerPage <= 2){
            if (pageHeight > pageWidth){
                region = new Rectangle2D.Double(0, 0, pageWidth, pageHeight/2);
                pdfStripper.addRegion("r0", region);
                region = new Rectangle2D.Double(0, pageHeight/2, pageWidth, pageHeight/2);
                pdfStripper.addRegion("r1", region);
            }else{
                region = new Rectangle2D.Double(0, 0, pageWidth/2, pageHeight);
                pdfStripper.addRegion("r0", region);
                region = new Rectangle2D.Double(pageWidth/2, 0, pageWidth/2, pageHeight);
                pdfStripper.addRegion("r1", region);
            }
        }else if (slidesPerPage <= 4){
            region = new Rectangle2D.Double(0, 0, pageWidth/2, pageHeight/2);
            pdfStripper.addRegion("r0", region);
            region = new Rectangle2D.Double(pageWidth/2, 0, pageWidth/2, pageHeight/2);
            pdfStripper.addRegion("r1", region);
            region = new Rectangle2D.Double(0, pageHeight/2, pageWidth/2, pageHeight/2);
            pdfStripper.addRegion("r2", region);
            region = new Rectangle2D.Double(pageWidth/2, pageHeight/2, pageWidth/2, pageHeight/2);
            pdfStripper.addRegion("r3", region);
        }
    }


    // Parse slides per page
    public String[] parseSlides(int slidesPerPage, String text){
        String[] currentSlides = new String[slidesPerPage];

        if (slidesPerPage == 1){
            currentSlides[0] = text;
        }
        return currentSlides;
    }
}