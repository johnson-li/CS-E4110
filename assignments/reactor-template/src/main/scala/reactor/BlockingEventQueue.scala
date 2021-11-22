// 123456 Familyname, Firstname

package reactor

import java.util.LinkedList
import scala.collection.{GenSeq, JavaConverters, LinearSeq}
import scala.jdk.javaapi.CollectionConverters
import reactor.api.Event

final class BlockingEventQueue[T](private val capacity: Int) {

  private val queue = new LinkedList[Event[T]]()
  private val emptyLock = new Object()
  private val fullLock = new Object()

  @throws[InterruptedException]
  def enqueue[U <: T](e: Event[U]): Unit = {
    fullLock.synchronized {
      while (queue.size() == capacity) {
        fullLock.wait()
      }
      queue.addLast(e.asInstanceOf[Event[T]])
    }
    emptyLock.synchronized {
      emptyLock.notify()
    }
  }

  @throws[InterruptedException]
  def dequeue: Event[T] = {
    val e = emptyLock.synchronized {
      while (queue.isEmpty) {
        emptyLock.wait()
      }
      queue.pollFirst()
    }
    fullLock.synchronized {
      fullLock.notify()
    }
    e
  }

  def getAll: Seq[Event[T]] = {
    queue.synchronized {
      val copy = CollectionConverters.asScala(queue).toSeq
      queue.clear()
      copy
    }
  }

  def getSize: Int = {
    queue.size
  }

  def getCapacity: Int = {
    capacity
  }
}
