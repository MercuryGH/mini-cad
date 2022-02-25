package minicad.shapes;

import java.awt.Graphics2D;
import java.awt.Point;

import minicad.Canvas;

public class Line extends Shape {
    private Point p1;
    private Point p2;

    public Line(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    @Override
    public void draw(Graphics2D graphics2d) {
        super.draw(graphics2d);
        graphics2d.drawLine((int) p1.getX(), (int) p1.getY(), (int) p2.getX(), (int) p2.getY());
    }

    @Override
    public void moveTo(int dx, int dy) {
        p1.translate(dx, dy);
        p2.translate(dx, dy);
    }

    public double getGradient() {
        return (p1.getX() == p2.getX() ? Double.MAX_VALUE : (p2.getY() - p1.getY()) / (p2.getX() - p1.getX()));
    }

    public Point getHigherPoint() {
        return p1.getY() > p2.getY() ? p1 : p2;
    }

    public Point getLowerPoint() {
        return p1.getY() < p2.getY() ? p1 : p2;
    }

    public Point getRighterPoint() {
        return p1.getX() > p2.getX() ? p1 : p2;
    }

    public Point getLefterPoint() {
        return p1.getX() < p2.getX() ? p1 : p2;
    }

    @Override
    public void expand() {
        if (this.getGradient() == Double.MAX_VALUE) {
            Point higherPoint = this.getHigherPoint();
            higherPoint.setLocation(higherPoint.getX(), higherPoint.getY() + 1);
        } else {
            Point righterPoint = this.getRighterPoint();
            righterPoint.setLocation(righterPoint.getX() + 1, righterPoint.getY() + this.getGradient());
        }
    }

    @Override
    public void shrink() {
        if (p1.distance(p2) >= Canvas.TOLERANCE) { // 不能缩小得太厉害
            if (this.getGradient() == Double.MAX_VALUE) {
                Point higherPoint = this.getHigherPoint();
                higherPoint.setLocation(higherPoint.getX(), higherPoint.getY() - 1);
            } else {
                Point righterPoint = this.getRighterPoint();
                righterPoint.setLocation(righterPoint.getX() - 1, righterPoint.getY() - this.getGradient());
            }
        }
    }

    public double distFromPoint(Point point) {
        // 没有完全实现点到线段的距离公式，够用就行
        if (point.getY() <= this.getLowerPoint().getY()) {
            return point.distance(this.getLowerPoint());
        } else if (point.getY() >= this.getHigherPoint().getY()) {
            return point.distance(this.getHigherPoint());
        } else if (this.getGradient() == Double.MAX_VALUE) {
            return Math.abs(point.getX() - this.p1.getX());
        } else {
            double interception = p1.getY() - this.getGradient() * p1.getX();
            return Math.abs(this.getGradient() * point.getX() + interception - point.getY());
        }
    }

    @Override
    public boolean containsPoint(Point point) {
        return this.distFromPoint(point) <= Canvas.TOLERANCE;
    }

}
