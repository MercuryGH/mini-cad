package minicad.shapes;

import minicad.Canvas;

import java.awt.Point;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

public class Text extends Shape {
    private Point location;
    private String content;
    private Font font;
    private FontMetrics fontMetrics;

    public static final String FONT_NAME = "宋体";

    public Text(Point location, String content) {
        this.location = location;
        this.content = content == null ? "" : content;
        this.font = new Font(FONT_NAME, Font.BOLD, 24);
    }

    @Override
    public void draw(Graphics2D graphics2d) {
        super.draw(graphics2d);
        this.fontMetrics = graphics2d.getFontMetrics(this.font);

        Font graphicsFont = null;
        if (this.isSelected() == true) {
            graphicsFont = new Font(FONT_NAME, Font.BOLD, this.font.getSize() + 2);
        } else {
            graphicsFont = this.font;
        }
        graphics2d.setFont(graphicsFont);
        graphics2d.drawString(this.content, (float) this.location.getX(), (float) this.location.getY());
    }

    @Override
    public void moveTo(int dx, int dy) {
        location.translate(dx, dy);
    }

    @Override
    public void expand() {
        this.font = new Font(FONT_NAME, Font.BOLD, this.font.getSize() + 1);
    }

    @Override
    public void shrink() {
        this.font = new Font(FONT_NAME, Font.BOLD, Math.max(this.font.getSize() - 1, Canvas.TOLERANCE));
    }

    @Override
    public boolean containsPoint(Point point) {
        return point.getX() >= location.getX() && point.getX() <= location.getX() + fontMetrics.stringWidth(content)
                && point.getY() <= location.getY() && point.getY() >= location.getY() - fontMetrics.getHeight();
    }
}
