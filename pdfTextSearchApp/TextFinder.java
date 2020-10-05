package pdfTextSearchApp;
/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
 
//package layout;
 
import java.awt.Component;
import javax.swing.*;
import static javax.swing.GroupLayout.Alignment.*;

import java.util.*;



import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
 
public class TextFinder extends JPanel {
    JButton findButton;
    JButton cancelButton;
    JTextField textField;

    MainCode parentFrame;

    JCheckBox caseCheckBox;
    JCheckBox wholeCheckBox;
    JCheckBox exactCheckBox;

    public TextFinder(MainCode parentFrame) {
        super();

        this.parentFrame = parentFrame;
        JLabel label = new JLabel("Find What:");;
        this.findButton = new JButton("Find");
        this.cancelButton = new JButton("Cancel");
        textField = new JTextField();
        caseCheckBox = new JCheckBox("Match Case");
        wholeCheckBox = new JCheckBox("Whole Words");
        exactCheckBox = new JCheckBox("Exact Phrase");
 
        // remove redundant default border of check boxes - they would hinder
        // correct spacing and aligning (maybe not needed on some look and feels)
        caseCheckBox.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        wholeCheckBox.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        exactCheckBox.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
 
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
 
        layout.setHorizontalGroup(layout.createSequentialGroup()
            .addComponent(label)
            .addGroup(layout.createParallelGroup(LEADING)
                .addComponent(textField)
                .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(LEADING)
                        .addComponent(caseCheckBox)
                        .addComponent(wholeCheckBox))
                    .addGroup(layout.createParallelGroup(LEADING)
                        .addComponent(exactCheckBox))))
            .addGroup(layout.createParallelGroup(LEADING)
                .addComponent(findButton)
                .addComponent(cancelButton))
        );
        
        layout.linkSize(SwingConstants.HORIZONTAL, findButton, cancelButton);
 
        layout.setVerticalGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(BASELINE)
                .addComponent(label)
                .addComponent(textField)
                .addComponent(findButton))
            .addGroup(layout.createParallelGroup(LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(caseCheckBox)
                        .addComponent(exactCheckBox))
                    .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(wholeCheckBox)))
                .addComponent(cancelButton))
        );

        
        //anonymous listener for the find button
        this.findButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event){
                parentFrame.updateUi("searchResults");
            }
        }
        );
        
        //anonymous listener for the cancel button
        this.cancelButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event){
                parentFrame.updateUi("findFolder");
            }
        }
        );


 
    }
     
    /*
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(
                                  "javax.swing.plaf.metal.MetalLookAndFeel");
                                //  "com.sun.java.swing.plaf.motif.MotifLookAndFeel");
                                //UIManager.getCrossPlatformLookAndFeelClassName());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                new Find().setVisible(true);
            }
        });
    }
    */
}