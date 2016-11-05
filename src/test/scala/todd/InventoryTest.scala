package todd
import org.scalatest._

class InventoryTest extends FunSpec with ShouldMatchers {
  
  it("has 10 of each item by default") {
    val inventory = new Inventory
    inventory.quantityFor(Cola) should be(10)
    inventory.quantityFor(Chips) should be(10)
    inventory.quantityFor(Candy) should be(10)
  }
  
}