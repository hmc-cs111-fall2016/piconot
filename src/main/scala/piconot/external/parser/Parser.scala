package picobot.external.parser

import scala.util.parsing.combinator._
// import picobot internal rep

/**
  * --------
  * Grammar
  * --------
  */

object PicoParser extends JavaTokenParsers with PackratParsers {
  // parsing interface
  def apply(s: String): ParseResult[Expr] = parseAll(expr, s)

  // the entire program
  lazy val program: PackratParser[Expr] =
      ( start~"\n"~rules ^^ {case s~"\n"~r => Program(s, r)} )

  lazy val start: PackratParser[Expr] =
      ( "#start"~stateName ^^ {case "#start"~name => Start(name)} )

  lazy val rules: PackratParser[Expr] =
      ( stateChunk~rules ^^ {case state~moreRules => AddRule(state, moreRules)}
       | stateChunk )

  //lazy val stateChunk: PackratParser[Expr] =
      //( stateName~"{"~conditions~"}" ^^ {case name~"{"~conds~"}" =>  })
}
