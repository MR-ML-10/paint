package controller_view;

import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.Brush;
import model.Circle;
import model.DrawingExplorer;
import model.DrawingTool;
import model.Eraser;
import model.Fill;
import model.Rectangle;
import model.Select;
import model.SprayPaintBrush;
import model.Square;
import model.TextTool;
import model.Triangle;
import model.WatercolorBrush;

/**
 * Represents the main class for showing all the components that are required
 * for the application. It also shows the DrawingCanvas. This class initializes
 * and manages the GUI components for a painting application.
 * 
 * @author Doom Al Rajhi, Vivian Alonso, Tanay Desai, Hannibal Oyebode
 * @file: PaintCanvasGUI.java
 * @assignment: Paint Application Final Project
 * @course: CSC 335 Fall 2023
 * @date: 11/30/2023
 * @version 1.6
 */
public class PaintCanvasGUI extends BorderPane {

	// Define a constant for the fill button style
	private static final String FILL_BUTTON_STYLE = "-fx-background-color: #FFFFFF;";

	private final MenuBar menuBar = new MenuBar();
	private final MenuBar secondMenuBar = new MenuBar();

	/*
	 * UI Elements for event handlers.
	 */
	private final Button fillButton = new Button("Fill");
	private final Button eraserButton = new Button("Eraser");
	private final MenuItem brushButton = new MenuItem("Brush");
	private final Button shapeButton = new Button("Shape");
	private final Button textButton = new Button("Text");
	private final Button selectButton = new Button("Select");
	private final Button resizeButton = new Button("Resize");

	private ColorPicker colorPicker = new ColorPicker();
	private Slider brushSizeSlider = null;
	/**
	 * The main drawing canvas where users can create and edit drawings.
	 */
	public DrawingCanvas drawingCanvas;
	private DrawingExplorer drawingExplorer;
	private PaintGUI parentGUI;

	/**
	 * Constructs a new PaintCanvasGUI instance.
	 * 
	 * This constructor initializes the main graphical user interface for the
	 * painting application and sets up the necessary components.
	 */
	public PaintCanvasGUI(PaintGUI parent) {
		parentGUI = parent;
		
		// Initialize the drawing canvas and the drawing
		drawingCanvas = new DrawingCanvas(1280, 710);
		drawingExplorer = new DrawingExplorer();

		// Initialize color picker and set default color
		colorPicker.setValue(Color.BLACK); // Set the default color to black
		colorPicker.setStyle("-fx-color-label-visible: false;");

		brushSizeSlider = new Slider(1, 20, 5);

		// Set the default brush color and size
		Color brushColor = colorPicker.getValue();
		double brushSize = brushSizeSlider.getValue();

		Brush brush = new Brush(brushColor, brushSize);

		// Set the drawing tool to the brush
		drawingCanvas.setDrawingTool(brush);

		setupEventFilters();
	}

	/**
	 * Setup Undo/Redo filters
	 */
	private void setupEventFilters() {
		addUndoFilter();
		addRedoFilter();
	}

