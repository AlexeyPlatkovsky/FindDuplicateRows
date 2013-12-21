package gui;

import logic.ParseFile;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;

/**
 * Created with IntelliJ IDEA.
 * User: platkovskyas
 * Date: 21.12.13
 * Time: 11:53
 */
public class MainForm{
    private JFrame frame = new JFrame();
    private JButton parseButton = new JButton("Parse text");
    private JButton chooseButton = new JButton("Choose txt file");
    private JLabel fileNameLabel = new JLabel("No file is selected");
    private JFileChooser chooseFile = new JFileChooser("Choose txt file");
    private JPanel parsePanel = new JPanel();
    private JFrame chooseFileFrame = new JFrame();
    private File fileToParse;
    private JRadioButton delete = new JRadioButton("Delete");
    private JRadioButton mark = new JRadioButton("Mark");
    private JPanel radioButtonPanel = new JPanel();
    private JPanel panel2 = new JPanel();
    private JPanel infoPanel = new JPanel();

    public MainForm(){
        frame.setBounds(400, 400, 300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Mark duplicated rows");
        frame.setResizable(false);
        frame.setLayout(new GridLayout(2, 2));
        frame.add(radioButtonPanel);
        frame.add(panel2);
        frame.add(infoPanel);
        frame.add(parsePanel);

        //infoPanel

        infoPanel.add(fileNameLabel);

        //option panel
        ButtonGroup group = new ButtonGroup();
        group.add(delete);
        group.add(mark);
        delete.setSelected(true);
        radioButtonPanel.add(delete);
        radioButtonPanel.add(mark);
        radioButtonPanel.setBounds(0, 0, 200, 100);
        radioButtonPanel.setLayout(new GridLayout(2, 1));

        //buttons Panel
        frame.add(parsePanel);
        parsePanel.add(chooseButton);
        parsePanel.add(parseButton);
        parsePanel.setBounds(250, 250, 200, 200);
        chooseButton.addActionListener(new ChooseButtonEventListener());
        parseButton.addActionListener(new ParseButtonEventListener(group));
        frame.setVisible(true);
    }

    private class ParseButtonEventListener implements ActionListener {
        String setting = "";
        Enumeration<AbstractButton> elements;

        public ParseButtonEventListener(ButtonGroup group) {
            elements = group.getElements();
        }

        public void actionPerformed(ActionEvent e) {
            for (Enumeration<AbstractButton> buttons = elements; buttons.hasMoreElements();) {
                AbstractButton button = buttons.nextElement();

                if (button.isSelected()) {
                    setting =  button.getText();
                }
            }

            ParseFile file = new ParseFile(fileToParse, setting);

            try {
                file.getResult();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private class ChooseButtonEventListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            chooseFileFrame.setVisible(true);
            chooseFileFrame.add(new JPanel().add(chooseFile));
            chooseFileFrame.setBounds(400, 400, 600, 300);
            chooseFileFrame.setResizable(false);
            chooseFileFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            chooseFile.addActionListener(new ChooseFileEvent());
            FileFilter ff = new FileFilter() {
                public boolean accept(File f) {
                    if (f.getName().toLowerCase().endsWith(".txt") || f.isDirectory())
                        return true;
                    return false;
                }
                public String getDescription() {
                    return "Only TXT files";
                }
            };
            chooseFile.setFileFilter(ff);
            chooseFile.setAcceptAllFileFilterUsed(false);
        }

        private class ChooseFileEvent implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                if (JFileChooser.CANCEL_OPTION == 1){
                    chooseFileFrame.setVisible(false);
                    return;
                }
                if (JFileChooser.OPEN_DIALOG == 0){
                    fileToParse = chooseFile.getSelectedFile();
                    fileNameLabel.setText("Selected file: " + fileToParse.getName());
                    chooseFile.setCurrentDirectory(fileToParse.getAbsoluteFile());
                    chooseFileFrame.setVisible(false);
                }
            }
        }
    }
}