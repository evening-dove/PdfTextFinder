import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.awt.Color;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;

import java.util.*;

public class ContextFrame extends JFrame{

    SearchResults resultFrame;
    JScrollPane mainPanel;
    //JPanel mainPanel = new JPanel(new BorderLayout());

    JTextArea contextText;

    //JButton openPageButton = new JButton("Open as single page PDF");

    public ContextFrame(SearchResults resultFrame, String textToShow, String searchedText) {
        this.resultFrame = resultFrame;

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
        mainPanel = new JScrollPane(contextText);
        add(mainPanel);
        setSize(this.getPreferredSize());
        setVisible(true);
    }

    // Setup prefered size for frame
    public Dimension getPreferredSize(){
        return new Dimension(300, 500);
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