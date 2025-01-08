package controller_view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import model.DrawingTool;
import model.Select;
import model.UndoRedoManager;

/**
 * DrawingCanvas: A canvas for drawing on with different tools.
 * 
 * This class extends the Canvas class and provides functionality for drawing on
 * a canvas using various drawing tools. It handles mouse events for drawing and
 * allows users to select a current drawing tool, pen color, and perform
 * freehand drawing.
 * 
 * @author Doom Al Rajhi, Vivian Alonso, Tanay Desai, Hannibal Oyebode
 * @file: DrawingCanvas.java
 * @assignment: Paint Application Final Project
 * @course: CSC 335 Fall 2023
 * @date: 11/30/2023
 *
 * @version 1.6
 */
public class DrawingCanvas extends Canvas implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * The GraphicsContext used for drawing on this canvas.
	 */
	private GraphicsContext gc;
	/**
	 * The current drawing tool being used on the canvas.
	 */
	private DrawingTool currentTool;

	/**
	 * The color of the pen for drawing.
	 */
	private Color penColor;

	/**
	 * The size of the pen stroke.
	 */
	private double strokeSize;

	/**
	 * Manages undo and redo actions on the canvas.
	 */
	public UndoRedoManager undoRedoManager;

	/**
	 * The width of the canvas.
	 */
	private double width;

	/**
	 * The height of the canvas.
	 */
	private double height;
	/**
	 * Indicates whether there are unsaved changes on the drawing canvas. If true,
	 * there are unsaved changes; if false, there are no unsaved changes.
	 */
	public boolean hasChanges = false;

	/**
	 * Constructor for the DrawingCanvas class.
	 * 
	 * @param width  The width of the canvas.
	 * @param height The height of the canvas.
	 */
	public DrawingCanvas(double width, double height) {
		super(width, height);
		this.width = width;
		this.height = height;
		initializeCanvas(width, height);
		this.currentTool = null;
		penColor = Color.BLACK;
		this.undoRedoManager = new UndoRedoManager();

		// Register mouse event handlers
		setOnMousePressed(e -> handleMousePressed(e));
		setOnMouseDragged(e -> handleMouseDragged(e));
		setOnMouseReleased(e -> handleMouseReleased(e));
	}

	/**
	 * Initialize the main drawing canvas
	 * 
	 * @param width
	 * @param height
	 */
	private void initializeCanvas(double width, double height) {
		gc = getGraphicsContext2D();

		Color backgroundColor = Color.web("#f4f4f4");
		// Set the background color to black
		gc.setFill(backgroundColor);
		gc.fillRect(0, 0, width, height);

		// Set initial drawing properties (line width, color)
		gc.setLineWidth(2);
		gc.setStroke(getPenColor());
	}

	/**
	 * Handles the mouse press event to initiate drawing.
	 *
	 * @param event The MouseEvent containing information about the press event.
	 */
	private void handleMousePressed(MouseEvent event) {

		WritableImage snapshot = new WritableImage((int) width, (int) height);
		this.snapshot(null, snapshot);
		this.undoRedoManager.addNewAction(snapshot);

		if (currentTool != null) {
			currentTool.draw(this, event.getX(), event.getY());

		} else {
			// Start drawing
			gc.beginPath();
			gc.setLineWidth(strokeSize);
			gc.setFill(getPenColor());
			gc.moveTo(event.getX(), event.getY());
			gc.stroke();

		}

		hasChanges = true;

	}

	/**
	 * Handles the mouse drag event to continue the drawing as the mouse is dragged.
	 * for select gives a preview of where the selected element will be by saving
	 * the current canvas in origGC.
	 * 
	 * @param event The MouseEvent containing information about the drag event.
	 */
	private void handleMouseDragged(MouseEvent event) {
		try {
			if (currentTool instanceof Select) {
				gc.drawImage(currentTool.moveSelected(gc, event.getX(), event.getY()).snapshot(null, null), 0, 0);
			} else if (currentTool != null) {
				currentTool.drawDragged(this, event.getX(), event.getY());

			} else {
				// Continue drawing as the mouse is dragged
				gc.lineTo(event.getX(), event.getY());
				gc.stroke();
			}
		} catch (Exception e) {
		}
		hasChanges = true;
	}

	/**
	 * Handles mouse released after being dragged to have the select and other tools
	 * end their functions at the desired location on the canvas
	 * 
	 * @param event contains info about where the mouse was released
	 */
	private void handleMouseReleased(MouseEvent event) {
		try {
			if (currentTool instanceof Select) {
				gc.drawImage(currentTool.mouseReleased(gc, event.getX(), event.getY()).snapshot(null, null), 0, 0);
			}
		} catch (Exception e) {
		}
	}

	/**
	 * Sets the current drawing tool for proper components to be utilized.
	 * 
	 * @param drawingTool Object of abstract DrawingTool class.
	 */
	public void setDrawingTool(DrawingTool drawingTool) {
		this.currentTool = drawingTool;
	}

	/**
	 * Draw method to be utilized by DrawingTool objects.
	 * 
	 * @param x Double x-coordinate
	 * @param y Double y-coordinate
	 */
	public void draw(double x, double y) {
		if (currentTool != null) {
			currentTool.draw(this, x, y);
		} else {
			gc.lineTo(x, y);
			gc.stroke();
		}
		hasChanges = false;
	}

	/**
	 * Getter for pen color.
	 * 
	 * @return the current pen color
	 */
	public Paint getPenColor() {
		return penColor;
	}

	/**
	 * Setter for pen color.
	 * 
	 * @param penColor Color chosen by user.
	 */
	public void setPenColor(Color penColor) {
		this.penColor = penColor;
		gc.setStroke(penColor); // Set the pen color
		gc.setFill(penColor);
	}

	/**
	 * Setter for pen stroke size
	 * 
	 * @param size is the size chosen by the user.
	 */
	public void setStrokeSize(double size) {
		try {
		this.strokeSize = size;
		currentTool.setSize(size);
		} catch (Exception e) {
			System.out.println("");
		}
	}

	/**
	 * Getter for the current drawing tool.
	 * 
	 * @return currentTool is the tool currently used by the user.
	 */
	public DrawingTool getDrawingTool() {
		return currentTool;
	}

	/**
	 * Loads a saved drawing from the specified file path and displays it on the
	 * canvas.
	 *
	 * @param filePath The file path to the saved drawing that will be loaded onto
	 *                 the canvas.
	 */
	public void loadCanvas(String filePath) {
		try {
			// Load the image from the file
			Image loadedImage = new Image(new FileInputStream(filePath));

			// Clear the current canvas
			this.getGraphicsContext2D().clearRect(0, 0, this.getWidth(), this.getHeight());

			// Draw the loaded image onto the canvas
			this.getGraphicsContext2D().drawImage(loadedImage, 0, 0);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}