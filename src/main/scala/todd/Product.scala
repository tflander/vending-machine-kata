package todd

sealed trait Product {
  def name: String
  def cost: Int
  var quantity = 0
}

case object Cola extends Product {
  val name = "Cola"
  val cost = 100
}

case object Chips extends Product {
  val name = "Chips"
  val cost = 50
}

case object Candy extends Product {
  val name = "Candy"
  val cost = 65
}
