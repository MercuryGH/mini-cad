package minicad.toolbar;

import java.awt.Color;

import java.awt.GridLayout;
import java.awt.Font;
import java.awt.Dimension;

import java.util.EnumSet;

import javax.swing.JButton;
import javax.swing.JPanel;

import minicad.App;
import minicad.Canvas;
import minicad.shapes.Shape;
import minicad.utils.ShapeManager;

// Toolbar associate Canvas
public class Toolbar extends JPanel {
    // private Color curSelectedColor = Color.BLACK; 不实现了
    // 修改颜色会直接修改选中的那个形状的颜色
    private Operation curSelectedOperation = Operation.DRAW_LINE;
    public static final String FONT_NAME = "宋体";
    public static final int BUTTON_NUM = 7;
    public static final Color[] COLORS = { Color.BLACK, Color.BLUE, Color.CYAN, Color.DARK_GRAY, Color.GRAY,
            Color.GREEN, Color.LIGHT_GRAY, Color.PINK, Color.ORANGE, Color.RED, Color.WHITE, Color.YELLOW };

    public Toolbar(Canvas canvas) {
        this.setPreferredSize(new Dimension(App.WINDOW_SIZE_X / BUTTON_NUM, App.WINDOW_SIZE_X));
        this.setLayout(new GridLayout(BUTTON_NUM, 1, 3, 3));
        this.setBackground(canvas.getBackground());
        EnumSet<Operation> operations = EnumSet.allOf(Operation.class);
        for (Operation operation : operations) {
            JButton operationButton = new JButton(operation.toString());
            operationButton.setFont(new Font(FONT_NAME, Font.BOLD, 20));
            // register event listener
            if (operation == Operation.OPEN_FILE) {
                operationButton.setBackground(new Color(0x66, 0xCC, 0xFF));
                operationButton.addActionListener(e -> {
                    App.handleOpenFile();
                    canvas.repaint();
                });
            } else if (operation == Operation.SAVE_FILE) {
                operationButton.setBackground(new Color(0x00, 0xE3, 0xE3));
                operationButton.addActionListener(e -> {
                    App.handleSaveFile();
                });
            } else {
                operationButton.setBackground(new Color(0xD0, 0xD0, 0xD0));
                operationButton.addActionListener(e -> {
                    curSelectedOperation = operation;
                });
            }
            this.add(operationButton);
        }

        // add colorPanel
        JPanel colorPanel = new JPanel();
        colorPanel.setLayout(new GridLayout(4, 3));
        for (Color color : COLORS) {
            JButton colorButton = new JButton();
            colorButton.setBackground(color);
            colorButton.setOpaque(true);
            colorButton.setBorderPainted(false);
            colorButton.addActionListener(e -> {
                Shape selectedShape = ShapeManager.findFirstSelectedShape();
                if (selectedShape != null) {
                    selectedShape.setColor(color);
                    canvas.repaint(); 
                }
            });
            colorPanel.add(colorButton);
        }
        this.add(colorPanel);
    }

    public Operation getCurSelectedOperation() {
        return this.curSelectedOperation;
    }
}
