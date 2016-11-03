package todd
import org.scalatest._

class VendingMachineTest extends FunSpec with ShouldMatchers {
  
  val vendingMachine = new VendingMachine()
  
  describe("display tests") {
    
    it("initially displays 'INSERT COIN'") {
      vendingMachine.display should be("INSERT COIN")
    }
    
  }
}