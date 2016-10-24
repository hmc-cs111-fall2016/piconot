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
  def apply(s: String): ParseResult[Prog] = parseAll(prog, s)

  lazy val prog: PackratParser[Prog] =
    ( comment~>"#"~stateName~comment~stateChunks 
      ^^ {case "#"~start~comm~chunks => Prog(start, chunks)})

  lazy val stateChunks: PackratParser[List[StateChunk]] = 
    ( stateChunks~stateChunk ^^ {case list~chunk => list :+ chunk}
      | stateChunk ^^ {case s => List(s)})

  lazy val stateChunk: PackratParser[StateChunk] =
    ( stateName~comment~"{"~comment~stateRules~"}"~comment ^^ 
        {case name~c1~"{"~c2~rules~"}"~c3 => StateChunk(name, rules)} )

  lazy val stateRules: PackratParser[List[Rule]] = 
    ( stateRules~rule ^^ {case s~r => List(r) ++ s}
      | rule ^^ {case r => List(r)})

  // if only one of movement and a new state is provided, we attempt to parse
  // as a movement, and failing that, parse it as a statename
  lazy val rule: PackratParser[Rule] = 
    ( surr~"->"~movement~stateName<~";"~comment
        ^^ {case s~"->"~m~state => Rule(s, Some(m), Some(state))}
    | surr~"->"~movement<~";"~comment  
        ^^ {case s~"->"~m => Rule(s, Some(m), None)}
    | surr~"->"~stateName<~";"~comment
        ^^ {case s~"->"~state => Rule(s, None, Some(state))})

  lazy val surr: PackratParser[API.Surroundings] =
    ( directions~"!"~directions~"_" ^^ {case walls~"!"~blanks~"_" => 
                                                Surr(walls, blanks).translate } 
      | directions~"_"~directions~"!" ^^ {case blanks~"_"~walls~"!" =>
                                                Surr(walls, blanks).translate }
      | directions~"!" ^^ {case walls~"!" => Surr(walls, null).translate }
      | directions~"_" ^^ {case blanks~"_" => Surr(null, blanks).translate} )

  lazy val directions: Parser[String] = """(N|E|W|S){1,4}""".r 
  lazy val movement: Parser[String] = """(N|E|W|S)""".r 
  
  // state names must be lower case
  lazy val stateName: Parser[String] = """([0-9]|[a-z]|_)+""".r
  lazy val comment: Parser[String] = """(//.*)?""".r

}
