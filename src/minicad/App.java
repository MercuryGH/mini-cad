package minicad;

import java.io.File;
import java.io.IOException;
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;

import minicad.utils.IOHandler;

public class App {
    public static final int WINDOW_SIZE_X = 1024;
    public static final int WINDOW_SIZE_Y = 768;
    public static final String TITLE = "MiniCAD";
    public static final String DEFAULT_FILENAME = "untitled.cad";

    private static JFrame appFrame = new JFrame(TITLE);

    public static void run() {
        Canvas canvas = new Canvas();
        appFrame.setSize(WINDOW_SIZE_X, WINDOW_SIZE_Y);
        appFrame.setResizable(false);
        appFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        appFrame.setLayout(new BorderLayout());
        appFrame.add(canvas, BorderLayout.CENTER);
        appFrame.add(canvas.getToolbar(), BorderLayout.EAST);
        appFrame.setLocationRelativeTo(null);
        appFrame.setVisible(true);
    }

    public static void handleOpenFile() {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("CAD files (*.cad)", "cad");
        chooser.setFileFilter(filter);
        chooser.setDialogTitle("Open a .cad File");

        int value = chooser.showOpenDialog(appFrame);
        if (value == JFileChooser.APPROVE_OPTION) {
            File openFile = chooser.getSelectedFile();
            if (openFile.exists() == true) {
                String path = openFile.getAbsolutePath();
                IOHandler.openFile(path);
            } else {
                JOptionPane.showMessageDialog(null, "Cannot open file. File name not exist!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void handleSaveFile() {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("CAD files (*.cad)", "cad");
        chooser.setFileFilter(filter);
        chooser.setDialogTitle("Save Current File");
        chooser.setSelectedFile(new File(DEFAULT_FILENAME));

        int value = chooser.showSaveDialog(appFrame);
        if (value == JFileChooser.APPROVE_OPTION) {
            File newFile = chooser.getSelectedFile();
            if (newFile.exists() == false) {
                try {
                    newFile.createNewFile();
                    String path = newFile.getAbsolutePath();
                    IOHandler.saveFile(path);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Cannot save current file. File name already exist!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        App.run();
    }
}
