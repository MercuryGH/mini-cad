package minicad.shapes;

import java.awt.Point;
import java.awt.Color;
import java.awt.Stroke;
import java.io.Serializable;
import java.awt.BasicStroke;
import java.awt.Graphics2D;

public abstract class Shape implements Serializable {
    private Color color = Color.BLACK;
    private float strokeWidth = 1.0f; // 轮廓厚度（API require float）
    private transient boolean selected = false;  // 串行化时不用存selected

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void draw(Graphics2D graphics2d) {
        graphics2d.setColor(this.color);
        Stroke stroke = null;
        if (this.selected == true) {
            stroke = new BasicStroke(this.strokeWidth + 1.0f); // highlight selected
        } else { 
            stroke = new BasicStroke(this.strokeWidth);
        }
        graphics2d.setStroke(stroke);
    }

    public void incWidth() {
        this.strokeWidth++;
    }

    public void decWidth() {
        this.strokeWidth = Math.max(this.strokeWidth - 1.0f, 1.0f);
    }

    public abstract void moveTo(int dx, int dy);

    public abstract void expand();

    public abstract void shrink();

    public abstract boolean containsPoint(Point point);
}