	/**
	 * Adds an event filter for handling the redo action (Ctrl+Y).
	 */
	private void addRedoFilter() {
		// Add an event filter for Ctrl+Y
		addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.getCode() == KeyCode.Y && event.isControlDown()) {
				redoAction();
				event.consume();
			}
		});
	}

	/**
	 * Adds an event filter for handling the undo action (Ctrl+Z).
	 */
	private void addUndoFilter() {
		// Add an event filter for Ctrl+Z
		addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.getCode() == KeyCode.Z && event.isControlDown()) {
				undoAction();
				event.consume();
			}
		});
	}

	/**
	 * Sets the top bar of the GUI, including menus and additional components.
	 */
	public void setTopBar() {
		createFileMenu(menuBar);
		createViewMenu(menuBar);
		createMenuItemUndo(menuBar);
		createMenuItemRedo(menuBar);
		createAdditionalComponents(secondMenuBar);
		VBox menuBarsVBox = createMenuBarsVBox(menuBar, secondMenuBar);
		setCenter(menuBarsVBox);
	}

	/**
	 * Creates the "File" menu and adds it to the menu bar.
	 *
	 * @param menuBar The MenuBar to which the "File" menu will be added.
	 */
	private void createFileMenu(MenuBar menuBar) {
		Menu fileMenu = new Menu("File");
		MenuItem saveMenuItem = new MenuItem("Save");
		MenuItem loadButton = new MenuItem("Load");
		fileMenu.getItems().add(saveMenuItem);
		fileMenu.getItems().add(loadButton);
		menuBar.getMenus().add(fileMenu);

		saveMenuItem.setOnAction(event -> saveCanvas());
		loadButton.setOnAction(event -> loadCanvas());
	}

	/**
	 * Creates the "View" menu and adds it to the menu bar.
	 *
	 * @param menuBar The MenuBar to which the "View" menu will be added.
	 */
	private void createViewMenu(MenuBar menuBar) {
		Menu viewMenu = new Menu("View");
		MenuItem clearCanvasMenuItem = new MenuItem("Clear");

		clearCanvasMenuItem.setOnAction(e -> clearCanvas());

		viewMenu.getItems().add(clearCanvasMenuItem);
		menuBar.getMenus().add(viewMenu);
	}

	/**
	 * Clears canvas by overwriting the canvas
	 * 
	 * @return null
	 */
	private EventHandler<ActionEvent> clearCanvas() {
		GraphicsContext gc = drawingCanvas.getGraphicsContext2D();
		gc.clearRect(0, 0, drawingCanvas.getWidth(), drawingCanvas.getHeight());
		return null;
	}

	/**
	 * Creates the "Undo" menu item and adds it to the provided menu.
	 *
	 * @param menu The menu to which the "Undo" menu item will be added.
	 */
	private void createMenuItemUndo(MenuBar menu) {
		ImageView undoIcon = createImageIcon("/documents/undo.png");
		undoIcon.setFitWidth(15);
		undoIcon.setFitHeight(15);
		Label label = new Label("");
		label.setGraphic(undoIcon);
		label.setOnMouseClicked(event -> undoAction());

		Menu undoMenuItem = new Menu();
		undoMenuItem.setGraphic(label);

		menu.getMenus().add(undoMenuItem);
	}

	/**
	 * Handles the undo action.
	 */
	private void undoAction() {
		WritableImage image = drawingCanvas.undoRedoManager.undo(drawingCanvas.snapshot(null, null));
		GraphicsContext gtx = drawingCanvas.getGraphicsContext2D();
		gtx.drawImage(image, 0, 0);

	}

	/**
	 * Creates the "Redo" menu item and adds it to the provided menu.
	 *
	 * @param menu The menu to which the "Redo" menu item will be added.
	 */
	private void createMenuItemRedo(MenuBar menu) {
		ImageView redoIcon = createImageIcon("/documents/redo.png");
		redoIcon.setFitWidth(15);
		redoIcon.setFitHeight(15);
		Label label = new Label("");
		label.setGraphic(redoIcon);
		label.setOnMouseClicked(event -> redoAction());

		Menu redoMenuItem = new Menu();
		redoMenuItem.setGraphic(label);

		menu.getMenus().add(redoMenuItem);
	}

	/**
	 * Handles the redo action.
	 */
	private void redoAction() {
		WritableImage image = drawingCanvas.undoRedoManager.redo(drawingCanvas.snapshot(null, null));
		GraphicsContext gtx = drawingCanvas.getGraphicsContext2D();
		gtx.drawImage(image, 0, 0);
	}

	/**
	 * Creates an ImageView for an image resource at the specified resource path.
	 *
	 * @param resourcePath The path to the image resource.
	 * @return An ImageView for the specified image resource.
	 */
	private ImageView createImageIcon(String resourcePath) {
		return new ImageView(new Image(getClass().getResourceAsStream(resourcePath)));
	}

	/**
	 * Creates additional components and adds them to the second menu bar.
	 *
	 * @param secondMenuBar The MenuBar for additional components.
	 */
	private void createAdditionalComponents(MenuBar secondMenuBar) {
		setToolsComponents(secondMenuBar);
	}

	/**
	 * Creates a VBox containing the main and second menu bars.
	 *
	 * @param menuBar       The main MenuBar.
	 * @param secondMenuBar The secondary MenuBar.
	 * @return A VBox containing both menu bars.
	 */
	private VBox createMenuBarsVBox(MenuBar menuBar, MenuBar secondMenuBar) {
		VBox menuBarsVBox = new VBox();
		menuBarsVBox.getChildren().addAll(menuBar, secondMenuBar);
		return menuBarsVBox;
	}

	/**
	 * Sets tools components, such as buttons, color pickers, and sliders in the
	 * second menu bar.
	 *
	 * @param secondMenuBar The MenuBar for additional components.
	 */
	public void setToolsComponents(MenuBar secondMenuBar) {
		// Create a drawing canvas
		setBottom(drawingCanvas);

		ImageView textIcon = createTextButtonWithIcon();
		ImageView fillIcon = createFillButtonWithIcon();
		ImageView brushesIcon = createBrushMenuWithIcon();
		ImageView eraserIcon = createEraserButtonWithIcon();
		ImageView selectIcon = createSelectButtonWithIcon();
		ImageView resizeIcon = createResizeButtonWithIcon();

		createColorPickerAndBrushSlider(secondMenuBar, selectIcon, fillIcon, textIcon, brushesIcon, eraserIcon);

	}

	/**
	 * Creates and returns an ImageView for the Text button with an icon.
	 *
	 * @return An ImageView for the Text button icon.
	 */
	private ImageView createTextButtonWithIcon() {
		// Load image resources for Fill and Text icons
		Image textImage = new Image(getClass().getResourceAsStream("/documents/text.png"));
		// Create ImageView for the icons
		ImageView textIcon = new ImageView(textImage);

		// Set icon size
		textIcon.setFitWidth(15);
		textIcon.setFitHeight(15);
		textButton.setGraphic(textIcon);
		textButton.setOnAction(e -> textListener());
		return textIcon;
	}

	/**
	 * A listener for the text button
	 */
	private void textListener() {
		Color textColor = colorPicker.getValue();
		double textSize = brushSizeSlider.getValue();
		TextTool textTool = new TextTool(textColor, textSize);
		drawingCanvas.setDrawingTool(textTool);
	}

	/**
	 * Creates and returns an ImageView for the Fill button with an icon.
	 *
	 * @return An ImageView for the Fill button icon.
	 */
	private ImageView createFillButtonWithIcon() {
		// Load image resources for Fill and Text icons
		Image fillImage = new Image(getClass().getResourceAsStream("/documents/fill.png"));
		ImageView fillIcon = new ImageView(fillImage);

		// Set icon size
		fillIcon.setFitWidth(15);
		fillIcon.setFitHeight(15);
		fillButton.setGraphic(fillIcon);
		fillButton.setStyle(FILL_BUTTON_STYLE);
		fillButton.setOnAction(event -> fillInAction());
		return fillIcon;
	}

	/**
	 * Creates and returns an ImageView for the Eraser button with an icon.
	 *
	 * @return An ImageView for the Eraser button icon.
	 */
	private ImageView createEraserButtonWithIcon() {
		// Load image resources for Eraser and Text icons
		Image eraserImage = new Image(getClass().getResourceAsStream("/documents/eraserImage.png"));
		ImageView eraserIcon = new ImageView(eraserImage);

		// Set icon size
		eraserIcon.setFitWidth(15);
		eraserIcon.setFitHeight(15);
		eraserButton.setGraphic(eraserIcon);
		eraserButton.setStyle(FILL_BUTTON_STYLE);
		eraserButton.setOnAction(event -> eraserAction());
		return eraserIcon;
	}

	/**
	 * Creates and returns an ImageView for the Select button with an icon.
	 *
	 * @return An ImageView for the SSelect button icon.
	 */
	private ImageView createSelectButtonWithIcon() {
		// Load image resources for Eraser and Text icons
		Image selectImage = new Image(getClass().getResourceAsStream("/documents/SelectIcon.png"));
		ImageView selectIcon = new ImageView(selectImage);

		// Set icon size
		selectIcon.setFitWidth(15);
		selectIcon.setFitHeight(15);
		selectButton.setGraphic(selectIcon);
		selectButton.setStyle(FILL_BUTTON_STYLE);
		selectButton.setOnAction(event -> selectAction());
		return selectIcon;
	}

	/**
	 * Creates a menu for shapes with the specified icon.
	 *
	 * @return The created Menu.
	 */
	private Menu createShapeMenu() {
		Menu shapeMenu = new Menu("Shape", createShapeButtonWithIcon());

		MenuItem circleItem = createShapeMenuItem("Circle", "/documents/circle.png");
		MenuItem triangleItem = createShapeMenuItem("Triangle", "/documents/triangle.png");
		MenuItem squareItem = createShapeMenuItem("Square", "/documents/square.png");
		MenuItem rectangleItem = createShapeMenuItem("Rectangle", "/documents/rectangle.png");

		shapeMenu.getItems().addAll(circleItem, triangleItem, rectangleItem, squareItem);
		return shapeMenu;
	}

	/**
	 * Creates and returns an ImageView for the Shape button with an icon.
	 *
	 * @return An ImageView for the Shape button icon.
	 */
	private ImageView createShapeButtonWithIcon() {
		// Load image resources for Eraser and Text icons
		Image shapeImage = new Image(getClass().getResourceAsStream("/documents/shapeImage.png"));
		ImageView shapeIcon = new ImageView(shapeImage);

		// Set icon size
		shapeIcon.setFitWidth(15);
		shapeIcon.setFitHeight(15);
		shapeButton.setGraphic(shapeIcon);
		shapeButton.setStyle(FILL_BUTTON_STYLE);
		return shapeIcon;
	}

	/**
	 * Creates a MenuItem for a shape with the specified name and image path.
	 *
	 * @param shapeName The name of the shape.
	 * @param imagePath The path to the shape's image.
	 * @return The created MenuItem.
	 */
	private MenuItem createShapeMenuItem(String shapeName, String imagePath) {
		ImageView shapeIcon = createIcon(imagePath);
		MenuItem shapeItem = new MenuItem(shapeName, shapeIcon);
		shapeItem.setOnAction(event -> shapeAction(event));
		return shapeItem;
	}

	/**
	 * Creates an ImageView for a whatever passed as icon with the specified image
	 * path.
	 *
	 * @param imagePath The path to the object's image.
	 * @return The created ImageView for the object icon.
	 */
	private ImageView createIcon(String imagePath) {
		Image objectImage = new Image(getClass().getResourceAsStream(imagePath));
		ImageView objectIcon = new ImageView(objectImage);
		objectIcon.setFitWidth(15);
		objectIcon.setFitHeight(15);
		return objectIcon;
	}

	/**
	 * Creates a menu for brushes with the specified icon.
	 *
	 * @return The created Menu.
	 */
	private Menu createBrushesMenu() {
		Menu brushMenu = new Menu("Brushes", createBrushMenuWithIcon());

		MenuItem defaultBrushItem = createBrushMenuItem("Default", "/documents/defaultBrush.png");
		MenuItem watercolorBrushItem = createBrushMenuItem("Watercolor", "/documents/watercolorBrush.png");
		MenuItem sprayPaintBrushItem = createBrushMenuItem("Spray", "/documents/sprayPaintBrush.png");

		brushMenu.getItems().addAll(defaultBrushItem, watercolorBrushItem, sprayPaintBrushItem);
		return brushMenu;
	}

	/**
	 * Creates and returns an ImageView for the Brush button with an icon.
	 *
	 * @return An ImageView for the Brush button icon.
	 */
	private ImageView createBrushMenuWithIcon() {
		// Load image resources for Eraser and Text icons
		Image brushImage = new Image(getClass().getResourceAsStream("/documents/brushImage.png"));
		ImageView brushIcon = new ImageView(brushImage);

		// Set icon size
		brushIcon.setFitWidth(15);
		brushIcon.setFitHeight(15);
		brushButton.setGraphic(brushIcon);
		brushButton.setStyle(FILL_BUTTON_STYLE);
		return brushIcon;
	}

	private ImageView createResizeButtonWithIcon() {
		Image resizeImage = new Image(getClass().getResourceAsStream("/documents/resizeImage.png"));
		ImageView resizeIcon = new ImageView(resizeImage);

		// Set icon size
		resizeIcon.setFitWidth(15);
		resizeIcon.setFitHeight(15);
		resizeButton.setGraphic(resizeIcon);
		resizeButton.setOnAction(event -> resizeAction());
		return resizeIcon;
	}

	/**
	 * Creates a MenuItem for a brush with the specified name and image path.
	 *
	 * @param brushName The name of the brush.
	 * @param imagePath The path to the brush's image.
	 * @return The created MenuItem.
	 */
	private MenuItem createBrushMenuItem(String brushName, String imagePath) {
		ImageView brushIcon = createIcon(imagePath);
		MenuItem brushItem = new MenuItem(brushName, brushIcon);
		brushItem.setOnAction(event -> brushAction(event));
		return brushItem;
	}

	/**
	 * Handles the required action for a brush button being clicked.
	 * 
	 * @param Action event of a brush button being clicked.
	 */
	private void brushAction(ActionEvent event) {
		MenuItem clickedMenuItem = (MenuItem) event.getSource();
		Color brushColor = colorPicker.getValue();
		double brushSize = brushSizeSlider.getValue();

		switch (clickedMenuItem.getText()) {
		case "Default":
			drawingCanvas.setDrawingTool(new Brush(brushColor, brushSize));
			break;
		case "Watercolor":
			drawingCanvas.setDrawingTool(new WatercolorBrush(brushColor, brushSize));
			break;
		case "Spray":
			drawingCanvas.setDrawingTool(new SprayPaintBrush(brushColor, brushSize));
			break;
		default:
			break;
		}
	}

	/**
	 * Handles the required action for the eraser being clicked.
	 */
	private void eraserAction() {
		Color eraserColor = Color.web("#f2f2f2");
		double eraserSize = brushSizeSlider.getValue();
		Eraser eraser = new Eraser(eraserColor, eraserSize);

		// Set the drawing tool to the eraser
		drawingCanvas.setDrawingTool(eraser);

	}

	/**
	 * Handles the required action to fill in a shape.
	 */
	private void fillInAction() {
		Color fillColor = colorPicker.getValue();
		double fillSize = brushSizeSlider.getValue();
		Fill fillTool = new Fill(fillColor, fillSize);
		// Set the drawing tool to the fill tool
		drawingCanvas.setDrawingTool(fillTool);
		fillButton.setStyle("-fx-background-color: #ADD8E6");
	}

	/**
	 * Handles the required action for the select button being clicked
	 */
	private void selectAction() {
		double outlineSize = 5;
		Select select = new Select(Color.WHITE, outlineSize);

		// Set the drawing tool to select
		drawingCanvas.setDrawingTool(select);
	}

	/**
	 * Shows an alert when the resize button is clicked prompting the user to
	 * input a new height and width for the canvas.
	 */
	private void resizeAction() {
		// create the alert being used for the inputs to toResize
		Alert inputPrompt = new Alert(Alert.AlertType.NONE);
		inputPrompt.setTitle("Change Canvas Size");
		inputPrompt.setHeaderText("Enter Height and Width of new canvas");

		// create the grid being shown in the alert
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);

		TextField heightField = new TextField();
		TextField widthField = new TextField();

		grid.add(new Label("Height:"), 0, 0);
		grid.add(heightField, 1, 0);
		grid.add(new Label("Width:"), 0, 1);
		grid.add(widthField, 1, 1);

		inputPrompt.getDialogPane().setContent(grid);

		inputPrompt.getButtonTypes().add(ButtonType.OK);

		// Show the dialog and wait for the user's response
		Optional<ButtonType> result = inputPrompt.showAndWait();

		if (result.isPresent() && result.get() == ButtonType.OK) {
			// Process the user input (parse and use height and width)
			try {
				double newHeight = Double.parseDouble(heightField.getText());
				double newWidth = Double.parseDouble(widthField.getText());

				resizeCanvas(newWidth, newHeight);

			} catch (NumberFormatException e) {
				// Handle invalid input (non-numeric values)
				System.out.println("Invalid input. Please enter numeric values.");
			}
		}
	}

	/**
	 * Copies the canvas and contents of it onto a new properly sized canvas
	 * before setting the new one as the one being used
	 * @param width is the width in pixels of the canvas
	 * @param height is the height in pixels of the canvas
	 */
	private void resizeCanvas(double width, double height) {
		GraphicsContext gc = drawingCanvas.getGraphicsContext2D();

		DrawingCanvas newCanvas = new DrawingCanvas(width, height);
		GraphicsContext newGC = newCanvas.getGraphicsContext2D();

		// Transfer content from old canvas to new canvas
		newGC.drawImage(drawingCanvas.snapshot(null, null), 0, 0);

		drawingCanvas = newCanvas;
		gc = newGC;

		setBottom(drawingCanvas);

        parentGUI.resizeWindow(width, height);
	}

	/**
	 * Handles the required action for a shape button being clicked.
	 * 
	 * @param Action event of a shape button being clicked.
	 */
	private void shapeAction(ActionEvent event) {
		MenuItem clickedMenuItem = (MenuItem) event.getSource();
		Color shapeColor = colorPicker.getValue();
		double sliderSize = brushSizeSlider.getValue(); // This line gets the value from the brush size slider

		switch (clickedMenuItem.getText()) {
		case "Circle":
			drawingCanvas.setDrawingTool(new Circle(shapeColor, sliderSize));
			break;
		case "Triangle":
			drawingCanvas.setDrawingTool(new Triangle(shapeColor, sliderSize));
			break;
		case "Rectangle":
			drawingCanvas.setDrawingTool(new Rectangle(shapeColor, sliderSize));
			break;
		case "Square":
			drawingCanvas.setDrawingTool(new Square(shapeColor, sliderSize));
			break;
		default:
			break;
		}
	}

	/**
	 * Creates ColorPicker and Slider components, and adds them to the second menu
	 * bar.
	 *
	 * @param secondMenuBar The MenuBar to which components are added.
	 * @param fillIcon      The icon for the Fill button.
	 * @param textIcon      The icon for the Text button.
	 * @param eraserIcon    The icon for the Eraser button.
	 */
	private void createColorPickerAndBrushSlider(MenuBar secondMenuBar, ImageView select, ImageView fillIcon,
			ImageView textIcon, ImageView brushIcon, ImageView eraserIcon) {
		// Create menus with icons
		Menu selectMenu = new Menu("", selectButton);
		Menu resizeMenu = new Menu("", resizeButton);
		Menu fillMenu = new Menu("", fillButton);
		Menu textMenu = new Menu("", textButton);
		Menu brushMenu = createBrushesMenu();
		Menu eraserMenu = new Menu("", eraserButton);
		Menu shapeMenu = createShapeMenu();
		Menu colorPickerMenu = new Menu("", colorPicker);
		Menu brushSizeMenu = new Menu("", brushSizeSlider);

		secondMenuBar.getMenus().addAll(selectMenu, fillMenu, textMenu, shapeMenu, brushMenu, eraserMenu,
				colorPickerMenu, brushSizeMenu, resizeMenu);

		initializeColorPicker();
	}

	/**
	 * Initializes the ColorPicker action and brushSizeSlider listener.
	 */
	private void initializeColorPicker() {
		colorPicker.setOnAction(event -> colorAction(colorPicker.getValue()));

		brushSizeSlider.valueProperty().addListener((observable, oldNum, newNum) -> {
			drawingCanvas.setStrokeSize(newNum.doubleValue());
			DrawingTool drawingTool = drawingCanvas.getDrawingTool();
			if (drawingTool != null) {
				drawingTool.setSize(brushSizeSlider.getValue());
			}
			// sets the global slider value
			sliderListener(newNum);
		});
	}

	/**
	 * Listens to changes in the slider value and updates the drawing canvas stroke
	 * size.
	 *
	 * @param newNum The new slider value.
	 */
	private void sliderListener(Number newNum) {
		// sets the global slider value
		drawingCanvas.setStrokeSize(newNum.doubleValue());
		newNum.doubleValue();
	}

	/**
	 * Handles the action when a new color is selected in the color picker.
	 *
	 * @param color The selected color.
	 */
	private void colorAction(Color color) {
		// Get the current drawing tool
		DrawingTool currentTool = drawingCanvas.getDrawingTool();

		// If the current tool is a type of brush, update its color
		if (currentTool instanceof Brush brush) {
			brush.setColor(color);

		} else if (currentTool instanceof WatercolorBrush watercolorBrush) {
			watercolorBrush.setColor(color);

		} else if (currentTool instanceof SprayPaintBrush sprayPaintBrush) {
			sprayPaintBrush.setColor(color);

		} else if (currentTool instanceof Rectangle rectangle) {
			rectangle.setColor(color);

		} else if (currentTool instanceof Circle circle) {
			circle.setColor(color);

		} else if (currentTool instanceof Square square) {
			square.setColor(color);

		} else if (currentTool instanceof Triangle triangle) {
			triangle.setColor(color);
		}
		// Update the drawing tool on the canvas
		drawingCanvas.setDrawingTool(currentTool);
	}

	/**
	 * Saves the current state of the drawingCanvas by exporting it to a file using
	 * the associated DrawingExplorer.
	 */
	public void saveCanvas() {
		drawingExplorer.exportDrawing(drawingCanvas);
	}

	/**
	 * Loads a previously saved drawing from a file into the drawingCanvas using the
	 * associated DrawingExplorer.
	 */
	private void loadCanvas() {
		clearCanvas();
		drawingExplorer.importDrawing(drawingCanvas);
	}
}