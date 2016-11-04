package todd
import org.scalatest._

class ProductTest extends FunSpec with ShouldMatchers {
  
  it("defines cola") {
    Cola.name should be("Cola")
    Cola.cost should be(100)
    Cola.quantity should be(1)
  }
  
}