package supervisor

import akka.actor._
import akka.actor.SupervisorStrategy._
import scala.concurrent.duration._
import scala.language.postfixOps

/* 
  Implement the missing parts of the actor Supervisor below that 
  creates a child of type A called child, implements a sane 
  supervisor strategy and terminates if the child actor terminates. 
*/

/** An exception thrown by the child for a temporary problem. */
class TransientException extends Exception {}

/** An exception thrown by the child to indicate a corrupted state (serious problem). */
class CorruptedException extends Exception {}

class Supervisor[A <: Actor : scala.reflect.ClassTag] extends Actor {

  val child: ActorRef = context.actorOf(Props[A](), "Child")

  override def preStart() = {
    context.watch(child)
  }

  override val supervisorStrategy = AllForOneStrategy() {
    case t: TransientException =>
      Resume
    case t: CorruptedException =>
      Restart
  }

  def receive = {
    case Terminated(child) =>
      context.stop(this.self)
  }

}
