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
import java.util.Iterator;

/**
 *  An instance of this class represents a stack of drawings.
 * 
 * @author Weihang Guo
 *
 */
public class DrawingStack implements StackADT<DrawingChange>, java.lang.Iterable<DrawingChange>{
  private LinkedNode<DrawingChange> top;//the top of the stack
  private int size;//the size of the stack
  
  /**
   * Iterates the drawing stack.
   * return an iterator
   */
  @Override
  public Iterator<DrawingChange> iterator() {
    return new DrawingStackIterator(top);//Creates an iterator with the top element in the stack.
  }

  /**
   * Add a DrawingChange to this stack
   * 
   * @param element an DrawingChange to be added
   * @throws java.util.IllegalArgumentException with a descriptive error message if the input
   *         element is null
   */
  @Override
  public void push(DrawingChange element) {
    if (element == null) {//checks if the input element is null
      throw new IllegalArgumentException("The new element to be added must not be null");
      //throw an IllegalArgumentExeption
    }
    if (isEmpty()) {//when the stack is empty, the added element is the only element
      top = new LinkedNode<DrawingChange>(element);//set top to the new element
    } else {//when the stack is not empty
      top = new LinkedNode<DrawingChange>(element, top);
      //set the new element's next element to the current top and set top to the new element
    }
    size ++;//increases size by one
  }

  /**
   * Remove the DrawingChange on the top of this stack and return it
   * 
   * @return the DrawingChange removed from the top of the stack
   * @throws java.util.EmptyStackException without error message if the stack is empty
   */
  @Override
  public DrawingChange pop() {
    if (isEmpty()) {//check if the stack is empty
      throw new EmptyStackException();//throw an EmptyStackException
    }
    
    DrawingChange curTop = top.getData();//stores the current top's data
    if (size() == 1) {//when the stack has only one element, the only element is removed
      top = null;//set top to null
    } else{//when the stack has more than one element, the top is removed
      top = top.getNext();//set top to the top's next element
    }
    
    size --;//decreases size by one
    return curTop;//return the current top
  }

  /**
   * Get the DrawingChange on the top of this stack
   * 
   * @return the DrawingChange on the stack top
   * @throws java.util.EmptyStackException without error message if the stack is empty
   */
  @Override
  public DrawingChange peek() {
    if (isEmpty()) {//check if the stack is empty
      throw new EmptyStackException();//throw an EmptyStackException
    }
    return top.getData();//return the top's data
  }

  /**
   * Check whether this stack is empty or not
   * 
   * @return true if this stack contains no elements, otherwise false
   */
  @Override
  public boolean isEmpty() {
    if (size() == 0) {//when the size is 0, the stack is empty
      return true;
    }
    return false;//when the size is not 0, the stack is not empty
  }

  /**
   * Get the number of elements in this stack
   * 
   * @return the size of the stack
   */
  @Override
  public int size() {
    return size;
  }

}
