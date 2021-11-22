package lifecycle

import akka.actor._
import akka.actor.SupervisorStrategy._
import scala.concurrent.duration._
import akka.actor.OneForOneStrategy._
import scala.language.postfixOps

/*
  The following actor(LifeCycler) has a child of type Other which is defined 
  in the file Other.scala. Your task is to get the child actor print the string "SCALA". 

  Hint: Looking at the Other.scala you can notice that the Other actor will print 
  "SCALA" if its lifecycle goes as follows: preStart, receive 1, preRestart, postRestart 
  and  postStop. Also, remember to use the appropriate supervision strategy to handle 
  the exception thrown by this actor in the LifeCycler actor.  
*/

class LifeCycler[A <: Actor : scala.reflect.ClassTag](sink: ActorRef) extends Actor {
  // TODO: Implement class

  val child = context.actorOf(Props(classOf[Other], sink), "child")
  var c = 0

  override def preStart() = {
    child ! 1
    child ! 0
  }

  override val supervisorStrategy = AllForOneStrategy() {
    case t: ExceptionA =>
      Stop
    case t: ExceptionB =>
      Restart
  }

  def receive = {
    case msg: Any =>
  }
}
