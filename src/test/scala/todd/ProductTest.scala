package todd
import org.scalatest._

class ProductTest extends FunSpec with ShouldMatchers {
  
  it("defines products") {
    Cola.name should be("Cola")
    Cola.cost should be(100)
    Cola.quantity should be(0)
  }
  
  it("lets you set the number of products available") {
    Chips.quantity = 10
  }
  
}