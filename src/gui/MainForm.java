package gui;

import logic.ParseFile;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: platkovskyas
 * Date: 21.12.13
 * Time: 11:53
 */
public class MainForm extends JFrame{
    private JButton parseButton = new JButton("Parse text");
    private JButton chooseButton = new JButton("Choose txt file");
    private JLabel fileNameLabel = new JLabel("Some file name");
    private JCheckBox deleteCommaCheckBox = new JCheckBox(",");
    private JCheckBox deleteDotCheckBox = new JCheckBox(".");
    private JCheckBox deleteQuoteCheckBox = new JCheckBox("'");
    private JFileChooser chooseFile = new JFileChooser("Choose txt file");
    private JPanel parse = new JPanel();
    private JFrame chooseFileFrame = new JFrame();
    private File fileToParse;

    public MainForm(){
        this.setBounds(400, 400, 550, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setName("Mark duplicated rows");
        this.setResizable(false);
        this.add(parse);
        parse.add(chooseButton);
        parse.add(parseButton);
        chooseButton.addActionListener(new ChooseButtonEventListener());
        parseButton.addActionListener(new ParseButtonEventListener());
    }

    private class ParseButtonEventListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ParseFile file = new ParseFile(fileToParse);
            try {
                file.getResult();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private class ChooseButtonEventListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            chooseFileFrame.setVisible(true);
            chooseFileFrame.add(new JPanel().add(chooseFile));
            chooseFileFrame.setBounds(400, 400, 600, 300);
            chooseFileFrame.setResizable(false);
            chooseFileFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            chooseFile.addActionListener(new ChooseFileEvent());
        }

        private class ChooseFileEvent implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (JFileChooser.CANCEL_OPTION == 1)
                    chooseFileFrame.setVisible(false);
                if (JFileChooser.OPEN_DIALOG == 0){
                    fileToParse = chooseFile.getSelectedFile();
                    chooseFileFrame.setVisible(false);
                }
            }
        }
    }
}
