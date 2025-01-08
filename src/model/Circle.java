package model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;

/**
 * Circle: Subclass of DrawingTool for drawing circles.
 * 
 * This class represents a drawing tool for creating circles on a canvas. It
 * extends the DrawingTool class and implements the 'draw' method to draw
 * circles on the canvas. Users can select different circle properties such as
 * color and size when using this tool.
 * 
 * @author Vivian Alonso, Doom Al Rajhi
 * @file: Circle.java
 * @assignment: Paint Application Final Project
 * @course: CSC 335 Fall 2023
 * @date: 11/30/2023
 * @version 1.6
 */
public class Circle extends DrawingTool {
	/**
	 * Constructor for the Circle class.
	 * 
	 * @param color The color of the circle.
	 * @param size  The size of the circle.
	 */
	public Circle(Color color, double size) {
		super(color, size);
	}

	/**
	 * Draw method to draw a circle on the canvas.
	 * 
	 * @param canvas The canvas on which to draw.
	 * @param x      The x-coordinate of the center of the circle.
	 * @param y      The y-coordinate of the center of the circle.
	 */
	@Override
	public void draw(Canvas canvas, double x, double y) {
		double radius = (Math.pow(getSize(), 2) / 2); // Use the size attribute to set the radius

		Arc arcUpper = new Arc();
		arcUpper.setCenterX(x); // X-coordinate of the center
		arcUpper.setCenterY(y); // Y-coordinate of the center
		arcUpper.setRadiusX(radius); // Set the radius using the size attribute
		arcUpper.setRadiusY(radius);
		arcUpper.setStartAngle(0); // Starting angle of the arc
		arcUpper.setLength(180); // Length of the arc (in degrees)
		arcUpper.setType(ArcType.OPEN);

		Arc arcLower = new Arc();
		arcLower.setCenterX(x); // X-coordinate of the center
		arcLower.setCenterY(y); // Y-coordinate of the center
		arcLower.setRadiusX(radius); // Set the radius using the size attribute
		arcLower.setRadiusY(radius);
		arcLower.setStartAngle(-180); // Starting angle of the arc
		arcLower.setLength(180); // Length of the arc (in degrees)
		arcLower.setType(ArcType.OPEN);

		GraphicsContext gc = canvas.getGraphicsContext2D();

		gc.setStroke(color);
		gc.setLineWidth(2.5);

		gc.strokeArc(arcUpper.getCenterX() - arcUpper.getRadiusX(), arcUpper.getCenterY() - arcUpper.getRadiusY(),
				2 * arcUpper.getRadiusX(), 2 * arcUpper.getRadiusY(), arcUpper.getStartAngle(), arcUpper.getLength(),
				arcUpper.getType());

		gc.strokeArc(arcLower.getCenterX() - arcLower.getRadiusX(), arcLower.getCenterY() - arcLower.getRadiusY(),
				2 * arcLower.getRadiusX(), 2 * arcLower.getRadiusY(), arcLower.getStartAngle(), arcLower.getLength(),
				arcLower.getType());
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