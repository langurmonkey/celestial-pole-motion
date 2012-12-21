package astrometry;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Vector2f;

import astrometry.catalog.Catalog;
import astrometry.catalog.Star;
import astrometry.core.AstrometryCore;
import astrometry.core.Parameters;
import astrometry.graph.Cross;
import astrometry.graph.ImageBufferLine;
import astrometry.gui.GUI;

public class Main extends BasicGame {

	public static int WIDTH;
	public static int HEIGHT;
	public static float scale;
	public static final int crossdev = 5;
	NumberFormat nf = new DecimalFormat("#######0.00");
	int lastx;
	int lasty;
	private static AppGameContainer container;
	AstrometryCore ac;
	Vector2f centre;
	Catalog catalog;

	Font starname;
	Font time;

	GUI gui;

	private ImageBufferLine path;

	/**
	 * Shapes and various commons
	 */
	Cross origin;

	public Main(String title) {
		super(title);
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {

		g.setBackground(Color.black);

		Image i = path.getImage();
		i.draw(0, 0);
		i.destroy();
		float x = (float) ac.getCurrent().getData()[0];
		float y = (float) ac.getCurrent().getData()[1];

		g.setFont(starname);
		/**
		 * Draw grid
		 */
		drawGrid(g, 10);

		/**
		 * Draw star names
		 */
		g.setColor(Color.white);
		for (Star star : catalog.getStars(Parameters.getParams().hemisphere)) {
			// Cut by magnitude
			if (star.getVmag() <= 3.5) {
				g.drawString(star.getBestName(), getX(star.getX()) + 2, getY(star.getY()) + 2);
			}
		}

		/**
		 * Draw path and crosses
		 */
		int tox = getX(x);
		int toy = getY(y);
		// Draw line
		path.setLineRGBA(lastx, lasty, tox, toy, 255, 255, 255, 100);
		// Update last position
		lastx = tox;
		lasty = toy;

		g.setColor(Color.white);
		origin.draw(g);

		g.setColor(Color.yellow);
		(new Cross(centre.x + x * scale, centre.y + y * scale, crossdev)).draw(g);

		/**
		 * Write time
		 */
		g.setFont(time);
		g.setColor(Color.white);
		double years = ac.getYears();
		String cutet = nf.format(years);
		g.drawString("t = " + cutet + " years", 20, 20);
		g.drawString("Year " + ac.getCurrentYear(), 20, 40);
		g.drawString("time step = " + Parameters.getParams().getDt() + " years", 20, 60);

		/**
		 * Draw gui
		 */
		gui.render(container, g);

	}

	@Override
	public void update(GameContainer container, int t) throws SlickException {
		ac.update();
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		path = new ImageBufferLine(WIDTH, HEIGHT);

		// Initialize the translate vector
		centre = new Vector2f((float) WIDTH / 2f, (float) HEIGHT / 2f);

		// Initialize star catalog
		if (catalog == null)
			catalog = new Catalog();

		// Draw star background to the image buffer
		drawStarBackground();

		// Initialize the system core
		ac = new AstrometryCore();
		ac.init();

		// Initialize last x and y
		lastx = getX(ac.getCurrent().getData()[0]);
		lasty = getY(ac.getCurrent().getData()[1]);

		// Init cross
		origin = new Cross(centre.x, centre.y, crossdev);

		// Init fonts
		starname = new TrueTypeFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 8), true);
		time = new TrueTypeFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 13), true);

		// Init GUI
		if (gui == null) {
			gui = new GUI(container, WIDTH - GUI.width, 10);
		}
		gui.setAstrometryCore(ac);
		container.getInput().addListener(gui);
	}

	public void drawStarBackground() {
		/**
		 * Draw star background
		 */
		List<Star> stars = catalog.getStars(Parameters.getParams().hemisphere);
		for (Star star : stars) {
			int x = getX(star.getX());
			int y = getY(star.getY());
			int alpha = (int) (55 + ((-star.getVmag() + 5) / 4) * 220);
			path.setRGBA(x, y, 255, 255, 255, alpha);
		}
	}

	public void drawGrid(Graphics g, int to) {
		String sign = Parameters.getParams().hemisphere ? "+" : "-";
		for (int i = 80; i >= to; i -= 10) {
			float radius = (float) Math.cos(Math.toRadians(i));
			Circle c = new Circle(centre.x, centre.y, radius * scale);
			g.setColor(new Color(51, 102, 255, 100));
			g.draw(c);
			g.setColor(new Color(51, 102, 255, 220));
			g.drawString(sign + i, centre.x + radius * scale + 2f, centre.y);
		}
	}

	public static void main(String[] args) throws Exception {
		container = new AppGameContainer(new Main("Celestial pole vs ICRS"));
		// Initialize screen width and height
		WIDTH = (int) (container.getScreenWidth() * 5f / 6f);
		HEIGHT = (int) (container.getScreenHeight() * 5f / 6f);

		// WIDTH = WIDTH - WIDTH / 3;
		// HEIGHT = HEIGHT - HEIGHT / 3;

		scale = (Math.min(WIDTH, HEIGHT) / 2f) - 30;

		// Initialize container
		container.setDisplayMode(WIDTH, HEIGHT, false);
		container.setShowFPS(false);
		container.start();
	}

	private int getX(double x) {
		return (int) (centre.x + x * scale);
	}

	private int getY(double y) {
		return (int) (centre.y + y * scale);
	}

	@Override
	public void keyReleased(int key, char c) {
		super.keyReleased(key, c);
		if (key == Input.KEY_Q || c == 'q') {
			container.destroy();
			container.exit();
		}
	}

	@Override
	public boolean closeRequested() {
		System.out.println("Commiting properties");
		Parameters.getParams().commit();
		return super.closeRequested();
	}

}
