package astrometry.gui;

import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.gui.MouseOverArea;

public class TextButton extends MouseOverArea {

    private String text;
    private Font font;
    private Font mouseOverFont;

    public TextButton(GUIContext container, String text, int x, int y, Font font) {
	this(container, text, x, y, font != null ? font.getWidth(text) : container.getDefaultFont().getWidth(text), font != null ? font
		.getHeight(text) : container.getDefaultFont().getHeight(text));
	this.font = font != null ? font : container.getDefaultFont();
	this.mouseOverFont = this.font;
    }

    public TextButton(GUIContext container, String text, int x, int y, int width, int height) {
	super(container, null, x, y, width, height);
	this.text = text;
    }

    public TextButton(GUIContext container, String text, int x, int y, Font font, ComponentListener listener) {
	this(container, text, x, y, font);
	addListener(listener);
    }

    /**
     * @see org.newdawn.slick.gui.AbstractComponent#render(org.newdawn.slick.gui.GUIContext,
     *      org.newdawn.slick.Graphics)
     */
    public void render(GUIContext container, Graphics g) {
	if (font != null)
	    g.setFont(font);
	g.drawString(text, this.getX(), this.getY());
    }

    public String getText() {
	return text;
    }

    public void setText(String text) {
	this.text = text;
    }

    public Font getFont() {
	return font;
    }

    public void setFont(Font font) {
	this.font = font;
    }

    public boolean collision(int x, int y) {
	return x >= getX() && x <= getX() + getWidth() && y >= getY() && y <= getY() + getHeight();
    }

}
