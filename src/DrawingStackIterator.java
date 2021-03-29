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

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An iterator to iterate a drawing stack.
 * @author Weihang Guo
 *
 */
public class DrawingStackIterator implements Iterator<DrawingChange> {
  private LinkedNode<DrawingChange> curDrawing;//keeps track of the next element in the iteration
  
  /**
   * Initializes the iterator with the first element
   * @param top represents the top of the stack
   */
  public DrawingStackIterator(LinkedNode<DrawingChange> top) {
    curDrawing = top;//set the current drawing change to the input
  }
  
  /**
   * Returns true if the iteration has more elements.
   * @return true if the iteration has more elements
   */
  @Override
  public boolean hasNext() {
    return curDrawing != null;
    //if the current drawing change is not null, there's more elements; if the current drawing 
    //change is null, there's no more element
  }

  /**
   * Returns the next drawing in the stack.
   * @return the next drawing in the stack
   */
  @Override
  public DrawingChange next() {
    if (!hasNext()) {//checks if the iteration has more elements
      throw new NoSuchElementException("there is no more element in the stack");
    }
    DrawingChange drawing = curDrawing.getData();
    //gets and stores the current drawing change's data
    curDrawing = curDrawing.getNext();
    //updates current drawing change to the next drawing change of current drawing chagne
    return drawing;
  }
  

}
