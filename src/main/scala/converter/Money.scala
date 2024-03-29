package converter

import converter.Errors.{MoneyAmountShouldBePositiveException, UnsupportedCurrencyException, WrongCurrencyException}

case class Money private (amount: BigDecimal, currency: String) {
  def +(other: Money): Money = changeAmount(other, this.amount + other.amount)

  def -(other: Money): Money =
    changeAmount(other, this.amount - other.amount)

  private def changeAmount(other: Money, amount: BigDecimal): Money =
    if (isSameCurrency(other))
      Money(amount, this.currency)
    else throw new WrongCurrencyException

  def isSameCurrency(other: Money): Boolean = this.currency == other.currency
}

object Money {

  def apply(amount: BigDecimal, currency: String): Money = {
    if (amount < 0) throw new MoneyAmountShouldBePositiveException
    else if (!Currencies.SupportedCurrencies.contains(currency)) throw new UnsupportedCurrencyException
    else new Money(amount.setScale(17, BigDecimal.RoundingMode.HALF_EVEN), currency)
  }
}
