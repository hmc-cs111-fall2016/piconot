package piconot.external.parser

import scala.language.postfixOps
import scala.util.parsing.combinator.{PackratParsers, RegexParsers}
import picolib.semantics._

/*
 * Syntax for a valid Picobot rule:
 *
 *         stmts ::= "while" "(" expr ")" body | "move" dir
 *          expr ::= "free" dir | "blocked" dir
 *           dir ::= "N" | "S" | "E" | "W"
 *          body ::= "{" body | stmts body | "}"
 *
 */

object PiconotParser extends RegexParsers{// with PackratParsers{
  // carried over from internal
  implicit def intToState(i: Int): State = State(i.toString)
  implicit def stateToInt(s: State): Int = s.name.toInt
  def anySurr: Surroundings = Surroundings(Anything, Anything, Anything, Anything)

  // helper function - returns the opposite relative description of surrounding
  def opp(r: RelativeDescription): RelativeDescription = r match {
    case Open => Blocked
    case Blocked => Open
    case Anything => Anything
  }

  // returns the "opposite surroundings"
  def opposite(surr: Surroundings): Surroundings = {
    Surroundings(opp(surr.north), opp(surr.east), opp(surr.west), opp(surr.south))
  }

  // converts to surroundings with one open direction
  def free(dir: MoveDirection): Surroundings = dir match {
    case North => Surroundings(Open, Anything, Anything, Anything)
    case East => Surroundings(Anything, Open, Anything, Anything)
    case West => Surroundings(Anything, Anything, Open, Anything)
    case South => Surroundings(Anything, Anything, Anything, Open)
  }

  // for parsing comments
  override protected val whiteSpace =
  """(\s|#.*)+""".r

  // parsing interface
  def apply(s: String): ParseResult[List[List[Rule]]] = {
    parseAll(program, s)
  }

  def program: Parser[List[List[Rule]]] = stmts *

  def stmts: Parser[List[Rule]] =
    (("while" ~ "(" ~ expr ~ ")" ~ body ^^ { case "while" ~ "(" ~ condition ~ ")" ~ b =>
          fixState(List(Rule(0, condition, StayHere, 1)) ++ List(Rule(0, opposite(condition), StayHere, 1)) ++ b)  })
      | ("move" ~ dir ^^ { case "move" ~ d => List(Rule(0, anySurr, d, 1)) })
      | failure("expected a 'while' or 'move' statement")
    )

  def expr: Parser[Surroundings] =
    (("free" ~ dir ^^ { case "free" ~ d => free(d) })
      | ("blocked" ~ dir ^^ { case "blocked" ~ d => opposite(free(d)) })
      | ("any" ^^^ anySurr)
      | failure("expected 'free', 'blocked', or 'any' expression")
    )

  // packrat? for left recursive, but this is right?
  def body: Parser[List[Rule]] =
    (("{" ~ body ^^ { case "{" ~ b => b })
      | (stmts ~ body ^^ { case s ~ b => s ++ b })
      | ("}" ^^^ List())
    )

  def dir: Parser[MoveDirection] =
    (("N" ^^^ North)
      | ("E" ^^^ East)
      | ("W" ^^^ West)
      | ("S" ^^^ South)
      | ("X" ^^^ StayHere))

  def fixState(states: List[Rule]):List[Rule] = {
    val endRule = states(1).copy(endState = states.tail.length);
    val fixB = fixBody(states.tail.tail, 1)
    List(states.head, endRule) ++ fixB
  }

  def fixBody(body: List[Rule], state: Int): List[Rule] = body match {
    case Nil => List()
    case first :: Nil => List(first.copy(startState = state, endState = 0))
    case first :: rest if first.startState + 1 == first.endState + 0 =>
      first.copy(startState = state, endState = state+1) :: fixBody(rest, state+1)
    case first :: rest if first.startState + 0 < first.endState + 0 =>
      first.copy(startState = state-1, endState = state-1 + first.endState) :: fixBody(rest, state)
    case first :: rest =>
      first.copy(startState = state, endState = state - first.startState) :: fixBody(rest, state+1)
  }


//
//  def pattern: Parser[Surroundings] =
//    ndirection ~ edirection ~ wdirection ~ sdirection ^^ { case n ~ e ~ w ~ s ⇒ Surroundings(n, e, w, s) }
//
//  def ndirection: Parser[RelativeDescription] =
//    (("N" ^^^ Blocked)
//      | odirection
//      | failure("expected N, or *, or x"))
//
//  def edirection: Parser[RelativeDescription] =
//    (("E" ^^^ Blocked)
//      | odirection
//      | failure("expected E, or *, or x"))
//
//  def wdirection: Parser[RelativeDescription] =
//    (("W" ^^^ Blocked)
//      | odirection
//      | failure("expected W, or *, or x"))
//
//  def sdirection: Parser[RelativeDescription] =
//    (("S" ^^^ Blocked)
//      | odirection
//      | failure("expected S, or *, or x"))
//
//  def odirection: Parser[RelativeDescription] =
//    (("*" ^^^ Anything)
//      | ("x" ^^^ Open))
//
//  def state: Parser[State] =
//    """\d\d?""".r ^^ State
}
