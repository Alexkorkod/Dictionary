package com.structure.app;

import ru.stachek66.nlp.mystem.model.Info;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CoreUI {
    private JPanel panel1;
    private JButton uploadButton;
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
                ArrayList<Iterable<Info>> resultList = core.process();
                BufferedWriter bw = null;
                FileWriter fw = null;
                try {
                    fw = new FileWriter("output.txt");
                    bw = new BufferedWriter(fw);
                    for (Iterable<Info> item : resultList) {
                        for (Info info : item) {
                            String content = info.rawResponse();
                            bw.write(content);
                        }
                        bw.write("\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (bw != null) {
                            bw.close();
                        }
                        if (fw != null) {
                            fw.close();
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    uploadButton.setText("Done.");
                }
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
