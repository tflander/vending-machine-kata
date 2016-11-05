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
  
  it("lets you check if inventory is available") {
    val inventory = new Inventory(initialColas=1, initialChips=0, initialCandy=3)
    inventory.isAvailable(Cola) should be(true)
    inventory.isAvailable(Chips) should be(false)
    inventory.isAvailable(Candy) should be(true)
  }

  it("lets you check if inventory is sold out") {
    val inventory = new Inventory(initialColas=1, initialChips=0, initialCandy=3)
    inventory.isSoldOut(Cola) should be(false)
    inventory.isSoldOut(Chips) should be(true)
    inventory.isSoldOut(Candy) should be(false)
  }
  
  
}