package controller_view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.DrawingExplorer;

/**
 * Represents the main class for a Paint graphical user interface. This class
 * sets up the JavaFX application and initializes the GUI components.
 *
 * @author Doom Al Rajhi, Vivian Alonso, Tanay Desai, Hannibal Oyebode
 * @file: PaintGUI.java
 * @assignment: Paint Application Final Project
 * @course: CSC 335 Fall 2023
 * @date: 11/30/2023
 * @version 1.6
 */
public class PaintGUI extends Application {
    private PaintCanvasGUI paintWindowCanvasGUI;
    private BorderPane mainWindow;
    private Stage initialStage;
    private DrawingExplorer drawingExplorer;


	/**
	 * The main method that launches the Paint application.
	 *
	 * @param args Command-line arguments (not used in this application).
	 */
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// Initialize the GUI layout and set up the primary stage.
		layoutGUI();
		Scene scene = new Scene(mainWindow, 1280, 768);
		primaryStage.setScene(scene);

		// Load and set the application icon
		Image icon = new Image("/documents/paintIcon.png");
		primaryStage.getIcons().add(icon);

		primaryStage.setTitle("Paint");
		drawingExplorer = new DrawingExplorer(primaryStage);

        // Handle window close request
        primaryStage.setOnCloseRequest(event -> handleCloseRequest(event, primaryStage));
        initialStage = primaryStage;
        primaryStage.show();
    }

    /**
     * Initializes the GUI layout and sets up the primary stage.
     */
    private void layoutGUI() {
        mainWindow = new BorderPane();
        paintWindowCanvasGUI = new PaintCanvasGUI(this);

		// Set the top bar in the PaintCanvasGUI
		paintWindowCanvasGUI.setTopBar();

		// Set the PaintCanvasGUI as the center of the main window
		mainWindow.setCenter(paintWindowCanvasGUI);

	}

    /**
     * Handles the window close request.
     *
     * @param event       The window close request event.
     * @param primaryStage The primary stage.
     */
    private void handleCloseRequest(WindowEvent event, Stage primaryStage) {
        drawingExplorer.closingAlert(event, paintWindowCanvasGUI);
    }

    /**
     * Resizes the window according to specifications
     * @param width the new width of the window in pixels
     * @param height the new height of the window in pixels (not including
     * the toolbar)
     */
	public void resizeWindow(double width, double height) {
		initialStage.setWidth(width);
		initialStage.setHeight(height + 58);
	}
}
