package com.structure.app;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CoreUI {
    private JPanel panel1;
    private JButton uploadButton;
    private JTextField textField1;
    private JFileChooser fileChooser;
    private static Core core;

    private CoreUI() {
        uploadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "Text files", "txt");
                fileChooser.setFileFilter(filter);
                fileChooser.showOpenDialog(panel1);
                core.setInput(fileChooser.getSelectedFile());
                String outputFileName = textField1.getText();
                if (outputFileName.length() > 0) {
                    core.setOutputFileName(outputFileName);
                }
                core.process();
                uploadButton.setText("Done.");
            }
        });
    }

    public static void main(String[] args) {
        core = new Core();
        JFrame frame = new JFrame("CoreUI");
        frame.setContentPane(new CoreUI().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
