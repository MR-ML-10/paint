package model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Eraser: Subclass of DrawingTool for erasing content.
 * 
 * This class represents an eraser tool that allows users to erase parts of the
 * canvas. It implements the 'draw' method to erase content based on the
 * eraser's size. Users can customize the eraser size.
 *
 * @author Vivian Alonso, Doom Al Rajhi
 * @file: Eraser.java
 * @assignment: Paint Application Final Project
 * @course: CSC 335 Fall 2023
 * @date: 11/30/2023
 * @version 1.6
 */
public class Eraser extends DrawingTool {
	/**
	 * Constructor for the Eraser class.
	 * 
	 * @param color The color of the eraser.
	 * @param size  The size or thickness of the eraser.
	 */
	public Eraser(Color color, double size) {
		super(color, size);
		color = Color.web("#f2f2f2");
	}

	/**
	 * Drawing method for the Eraser DrawingTool.
	 * 
	 * @param canvas The Canvas to draw on.
	 * @param x      Double x-coordinate
	 * @param y      Double y-coordinate
	 */
	@Override
	public void draw(Canvas canvas, double x, double y) {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		double defaultSize = (Math.pow(this.getSize(), 2) / 2);
		gc.clearRect(x - defaultSize / 2, y - defaultSize / 2, defaultSize, defaultSize);
	}

	@Override
	public void drawDragged(Canvas canvas, double x, double y) {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		double defaultSize = (Math.pow(this.getSize(), 2) / 2);
		gc.clearRect(x - defaultSize / 2, y - defaultSize / 2, defaultSize, defaultSize);
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
