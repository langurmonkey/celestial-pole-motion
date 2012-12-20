package astrometry.graph;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Line;

public class Cross {
    Line l1;
    Line l2;

    /**
     * Constructs a cross with the center at x,y and a deviation of size
     * @param x - The x of the center
     * @param y - The y of the center
     * @param size
     */
    public Cross(float x, float y, float size) {
	l1 = new Line(x - size, y - size, x + size, y + size);
	l2 = new Line(x - size, y + size, x + size, y - size);
    }

    public void draw(Graphics g) {
	g.draw(l1);
	g.draw(l2);
    }

}
