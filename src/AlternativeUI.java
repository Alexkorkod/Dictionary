import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import org.apache.logging.log4j.Logger;

//TODO make word wrap
//TODO make documentation
public class AlternativeUI {
    private JButton generateAwesomenessButton;
    private JButton uploadRawInputForButton;
    private JButton exitButton;
    private JPanel mailPanel;
    private JTextField theme;
    private JTextField sentencesNumber;
    private JFileChooser fileChooser;
    private Master masterInstance = Master.getInstance();
    private Logger logger = masterInstance.getLogger();
    private int numOfPhrases = 1;

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
        generateAwesomenessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                String numOsfPhrasesStr = sentencesNumber.getText();
                String genResult;
                File themeDir = new File(theme.getText());
                JFrame frame = new JFrame("OutputUI");
                OutputUI gui = new OutputUI();
                try {
                    numOfPhrases = Integer.parseInt(numOsfPhrasesStr);
                } catch (NumberFormatException nfex) {
                    String errNotify = "Number of phrases is not number at all! Generating one string. ";
                    gui.textArea1.append(errNotify + "\n");
                    logger.warn( errNotify + "Input: " + numOsfPhrasesStr);
                }
                frame.setContentPane(gui.outputPanel);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.getContentPane().setMaximumSize(screenSize);
                masterInstance.setThemeDir(themeDir);
                masterInstance.setFiller();
                for (int i = 0; i < numOfPhrases ; i++) {
                    genResult = masterInstance.getMadeUpText();
                    if (genResult.equals(masterInstance.STOP)) {
                        logger.warn("Error occurred in master during text creation.");
                        return;
                    }
                    gui.textArea1.append(genResult + "\n");
                }
                frame.pack();
                frame.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("AlternativeUI");
        frame.setContentPane(new AlternativeUI().mailPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
