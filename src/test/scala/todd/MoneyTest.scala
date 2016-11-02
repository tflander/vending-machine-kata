package todd
import org.scalatest._

class MoneyTest extends FunSpec with ShouldMatchers {
  
  describe("coin validation tests") {
    
    it("identifies a quarter") {
      Coin(ROUND, size=24.26, weight=5.67).valid should be(true)
    }
    
    it("rejects a non-round quarter") {
      Coin(NON_ROUND, size=24.26, weight=5.67).valid should be(false)      
    }
    
    it("rejects a big quarter") {
      Coin(ROUND, size=24.27, weight=5.67).valid should be(false)      
    }
    
  }
  
}