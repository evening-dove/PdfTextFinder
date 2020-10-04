// The frame where search reults are shown

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

import java.util.*;
import java.io.File;
import java.io.IOException;


public class SearchResults extends JFrame{

   MainCode parentFrame;

   JPanel mainPanel=new JPanel(new BorderLayout());

   java.util.List<ContextFrame> cFrames = new ArrayList<ContextFrame>();

   SearchResults thisFrame;


    // COnstructor for frame that shows all results
  public SearchResults(MainCode parentFrame, String searchedText, Hashtable<String, Hashtable<Integer, String>> results) {
    this.parentFrame = parentFrame;
    thisFrame = this;

    buildLayout(searchedText, results);
    add(mainPanel);
    
    setSize(this.getPreferredSize(results.size()));
    setVisible(true);
   }

    // Build the layout for this frame
    public void buildLayout(String searchedText, Hashtable<String, Hashtable<Integer, String>> results){

        // Place text explaining results
        JLabel searchText = new JLabel("Results for: " + searchedText);
        mainPanel.add(searchText, BorderLayout.PAGE_START);

        // Place section showing results
        
        JScrollPane resultsScrollable;
        JPanel resultsStack = new JPanel();
        resultsStack.setLayout(new BoxLayout(resultsStack, BoxLayout.PAGE_AXIS));
        buildResultsPanel(resultsStack, results, searchedText);
        resultsScrollable = new JScrollPane(resultsStack);
        mainPanel.add(resultsScrollable, BorderLayout.CENTER);
   }

   // Build the section that will house results
   public void buildResultsPanel(JPanel resultsStack, Hashtable<String, Hashtable<Integer, String>> results, String searchedText){

    // Loop though all files with reults
    for (String fileName: new TreeSet<String>(results.keySet())){

        // Create and add the line for file with results
        JPanel resultFullLine = new JPanel(new BorderLayout());
        resultFullLine.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));
        JButton fileButton = new JButton(fileName);

        fileButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event){
                if (Desktop.isDesktopSupported()) {
                    try {
                        File myFile = new File(parentFrame.fPicker.chosenDir + "/" + fileName);
                        Desktop.getDesktop().open(myFile);
                    } catch (IOException ex) {
                        // no application registered for PDFs
                    }
                }
            }
        });

        JPanel tmpPanel = new JPanel();
        tmpPanel.add(fileButton);
        resultFullLine.add(tmpPanel, BorderLayout.LINE_START);

        GridLayout pagesLayout = new GridLayout(0, 10, 10, 10);
        JPanel resultPages = new JPanel(pagesLayout);

        // loop though all slides with results
        for (Integer pageNum: new TreeSet<Integer>(results.get(fileName).keySet())){
            // Create and add the open slide button
            JButton pageButton = new JButton("Slide " + pageNum);
            // Add action listener so clicking on a slide will open its page text
            pageButton.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent event){
                    cFrames.add(new ContextFrame(thisFrame,  results.get(fileName).get(pageNum), searchedText));
                    }
            });
            resultPages.add(pageButton);
        }
        resultFullLine.add(resultPages, BorderLayout.LINE_END);
        resultsStack.add(resultFullLine);
    }
   }

    // Setup prefered size for frame
    public Dimension getPreferredSize(int resultCount){
        return new Dimension(600, 10 + 120 * resultCount);
    }

    /*
  public static void main(String s[]) {
    JFrame frame = new JFrame("");
    FolderPicker panel = new FolderPicker(frame);
    frame.addWindowListener(
      new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
          System.exit(0);
          }
        }
      );
    frame.getContentPane().add(panel,"Center");
    frame.setSize(panel.getPreferredSize());
    frame.setVisible(true);
    }
    */
}