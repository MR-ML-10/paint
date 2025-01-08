package model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Abstract base class for drawing tools.
 * 
 * This abstract class serves as the foundation for all drawing tools in the
 * application, providing a common structure and managing properties like color
 * and size. Subclasses must implement the 'draw' method.
 * 
 * 
 * @author Doom Al Rajhi, Hannibal Oyebode
 * @file: DrawingTool.java
 * @assignment: Paint Application Final Project
 * @course: CSC 335 Fall 2023
 * @date: 11/30/2023
 * @version 1.6
 */
public abstract class DrawingTool {
	protected Color color; // The color used by the drawing tool.
	protected double size; // The size or thickness of the drawing tool's stroke.

	/**
	 * Constructor for creating a new DrawingTool instance.
	 * 
	 * @param color The color to be used by the drawing tool.
	 * @param size  The size or thickness of the drawing tool's stroke.
	 */
	protected DrawingTool(Color color, double size) {
		this.color = color;
		this.size = size;
	}

	/**
	 * Abstract method that must be implemented by subclasses. This method is
	 * responsible for drawing on the canvas.
	 * 
	 * @param canvas The canvas on which to draw.
	 * @param x      The x-coordinate of the drawing point.
	 * @param y      The y-coordinate of the drawing point.
	 */
	public abstract void draw(Canvas canvas, double x, double y);

	/**
	 * Abstract method that must be implemented by subclasses. This method is
	 * responsible for handling continuous drawing as the mouse is dragged.
	 * 
	 * @param canvas The canvas on which to draw.
	 * @param x      The x-coordinate of the current drawing point.
	 * @param y      The y-coordinate of the current drawing point.
	 */
	public abstract void drawDragged(Canvas canvas, double x, double y);

	/**
	 * Get the color currently set for the drawing tool.
	 * 
	 * @return The color of the drawing tool.
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Set the color for the drawing tool.
	 * 
	 * @param color The new color to set.
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Get the size or thickness of the drawing tool's stroke.
	 * 
	 * @return The size of the drawing tool's stroke.
	 */
	public double getSize() {
		return size;
	}

	/**
	 * Set the size or thickness of the drawing tool's stroke.
	 * 
	 * @param size The new size to set.
	 */
	public void setSize(double size) {
		this.size = size;
	}

	/**
	 * Abstract method that must be implemented by subclasses. This method is
	 * responsible for handling the movement of a selected drawing element.
	 * 
	 * @param graphicscontext The GraphicsContext for drawing.
	 * @param x               The new x-coordinate for the selected element.
	 * @param y               The new y-coordinate for the selected element.
	 * @return A Canvas object representing the updated drawing after the movement.
	 */
	public abstract Canvas moveSelected(GraphicsContext graphicscontext, double x, double y);

	/**
	 * Abstract method that must be implemented by subclasses. This method is called
	 * when the mouse button is released during a drawing operation.
	 * 
	 * @param gc The GraphicsContext for drawing.
	 * @param x  The x-coordinate where the mouse button was released.
	 * @param y  The y-coordinate where the mouse button was released.
	 * @return A Canvas object representing the updated drawing after the mouse
	 *         release.
	 */
	public abstract Canvas mouseReleased(GraphicsContext gc, double x, double y);
}
