package minicad.utils;

import java.util.ArrayList;

import minicad.shapes.Shape;

import java.awt.Point;
import java.awt.Graphics2D;
import minicad.shapes.Line;

public class ShapeManager {
    private static ArrayList<Shape> shapes = new ArrayList<>();

    public static int size() {
        return shapes.size();
    }

    public static ArrayList<Shape> getShapes() {
        return shapes;
    }

    public static void setShapes(ArrayList<Shape> readShapes) {
        shapes = readShapes;
    }

    public static void addShape(Shape shape) {
        shapes.add(shape);
    }

    public static void removeShape(Shape shape) {
        shapes.remove(shape);
    }

    public static void drawAll(Graphics2D graphics2D) {
        shapes.forEach(shape -> shape.draw(graphics2D));
    }

    public static Shape findFirstSelectedShape() {
        for (Shape shape : shapes) {
            if (shape.isSelected()) {
                return shape;
            }
        }
        return null;
    }

    public static boolean handleSelection(Point mousePoint) {
        shapes.forEach(shape -> shape.setSelected(false));
        for (Shape shape : shapes) {
            if (shape.containsPoint(mousePoint)) {
                shape.setSelected(true);
                return true;
            }
        }
        return false;
    }

    // Debug purpose only
    public static void printAllLines() {
        shapes.forEach(shape -> {
            if (shape instanceof Line) {
                Line line = (Line) shape;
                System.out.println("ShapeManager: " + line.isSelected());
            }
        });
        System.out.println();
    }
}
