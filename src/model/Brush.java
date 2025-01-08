package model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Represents the main class for a Paint graphical user interface. This class
 * sets up the JavaFX application and initializes the GUI components. It
 * provides the implementation for the Brush tool, allowing users to perform
 * free hand drawing with customizable brush sizes and colors.
 * 
 * @author Doom Al Rajhi, Vivian Alonso
 * @file: Brush.java
 * @assignment: Paint Application Final Project
 * @course: CSC 335 Fall 2023
 * @date: 11/30/2023
 * @version 1.6
 */
public class Brush extends DrawingTool {

	/**
	 * Constructor for the Brush class.
	 * 
	 * @param color The color of the brush.
	 * @param size  The size of the brush.
	 */
	public Brush(Color color, double size) {
		super(color, size);
	}

	/**
	 * Draw method to draw free hand lines on the canvas.
	 * 
	 * @param canvas The canvas on which to draw.
	 * @param x      The x-coordinate of the drawing point.
	 * @param y      The y-coordinate of the drawing point.
	 */
	@Override
	public void draw(Canvas canvas, double x, double y) {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.beginPath();
		gc.setStroke(color);
		gc.setLineWidth((Math.pow(getSize(), 2) / 2));
		gc.lineTo(x, y);
		gc.stroke();
	}

	@Override
	public void drawDragged(Canvas canvas, double x, double y) {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.lineTo(x, y);
		gc.stroke();
	}

	@Override
	public Canvas moveSelected(GraphicsContext gc, double x, double y) {
		// Doesn't do anything in this class
		return null;
	}

	@Override
	public Canvas mouseReleased(GraphicsContext gc, double x, double y) {
		// Doesn't do anything in this class
		return null;
	}
}
