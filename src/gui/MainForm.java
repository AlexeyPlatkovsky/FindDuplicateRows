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

    //settings
    private JLabel settingsLabel = new JLabel("Duplicated rows:");
    private JRadioButton delete = new JRadioButton("Delete");
    private JRadioButton mark = new JRadioButton("Mark");

    //filePanel
    private JButton parseButton = new JButton("Parse text");
    private JButton chooseButton = new JButton("Choose txt file");
    private JLabel fileNameLabel = new JLabel("No file is selected");
    private JFileChooser chooseFile = new JFileChooser("Choose txt file");
    private JPanel parsePanel = new JPanel();
    private JFrame chooseFileFrame = new JFrame();
    private File fileToParse = null;
    private String duplicatedRowSettings = "";
    private String deletePunctionSetting = "";

    //deletePanel
    private JPanel radioButtonPanel = new JPanel();
    private JPanel deletePanel = new JPanel();
    private JPanel infoPanel = new JPanel();
    private JRadioButton deleteAll = new JRadioButton("All punctuation");
    private JRadioButton deleteNothing = new JRadioButton("Nothing");
    private JRadioButton deleteSome = new JRadioButton("Part of symbols");
    private JLabel deleteLabel = new JLabel("Delete punctuation:");



    public MainForm(){
        frame.setBounds(400, 400, 300, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Mark duplicated rows");
        frame.setResizable(false);
        frame.setLayout(new GridLayout(2, 2));

        frame.add(radioButtonPanel);
        frame.add(deletePanel);
        frame.add(infoPanel);
        frame.add(parsePanel);

        //deletePanel
        ButtonGroup groupDelete = new ButtonGroup();
        groupDelete.add(deleteNothing);
        groupDelete.add(deleteSome);
        groupDelete.add(deleteAll);
        deletePanel.add(deleteLabel);
        deletePanel.add(deleteNothing);
        deletePanel.add(deleteSome);
        deletePanel.add(deleteAll);
        deleteNothing.setSelected(true);
        deletePanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        //infoPanel
        infoPanel.add(fileNameLabel);

        //option panel
        radioButtonPanel.add(settingsLabel);
        ButtonGroup groupSettings = new ButtonGroup();
        groupSettings.add(delete);
        groupSettings.add(mark);
        delete.setSelected(true);
        radioButtonPanel.add(delete);
        radioButtonPanel.add(mark);
        radioButtonPanel.setBounds(0, 0, 200, 100);
        radioButtonPanel.setLayout(new GridLayout(3, 1));

        //buttons Panel
        frame.add(parsePanel);
        parsePanel.add(chooseButton);
        parsePanel.add(parseButton);
        parsePanel.setBounds(250, 250, 200, 200);
        chooseButton.addActionListener(new ChooseButtonEventListener());
        parseButton.addActionListener(new ParseButtonEventListener(groupSettings, groupDelete));
        frame.setVisible(true);
    }

    //parse Button event
    private class ParseButtonEventListener implements ActionListener {
        private final ButtonGroup groupSettings;
        private final ButtonGroup groupDelete;

        public ParseButtonEventListener(ButtonGroup groupSettings, ButtonGroup groupDelete) {
            this.groupSettings = groupSettings;
            this.groupDelete = groupDelete;
        }

        public void actionPerformed(ActionEvent e) {

            getSetting(groupSettings);
            getDeleteSetting(groupDelete);
            if (fileToParse == null)
                return;
            ParseFile file = new ParseFile(fileToParse, duplicatedRowSettings, deletePunctionSetting);

            try {
                file.getResult();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    //what punction to delete
    public void getDeleteSetting(ButtonGroup group){
        for (Enumeration<AbstractButton> buttons = group.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                deletePunctionSetting =  button.getText();
            }
        }
    }

    //what to do with duplicated rows
    public void getSetting(ButtonGroup group){
        for (Enumeration<AbstractButton> buttons = group.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                duplicatedRowSettings =  button.getText();
            }
        }
    }

    //choose button event
    private class ChooseButtonEventListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            chooseFileFrame.setVisible(true);
            chooseFileFrame.add(new JPanel().add(chooseFile));
            chooseFileFrame.setBounds(400, 400, 600, 300);
            chooseFileFrame.setResizable(false);
            chooseFileFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
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

            chooseFile.addActionListener(new ChooseFileEvent());

        }

        private class ChooseFileEvent implements ActionListener {
            public void actionPerformed(ActionEvent e) {

                if (JFileChooser.APPROVE_SELECTION.equals(e.getActionCommand())){
                    fileToParse = chooseFile.getSelectedFile();
                    fileNameLabel.setText("Selected file: " + fileToParse.getName());
                    chooseFile.setCurrentDirectory(fileToParse.getAbsoluteFile());
                    chooseFileFrame.setVisible(false);
                }

                if (JFileChooser.CANCEL_SELECTION.equals(e.getActionCommand())){
                    chooseFileFrame.setVisible(false);
                }
            }
        }
    }
}