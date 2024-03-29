package converter

case class Money private (amount: BigDecimal, currency: String) {
  def +(other: Money): Money = ???

  def -(other: Money): Money = ???

  def isSameCurrency(other: Money): Boolean = this.currency == other.currency
}

