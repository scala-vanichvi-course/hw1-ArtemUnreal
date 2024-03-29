import converter.Errors.{UnsupportedCurrencyException, WrongCurrencyException}
import converter.{CurrencyConverter, Money}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class CurrencyConverterSpec extends AnyFlatSpec with Matchers {
  "exchange" should "convert money for supported currencies" in {
    val rates = Map(
      "USD" -> Map("RUB" -> BigDecimal(72.5)),
      "RUB" -> Map("USD" -> BigDecimal(1 / 72.5))
    )
    val converter = CurrencyConverter(rates)
    val exchangedRub = converter.exchange(Money(2, "USD"), "RUB")
    val exchangedUsd = converter.exchange(Money(10, "RUB"), "USD")

    exchangedRub.amount shouldEqual 145
    exchangedRub.currency shouldEqual "RUB"
    exchangedUsd.amount shouldEqual BigDecimal(1 / 7.25)
    exchangedUsd.currency shouldEqual "USD"
    assertThrows[WrongCurrencyException](converter.exchange(Money(10, "RUB"), "RUB"))
    assertThrows[UnsupportedCurrencyException](converter.exchange(Money(10, "RUB"), "GBP"))

  }

  "converted constructor" should "throw UnsupportedCurrencyException if rates dictionary contains wrong currency" in {
    val rates = Map(
      "GBP" -> Map("RUB" -> BigDecimal(85))
    )
    assertThrows[UnsupportedCurrencyException] {
      CurrencyConverter(rates)
    }
  }
}
