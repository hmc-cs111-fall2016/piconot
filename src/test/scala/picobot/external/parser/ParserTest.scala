package picobot.external.parser

//import java.token.parsers
import picobot.external.ir._
import org.scalatest._

class PicoParserSpec extends FlatSpec with Matchers {

  "PicoParser" should "parse a single block" in {
    val str = """
        # start
        start {
          E_ -> E;
        }"""
    val expectedChunk = StateChunk("start", List(Rule(
                API.Surroundings(API.Anything, API.Open, API.Anything, API.Anything), 
                Some(API.East), None)))
    PicoParse(str) should be Prog("start", List(expectedChunk))
  }
}



