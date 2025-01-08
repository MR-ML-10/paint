package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controller_view.DrawingCanvas;
import javafx.scene.paint.Color;

/**
 * DrawingCanvasTest: JUnit tests for the DrawingCanvas class.
 * 
 * This test class contains unit tests for the DrawingCanvas class, which is
 * responsible for providing functionality for drawing on a canvas using various
 * drawing tools.
 * 
 * @author Doom Al Rajhi
 * @FILE: DrawingCanvasTest.java
 * @ASSIGNMENT: Paint Application Final Project
 * @COURSE: CSC 335 Fall 2023
 * @DATE: 11/08/2023
 *
 * @version 1.0
 */
public class DrawingCanvasTest {

	// Create a DrawingCanvas instance for testing.
	private DrawingCanvas drawingCanvas;

	/**
	 * Set up the test environment before each test method.
	 */
	@BeforeEach
	public void setUp() {
		drawingCanvas = new DrawingCanvas(800, 600);
	}

	/**
	 * Test the initialization of the DrawingCanvas.
	 */
	@Test
	public void testInitialization() {
		// Ensure that the DrawingCanvas instance is not null.
		assertNotNull(drawingCanvas);

		// Check if the canvas has the expected width and height.
		assertEquals(800, drawingCanvas.getWidth());
		assertEquals(600, drawingCanvas.getHeight());
	}

	/**
	 * Test setting and getting the pen color.
	 */
	@Test
	public void testPenColor() {
		// Arrange: Define the expected pen color.
		Color expectedColor = Color.RED;

		// Act: Set the pen color in the DrawingCanvas.
		drawingCanvas.setPenColor(expectedColor);

		// Assert: Check if the pen color is correctly set.
		assertEquals(expectedColor, drawingCanvas.getPenColor());

		// Additionally, verify that the canvas's GraphicsContext has the same stroke
		// and fill color.
		assertEquals(expectedColor, drawingCanvas.getGraphicsContext2D().getStroke());
		assertEquals(expectedColor, drawingCanvas.getGraphicsContext2D().getFill());
	}

}