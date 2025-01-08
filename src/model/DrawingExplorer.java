package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;

import controller_view.DrawingCanvas;
import controller_view.PaintCanvasGUI;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * DrawingExplorer: Manages drawing-related actions and alerts in the
 * application.
 * 
 * This class is responsible for managing various drawing-related actions and
 * alerts within the application. It interacts with the primary stage and
 * handles actions such as exporting, importing, saving drawings, and showing
 * alerts.
 * 
 * @author Doom Al Rajhi, Tanay Desai
 * @file: DrawingExplorer.java
 * @assignment: Paint Application Final Project
 * @course: CSC 335 Fall 2023
 * @date: 11/30/2023
 * @version 1.6
 */
public class DrawingExplorer implements Serializable {
	private static final long serialVersionUID = 4211396090447480389L;
	/**
	 * The primary stage of the application, used for managing the main user
	 * interface.
	 */
	private Stage primaryStage;

	/**
	 * Constructor for the DrawingExplorer class.
	 *
	 * @param primaryStage The primary stage of the application.
	 */
	public DrawingExplorer(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	/**
	 * A default constructor
	 */
	public DrawingExplorer() {
	}

	/**
	 * Export the current drawing from the given drawingCanvas to a file. The
	 * exported file format is typically in PNG, which supports images with
	 * transparency.
	 *
	 * @param drawingCanvas The drawingCanvas containing the artwork to be exported.
	 */
	public void exportDrawing(DrawingCanvas drawingCanvas) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save Drawing");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG Files", "*.png"));
		File selectedFile = fileChooser.showSaveDialog(drawingCanvas.getScene().getWindow());

		if (selectedFile != null) {
			WritableImage writableImage = drawingCanvas.snapshot(null, null);

			PixelReader pixelReader = writableImage.getPixelReader();

			int width = (int) writableImage.getWidth();
			int height = (int) writableImage.getHeight();

			int[] pixels = new int[width * height];

			pixelReader.getPixels(0, 0, width, height, javafx.scene.image.PixelFormat.getIntArgbInstance(), pixels, 0,
					width);

			BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			bufferedImage.setRGB(0, 0, width, height, pixels, 0, width);

			try {
				ImageIO.write(bufferedImage, "png", selectedFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		drawingCanvas.hasChanges = false;
	}

	/**
	 * Imports a saved drawing from a file and loads it into the provided
	 * drawingCanvas.
	 *
	 * @param drawingCanvas The drawingCanvas where the imported drawing will be
	 *                      loaded. The saved drawing should be in a compatible
	 *                      format (e.g., PNG).
	 */
	public void importDrawing(DrawingCanvas drawingCanvas) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Load Drawing");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG Files", "*.png"));
		File selectedFile = fileChooser.showOpenDialog(drawingCanvas.getScene().getWindow());

		if (selectedFile != null) {
			Image image = new Image(selectedFile.toURI().toString());
			PixelReader pixelReader = image.getPixelReader();

			int width = (int) image.getWidth();
			int height = (int) image.getHeight();

			WritableImage writableImage = new WritableImage(width, height);
			drawingCanvas.setWidth(width);
			drawingCanvas.setHeight(height);

			PixelWriter pixelWriter = writableImage.getPixelWriter();
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					pixelWriter.setArgb(x, y, pixelReader.getArgb(x, y));
				}
			}

			drawingCanvas.getGraphicsContext2D().drawImage(writableImage, 0, 0);

		}

		drawingCanvas.hasChanges = false;

	}

	/**
	 * Displays a confirmation alert when the user attempts to close the application
	 * with unsaved changes.
	 * 
	 * @param event                The WindowEvent representing the close request.
	 * @param paintWindowCanvasGUI The PaintCanvasGUI instance managing the
	 *                             application.
	 */
	public void closingAlert(WindowEvent event, PaintCanvasGUI paintWindowCanvasGUI) {
		if (paintWindowCanvasGUI.drawingCanvas.hasChanges) {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Save Confirmation");
			alert.setHeaderText(null);
			alert.setContentText("Do you want to save your work before exiting?");

			ButtonType saveButton = new ButtonType("Save");
			ButtonType dontSaveButton = new ButtonType("Don't Save");
			ButtonType cancelButton = new ButtonType("Cancel");

			alert.getButtonTypes().setAll(saveButton, dontSaveButton, cancelButton);

			// Show the alert and handle the user's choice
			alert.showAndWait().ifPresent(response -> {
				if (response == saveButton) {
					paintWindowCanvasGUI.saveCanvas();
					primaryStage.close();
				} else if (response == dontSaveButton) {
					primaryStage.close();
				} else if (response == cancelButton) {
					// User chose to cancel the close request, so consume the event
					event.consume();
				}
			});
		}
	}
}
