package astrometry.graph;

import org.newdawn.slick.ImageBuffer;
import org.newdawn.slick.geom.Vector2f;

public class ImageBufferLine extends ImageBuffer {

    public ImageBufferLine(int width, int height) {
	super(width, height);
    }

    public void setLineRGBA(float ax, float ay, float bx, float by, int r, int g, int b, int a) {
	if ((int) ax == (int) bx && (int) ay == (int) by) {
	    try {
		setRGBA((int) bx, (int) by, r, g, b, a);
	    } catch (RuntimeException e) {
	    }
	} else {
	    //Unit vector from a to b
	    Vector2f v = new Vector2f(bx - ax, by - ay);
	    v.normalise();

	    Vector2f curr = new Vector2f(ax, ay);
	    while (curr.x != bx || curr.y != by) {
		try {
		    setRGBA((int) curr.x, (int) curr.y, r, g, b, a);
		} catch (RuntimeException e) {
		}
		Vector2f left = new Vector2f(bx - curr.x, by - curr.y);
		if (left.length() <= 1)
		    curr = new Vector2f(bx, by);
		else
		    curr.add(v);
	    }

	}
    }
}
