package model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Triangle: Subclass of DrawingTool for drawing Triangles.
 * 
 * This class represents a drawing tool for creating Triangles on a canvas. It
 * extends the DrawingTool class and implements the 'draw' method to draw
 * Triangles on the canvas. Users can select different Triangle properties such
 * as color and size when using this tool.
 * 
 * @author Vivian Elena Alonso, Doom Al Rajhi
 * @file: Triangle.java
 * @assignment: Paint Application Final Project
 * @course: CSC 335 Fall 2023
 * @date: 11/30/2023
 * @version 1.6
 */
public class Triangle extends DrawingTool {

	/**
	 * Constructor for the Triangle class.
	 * 
	 * @param color The color of the Triangle.
	 * @param size  The size of the Triangle.
	 */
	public Triangle(Color color, double size) {
		super(color, size);
	}

	/**
	 * Draw method to draw a Triangle on the canvas.
	 * 
	 * @param canvas The canvas on which to draw.
	 * @param x      The x-coordinate of the top-left corner of the Triangle.
	 * @param y      The y-coordinate of the top-left corner of the Triangle.
	 */
	@Override
	public void draw(Canvas canvas, double x, double y) {
		// Get the graphics context from the canvas
		GraphicsContext gc = canvas.getGraphicsContext2D();

		// Set the color and size for drawing the outline
		gc.setStroke(color);
		double size = Math.pow(getSize(), 2); // Get the size of the triangle

		// Calculate the coordinates for the three vertices of the triangle
		double x1 = x;
		double y1 = y;
		double x2 = x - size / 2;
		double y2 = y + size;
		double x3 = x + size / 2;
		double y3 = y + size;

		gc.strokeLine(x1, y1, x2, y2);
		gc.strokeLine(x2, y2, x3, y3);
		gc.strokeLine(x3, y3, x1, y1);
	}

	@Override
	public void drawDragged(Canvas canvas, double x, double y) {
	}

	public Canvas moveSelected(GraphicsContext graphicscontext, double x, double y) {
		return null;
	}

	@Override
	public Canvas mouseReleased(GraphicsContext gc, double x, double y) {
		return null;
	}
}
