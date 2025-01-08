package model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * This class represents the text logic, which handles all the necessary changes
 * or logic for the text feature
 * 
 * @author Doom Al Rajhi
 * @file: TextTool.java
 * @assignment: Paint Application Final Project
 * @course: CSC 335 Fall 2023
 * @date: 11/30/2023
 * @version 1.6
 */
public class TextTool extends DrawingTool {
	private TextField textField;

	/**
	 * Constructor for creating a TextTool instance.
	 *
	 * @param color The color to be used for the text tool.
	 * @param size  The size or thickness of the text.
	 */
	public TextTool(Color color, double size) {
		super(color, size);
		this.textField = new TextField();
	}

	/**
	 * Draw method to be utilized by DrawingTool objects.
	 * 
	 * @param x Double x-coordinate
	 * @param y Double y-coordinate
	 */
	@Override
	public void draw(Canvas canvas, double x, double y) {
		setStyle(x, y);
		// Add the TextField to the layout if it's not already there
		if (textField.getParent() == null) {
			((Pane) canvas.getParent()).getChildren().add(textField);
		}
		textField.requestFocus();
		textHandler(canvas, x, y);
	}

	/**
	 * Handles the primary logic for adding text to canvas
	 * 
	 * @param canvas The main canvas
	 * @param x      x coordinate
	 * @param y      y coordinate
	 */
	private void textHandler(Canvas canvas, double x, double y) {
		// Handle the Enter key press to draw the text
		textField.setOnAction(event -> {
			GraphicsContext gc = canvas.getGraphicsContext2D();
			gc.setFill(getColor());
			gc.setFont(new Font(Math.pow(size, 2) / (size / 2)));
			gc.fillText(textField.getText(), x, y);

			// Clear and hide the TextField after text is entered
			textField.clear();
			textField.setVisible(false);
			textField.setManaged(false);
		});
	}

	/**
	 * Sets the primary style for the text field
	 * 
	 * @param x x coordinate
	 * @param y y coordinate
	 */
	private void setStyle(double x, double y) {
		textField.setLayoutX(x);
		textField.setLayoutY(y);
		textField.setPrefWidth(200); // Set a preferred width
		textField.setPromptText("Enter text here...");
		textField.setVisible(true);
		textField.setManaged(true);
		textField.setStyle("-fx-border-color: black; -fx-background-color: black;");
	}

	/**
	 * not applicable for this class
	 */
	@Override
	public void drawDragged(Canvas canvas, double x, double y) {
	}

	/**
	 * gets the return version of TextField
	 * 
	 * @return TextField
	 */
	public TextField getTextField() {
		return textField;
	}

	public Canvas moveSelected(GraphicsContext graphicscontext, double x, double y) {
		return null;
	}

	@Override
	public Canvas mouseReleased(GraphicsContext gc, double x, double y) {
		return null;
	}
}
