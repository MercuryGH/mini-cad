package minicad.shapes;

import java.awt.Graphics2D;
import java.awt.Point;

import minicad.Canvas;

/**
 * Note: x axis is downward,
 *       y axis is rightward.
 *  --------> y
 *  |
 *  |
 * \|/
 *  x
 */ 
public class Rectangle extends Shape {
    private Point upperLeftPoint;
    private double width;
    private double height;

    public Rectangle(Point bottomLeftPoint, double width, double height) {
        this.upperLeftPoint = bottomLeftPoint;
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(Graphics2D graphics2d) {
        super.draw(graphics2d);
        graphics2d.drawRect((int) upperLeftPoint.getX(), (int) upperLeftPoint.getY(), (int) width, (int) height);
    }

    @Override
    public void moveTo(int dx, int dy) {
        upperLeftPoint.translate(dx, dy);
    }

    @Override
    public void expand() {
        height += height / width;
        width++;
    }

    @Override
    public void shrink() {
        height = Math.max(height - height / width, Canvas.TOLERANCE);
        width = Math.max(width - 1, Canvas.TOLERANCE);
    }

    @Override
    public boolean containsPoint(Point point) {
        return point.getX() >= upperLeftPoint.getX() && point.getX() <= upperLeftPoint.getX() + width
                && point.getY() >= upperLeftPoint.getY() && point.getY() <= upperLeftPoint.getY() + height;
    }

}
