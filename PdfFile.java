import java.util.*;

public class PdfFile {

    List<String> fileText = new ArrayList<String>();
    String fileName;

    static List<PdfFile> allPdfs = new ArrayList<PdfFile>();

    public static void main(String args[]){

        /*
        List<String> tmpFText = new ArrayList<String>();
        tmpFText.add("a b an dd");
        new PdfFile("file1", tmpFText);
        new PdfFile("file3", tmpFText);
        new PdfFile("file4", tmpFText);
        new PdfFile("file2", tmpFText);
        new PdfFile("file0", tmpFText);
        new PdfFile("file5", tmpFText);

        for (PdfFile pdf : PdfFile.allPdfs){
            System.out.println(pdf.fileName);
        }*/

        
        new PdfReader(4, "../sample/");

        for (PdfFile pdf : PdfFile.allPdfs){
            System.out.println(pdf.fileName);
        }


        System.out.println(searchPdfs("and", false, true, false));
    }


    public PdfFile(String fileName, List<String> fileText){
        this.fileName = fileName;
        this.fileText = fileText;

        PdfFile.addPdf(this);
        System.out.println(PdfFile.allPdfs.size());
    }

    // Add a new pdf to PdfFile.allPdfs
    public static void addPdf(PdfFile newPdf){
        PdfFile.allPdfs.add(PdfAdder(newPdf, PdfFile.allPdfs), newPdf);
        System.out.println("Added");
    }
    
    // Look for where to put a new pdf in PdfFile.allPdfs so it remains sorted
    public static int PdfAdder(PdfFile newPdf, List<PdfFile> allPdfs){

        int pdfsSize = allPdfs.size();
        if (pdfsSize == 0){
            return 0;
        }else{
            System.out.println((int)pdfsSize/2);
            if (allPdfs.get((int)pdfsSize/2).fileName.compareTo(newPdf.fileName) > 0){
                return PdfAdder(newPdf, allPdfs.subList(0, (int)pdfsSize/2));
            }else{
                return (int)pdfsSize/2 + 1 + PdfAdder(newPdf, allPdfs.subList(((int)pdfsSize/2)+1, pdfsSize));
            }
        }
    }

    // Print all pages in a PdfFile
    public void printPages(){
        System.out.println(this.fileName);
        for (int i = 0; i < this.fileText.size(); i++){
            System.out.println(this.fileText.get(i));
        }

    }

    // Search all PdfFiles for a text string. Return a hashtable that maps file names to a hashtable
    // that maps slide numbers to the text of any slides that contain the searched for text
    public static Hashtable<String, Hashtable<Integer, String>> searchPdfs(String toFind,
                                                                           boolean matchCase,
                                                                           boolean wholeWord,
                                                                           boolean exactPhrase){
        
        PdfFile cPdf;
        Hashtable<Integer, String> cPages;
        Hashtable<String, Hashtable<Integer, String>> allPages = new Hashtable<String, Hashtable<Integer, String>>();

        for (int i = 0; i < PdfFile.allPdfs.size(); i++){
            cPdf = PdfFile.allPdfs.get(i);
            cPages = cPdf.searchSinglePdf(toFind, matchCase, wholeWord, exactPhrase);
            if (cPages.size() != 0){
                allPages.put(cPdf.fileName, cPages);
            }
        }

        return allPages;
    }


    // Search a PdfFile for a text string. Return a hashtable that maps slide numbers to the text
    // of any slides that contain the searched for text
    public Hashtable<Integer, String> searchSinglePdf(String toFind,
                                                      boolean matchCase,
                                                      boolean wholeWord,
                                                      boolean exactPhrase){
                                      
        Hashtable<Integer, String> pages = new Hashtable<Integer, String>();
        String cPage;
        boolean foundOnPage;

        // If case dosnt matter, put toFind to lowercase
        if (matchCase == false){
            toFind = toFind.toLowerCase();
        }
        List<String> wordList = new ArrayList<String>();
        if (exactPhrase){
            // if exactPhrase is on, search for everything as a unit
            wordList.add(toFind);
        }else{
            // if exactPhrase is off, split the search words so they can be
            // searched for individually
            wordList = Arrays.asList(toFind.split(" "));
        }
        for (int i = 0; i < this.fileText.size(); i++){
            foundOnPage = true;
            cPage = this.fileText.get(i);


            // If case dosnt matter, put page text to lowercase
            if (matchCase == false){
                cPage = cPage.toLowerCase();
            }
            cPage = cPage.replace("\n", " ").replace("\r", " ");


            // Loop through all words to find
            for (String cToFind : wordList){



                if (wholeWord){
                    if (cPage.matches(".*\\b" + cToFind + "\\b.*") == false){
                        foundOnPage = false;
                    }
                }else{
                    if (cPage.contains(cToFind) == false){
                        foundOnPage = false;
                    }
                }
            }
            if (foundOnPage){
                pages.put(i + 1, this.fileText.get(i));
            }
        }
        return pages;


    }


    public static PdfFile find(String toFind){
        for (PdfFile pdf : PdfFile.allPdfs){
            if (pdf.fileName == toFind){
                return pdf;
            }
        }
        return null;
    }
}