import sun.awt.X11.Screen;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Ganitsky
 * Date: 14.11.12
 * Time: 23:12
 */
class InputUi extends JFrame {
    private int crunch = 0;
    private File themeDir;
    private int numOfPhrases;

    private JLabel label;
    private JComboBox<String> choice;
    private JTextField numberOfPhrasesEditor = new JTextField("5", 8);
    private JFileChooser fileChooser;
    private JTextArea output;
    private Container container;

    InputUi() {
        super("Random-o-Phrase");
        this.setBounds(300, 300, 600, 430);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        container = this.getContentPane();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        label = new JLabel("Выберите тему");
        container.add(label);

        String[] items = {
                "Physics",
                "Classic",
                "Philosophy",
                "Religion",
                "Math",
        };
        choice = new JComboBox<String>(items);
        //TODO make it better
        choice.setSelectedItem(items[0]);
        choice.setMaximumSize(new Dimension(500, 20));
        choice.addActionListener(new ComboBoxEventListener());
        container.add(choice);

        fileChooser = new JFileChooser();
        fileChooser.setMaximumSize(new Dimension(600, 300));
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Text files", "txt");
        fileChooser.setFileFilter(filter);
        fileChooser.addActionListener(new FileUploaderEventListener());
        container.add(fileChooser);

        JButton button = new JButton("Apply");
        button.addActionListener(new ButtonEventListener());
        container.add(button);

        output = new JTextArea();
        output.setAutoscrolls(true);
    }

    class FileUploaderEventListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (actionEvent.getActionCommand().equals("ApproveSelection")) {
                File input = fileChooser.getSelectedFile();
                Core.getMaster().setFiller(new Filler(input));
            }
            container.remove(fileChooser);
        }
    }

    class ComboBoxEventListener implements ActionListener{
        public void actionPerformed(ActionEvent event){
            String localCont = (String) choice.getSelectedItem();
            File tmpThemeDir = new File(localCont);
            if (tmpThemeDir.exists() && tmpThemeDir.isDirectory()) {
                themeDir = tmpThemeDir;
            } else {
                //TODO error handling and default dir(?)
            }
        }
    }

    class ButtonEventListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            switch (crunch) {
                case 0: {
                    drawSecondScreen();
                    crunch++;
                    break;
                }
                case 1: {
                    numOfPhrases = Integer.parseInt(numberOfPhrasesEditor.getText());
                    try {
                        drawLastScreen();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    crunch++;
                    break;

                }
                case 2:{

                }
                default: {
                    break;
                }
            }

        }
    }


    private void drawSecondScreen() {
        label.setText("Введите кол-во фраз в выводе");
        JButton button = new JButton("Apply");
        button.addActionListener(new ButtonEventListener());
        this.setBounds(300, 300, 250, 100);
        container.removeAll();
        container.add(label);
        container.add(numberOfPhrasesEditor);
        container.add(button);
    }

    private void drawLastScreen() throws IOException {
        container.removeAll();
        if (themeDir == null) {
            themeDir = new File("List");
        }
        for (int i =0; i < numOfPhrases ; i++){
            output.append(Core.getMaster().getRawOutput(themeDir, numOfPhrases)+"\n");
        }
        this.setBounds(300,300,500,25 + numOfPhrases*20);
        container.add(output);
    }

}