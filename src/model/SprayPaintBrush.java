package model;

import java.util.Random;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * SprayPaintBrush: Represents a spray paint brush tool for artistic effects.
 * 
 * This class extends the Brush class and defines the behavior of a spray paint
 * brush tool for creating artistic effects on a canvas. It simulates the
 * dispersion of paint particles to achieve a spray paint-like appearance.
 * 
 * @author Doom Al Rajhi
 * @file: SprayPaintBrush.java
 * @assignment: Paint Application Final Project
 * @course: CSC 335 Fall 2023
 * @date 11/30/2023
 * @version 1.6
 * 
 */
public class SprayPaintBrush extends Brush {

	private static final int SPRAY_THICKNESS = 100;

	/**
	 * Constructor for creating a SprayPaintBrush instance.
	 *
	 * @param color The color to be used for the spray paint brush.
	 * @param size  The size or thickness of the spray paint brush strokes.
	 */
	public SprayPaintBrush(Color color, double size) {
		super(color, size);
	}

	/**
	 * Drawing method for the WatercolorBrush.
	 * 
	 * @param canvas The Canvas to draw on.
	 * @param x      Double x-coordinate
	 * @param y      Double y-coordinate
	 */
	@Override
	public void draw(Canvas canvas, double x, double y) {
		// This method is called when the user clicks on the canvas
		spray(canvas, x, y);
	}

	@Override
	public void drawDragged(Canvas canvas, double x, double y) {
		// This method is called when the user drags the mouse on the canvas
		spray(canvas, x, y);
	}

	/**
	 * Creates a visual effect on the canvas that resembles the dispersion of paint
	 * particles, characteristic of spray paint. This method simulates a spray paint
	 * effect by randomly placing a specified number of small dots (paint particles)
	 * around a central point. The dispersion range and density of the particles are
	 * influenced by the brush size.
	 * 
	 * @param canvas The Canvas object on which the spray paint effect is to be
	 *               applied.
	 * @param x      The x-coordinate of the central point where the spray effect
	 *               originates.
	 * @param y      The y-coordinate of the central point where the spray effect
	 *               originates.
	 */
	private void spray(Canvas canvas, double x, double y) {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		Random random = new Random();

		for (int i = 0; i < SPRAY_THICKNESS; i++) {
			// random values to determine the position of each paint particle relative to
			// the original point (x, y)
			double offsetX = (random.nextDouble() - 0.5) * (Math.pow(getSize(), 2) / 2);
			double offsetY = (random.nextDouble() - 0.5) * (Math.pow(getSize(), 2) / 2);

			gc.setFill(color);
			gc.fillOval(x + offsetX, y + offsetY, 1, 1);
		}
	}
}
