package picobot.external.parser

import picobot.external.ir._
import org.scalatest._
import picolib.{semantics => API}

class PicoParserSpec extends FlatSpec with Matchers {

  "PicoParser" should "parse a single block with one rule" in {
    val str = """
        # start
        start {
          E_ -> E;
        }"""
    val expectedChunk = StateChunk("start", List(Rule(
                API.Surroundings(API.Anything, API.Open, API.Anything, API.Anything), 
                Some("E"), None)))
    PicoParser(str).get should be (Prog("start", List(expectedChunk)))
  }

  "PicoParser" should "parse a single block with multiple rules" in {
    val str = """
        # start
        start {
          E_ -> E;
          W_ -> W;
        }"""
    val rule1 = Rule( 
      API.Surroundings(API.Anything, API.Open, API.Anything, API.Anything), 
      Some("E"), 
      None)
    val rule2 = Rule( 
      API.Surroundings(API.Anything, API.Anything, API.Open, API.Anything), 
      Some("W"), 
      None)

    val parsedRules = PicoParser(str).get.chunks.head.rules 
    parsedRules should contain (rule1)
    parsedRules should contain (rule2)
  }

  "PicoParser" should "parse multiple chunks" in {
    val str = """
        # start
        start {
          E_ -> E;
          W_ -> W then;
        }
        then {
          E! W_ -> start;
          SEWN_ -> N;
        }"""

    val rule1 = Rule( 
      API.Surroundings(API.Anything, API.Open, API.Anything, API.Anything), 
      Some("E"), 
      None)
    val rule2 = Rule( 
      API.Surroundings(API.Anything, API.Anything, API.Open, API.Anything), 
      Some("W"), 
      Some("then"))
    val rule3 = Rule(
      API.Surroundings(API.Anything, API.Blocked, API.Open, API.Anything), 
      None,
      Some("start"))
    val rule4 = Rule( 
      API.Surroundings(API.Open, API.Open, API.Open, API.Open), 
      Some("N"),
      None)
    val chunks = PicoParser(str).get.chunks
    val block0rules = chunks(0).rules
    val block1rules = chunks(1).rules
    block0rules should contain (rule1)
    block0rules should contain (rule2)
    block1rules should contain (rule3)
    block1rules should contain (rule4)
  }
}



