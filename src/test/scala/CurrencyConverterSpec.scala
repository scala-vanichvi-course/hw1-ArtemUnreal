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

    exchangedRub.amount shouldEqual 145

  }
}
