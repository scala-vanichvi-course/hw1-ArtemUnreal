package converter

import converter.errors._

case class Money private (amount: BigDecimal, currency: String) {
  def +(other: Money): Money = {
    val res = this.amount + other.amount

    if (currency != "USD" && currency != "RUB" && currency != "EUR") {
      throw new UnsupportedCurrencyException
    }

    if (res <= 0) {
      throw new MoneyAmountShouldBePositiveException
    }

    if (isSameCurrency(other)) {
      Money(this.amount + other.amount, currency)
    }
    else {
      throw new WrongCurrencyException
    }
  }

  def -(other: Money): Money = {

    if (currency != "USD" && currency != "RUB" && currency != "EUR") {
      throw new UnsupportedCurrencyException
    }

    if (isSameCurrency(other)) {
      val res = this.amount - other.amount
      if (res > 0) {
        Money(this.amount - other.amount, currency)
      }
      else {
        throw new MoneyAmountShouldBePositiveException
      }
    }
    else {
      throw new WrongCurrencyException
    }
  }

  def isSameCurrency(other: Money): Boolean = this.currency == other.currency
}

