package picobot.external.parser

import scala.util.parsing.combinator._
import picobot.external.ir._
import picolib.{semantics => API}

/**
  * --------
  * Grammar
  * --------
  */

object PicoParser extends JavaTokenParsers with PackratParsers 
                                           with RegexParsers {
  // parsing interface
  def apply(s: String): ParseResult[StateChunk] = parseAll(stateChunk, s)

  // the entire program
//  lazy val prog: PackratParser[Prog] = 
 //     ( start~"\n"~rules ^^ {case s~"\n"~r => Prog(s, r)} )
/*
  lazy val start: PackratParser[Expr] =
      ( "#start"~stateName ^^ {case "#start"~name => Start(name)} )

  lazy val rules: PackratParser[Expr] =
      ( stateChunk~rules ^^ {case state~moreRules => AddRule(state, moreRules)}
       | stateChunk )
*/
  lazy val stateChunk: PackratParser[StateChunk] =
    ( stateName~"{"~rule~"}" ^^ {case name~"{"~rules~"}" =>  
                                       StateChunk(name, List(rules))} )

  /*)
  lazy val stateRules: PackratParser[List[Rule]] = 
    ( rule~stateRules ^^ {r~sr => r :: sr}
      | rule ^^ {r => List(r)})
*/

  // if only one of movement and a new state is provided, we attempt to parse
  // as a movement, and failing that, parse it as a statename
  lazy val rule: PackratParser[Rule] = 
    ( surr~"->"~movement~stateName ^^ {case s~"->"~m~state => 
                                        Rule(s, Some(m), Some(state))}
      | surr~"->"~movement ^^ {case s~"->"~m => Rule(s, Some(m), None)}
      | surr~"->"~stateName ^^ {case s~"->"~state => Rule(s, None, Some(state))})

  lazy val surr: PackratParser[API.Surroundings] =
    ( directions~"!"~directions~"_" ^^ {case walls~"!"~blanks~"_" => 
                                                Surr(walls, blanks).translate } 
      | directions~"_"~directions~"!" ^^ {case blanks~"_"~walls~"!" =>
                                                Surr(walls, blanks).translate }
      | directions~"!" ^^ {case walls~"!" => Surr(walls, null).translate }
      | directions~"_" ^^ {case blanks~"_" => Surr(null, blanks).translate} )

  lazy val directions: Parser[String] = """(N|E|W|S){1,4}""".r 
  lazy val movement: Parser[String] = """(N|E|W|S)""".r 
  lazy val stateName: Parser[String] = ident

}
