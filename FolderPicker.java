import javax.swing.*;
import java.awt.event.*;
import java.awt.*;


public class FolderPicker extends JPanel
   implements ActionListener {
   JButton fSelector;

   JLabel slidesPerPageLabel = new JLabel("Slides per page:");
   JSpinner slidesPerPageSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 8, 1));

   JFileChooser chooser;
   String choosertitle;

   MainCode parentFrame;

   String chosenDir;

   static String current_menu="main";

  public FolderPicker(MainCode parentFrame) {
      setLayout(new FlowLayout());
    fSelector = new JButton("Select Folder");
    fSelector.addActionListener(this);
    add(new JLabel("Step 1:"));
    add(slidesPerPageLabel);
    add(slidesPerPageSpinner);
    add(new JLabel("Step 2:"));
    add(fSelector);
    this.parentFrame = parentFrame;
   }

  public void actionPerformed(ActionEvent e) {            
    chooser = new JFileChooser(); 
    chooser.setCurrentDirectory(new java.io.File("."));
    chooser.setDialogTitle(choosertitle);
    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    //
    // disable the "All files" option.
    //
    chooser.setAcceptAllFileFilterUsed(false);
    //    
    if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
      System.out.println("getCurrentDirectory(): " 
         +  chooser.getCurrentDirectory());
      System.out.println("getSelectedFile() : " 
         +  chooser.getSelectedFile());
         chosenDir = "" + chooser.getSelectedFile();
         parentFrame.gatherData();
      }
    else {
      System.out.println("No Selection ");
      }
     }

  public Dimension getPreferredSize(){
    return new Dimension(200, 200);
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