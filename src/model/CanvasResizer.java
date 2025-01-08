package model;
/**
 * This file was originally going to be used to implement the canvas resizing
 * as a separate class, but we found it would be more efficient to implement
 * as a few methods within the PaintGUI and PaintCanvasGUI classes
 * 
 * @author Hannibal Oyebode
 */
import controller_view.DrawingCanvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;

/**
 * CanvasResizer: Manages the resizing of a drawing canvas.
 * 
 * This class facilitates the creation of a new canvas with a specified width
 * and height, transferring the content from an existing canvas to the new one.
 * It is used to resize the drawing canvas while preserving its content.
 * 
 * @author Hannibal, Doom Al Rajhi
 * @file: CanvasResizer.java
 * @assignment: Paint Application Final Project
 * @course: CSC 335 Fall 2023
 * @date: 11/30/2023
 * @version 1.6
 */
public class CanvasResizer {
	private DrawingCanvas canvas;
	private GraphicsContext gc;

	/**
	 * Constructor for the CanvasResizer class.
	 * 
	 * @param inpCanvas The drawing canvas to be resized.
	 */
	public CanvasResizer(DrawingCanvas inpCanvas) {
		canvas = inpCanvas;
		gc = canvas.getGraphicsContext2D();
	}

	/**
	 * Creates a new drawing canvas with the specified width and height,
	 * transferring the content from the original canvas to the new canvas.
	 * 
	 * @param width  The new width of the canvas.
	 * @param height The new height of the canvas.
	 * @return The new drawing canvas with transferred content.
	 */
	public DrawingCanvas makeNewCanvas(int width, int height) {
		DrawingCanvas newCanvas = new DrawingCanvas(width, height);
		GraphicsContext newGC = newCanvas.getGraphicsContext2D();

		// Transfer content from old canvas to new canvas
		newGC.drawImage(canvas.snapshot(null, null), 0, 0);

		canvas = newCanvas;
		gc = newGC;

		StackPane root = (StackPane) canvas.getParent();
		root.getChildren().setAll(canvas);

		return canvas;
	}
}
