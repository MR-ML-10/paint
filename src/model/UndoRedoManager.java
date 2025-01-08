package model;

import java.util.Stack;

import javafx.scene.image.WritableImage;

/**
 * UndoRedoManager: Manages undo and redo functionality for user actions.
 *
 * This class is responsible for managing the undo and redo functionality for
 * user actions. It maintains two stacks, one for undoing actions and another
 * for redoing actions. Users can undo the most recent action and redo
 * previously undone actions.
 *
 * @author Doom Al Rajhi and Tanay Desai
 * @file: UndoRedoManager.java
 * @assignment: Paint Application Final Project
 * @course: CSC 335 Fall 2023
 * @date: 11/30/2023
 * @version 1.6
 */
public class UndoRedoManager {
	private Stack<Action> undoStack; // Stack for storing actions to undo.
	private Stack<Action> redoStack; // Stack for storing actions to redo.

	/**
	 * Constructor for the UndoRedoManager class.
	 */
	public UndoRedoManager() {
		undoStack = new Stack<>();
		redoStack = new Stack<>();
	}

	/**
	 * Undo the most recent action.
	 *
	 * @param image The current state of the canvas as a WritableImage.
	 * @return The state of the canvas before the undo operation as a WritableImage.
	 *         Returns null if there's nothing to undo.
	 */
	public WritableImage undo(WritableImage image) {
		if (canUndo()) {
			Action action = undoStack.pop();
			redoStack.push(new Action(image));
			return action.getWritableImage();
		}
		return null;
	}

	/**
	 * Redo the previously undone action.
	 *
	 * @param image The current state of the canvas as a WritableImage.
	 * @return The state of the canvas after the redo operation as a WritableImage.
	 *         Returns null if there's nothing to redo.
	 */
	public WritableImage redo(WritableImage image) {
		if (canRedo()) {
			Action action = redoStack.pop();
			undoStack.push(new Action(image));
			return action.getWritableImage();
		}
		return null;
	}

	/**
	 * Check if there are actions that can be undone.
	 *
	 * @return true if there are actions that can be undone, false otherwise.
	 */
	public boolean canUndo() {
		return !undoStack.isEmpty();
	}

	/**
	 * Check if there are actions that can be redone.
	 *
	 * @return true if there are actions that can be redone, false otherwise.
	 */
	public boolean canRedo() {
		return !redoStack.isEmpty();
	}

	/**
	 * Add a new action to the undo stack.
	 *
	 * @param image The current state of the image.
	 */
	public void addNewAction(WritableImage image) {
		undoStack.push(new Action(image));
		redoStack.clear();

	}

	/**
	 * class to represent user actions for undo and redo.
	 */
	private class Action {
		private WritableImage writableImage;

		/**
		 * Constructor for the Action class.
		 *
		 * @param writableImage The image state associated with the action.
		 */
		public Action(WritableImage writableImage) {

			this.writableImage = writableImage;
		}

		/**
		 * Get the image state associated with the action.
		 *
		 * @return The image state.
		 */
		public WritableImage getWritableImage() {
			return writableImage;
		}
	}
}
