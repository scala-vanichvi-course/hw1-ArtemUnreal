import converter.Errors.{MoneyAmountShouldBePositiveException, UnsupportedCurrencyException, WrongCurrencyException}
import converter.Money
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class MoneySpec extends AnyFlatSpec with Matchers {

  "money constructor" should "throw MoneyAmountShouldBePositiveException if amount is negative" in {
    assertThrows[MoneyAmountShouldBePositiveException](Money(-10, "RUB"))
    Money(10, "RUB")
    Money(0, "RUB")
  }
  "money constructor" should "trow UnsupportedCurrencyException if currency is not supported" in {
    assertThrows[UnsupportedCurrencyException](Money(5, "GBP"))
    assertThrows[UnsupportedCurrencyException](Money(0, "USDT"))
  }

  "money +" should "correctly sum amounts of two Money values" in {
    val rub = Money(10, "RUB")
    val rub1 = Money(15, "RUB")
    val rub2 = Money(0, "RUB")
    val usd = Money(3, "USD")
    val usd1 = Money(5, "USD")
    (rub + rub).amount shouldEqual 20
    (rub + rub1).amount shouldEqual 25
    (rub + rub2).amount shouldEqual 10
    (usd + usd1).amount shouldEqual 8
    assertThrows[WrongCurrencyException](rub + usd)
  }
  "money -" should "" in {
    val rub = Money(10, "RUB")
    val rub1 = Money(15, "RUB")
    val rub2 = Money(0, "RUB")
    val usd = Money(3, "USD")
    val usd1 = Money(5, "USD")
    (rub1 - rub).amount shouldEqual 5
    (rub - rub2).amount shouldEqual 10
    (usd1 - usd).amount shouldEqual 2
    assertThrows[WrongCurrencyException](rub - usd)
    assertThrows[MoneyAmountShouldBePositiveException](rub - rub1)
    assertThrows[MoneyAmountShouldBePositiveException](usd - usd1)
  }
}
