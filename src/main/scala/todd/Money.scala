package todd

/*
Quarter
24.26 mm
5.67 g
1965–present

Nickel
21.21 mm
5 g
1866–present

Dime
17.91 mm
2.268 g
1965–present	

Small Cent
19.05 mm
2.5 g
1857–present 
 */

sealed trait CoinShape {}
case object ROUND extends CoinShape
case object NON_ROUND extends CoinShape

case class Coin(shape: CoinShape, size: Double, weight: Double) {
  val valid = if(shape == NON_ROUND) false else {
    if(size == 24.26) true else false
  }
}
