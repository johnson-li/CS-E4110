package lockFree
import instrumentation.monitors.LockFreeMonitor
import atomicReference.SimpleAtomicReference

/*
* One of the main uses of atomic types is to implement synchronization 
* primitives such as semaphores and mutexes. It is also useful to implement
* lock-free algorithms.
*  
* Task: In this exercise we implement a lock-free stack using an atomic reference.
* Assume that you have a class implementing an atomic reference called 
* SimpleAtomicReference with an interface as follows:
*
* class SimpleAtomicReference[V](initValue: V) {
*  protected var value:V
*
*  def compareAndSet(expect: V, update: V): Boolean
*  def get: V
*  def set(newValue: V): Unit 
*}
*
* Hint: refer and use compareAndSet(expect: V, update: V): Boolean method from
* past exercises. 
*/

/* Implement a lock free stack. */
class LockFreeStack[E](capacity: Int) extends LockFreeMonitor {
  class Node[E](val value: E) {
    var next: Node[E] = null
  }
  val top = new SimpleAtomicReference[Node[E]](null)
  // Do not add other variables

  def push(e: E): Unit = {
    val node = new Node[E](e)
    node.next = top.get
    while (!top.compareAndSet(node.next, node)) {
      node.next = top.get
    }
  }

  def pop(): E = {
    var t = top.get
    while (!top.compareAndSet(t, t.next)) {
      t = top.get
    }
    t.value
  }
}
