package helloworld
import org.scalatest._

class VariableContainerTest extends FlatSpec with BeforeAndAfterAll {
  val x: Int = 3;
  val y: Int = 5;
  val intContainer: VariableContainer[Int] = new VariableContainer[Int](x, y)

  override def beforeAll() {
  }

  override def afterAll() {
  }

  "getX()" should "return assigned value x" in{
    assert(x == intContainer.getX())
  }
}
