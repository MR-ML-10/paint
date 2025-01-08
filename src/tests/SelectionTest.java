package tests;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * SelectionTest: A class for testing pixel selection and manipulation.
 *
 * This class provides functionality for testing pixel selection based on
 * certain criteria, such as color, and manipulating the selected pixels. It
 * demonstrates how to select pixels on a canvas and create a new canvas with
 * the selected pixels.
 * 
 * @author Hannibal, Doom Al Rajhi
 * @file: SelectionTest.java
 * @assignment: Paint Application Final Project
 * @course: CSC 335 Fall 2023
 * @date: 11/30/2023
 * @version 1.6
 */
public class SelectionTest extends Application {

	@Override
	public void start(Stage primaryStage) {
		// Create a canvas
		Canvas canvas = new Canvas(400, 400);
		GraphicsContext gc = canvas.getGraphicsContext2D();

		// Draw on the canvas
		drawOnCanvas(gc);

		// Get the selected pixels
		WritableImage selectedImage = selectPixels(gc);

		// Create a new canvas and draw the selected pixels
		Canvas selectedCanvas = new Canvas(400, 400);
		GraphicsContext selectedGC = selectedCanvas.getGraphicsContext2D();
		selectedGC.drawImage(selectedImage, 0, 0);

		StackPane root = new StackPane();
		root.getChildren().add(selectedCanvas);

		Scene scene = new Scene(root, 400, 400);
		primaryStage.setTitle("Selected Pixels Example");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void drawOnCanvas(GraphicsContext gc) {
		// Draw some shapes on the canvas
		gc.setFill(Color.RED);
		gc.fillRect(50, 50, 100, 100);

		gc.setFill(Color.BLUE);
		gc.fillOval(200, 150, 80, 80);
	}

	private WritableImage selectPixels(GraphicsContext gc) {
		// Get the pixel reader from the canvas
		PixelReader pixelReader = gc.getCanvas().snapshot(null, null).getPixelReader();

		// Create a new writable image
		WritableImage selectedImage = new WritableImage((int) gc.getCanvas().getWidth(),
				(int) gc.getCanvas().getHeight());
		// Get the pixel writer for the writable image
		var pixelWriter = selectedImage.getPixelWriter();

		// Select pixels based on our criteria
		for (int y = 0; y < gc.getCanvas().getHeight(); y++) {
			for (int x = 0; x < gc.getCanvas().getWidth(); x++) {
				Color color = pixelReader.getColor(x, y);

				// Check if the pixel meets some criteria (e.g., is red)
				if (color.equals(Color.RED)) {
					// Set the pixel in the writable image to the selected color
					pixelWriter.setColor(x, y, color);
				}
			}
		}

		return selectedImage;
	}

	/**
	 * The main entry point of the application.
	 *
	 * @param args The command line arguments passed to the application.
	 */
	public static void main(String[] args) {
		launch(args);
	}
}