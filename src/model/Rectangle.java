package model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Rectangle: Subclass of DrawingTool for drawing rectangles.
 * 
 * This class represents a drawing tool for creating rectangles on a canvas. It
 * extends the DrawingTool class and implements the 'draw' method to draw
 * rectangles on the canvas. Users can select different rectangle properties
 * such as color and size when using this tool.
 * 
 * @author Vivian Alonso, Doom Al Rajhi
 * @file: Rectangle.java
 * @assignment: Paint Application Final Project
 * @course: CSC 335 Fall 2023
 * @date: 11/30/2023
 * @version 1.6
 */
public class Rectangle extends DrawingTool {

	/**
	 * Constructor for the Rectangle class.
	 * 
	 * @param color The color of the rectangle.
	 * @param size  The size of the rectangle.
	 */
	public Rectangle(Color color, double size) {
		super(color, size);
	}

	/**
	 * Draw method to draw a rectangle on the canvas.
	 * 
	 * @param canvas The canvas on which to draw.
	 * @param x      The x-coordinate of the top-left corner of the rectangle.
	 * @param y      The y-coordinate of the top-left corner of the rectangle.
	 */
	@Override
	public void draw(Canvas canvas, double x, double y) {
		GraphicsContext gc = canvas.getGraphicsContext2D();

		gc.setStroke(getColor());
		gc.setLineWidth(2);

		double width = Math.pow(getSize(), 2) / 2;
		double height = Math.pow(getSize(), 2) / 2;

		gc.strokeRect(x, y, width + width, height);
	}

	@Override
	public void drawDragged(Canvas canvas, double x, double y) {
	}

	@Override
	public Canvas moveSelected(GraphicsContext graphicscontext, double x, double y) {
		return null;
	}

	@Override
	public Canvas mouseReleased(GraphicsContext gc, double x, double y) {
		return null;
	}
}
