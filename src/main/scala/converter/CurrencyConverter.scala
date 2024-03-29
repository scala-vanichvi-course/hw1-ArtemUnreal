package converter

import converter.Errors.{UnsupportedCurrencyException, WrongCurrencyException}

class CurrencyConverter(ratesDictionary: Map[String, Map[String, BigDecimal]]) {
  def exchange(money: Money, toCurrency: String): Money = {
    if (!Currencies.SupportedCurrencies.contains(toCurrency)) {
      throw new UnsupportedCurrencyException
    } else if (money.currency == toCurrency) {
      throw new WrongCurrencyException
    } else {
      val newAmount = money.amount * ratesDictionary(money.currency)(toCurrency)
      Money(newAmount, toCurrency)
    }
    val newAmount = money.amount * ratesDictionary(money.currency)(toCurrency)
    Money(newAmount, toCurrency)
  }
}
object CurrencyConverter {
  import Currencies.SupportedCurrencies
  def apply(ratesDictionary: Map[String, Map[String, BigDecimal]]): CurrencyConverter = {
    val fromCurrencies = ratesDictionary.keys
    val toCurrencies = ratesDictionary.values
    if (
      fromCurrencies.toSet
        .subsetOf(SupportedCurrencies) && toCurrencies.forall(_.keys.toSet.subsetOf(SupportedCurrencies))
    )
      new CurrencyConverter(ratesDictionary)
    else throw new UnsupportedCurrencyException
  }
}
