package packageTest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.io.IOException;
import java.util.*;



public class MainCode extends JFrame
{
    
    private MainCode thisFrame=this;
    
    static JPanel mainPanel=new JPanel(new FlowLayout());
    JPanel mainMenuPanel = new JPanel();
    
    static JButton startButton=new JButton("Start");

    FolderPicker fPicker = new FolderPicker(this);

    TextFinder findPanel = new TextFinder(this);
    java.util.List<SearchResults> resultsPanels = new ArrayList<SearchResults>();

    
    
    public static void main(String args[]){
        new MainCode();
    }
    
    public MainCode()
    {
        
        super();
        
        //anonymous listener for the back button
        startButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event){

                thisFrame.updateUi("findFolder");;
                }
        });

        mainMenuPanel.setLayout(new BoxLayout(mainMenuPanel, BoxLayout.PAGE_AXIS));
        mainMenuPanel.add(new JLabel("Hello! This is a program that lets you search for stuff :D"));
        mainMenuPanel.add(startButton);


        
        updateUi("main");
        add(mainPanel);
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(this.getPreferredSize());
        setVisible(true);
        
    }





    public Dimension getPreferredSize(){
        return new Dimension(500, 200);
    }



    public void gatherData(){

        new PdfReader((int)fPicker.slidesPerPageSpinner.getValue(), fPicker.chosenDir);

        updateUi("findText");
    }


    public void updateUi(String newMenu){



        try{
            if (newMenu == "main"){
                mainPanel.removeAll();
                mainPanel.add(mainMenuPanel);
            } else if (newMenu == "findFolder"){

                mainPanel.removeAll();
                mainPanel.add(fPicker);
                SwingUtilities.updateComponentTreeUI(thisFrame);
                SwingUtilities.updateComponentTreeUI(this.fPicker);
                thisFrame.pack();

            }else if (newMenu == "findText"){
                mainPanel.removeAll();
                mainPanel.add(this.findPanel);
                thisFrame.pack();
                SwingUtilities.updateComponentTreeUI(thisFrame);
                SwingUtilities.updateComponentTreeUI(this.findPanel);
                thisFrame.requestFocus();

            }else if (newMenu == "searchResults"){

                String sText = this.findPanel.textField.getText();
                Hashtable<String, Hashtable<Integer, String>> sResults =
                 PdfFile.searchPdfs(sText, findPanel.caseCheckBox.isSelected(),
                                    findPanel.wholeCheckBox.isSelected(),
                                    findPanel.exactCheckBox.isSelected());

                if (sResults.isEmpty()){
                    JOptionPane.showMessageDialog(this, "No results for \"" + sText + "\"" );
                }else{
                    resultsPanels.add(new SearchResults(this, sText, sResults));
                }
            }else {
                throw new IOException("Attempting to navigate to an unknown menu: " + newMenu);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }



    }
}

