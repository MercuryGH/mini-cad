package minicad;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import minicad.shapes.Circle;
import minicad.shapes.Line;
import minicad.shapes.Rectangle;
import minicad.shapes.Shape;
import minicad.shapes.Text;
import minicad.toolbar.Operation;
import minicad.toolbar.Toolbar;
import minicad.utils.ShapeManager;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Canvas extends JPanel {
    private Toolbar toolbar;
    private Point mouseStart = new Point();
    private Point mouseEnd = new Point();
    private boolean selectingShape = false; // 当前是否选中了什么

    public static final int TOLERANCE = 5; // accepted mouse click delta (Human friendly)

    public Toolbar getToolbar() {
        return toolbar;
    }

    public Canvas() {
        this.toolbar = new Toolbar(this);
        this.setBackground(new Color(0xE8, 0xE8, 0xD0));  // This color should be good
        this.setFocusable(true);
        this.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (selectingShape == true) {
                    mouseStart.setLocation(mouseEnd);
                    mouseEnd.setLocation(e.getPoint());
                    Shape selectedShape = ShapeManager.findFirstSelectedShape();
                    if (selectedShape != null) {
                        selectedShape.moveTo(mouseEnd.x - mouseStart.x, mouseEnd.y - mouseStart.y);
                        repaint();  
                    }
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
            }
        });
        this.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                mouseStart.setLocation(e.getPoint());
                mouseEnd.setLocation(e.getPoint());
                selectingShape = ShapeManager.handleSelection(mouseStart);
                repaint();
                requestFocus();  // 必须重新获取焦点
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (selectingShape == false) {
                    mouseStart.setLocation(e.getPoint());
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (selectingShape == false) {
                    mouseEnd.setLocation(e.getPoint());
                    if (mouseStart.distance(mouseEnd) >= TOLERANCE) {
                        drawShape();
                    }
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (selectingShape == true) {
                    Shape selectedShape = ShapeManager.findFirstSelectedShape();
                    if (selectedShape != null) {
                        // 可能需要打开大写锁定才能生效
                        char keyChar = e.getKeyChar();
                        if (keyChar == '+' || keyChar == '=') {
                            selectedShape.expand();
                        } else if (keyChar == '-' || keyChar == '_') {
                            selectedShape.shrink();
                        } else if (keyChar == '>' || keyChar == '.') {
                            selectedShape.incWidth();
                        } else if (keyChar == '<' || keyChar == ',') {
                            selectedShape.decWidth();
                        } else if (keyChar == 'R' || keyChar == 'r') {
                            selectingShape = false;
                            ShapeManager.removeShape(selectedShape);
                        }
                        repaint(); 
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
    }

    public void drawShape() {
        Operation operation = this.toolbar.getCurSelectedOperation();
        Shape shape = null;
        switch (operation) {
        case DRAW_LINE: {
            shape = new Line(new Point(mouseStart), new Point(mouseEnd));
            break;
        }
        case DRAW_RECTANGLE: {
            double tmpX, tmpY, tmpWidth, tmpHeight;
            if (mouseStart.getX() < mouseEnd.getX()) {
                tmpX = mouseStart.getX();
                tmpWidth = mouseEnd.getX() - mouseStart.getX();
            } else {
                tmpX = mouseEnd.getX();
                tmpWidth = mouseStart.getX() - mouseEnd.getX();
            }
            if (mouseStart.getY() < mouseEnd.getY()) {
                tmpY = mouseStart.getY();
                tmpHeight = mouseEnd.getY() - mouseStart.getY();
            } else {
                tmpY = mouseEnd.getY();
                tmpHeight = mouseStart.getY() - mouseEnd.getY();
            }
            shape = new Rectangle(new Point((int) tmpX, (int) tmpY), tmpWidth, tmpHeight);
            break;
        }
        case DRAW_CIRCLE: {
            double tmpX, tmpY, tmpDoubleRadius;
            if (mouseStart.getX() < mouseEnd.getX()) {
                tmpX = mouseStart.getX();
                tmpDoubleRadius = mouseEnd.getX() - mouseStart.getX();
            } else {
                tmpX = mouseEnd.getX();
                tmpDoubleRadius = mouseStart.getX() - mouseEnd.getX();
            }
            if (mouseStart.getY() < mouseEnd.getY()) {
                tmpY = mouseStart.getY();
            } else {
                tmpY = mouseEnd.getY();
            }
            shape = new Circle(new Point((int) tmpX, (int) tmpY), tmpDoubleRadius);
            break;
        }
        case DRAW_PLAIN_TEXT: {
            shape = new Text(new Point(mouseStart.x, mouseStart.y),
                    JOptionPane.showInputDialog("Input the text you want to show: "));
            break;
        }
        case OPEN_FILE: 
            return;
        case SAVE_FILE:
            return;
        }
        ShapeManager.addShape(shape);
        this.repaint();
    }

    // 不要忘了override该函数
    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        ShapeManager.drawAll((Graphics2D) graphics);
    }
}
