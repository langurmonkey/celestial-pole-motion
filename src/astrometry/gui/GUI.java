package astrometry.gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.InputListener;
import org.newdawn.slick.ShapeFill;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.GUIContext;

import astrometry.core.AstrometryCore;
import astrometry.core.Parameters;

public class GUI extends AbstractComponent implements InputListener {

    private int x;
    private int y;
    public static int width = 250;
    public static int height = 110;

    TextButton speedUp;
    TextButton speedDown;
    TextButton restart;
    TextButton pause;
    TextButton euler;
    TextButton hemisphere;
    TextButton close;
    AstrometryCore ac;

    Rectangle contents;
    ShapeFill sf;

    public GUI(GameContainer container, int x, int y) {
	super(container);

	setLocation(x, y);
	Font buttonFont = new TrueTypeFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 14), true);
	//North and south
	speedUp = new TextButton(container, "Speed Up", getCompX(0), getCompY(0), buttonFont);
	speedDown = new TextButton(container, "Speed Down", getCompX(speedUp.getWidth() + 30), getCompY(0), buttonFont);

	pause = new TextButton(container, "Pause", getCompX(0), speedUp.getY() + speedUp.getHeight() + 10, buttonFont);
	restart = new TextButton(container, "Restart", speedDown.getX(), speedUp.getY() + speedUp.getHeight() + 10, buttonFont);

	euler = new TextButton(container, "Angle set: " + getAngleSet(), getCompX(0), pause.getY() + pause.getHeight() + 10, buttonFont);

	hemisphere = new TextButton(container, getHemisphereText(), getCompX(0), euler.getY() + euler.getHeight() + 10, buttonFont);

	close = new TextButton(container, "X", speedDown.getX() + speedDown.getWidth() + 50, getCompY(0), buttonFont);

	contents = new Rectangle(x - 5, y - 5, width, height);
	sf = new GradientFill(0, 0, new Color(10, 10, 10, 200), width, 0, new Color(60, 60, 60, 200));
    }

    @Override
    public void render(GUIContext container, Graphics g) throws SlickException {
	//Render container
	g.fill(contents, sf);

	//Render buttons
	speedUp.render(container, g);
	speedDown.render(container, g);
	pause.render(container, g);
	restart.render(container, g);
	euler.render(container, g);
	hemisphere.render(container, g);
	close.render(container, g);
    }

    @Override
    public void mouseReleased(int button, int x, int y) {
	if (speedUp.collision(x, y)) {
	    Parameters.getParams().dt += 10;
	} else if (speedDown.collision(x, y)) {
	    Parameters.getParams().dt -= 10;
	} else if (pause.collision(x, y)) {
	    if (ac.isPaused()) {
		ac.resume();
	    } else {
		ac.pause();
	    }
	} else if (restart.collision(x, y)) {
	    GameContainer gc = ((GameContainer) container);
	    try {
		gc.reinit();
	    } catch (SlickException e) {
		System.err.println(e);
	    }
	} else if (euler.collision(x, y)) {
	    Parameters.getParams().euler = !Parameters.getParams().euler;
	    euler.setText("Angle set: " + getAngleSet());
	} else if (hemisphere.collision(x, y)) {
	    Parameters.getParams().hemisphere = !Parameters.getParams().hemisphere;
	    hemisphere.setText(getHemisphereText());
	    GameContainer gc = ((GameContainer) container);
	    try {
		gc.reinit();
	    } catch (SlickException e) {
		System.err.println(e);
	    }
	} else if (close.collision(x, y)) {
	    //Close program
	    GameContainer gc = ((GameContainer) container);
	    try {
		System.out.println("Commiting parameters");
		Parameters.getParams().commit();
		gc.exit();
	    } catch (Exception e) {
		System.err.println(e);
	    }
	}
    }

    @Override
    public void setLocation(int x, int y) {
	this.x = x;
	this.y = y;

    }

    @Override
    public int getX() {
	return x;
    }

    @Override
    public int getY() {
	return y;
    }

    @Override
    public int getWidth() {
	return Math.max(pause.getWidth() + restart.getWidth(), speedUp.getWidth() + speedDown.getWidth());
    }

    @Override
    public int getHeight() {
	return Math.max(pause.getHeight() + restart.getHeight(), speedUp.getHeight() + speedDown.getHeight());
    }

    public int getCompX(int x) {
	return this.x + x;
    }

    public int getCompY(int y) {
	return this.y + y;
    }

    private String getAngleSet() {
	if (Parameters.getParams().euler) {
	    return "z, theta, zeta";
	} else {
	    return "psi, omega, epsilon, chi";
	}
    }

    private String getHemisphereText() {
	return "Hemisphere: " + getHemisphere().toUpperCase();
    }

    private String getHemisphere() {
	if (Parameters.getParams().hemisphere) {
	    return "north";
	} else {
	    return "south";
	}
    }

    public void setAstrometryCore(AstrometryCore ac) {
	this.ac = ac;
    }

}
