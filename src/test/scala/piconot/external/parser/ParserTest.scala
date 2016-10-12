package piconot.external

import org.scalatest.FunSuite
import org.scalatest.Matchers

import Piconot._

class ParserSuite extends FunSuite with Matchers {

  implicit class ParseResultChecker(input: String) {
    def ->(output: Seq[State]) = {
      val result = PiconotParser(input)
      (result.successful) should be (true)
      (result.get) should be (output)
    }

    def fails = (PiconotParser(input).successful) should be (false)
  }

  def stateNameTest(name: String) = {
    s"state ${name}\n" +
    " | move north" ->
    (Seq(State( name,
      Seq( rule(Move(North))))))
  }

  def ruleTest(syntax: String, ast: Rule) = {
    "state a\n" +
    s" | ${syntax}" ->
    (Seq( State( "a", Seq(ast))))
  }

  def badRuleTest(syntax: String) = {
    "state a\n" +
    s" | ${syntax}" fails
  }

  test("program with one rule") {
    "state startstate" +
     "  | move north" ->
    (Seq(State("startstate", Seq(rule(Move(North))))))
  }

  test("program with one nontrivial rule") {
    "state start" +
    " | move forward, face east, new_state" ->
    (Seq( State( "start",
      Seq( rule( Move(Forward),
                 Face(East),
                 Goto("new_state"))))))
  }

  test("program with one state and multiple rules") {
    "state a" +
    " | move backward" +
    " | face east" +
    " | b" ->
    (Seq( State( "a",
      Seq( rule( Move(Backward)),
           rule( Face(East)),
           rule( Goto("b"))))))
  }

  test("program with multiple states") {
    "state a\n" +
    " | b\n" +
    "state b\n" +
    " | a" ->
    (Seq(
    State( "a",
      Seq( rule(Goto("b")))),
    State( "b",
      Seq( rule(Goto("a"))))))
  }

  test("a statename which is a number") { stateNameTest("0") }
  test("a statename which starts with a number") { stateNameTest("0a") }
  test("a statename which starts with a letter") { stateNameTest("a0") }
  test("a statename with all valid character types") { stateNameTest("state_name123") }

  test("a rule with just a move") { ruleTest("move north", rule(Move(North))) }
  test("a rule with just a face") { ruleTest("face left", rule(Face(Left))) }
  test("a rule with just a goto") { ruleTest("next_state", rule(Goto("next_state"))) }
  test("a rule with a move and a face") {
    ruleTest("move north, face left", rule(Move(North), Face(Left)))
  }
  test("a rule with a move and a goto") {
    ruleTest("move north, next_state", rule(Move(North), Goto("next_state")))
  }
  test("a rule with a face and a goto") {
    ruleTest("face backward, next_state", rule(Face(Backward), Goto("next_state")))
  }
  test("a rule with a move, a face, and a goto") {
    ruleTest("move forward, face left, next_state",
      rule(Move(Forward), Face(Left), Goto("next_state")))
  }

  test("an invalid rule, with a face then a move") {
    badRuleTest("face left, move forward")
  }
  test("an invalid, empty rule") {
    badRuleTest("")
  }
  test("an invalid rule, with two moves") {
    badRuleTest("move forward, move forward")
  }
  test("an invalid rule, with two faces") {
    badRuleTest("face left, face left")
  }
  test("an invalid rule, with two gotos") {
    badRuleTest("next_state, next_next_state")
  }

}