package model;

import java.util.Stack;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

/**
 * Fill: Subclass of DrawingTool for filling enclosed areas.
 * 
 * This class represents a drawing tool for filling enclosed areas on a canvas.
 * It extends the DrawingTool class and implements the 'draw' method to fill
 * enclosed areas on the canvas. Users can choose the fill color when using this
 * tool.
 * 
 * @author Vivian Alonso, Doom Al Rajhi
 * @file: Fill.java
 * @assignment: Paint Application Final Project
 * @course: CSC 335 Fall 2023
 * @date: 11/30/2023
 * @version 1.6
 */
public class Fill extends DrawingTool {

	/**
	 * Constructor for the Fill class.
	 * 
	 * @param color The fill color.
	 * @param size  The size of the fill tool (usually not relevant for filling).
	 */
	public Fill(Color color, double size) {
		super(color, size);
	}

	/**
	 * Draw method to fill enclosed areas on the canvas.
	 * 
	 * @param canvas The canvas on which to perform the fill.
	 * @param x      The x-coordinate within the area to start filling.
	 * @param y      The y-coordinate within the area to start filling.
	 */
	@Override
	public void draw(Canvas canvas, double x, double y) {
		GraphicsContext gc = canvas.getGraphicsContext2D();

		PixelWriter pixelWriter = gc.getPixelWriter();
		PixelReader pixelReader = canvas.snapshot(null, null).getPixelReader();

		// Get the color of the starting pixel
		Color startColor = pixelReader.getColor((int) x, (int) y);

		boolean[][] visited = new boolean[(int) canvas.getWidth()][(int) canvas.getHeight()];

		// Stack for a flood fill algorithm
		Stack<Point2D> stack = new Stack<>();
		stack.push(new Point2D(x, y));

		while (!stack.isEmpty()) {
			Point2D point = stack.pop();
			int px = (int) point.getX();
			int py = (int) point.getY();

			if (px >= 0 && px < canvas.getWidth() && py >= 0 && py < canvas.getHeight()) {
				if (!visited[px][py]) {
					// Mark pixel location as visited
					visited[px][py] = true;

					if (pixelReader.getColor(px, py).equals(startColor)) {
						// Set the color of the pixel to the fill color
						pixelWriter.setColor(px, py, color);

						// Push remaining spots to color
						stack.push(new Point2D(px + 1, py));
						stack.push(new Point2D(px - 1, py));
						stack.push(new Point2D(px, py + 1));
						stack.push(new Point2D(px, py - 1));
					}
				}
			}
		}
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
