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

import java.util.EmptyStackException;

/**
 * This class tests the methods in the DrawingStack class and the Canvas class.
 * 
 * @author Weihang Guo
 *
 */
public class AsciiArtTester {
  
  /**
   * Test the methods in the DrawingStack class.
   * @return true when the methods run correctly, false otherwise.
   */
  public static boolean testStackPushPeek(){
    DrawingStack test = new DrawingStack();//creates a new empty DrawingStack
    boolean result = false;//stores the test result
    
    //test the case when the stack is empty
    try{
      test.peek();
    } catch(EmptyStackException e) {
      result = true;//the expected behavior is an EmptyStackException being thrown
    }
    
    //test the case when the stack has only one element
    test.push(new DrawingChange(0, 0, 'A', 'B'));
    if (test.peek().newChar != ('B')) {
      return false;
    }
    
    //test the case when the stack has two elements
    test.push(new DrawingChange(0, 0, 'B', 'C'));
    if (test.peek().newChar != 'C') {
      return false;
    }
    
    return result;
    
  }
  
  /**
   * Test the methods in the Canvas class.
   * @return true when the methods run correctly, false otherwise.
   */
  public static boolean runAsciiArtTestSuite() {
    
    return testConstructor() && testDraw() && testUndo() && testRedo() && testIterator();
  }
  
  /**
   * Test the constructor of Canvas class.
   * @return true when the method runs correctly, false otherwise.
   */
  public static boolean testConstructor() {
    boolean result = false;//store the test result
    Canvas test = new Canvas(4, 4);//create a 4 * 4 canvas
    if (test.width() != 4 || test.height() != 4) {
      return false;//test if the width and height are exactly the given integers
    }
    try {
      Canvas valid = new Canvas(1, 1);//test the constructor when passing in valid parameters
    } catch(IllegalArgumentException e) {
      return false;//The width and height are positive, so it shouldn't throw an exception
    }
    try {
      Canvas invalid1 = new Canvas(0, 1);//test the constructor when passing in invalid parameters
    } catch(IllegalArgumentException e) {
      result = true;//the width is 0, so it should throw an exception
    }
    try {
      Canvas invalid2 = new Canvas(1, -1);//test the constructor when passing in invalid parameters
    } catch(IllegalArgumentException e) {
      return result;//the height is negative, so it should throw an exception
    }
    return false;
  }
  
  /**
   * Test the method draw() of Canvas class.
   * @return true when the method runs correctly, false otherwise.
   */
  public static boolean testDraw() {
    boolean result = false;//store the test result
    Canvas test = new Canvas(4, 4);//create a 4 * 4 canvas
    test.draw(3, 3, 'A');//draw 'A' at the position (3, 3)
    if (test.drawingArray()[3][3] != 'A') {
      return false;//test if A is drawn at the right position
    }
    test.draw(3, 3, 'B');//draw 'A' at the position (3, 3)
    if (test.drawingArray()[3][3] != 'B') {
      return false;//test if the character at (3, 3) has been changed to 'B'
    }
    if (test.undoStack().peek().newChar != 'B') {
      return false;//test if the drawing has been recorded in the undo stack
    }
    if (test.redoStack().size() != 0) {
      return false;//test if the redo stack has been cleared
    }
    try {
      test.draw(0, 0, 'A');//draw 'A' at the position (0, 0)
    } catch(IllegalArgumentException e) {
      return false;//(0, 0) is within the canvas, so it shouldn't throw an exception
    }
    try {
      test.draw(-1, 0, 'B');//draw 'B' at the position(-1, 0)
    } catch(IllegalArgumentException e) {
      result = true;//(-1, 0) is outside the canvas, so it should throw an exception
    }
    try {
      test.draw(3, 4, 'R');//draw 'R' at the position(3, 4)
    } catch(IllegalArgumentException e) {
      return result;//(3, 4) is outside the canvas, so it should throw an exception
    }
    return false;
  }
  
  /**
   * Test the method undo() of Canvas class.
   * @return true when the method runs correctly, false otherwise.
   */
  public static boolean testUndo() {
    Canvas test = new Canvas(5, 5);//create a 5 * 5 canvas
    if (test.undo()) {//no drawing has been made, so undo should fail
      return false;
    }
    test.draw(1, 1, 'A');//draw 'A' at the position (1, 1)
    if(!test.undo()) {//undo the previous drawing and check if the undo is success
      return false;
    }
    if (test.drawingArray()[1][1] != ' ') {
      return false;//check if the previous drawing has been undone
    }
    if (test.redoStack().peek().newChar != 'A') {
      return false;//check if the redo stack has been updated
    }
    if (test.undoStack().size() != 0) {
      return false;//check if this action has been popped off the undo stack
    }
    return true;
    
  }
  
  /**
   * Test the method redo() of Canvas class.
   * @return true when the method runs correctly, false otherwise.
   */
  public static boolean testRedo() {
    Canvas test = new Canvas(3, 3);//create a 3 * 3 canvas
    test.draw(2, 2, 'E');//draw 'E' at the position (2, 2)
    test.draw(2, 2, 'Y');//draw 'Y' at the position (2, 2)
    if (test.redo()) {//No undo has been made, so redo should fail
      return false;
    }
    test.undo();//undo drawing 'Y'
    test.redo();//redo drawing 'Y'
    if (test.drawingArray()[2][2] != 'Y') {
      return false;//check if drawing 'Y' has been redone
    }
    if (test.redoStack().size() != 0) {
      return false;//check if this action has been popped off the redo stack
    }
    if (test.undoStack().peek().newChar != 'Y') {
      return false;//check if this action has been pushed into the undo stack
    }
    return true;
  }
  
  /**
   * Test the Iterator.
   * @return true when the method runs correctly, false otherwise.
   */
  public static boolean testIterator() {
    DrawingStack test = new DrawingStack();//create a new DrawingStack
    String result = "";//store the string to be formed
    //add three elements into the stack
    test.push(new DrawingChange(1, 2, 'A', 'B'));
    test.push(new DrawingChange(3, 4, 'F', 'E'));
    test.push(new DrawingChange(5, 0, 'R', 'C'));
    //traverse the stack
    for(DrawingChange element: test) {
      result = result + element.newChar;
    }
    if (!result.equals("CEB")) {
      return false;//checks if the iterator visits the elements correctly
    }
    return true;
  }
  
  /**
   * Print out the results of each test methods.
   * @param args input arguments
   */
  public static void main(String[] args) {
    System.out.println(testStackPushPeek() + " " + runAsciiArtTestSuite());
    System.out.println("draw: " + testDraw());
    System.out.println("constructor: " + testConstructor());
    System.out.println("undo: " + testUndo());
    System.out.println("redo: " + testRedo());
  }
}
