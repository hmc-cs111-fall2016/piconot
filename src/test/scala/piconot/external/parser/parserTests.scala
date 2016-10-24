package piconot.external.parser

import org.scalatest._
import picolib.semantics._
import piconot.external._

/**
  * Created by tmf on 10/23/16.
  */
class parserTests extends FunSuite with Matchers {
  // some syntactic sugar for expressing parser tests
  implicit class ParseResultChecker(input: String) {
    def ~>(output: List[List[Rule]]) = {
      val result = PiconotParser(input)
      result.successful && result.get == output
    }
  }

  test("single move statement") {
    "move E" ~> List(List(
      Rule(
        State("0"),
        Surroundings(Anything, Anything, Anything, Anything),
        East,
        State("1"))))
  }

  test("single while statement with one move statement") {
    """while (free E) {
          move E
       }
    """ ~> List(List(
      Rule(
        State("0"),
        Surroundings(Anything, Open, Anything, Anything),
        StayHere,
        State("1")
      ),
      Rule(
        State("0"),
        Surroundings(Anything, Anything, Anything, Anything),
        East,
        State("1")),
      Rule(
        State("1"),
        Surroundings(Anything, Blocked, Anything, Anything),
        StayHere,
        State("0")
      )

    ))
  }

  test("single while followed by a move") {
    """while (free E) {
          move E
       }
       move S
    """ ~> List(
      List(
      Rule(
        State("0"),
        Surroundings(Anything, Open, Anything, Anything),
        StayHere,
        State("1")
      ),
      Rule(
        State("0"),
        Surroundings(Anything, Anything, Anything, Anything),
        East,
        State("1")),
      Rule(
        State("1"),
        Surroundings(Anything, Blocked, Anything, Anything),
        StayHere,
        State("0")
      )

    ),
      List(Rule(
        State("0"),
        Surroundings(Anything, Anything, Anything, Anything),
        South,
        State("1")))
    )
  }

  test("blocked condition in while loop") {
    """while (blocked E) {
          move N
        }
    """ ~> List(List(
      Rule(
        State("0"),
        Surroundings(Anything, Blocked, Anything, Anything),
        StayHere,
        State("1")
      ),
      Rule(
        State("0"),
        Surroundings(Anything, Anything, Anything, Anything),
        North,
        State("1")),
      Rule(
        State("1"),
        Surroundings(Anything, Open, Anything, Anything),
        StayHere,
        State("0")
      )

    ))
  }

  test("nested while loop") {
    """while (free E) {
          while (free S) {
            move S
          }
        }
    """ ~> List(List(
      Rule(
        State("0"),
        Surroundings(Anything, Open, Anything, Anything),
        StayHere,
        State("1")
      ),
      Rule(
        State("0"),
        Surroundings(Anything, Anything, Anything, Open),
        StayHere,
        State("1")),
      Rule(
        State("0"),
        Surroundings(Anything, Anything, Anything, Anything),
        South,
        State("1")
      ),
      Rule(
        State("1"),
        Surroundings(Anything, Anything, Anything, Blocked),
        StayHere,
        State("0")
      ),
      Rule(
        State("1"),
        Surroundings(Anything, Blocked, Anything, Anything),
        StayHere,
        State("0")
      )

    ))
  }

}
