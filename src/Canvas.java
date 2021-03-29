//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title:           P08 Ascii Art
// Files:           AsciiArtTester.java, Canvas.java, DrawingChange.java, 
//                  DrawingStack.java, DrawingStackIterator.java
// Course:          CS300, fall, 2019
//
// Author:          Weihang Guo
// percentage:           wguo63@wisc.edu
// Lecturer's Name: Mouna Kacem
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Students who get help from sources other than their partner must fully 
// acknowledge and credit those sources of help here.  Instructors and TAs do 
// not need to be credited here, but tutors, friends, relatives, room mates, 
// strangers, and others do.  If you received no outside help from either type
//  of source, then please explicitly indicate NONE.
//
// Milks: None
// Online Sources: None
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////

/**
 * This class represents a canvas that allows users to draw on and make undo/redo actions.
 * 
 * @author Weihang Guo
 *
 */
public class Canvas {
  private final int width; // width of the canvas
  private final int height; // height of the canvas
  private char [][] drawingArray; // 2D character array to store the drawing
  private final DrawingStack undoStack; // store previous changes for undo
  private final DrawingStack redoStack; // store undone changes for redo
  
  
  /**
   * Constructor creates a new canvas which is initially blank 
   * @param width the width of the canvas
   * @param height the width of the canvas
   * @throws IllegalArgumentException with a descriptive error message if width or height is 0 
   * or negative.
   */
  public Canvas(int width, int height) {
    if (width <= 0 || height <= 0) {//check if width or height is 0 or negative
      throw new IllegalArgumentException("The width and height of the canvas must be positive");
      //throw an IllegalArgumentException with a descriptive error message
    }
    //initialize all the private fields
    this.width = width;
    this.height = height;
    drawingArray = new char[height][width];
    //initialize all the elements in drawingArray to a space
    for (int row = 0; row < drawingArray.length; row ++) {
      for (int col = 0; col < drawingArray[row].length; col ++) {
        drawingArray[row][col] = ' ';
      }
    }
    undoStack = new DrawingStack();
    redoStack = new DrawingStack();
    
  }
  
  /**
   * Draw a character at the given position drawingArray[row][col]
   * @param row the row to be drawn in
   * @param col the column to be drawn in
   * @param c the character to be drawn at the given position
   * @throws an IllegalArgumentException if the drawing position is outside the canvas
   */
  public void draw(int row, int col, char c) {
    if (row < 0 || row >= height || col < 0 || col >= width) {
      //check if the drawing position is outside the canvas
      throw new IllegalArgumentException("The position must is not available.");
      //throw an IllegalArgumentException
      
    }
    
    char curChar = drawingArray[row][col];//store the current character before overwriting
    drawingArray[row][col] = c;//make the change
    undoStack.push(new DrawingChange(row, col, curChar, c));
    //add a matching DrawingChange to the undoStack
    while(!redoStack.isEmpty()) {
      redoStack.pop();
    }//After making a new change, clear the redoStack
  }
  
  /**
   * Undo the most recent drawing change. Return true if successful, false otherwise.
   * @return true if successful, false otherwise.
   */
  public boolean undo() {
    if (undoStack.size() == 0) {
      return false;//when there is no undo action to make, return false
    }
    
    DrawingChange lastChange = undoStack.peek();//store the last change
    redoStack.push(undoStack.pop());
    //Pop off a change from the undo stack and push it into the redo stack.
    drawingArray[lastChange.row][lastChange.col] = lastChange.prevChar;
    //The content of the drawingArray should be updated accordingly to this change.
    return true;
  }
  
  /**
   * Redo the most recent undone drawing change. Return true if successful. False otherwise.
   * @return true if successful, false otherwise.
   */
  public boolean redo() {
    if (redoStack.size() == 0) {
      return false;//when there is no redo action to make, return false
    }
    
    DrawingChange lastUndo = redoStack.peek();//store the last undo action
    undoStack.push(redoStack.pop());
    //Pop off a change from the redo stack and push it into the undo stack.
    drawingArray[lastUndo.row][lastUndo.col] = lastUndo.newChar;
    //The content of the drawingArray should be updated accordingly to this change.
    return true;
  }
  
  /**
   * Return a printable string version of the Canvas.
   *
   * @return a printable string version of the Canvas
   */
  public String toString() {
    String canvas = "";//store the printable string
    for (char[] row : drawingArray) {//visit every row in the drawingArray
      for (char character: row) {//visit each element in the row
        canvas = canvas + character;//line up elements in each rows
      }
      canvas = canvas + System.lineSeparator();//separate the line after each row is completed
    }
    return canvas;
  }
  
  /**
   * Access width of the canvas.
   * @return width of the canvas
   */
  public int width() {
    return width;
  }
  
  /**
   * Access height of the canvas.
   * @return height of the canvas
   */
  public int height() {
    return height;
  }
  
  /**
   * Access the drawingArray.
   * @return the drawingArray
   */
  public char[][] drawingArray(){
    return drawingArray;
  }
  
  /**
   * Access the undoStack.
   * @return the undoStack
   */
  public DrawingStack undoStack() {
    return undoStack;
  }
  
  /**
   * Access the redoStack.
   * @return the redoStack
   */
  public DrawingStack redoStack() {
    return redoStack;
  }
  
  /**
   * Print out the canvas.
   */
  public void printDrawing() {
    System.out.print(toString());
  }
  
  /**
   * Generate the drawing history.
   */
  public void printHistory() {
    int i = undoStack.size();//store the number of undo actions that can be made
    for(DrawingChange change : undoStack) {//traverse the undoStack
      System.out.println(i + ". draw \'" + change.newChar + "\' on (" + 
          change.row + ", " + change.col + ")");//print out the drawing changes already made
      i --;//go to the previous drawing change
    }
  }


}
