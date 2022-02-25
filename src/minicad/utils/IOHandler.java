package minicad.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JOptionPane;
import minicad.shapes.Shape;
import java.util.ArrayList;

public class IOHandler {
    @SuppressWarnings("unchecked")
    public static void openFile(String path) {
        ObjectInputStream in;
        try {
            in = new ObjectInputStream(new FileInputStream(path));
            // 泛型erasure的后果
            ArrayList<Shape> readShapes = (ArrayList<Shape>) in.readObject();

            ShapeManager.setShapes(readShapes);
            in.close();
            JOptionPane.showMessageDialog(null, "Successfully open file", "OK", JOptionPane.INFORMATION_MESSAGE);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Fine Not Found!", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Unknown IO Exception!", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Class Not Found Exception!", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void saveFile(String path) {
        ObjectOutputStream out;
        try {
            out = new ObjectOutputStream(new FileOutputStream(path));
            out.writeObject(ShapeManager.getShapes());
            out.close();
            JOptionPane.showMessageDialog(null, "Successfully save file", "OK", JOptionPane.INFORMATION_MESSAGE);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Fine Not Found!", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Unknown IO Exception!", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

}
