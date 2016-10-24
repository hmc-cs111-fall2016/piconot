package piconot.internal

import org.scalatest.FunSuite
import org.scalatest.Matchers

import Piconot._

class SyntaxSuite extends FunSuite with Matchers {
  test("move") {
    (move(North)) should be (Move(North))
  }

  test("face") {
    (face(South)) should be (Face(South))
  }

  test("implicit conversion from StateName to Command") {
    (rule("new_state")) should be (Rule(None, None, Some(Goto("new_state"))))
  }

  test("creating a state") {
    (state ("testState") (
        rule (move(Forward), face(Right), "state2"),
        rule (face(Left))
        )) should be

    State("new_state", List(
        Rule(Some(Move(Forward)), Some(Face(Right)), Some(Goto("state2"))),
        Rule(None, Some(Face(Left)), None)))
  }

  /** test that a program compiles.  The semantics wil be tested later*/
  test("create a program") {
    runPiconot(

        state ("state1") (
            rule(move(Forward), face(Right), "state2"),
            rule("state1")
        ),

        state ("state2") (
            rule(face(East), "state1")
        )
    )
  }

}