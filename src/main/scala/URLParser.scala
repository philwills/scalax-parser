import org.parboiled2._
import org.parboiled2.CharPredicate._

class URLParser(val input: ParserInput) extends Parser {

  def URL = rule { URLPattern ~> URLParts }

  def URLPattern = rule {
    Scheme ~ "//" ~ Host ~ optional(Port) ~ Path ~ QueryString ~ optional(Fragment)
  }

  def Path = rule { capture(zeroOrMore('/' ~ zeroOrMore(unreserved))) }

  def Host = rule { capture(oneOrMore(oneOrMore(unreserved)))}

  def Port = rule { ":" ~ capture(oneOrMore(Digit)) ~> (_.toInt) }

  def Scheme = rule { capture(oneOrMore(LowerAlpha)) ~ ':' }

  def QueryString = rule {
    '?' ~ (oneOrMore(KeyValue).separatedBy("&")) ~> ((kvs) => Map(kvs: _*))
  }

  def KeyValue = rule { (Word ~ '=' ~ Word) ~> ((k, v) => (k, v)) }

  def Fragment = rule { '#' ~  capture(oneOrMore(unreserved))}

  def Word = rule { Chars ~> (_.mkString) }

  def Chars = rule { oneOrMore(capture(unreserved) | EscapedChar ~> (_.toString)) }

  val hexToChar = (hexDigits: String) => Integer.parseInt(hexDigits, 16).toChar

  def EscapedChar  = rule{ '%' ~ capture(2.times(HexDigit)) ~>  hexToChar }

  val unreserved = AlphaNum ++ "_-."

}

case class URLParts(scheme: String, host: String, port: Option[Int], path: String, params: Map[String, String], fragment: Option[String])

