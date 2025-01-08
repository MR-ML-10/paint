package tests;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * This class demonstrates canvas resizing by copying the graphics context of an
 * initial canvas and pasting it on a new canvas of different size. It showcases
 * how to resize a canvas while preserving its content.
 * 
 * @author Hannibal, Doom Al Rajhi
 * @file: CanvasSizeTest.java
 * @assignment: Paint Application Final Project
 * @course: CSC 335 Fall 2023
 * @date: 11/30/2023
 * @version 1.6
 */
public class CanvasSizeTest extends Application {

	private Canvas canvas;
	private GraphicsContext gc;

	@Override
	public void start(Stage primaryStage) {
		canvas = new Canvas(300, 200); // Initial size
		gc = canvas.getGraphicsContext2D();

		drawSomething(); // Draw initial content

		StackPane root = new StackPane(canvas);

		primaryStage.setTitle("Resize Canvas Example");
		primaryStage.setScene(new Scene(root, 400, 300));
		primaryStage.show();

		// Simulate resizing after a delay
		new Thread(() -> {
			try {
				Thread.sleep(3000); // Wait for 3 seconds
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// Change canvas size
			resizeCanvas(500, 400);
		}).start();
	}

	/**
	 * Draws an example shape on the canvas.
	 */
	private void drawSomething() {
		// Example drawing
		gc.setFill(javafx.scene.paint.Color.BLUE);
		gc.fillRect(50, 50, 200, 100);
	}

	/**
	 * Resizes the canvas to the specified width and height while preserving its
	 * content.
	 * 
	 * @param width  The new width of the canvas.
	 * @param height The new height of the canvas.
	 */
	private void resizeCanvas(double width, double height) {
		Canvas newCanvas = new Canvas(width, height);
		GraphicsContext newGC = newCanvas.getGraphicsContext2D();

		// Transfer content from old canvas to new canvas
		newGC.drawImage(canvas.snapshot(null, null), 0, 0);

		canvas = newCanvas;
		gc = newGC;
	}

	/**
	 * The main entry point of the application.
	 * 
	 * @param args Command-line arguments (not used in this application).
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
