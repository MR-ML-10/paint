package model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 * Select: Represents a drawing tool for selecting and manipulating objects on
 * the canvas.
 *
 * This class provides functionality for selecting and manipulating objects on
 * the canvas. It allows users to click and drag to select objects and perform
 * actions like moving or resizing the selection.
 * 
 * @author Hannibal Oyebode, Doom Al Rajhi
 * @file: Select.java
 * @assignment: Paint Application Final Project
 * @course: CSC 335 Fall 2023
 * @date: 11/30/2023
 * @version 1.6
 */
public class Select extends DrawingTool {

	private int startX;
	private int startY;
	private int endX;
	private int endY;
	private int timesClicked = 0;
	GraphicsContext selectedArea;
	GraphicsContext origGC;

	/**
	 * Constructor for the Select class.
	 * 
	 * @param color The color of the border.
	 * @param size  The size of the border.
	 */
	public Select(Color color, double size) {
		super(color, size);
	}

	/**
	 * Draw method to draw a box from the start point at the first click to the end
	 * point at the second (The box is currently a line) TODO set the outline to be
	 * a square encasing the area once the shape method is done
	 * 
	 * @param canvas The canvas on which to draw.
	 * @param x      The x-coordinate of the drawing point.
	 * @param y      The y-coordinate of the drawing point.
	 */
	@Override
	public void draw(Canvas canvas, double x, double y) {
		try {
			if (timesClicked % 2 == 0) {
				// set starting coordinates
				startX = (int) x;
				startY = (int) y;
//			gc.beginPath();
//			gc.setStroke(color);
//			gc.setLineWidth(size);
//			gc.stroke();
			}

			else {
				// set ending coordinates
				endX = (int) x;
				endY = (int) y;

//			gc.lineTo(x, y);
//			gc.stroke();

				// select the area between coordinates
				selectedArea = selectPixels(canvas);

				// save the original canvas
				Canvas tempCanvas = new Canvas(canvas.getWidth(), canvas.getHeight());
				origGC = tempCanvas.getGraphicsContext2D();
				origGC.drawImage(canvas.snapshot(null, null), 0, 0);

				// remove the selected area from the original canvas
				originalCleaner();

			}

			timesClicked++;
		} catch (Exception e) {
		}
	}

	@Override
	public void drawDragged(Canvas canvas, double x, double y) {
		// does nothing in this class
	}

	public Canvas moveSelected(GraphicsContext gc, double x, double y) {
		Canvas movedCanvas = combineGCs(origGC, selectedArea, (int) x, (int) y);

		return movedCanvas;
	}

	/**
	 * A method that gets selected pixels and creates a WriteableImage from them
	 * 
	 * @param canvas gc is the input GraphicsContext object
	 * @return selectedImage is the WriteableImage created from the selected pixels
	 */
	public GraphicsContext selectPixels(Canvas canvas) {
		fixCoords();
		try {
			// Get the pixel reader from the canvas
			PixelReader pixelReader = canvas.snapshot(null, null).getPixelReader();

			// Create a new writable image
			WritableImage selectedImage = new WritableImage(endX - startX, endY - startY);

			// Get the pixel writer for the writable image
			var pixelWriter = selectedImage.getPixelWriter();

			// Select pixels between start and end
			for (int y = 0; y < canvas.getHeight(); y++) {
				for (int x = 0; x < canvas.getWidth(); x++) {
					if (((y < endY) && (y > startY)) && ((x < endX) && (x > startX))) {
						Color color = pixelReader.getColor(x, y);

						// Set the pixel in the writable image to the selected color
						pixelWriter.setColor(x - startX, y - startY, color);
					}
				}
			}

			// Create a new canvas and draw the selected pixels
			Canvas selectedCanvas = new Canvas(endX - startX, endY - startY);
			GraphicsContext selectedGC = selectedCanvas.getGraphicsContext2D();
			selectedGC.drawImage(selectedImage, 0, 0);

			return selectedGC;
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * This function combnes two GraphicsContext objects into one Canvas
	 * 
	 * @param gc1 the first GraphicsContext object from which the size of the final
	 *            Canvas is obtained
	 * @param gc2 the second GraphicsContes=xt object that's being 'pasted' on the
	 *            first.
	 * @param x   is the location on the x axis at which gc2 is pasted
	 * @param y   is the location on the y axis at which gc2 is pasted
	 * @return combinedCanvas, the result of adding the two GraphicsContexts
	 */
	private Canvas combineGCs(GraphicsContext gc1, GraphicsContext gc2, int x, int y) {
		// Create a new canvas with the same dimensions
		Canvas combinedCanvas = new Canvas(gc1.getCanvas().getWidth(), gc1.getCanvas().getHeight());
		GraphicsContext combinedGC = combinedCanvas.getGraphicsContext2D();

		// Draw on the new canvas using both graphics contexts
		combinedGC.drawImage(gc1.getCanvas().snapshot(null, null), 0, 0);
		combinedGC.drawImage(gc2.getCanvas().snapshot(null, null), x, y);

		return combinedCanvas;
	}

	/**
	 * Switches the start and end coordinates to be in a format on which the other
	 * methods can work with.
	 */
	private void fixCoords() {
		int temp;
		// if coords are backwards flip them
		if (endX < startX) {
			temp = startX;
			startX = endX;
			endX = temp;
		}

		if (endY < startY) {
			temp = startY;
			startY = endY;
			endY = temp;
		}
	}

	/**
	 * Draw method to finalize the location at which you want to drop your selection
	 * on the main canvas
	 */
	@Override
	public Canvas mouseReleased(GraphicsContext gc, double x, double y) {
		Canvas lastCanvas = combineGCs(origGC, selectedArea, (int) x, (int) y);
		return lastCanvas;
	}

	/**
	 * removes the area that was selected and replaces it with the background color.
	 */
	private void originalCleaner() {

		// Create a new writable image
		WritableImage blankBackground = new WritableImage(endX - startX, endY - startY);

		// Get the pixel writer for the writable image
		var pixelWriter = blankBackground.getPixelWriter();

		// Select pixels between start and end
		for (int y = 0; y < origGC.getCanvas().getHeight(); y++) {
			for (int x = 0; x < origGC.getCanvas().getWidth(); x++) {
				if (((y < endY) && (y > startY)) && ((x < endX) && (x > startX))) {

					// Set the pixel in the writable image to the selected color
					pixelWriter.setColor(x - startX, y - startY, Color.web("#f2f2f2"));
				}
			}
		}

		origGC.drawImage(blankBackground, startX, startY);
	}
}
