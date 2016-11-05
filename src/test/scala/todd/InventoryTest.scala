package todd
import org.scalatest._

class InventoryTest extends FunSpec with ShouldMatchers {
  
  it("has 10 of each item by default") {
    val inventory = new Inventory
    inventory.quantityFor(Cola) should be(10)
    inventory.quantityFor(Chips) should be(10)
    inventory.quantityFor(Candy) should be(10)
  }
  
  it("can be created with different quantities") {
    val inventory = new Inventory(initialColas=1, initialChips=2, initialCandy=3)
    inventory.quantityFor(Cola) should be(1)
    inventory.quantityFor(Chips) should be(2)
    inventory.quantityFor(Candy) should be(3)    
  }
  
  it("can decrement quantities, but not past zero") {
    val inventory = new Inventory(initialColas=1, initialChips=2, initialCandy=3)
    inventory.removeOne(Cola)
    inventory.quantityFor(Cola) should be(0)
    intercept[IllegalStateException] {
      inventory.removeOne(Cola)      
    }
    
  }
  
}