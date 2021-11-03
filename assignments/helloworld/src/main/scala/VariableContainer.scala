package helloworld

/*
 * Task: In this exercise, we implement a class, which takes two variables, x and y,
 * as parameters when it is created. These variables are stored in the class, and can
 * be retrieved with methods getX() and getY(). The variables are both of the same
 * type but the type is also given as a parameter rather than being pre-defined.
 */

class VariableContainer[T] (private val x: T, private val y: T) {
  /* Returns value x that was assigned when the object was build */
  def getX(): T = {
    return x
  }

  /* Returns value y that was assigned when the object was build */
  def getY(): T = {
    return y
  }
}
