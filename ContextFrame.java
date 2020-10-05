import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.awt.Color;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;

import java.util.*;

import java.io.File;
import org.apache.pdfbox.pdmodel.PDDocument;
import java.io.IOException;

public class ContextFrame extends JFrame{

    SearchResults resultFrame;
    JPanel mainPanel = new JPanel(new BorderLayout());

    JTextArea contextText;

    JLabel searchInfoLabel;
    JLabel searchPageLabel;
    JButton openPageButton = new JButton("Open as single page PDF");

    public ContextFrame(SearchResults resultFrame, 
                        String textToShow, 
                        String searchedText, 
                        int pageNum,
                        String fileName) {

        this.resultFrame = resultFrame;

        searchInfoLabel = new JLabel("Results for: \"" + searchedText + "\"");
        searchPageLabel = new JLabel("Found on Page: " + (pageNum + 1));




        //anonymous listener for the open page button
        openPageButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event){
                try{
                    System.out.println(resultFrame.parentFrame.fPicker.chosenDir + "/" + fileName);
                    System.out.println(System.getProperty("java.io.tmpdir") + 
                    "Page" + 
                    pageNum + 
                    fileName);



                    File file = new File(resultFrame.parentFrame.fPicker.chosenDir + "/" +
                        fileName);
                    File outFile = new File(System.getProperty("java.io.tmpdir") + 
                                            "Page" + 
                                            pageNum + 
                                            fileName);
                    System.out.println(System.getProperty("java.io.tmpdir"));
                    PDDocument originalPdf = PDDocument.load(file);
                    PDDocument singlePagePdf = new PDDocument();
                    singlePagePdf.addPage(originalPdf.getPage(pageNum));
                    singlePagePdf.save(outFile);
                    singlePagePdf.close();
                    Desktop.getDesktop().open(outFile);
                    
                }
                catch(IOException e){
                    //System.out.println(e);
                }
            }
        });






        // Create and setup text area for slide text
        contextText = new JTextArea(textToShow);
        contextText.setLineWrap(true);
        // highlight text
        try{
            highlightText(searchedText, textToShow);
        }
        catch (BadLocationException e){
        }

        // Setup main UI elements
        JScrollPane scrollPanel = new JScrollPane(contextText);

        JPanel searchInfoPanel = new JPanel(new FlowLayout());
        JPanel pageLocationPanel = new JPanel();
        pageLocationPanel.setLayout(new BoxLayout(pageLocationPanel, BoxLayout.PAGE_AXIS));
        
        searchInfoPanel.add(searchInfoLabel);
        pageLocationPanel.add(searchPageLabel);
        pageLocationPanel.add(openPageButton);
        searchInfoPanel.add(pageLocationPanel);

        mainPanel.add(searchInfoPanel, BorderLayout.PAGE_START);
        mainPanel.add(scrollPanel, BorderLayout.CENTER);
        add(mainPanel);
        setSize(this.getPreferredSize());
        setVisible(true);
    }

    // Setup prefered size for frame
    public Dimension getPreferredSize(){
        return new Dimension(600, 500);
    }

    // Highlight text that was searched for
    public void highlightText(String searchedText, String textToShow) throws BadLocationException{

        // If match case was not selected, be sure to highlight all cases
        if (resultFrame.parentFrame.findPanel.caseCheckBox.isSelected() == false){
            searchedText = searchedText.toLowerCase();
            textToShow = textToShow.toLowerCase();
        }


        // If exact phrase was not selected, be sure to highlight all seperate words
        java.util.List<String> allSearchWords = new ArrayList<String>();
        if (resultFrame.parentFrame.findPanel.exactCheckBox.isSelected()){
            allSearchWords.add(searchedText);
        }else{
            allSearchWords = Arrays.asList(searchedText.split(" "));
        }

        // Setup highlighter
        Highlighter highlighter = contextText.getHighlighter();
        HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);

        // Find all searched for text
        int textLen;
        int textIndex;
        int textEnd;
        for (String cSearchText: allSearchWords){
            textLen = cSearchText.length();
            textIndex = 0;
            while (textToShow.substring(textIndex).contains(cSearchText)){
                textIndex += textToShow.substring(textIndex).indexOf(cSearchText);
                textEnd = textIndex + textLen;
                // highlight the text
                highlighter.addHighlight(textIndex, textEnd, painter);
                textIndex = textEnd;
            }
        }
    }

}