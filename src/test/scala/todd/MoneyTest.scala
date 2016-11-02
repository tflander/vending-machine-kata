package todd
import org.scalatest._

class MoneyTest extends FunSpec with ShouldMatchers {
  
  describe("coin validation tests") {
    
    it("identifies a quarter") {
      Coin(ROUND, size=24.26, weight=5.67).valid should be(true)
    }
    
  }
  
}