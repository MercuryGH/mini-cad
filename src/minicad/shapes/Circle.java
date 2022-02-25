package minicad.shapes;

import java.awt.Graphics2D;
import java.awt.Point;

import minicad.Canvas;

public class Circle extends Shape {
    private Point upperLeftPoint;
    private double doubleRadius; // radius * 2 for a circle

    public Circle(Point upperLeftPoint, double width) {
        this.upperLeftPoint = upperLeftPoint;
        this.doubleRadius = width;
    }

    @Override
    public void draw(Graphics2D graphics2d) {
        super.draw(graphics2d);
        graphics2d.drawOval((int) upperLeftPoint.getX(), (int) upperLeftPoint.getY(), (int) doubleRadius,
                (int) doubleRadius);
    }

    @Override
    public void moveTo(int dx, int dy) {
        upperLeftPoint.translate(dx, dy);
    }

    @Override
    public void expand() {
        doubleRadius++;
    }

    @Override
    public void shrink() {
        doubleRadius = Math.max(doubleRadius - 1, Canvas.TOLERANCE);
    }

    @Override
    public boolean containsPoint(Point point) {
        double radius = doubleRadius / 2;
        return Math.pow(point.getX() - upperLeftPoint.getX() - radius, 2)
                + Math.pow(point.getY() - upperLeftPoint.getY() - radius, 2) <= Math.pow(radius, 2);
    }
}
