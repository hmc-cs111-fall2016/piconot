package piconot.external.parser

import scala.language.postfixOps
import scala.util.parsing.combinator.RegexParsers

import picolib.semantics._

/*
 * Our Syntax
 *
 * rule ::= state "if" pattern "go" nextState
 * state ::= "North" | "South" | "East" | "West"
 * pattern ::= something
 *             | nothing
 *             | something "and" nothing
 * something ::= "something" dir
 * nothing ::= "nothing" dir
 * dir ::= [N | E | W | S]*
 * nextState ::= state
 *
 *
 */

object PiconotParser extends RegexParsers {

	// for parsing comments
	override protected val whiteSpace = """(\s|#.*)+""".r

	// parsing interface
	def apply(s: String): ParseResult[List[Rule]] = parseAll(program, s)

	def program: Parser[List[Rule]] = rule*

	def rule: Parser[Rule] =
		state ~ "if" ~ pattern ~ "go" ~ nextState
		{ case stateNow ~ "if" ~ surroundings ~ "go"  ~ Dir ⇒
		  Rule(DirToState(stateNow), surroundings, Dir, DirToState(Dir) }

	def state: Parser[MoveDirection] = 
		(	("North" ^^^ North)
		  | ("South" ^^^ South)
		  | ("East" ^^^ East)
		  | ("West" ^^^ West)

	def pattern: Parser[Surroundings] =
		something
		| nothing
		| something ~ "and" ~ nothing 
		| nothing ~ "and" ~ something
		{ case sth ⇒ something
		  case nth ⇒ nothing
		  case sth ~ "and" ~ nth ⇒ 
		  case nth ~ "and" ~ sth ⇒ } 

		// ndirection ~ edirection ~ wdirection ~ sdirection ^^
		// { case n ~ e ~ w ~ s ⇒ Surroundings(n, e, w, s) }


	def something: Parser[Surroundings] = """"?<=Something\\Q(\\E)(.*)(?=\\Q)\\E)""".r ^^ 
		{ case "Something(" + dir + ")"} ⇒  interpretSomething(dir) }

	def nothing: Parser[Surroundings] = """"?<=Nothing\\Q(\\E)(.*)(?=\\Q)\\E)""".r ^^ 
		{ case "Nothing(" + dir + ")"} ⇒  interpretNothing(dir) }

	def nextState: Parser[State] = 
		state
		{ case dir ⇒ interpretState(dir)}

	def DirToState(dir: MoveDirection): State =
		( (North ^^^ 0)
		| (South ^^^ 1)
		| (East ^^^ 2)
		| (West ^^^ 3) )

 }

