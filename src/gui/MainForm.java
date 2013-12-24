package gui;

import logic.ParseFile;
import sources.Cons;
import sources.Localization;

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
    private JLabel settingsLabel = new JLabel();
    private JRadioButton delete = new JRadioButton();
    private JRadioButton mark = new JRadioButton();

    //filePanel
    private JButton parseButton = new JButton();
    private JButton chooseButton = new JButton();
    private JLabel fileNameLabel = new JLabel();
    private JFileChooser chooseFile = new JFileChooser();
    private JPanel buttonsPanel = new JPanel();
    private JFrame chooseFileFrame = new JFrame();
    private File fileToParse = null;
    private String duplicatedRowSettings = "";
    private String deletePunctuationSetting = "";

    //punctuationOptionalsPanel
    private JPanel markOptionsPanel = new JPanel();
    private JPanel punctuationOptionalsPanel = new JPanel();
    private JPanel infoPanel = new JPanel();
    private JRadioButton deleteAll = new JRadioButton();
    private JRadioButton deleteNothing = new JRadioButton();
    private JRadioButton deleteSome = new JRadioButton("");
    private JLabel deleteLabel = new JLabel();
    private String fileFilter;
    private String selectedFile;

    public void doLocalization() throws IOException {
        Localization.loadProperties(new File("sources/eng.properties"));
        settingsLabel.setText(Localization.settingsLabel);
        delete.setText(Localization.delete);
        mark.setText(Localization.mark);
        parseButton.setText(Localization.parseButton);
        chooseButton.setText(Localization.chooseButton);
        fileNameLabel.setText(Localization.fileNameLabel);
        chooseFile.setName(Localization.chooseFile);
        deleteAll.setText(Localization.deleteAll);
        deleteNothing.setText(Localization.deleteNothing);
        deleteSome.setText(Localization.deleteSome);
        deleteLabel.setText(Localization.deleteLabel);
        fileFilter = Localization.fileFilter;
        selectedFile = Localization.selectedFile;
    }

    public MainForm() throws IOException {
        doLocalization();

        frame.setBounds(Cons.frameBounds.get(0), Cons.frameBounds.get(1), Cons.frameBounds.get(2), Cons.frameBounds.get(3));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle(Localization.frame);
        frame.setResizable(false);
        frame.setLayout(null);

        frame.add(markOptionsPanel);
        frame.add(punctuationOptionalsPanel);
        frame.add(infoPanel);
        frame.add(buttonsPanel);

        //option panel
        ButtonGroup duplicatedRowsSettings = new ButtonGroup();
        duplicatedRowsSettings.add(delete);
        duplicatedRowsSettings.add(mark);
        delete.setSelected(true);
        markOptionsPanel.add(settingsLabel);
        markOptionsPanel.add(delete);
        markOptionsPanel.add(mark);
        markOptionsPanel.setBounds(Cons.makrOptionsPanelBounds.get(0), Cons.makrOptionsPanelBounds.get(1), Cons.makrOptionsPanelBounds.get(2), Cons.makrOptionsPanelBounds.get(3));
        markOptionsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        //punctuationOptionalsPanel
        ButtonGroup punctuationGroup = new ButtonGroup();
        punctuationGroup.add(deleteNothing);
        punctuationGroup.add(deleteSome);
        punctuationGroup.add(deleteAll);
        punctuationOptionalsPanel.setBounds(150, 0, 150, 150);
        punctuationOptionalsPanel.add(deleteLabel);
        punctuationOptionalsPanel.add(deleteNothing);
        punctuationOptionalsPanel.add(deleteSome);
        punctuationOptionalsPanel.add(deleteAll);
        deleteNothing.setSelected(true);
        punctuationOptionalsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        //infoPanel
        infoPanel.add(fileNameLabel);
        infoPanel.setBounds(0, 150, 150, 150);

        //buttons Panel
        frame.add(buttonsPanel);
        buttonsPanel.add(chooseButton);
        buttonsPanel.add(parseButton);
        buttonsPanel.setBounds(150, 150, 150, 150);
        chooseButton.addActionListener(new ChooseButtonEventListener());
        parseButton.addActionListener(new ParseButtonEventListener(duplicatedRowsSettings, punctuationGroup));

        frame.setVisible(true);
    }

    //parse Button event
    private class ParseButtonEventListener implements ActionListener {
        private final ButtonGroup rowSettings;
        private final ButtonGroup punctuationSettings;

        public ParseButtonEventListener(ButtonGroup rowSettings, ButtonGroup punctuationSettings) {
            this.rowSettings = rowSettings;
            this.punctuationSettings = punctuationSettings;
        }

        public void actionPerformed(ActionEvent e) {

            duplicatedRowSettings = getRadioButtonSettings(rowSettings);
            deletePunctuationSetting = getRadioButtonSettings(punctuationSettings);


            if (fileToParse == null)
                return;
            ParseFile file = new ParseFile(fileToParse, duplicatedRowSettings, deletePunctuationSetting);

            try {
                file.doParse();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    //what punctuation to delete
    public String getRadioButtonSettings(ButtonGroup group){
        for (Enumeration<AbstractButton> buttons = group.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                return button.getText();
            }
        }
        return "";
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
                    return fileFilter;
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
                    fileNameLabel.setText(selectedFile + " " + fileToParse.getName());
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