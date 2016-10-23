package picobot.external.parser

import scala.util.parsing.combinator._
import picobot.external.ir._
import picolib.{semantics => API}

/**
  * --------
  * Grammar
  * --------
  */

object PicoParser extends JavaTokenParsers with PackratParsers {
  // parsing interface
  def apply(s: String): ParseResult[API.Surroundings] = parseAll(surr, s)

  // the entire program
  lazy val prog: PackratParser[Prog] = 
      ( start~"\n"~rules ^^ {case s~"\n"~r => Prog(s, r)} )
/*
  lazy val start: PackratParser[Expr] =
      ( "#start"~stateName ^^ {case "#start"~name => Start(name)} )

  lazy val rules: PackratParser[Expr] =
      ( stateChunk~rules ^^ {case state~moreRules => AddRule(state, moreRules)}
       | stateChunk )
*/
  //lazy val stateChunk: PackratParser[Expr] =
      //( stateName~"{"~conditions~"}" ^^ {case name~"{"~conds~"}" =>  })
      //
  lazy val surr = ParseResult[API.Surroundings] =
    ( walls~"!"~blanks~"_" ^^ {case w} // get w and b then have IR object of w and b which has a method that returns the corresponding API.Surroundings
      | blanks~"_"~walls~"!" ^^
      | walls~"!" ^^
      | blanks~"_" ^^ )
}
