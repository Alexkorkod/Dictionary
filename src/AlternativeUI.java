import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

//TODO make word wrap
//TODO deal with default values
//TODO make documentation
public class AlternativeUI {
    private JButton generateAwesomnessButton;
    private JButton uploadRawInputForButton;
    private JButton exitButton;
    private JPanel mailPanel;
    private JTextField theme;
    private JTextField sentencesNumber;
    private JFileChooser fileChooser;
    private Master masterInstance = Master.getInstance();

    private AlternativeUI() {
        uploadRawInputForButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "Text files", "txt");
                fileChooser.setFileFilter(filter);
                fileChooser.showOpenDialog(mailPanel);
                File input = fileChooser.getSelectedFile();
                masterInstance.setFiller(new Filler(input));
            }
        });
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
            }
        });
        generateAwesomnessButton.addActionListener(new ActionListener() {
            //TODO input validation
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                int numOfPhrases = Integer.parseInt(sentencesNumber.getText());
                File themeDir = new File(theme.getText());
                JFrame frame = new JFrame("OutputUI");
                OutputUI gui = new OutputUI();
                frame.setContentPane(gui.outputPanel);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.getContentPane().setMaximumSize(screenSize);
                for (int i =0; i < numOfPhrases ; i++) {
                    gui.textArea1.append(masterInstance.getRawOutput(themeDir, numOfPhrases) + "\n");
                }
                frame.pack();
                frame.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("AlternativeUI");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        frame.setContentPane(new AlternativeUI().mailPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
