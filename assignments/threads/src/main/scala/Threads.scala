package threads

import scala.collection.mutable.ListBuffer

/* 
 * Computer systems are expected to support multitasking. For example,
 * a personal computer is expected to play music, edit text, fetch and 
 * and notify of email all at the same time. Even a single application such as
 * an audio player has to do multiple tasks at the same time including fetching 
 * the stream containing the music, decompressing the music and accepting user 
 * commands simultaneously. Such software are called concurrent software. 
 * Many programming languages such as Java and Scala have a built-in support for
 * programming concurrent applications. 
 * 
 * One of the basic units of execution in concurrent applications are Threads.
 * Threads are the smallest set of instructions that can be executed and managed 
 * independently by a computer system. 
 *  
 *    
 * Task: In this exercise we implement a parallelMap using Threads. The task is to 
 * start threads that run the function f (passed as first parameter) on each element of 
 * the array as (passed as a second parameter) and produce bs (the return value). Note 
 * that you have to create a separate thread for each element of the array as. Also remember
 * to start and join the threads. Note also that Scala inherits its low level concurrency 
 * mechanisms including Threads from Java. 
 * 
 * Hint: You may use Thread and Runnable. See https://docs.oracle.com/javase/7/docs/api/java/lang/Thread.html.
 */

class Threads {
  /* Starts threads to apply f to each element of array as to produce a new array bs */
  def parallelMap[A, B: scala.reflect.ClassTag](f: A => B, as: Array[A]): Array[B] = {
    val threads = new Array[Thread](as.knownSize)
    val bs = new Array[B](as.knownSize)
    for ((a, i) <- as.view.zipWithIndex) {
      val thread = new Thread {
        override def run(): Unit = {
          bs(i) = f(a)
        }
      }
      threads(i) = thread
      thread.start()
    }
    for (t <- threads) {
      t.join()
    }
    bs
  }
}
