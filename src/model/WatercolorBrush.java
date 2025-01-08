package model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * WatercolorBrush: Represents a watercolor brush tool for drawing.
 * 
 * This class extends the Brush class and defines the behavior of a watercolor
 * brush tool for drawing on a canvas. It provides methods for drawing with
 * varying opacity, making it suitable for creating watercolor-like effects.
 * 
 * @author Doom Al Rajhi
 * @file: WatercolorBrush.java
 * @assignment: Paint Application Final Project
 * @course: CSC 335 Fall 2023
 * @date: 11/30/2023
 * @version 1.6
 * 
 */
public class WatercolorBrush extends Brush {
	/**
	 * Constructs a WatercolorBrush with the specified color and size.
	 *
	 * @param color The color of the watercolor brush.
	 * @param size  The size or thickness of the watercolor brush.
	 */
	public WatercolorBrush(Color color, double size) {
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
		GraphicsContext gc = canvas.getGraphicsContext2D();
		// Create a semi-transparent version of the brush color
		Color semiTransparentColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), 0.5);

		gc.beginPath();

		// Set the stroke color and opacity
		gc.setStroke(semiTransparentColor);
		gc.setLineWidth((Math.pow(getSize(), 2) / 2));
		gc.setGlobalAlpha(0.5); // Adjust opacity as needed
		gc.lineTo(x, y);
		gc.stroke();
	}

	@Override
	public void drawDragged(Canvas canvas, double x, double y) {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		// Create a semi-transparent version of the brush color
		Color semiTransparentColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), 0.5);

		gc.beginPath();

		// Set the stroke color
		gc.setStroke(semiTransparentColor);
		gc.setLineWidth((Math.pow(getSize(), 2) / 2));
		gc.lineTo(x, y);
		gc.stroke();
	}
}
