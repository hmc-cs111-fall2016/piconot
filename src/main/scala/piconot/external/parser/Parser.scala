package piconot.external

import scala.util.parsing.combinator._
import PiconotTypes._

/** A recursive descent parser for the Piconot language. See design.md for
 *  the grammar.
 */
object PiconotParser extends JavaTokenParsers with PackratParsers with RegexParsers {

  // parsing interface
  def apply(s: String): ParseResult[Seq[State]] = parseAll(program, s)

  // a program is a sequence of states
  val program: PackratParser[Seq[State]] =
    (  state ~ program ^^ {case s~p => s +: p}
      | state          ^^ {case s   => Seq(s)} )

  // a state consists of a name and a sequence of rules
  val state: Parser[State] =
    (  ("state" ~> stateName) ~ rules ^^ {case n~r => State(n, r)} )

  // recursively build a list of rules
  val rules: PackratParser[Seq[Rule]] =
    (  rule ~ rules   ^^ {case r~rs => r +: rs}
      | rule          ^^ {case r => Seq(r)} )

  // a rule consists of a list of commands
  val rule: Parser[Rule] =
    ( ("|" ~> "move" ~> dir <~ ",") ~ ("face" ~> dir <~ ",") ~ stateName
        ^^ {case m~f~g => Piconot.rule(Move(m), Face(f), Goto(g))}

      | ("|" ~> "move" ~> dir <~ ",") ~ ("face" ~> dir)
        ^^ {case m~f => Piconot.rule(Move(m), Face(f))}
      | ("|" ~> "move" ~> dir <~ ",") ~ stateName
        ^^ {case m~g => Piconot.rule(Move(m), Goto(g))}
      | ("|" ~> "face" ~> dir <~ ",") ~ stateName
        ^^ {case f~g => Piconot.rule(Face(f), Goto(g))}

      | "|" ~> "move" ~> dir  ^^ {case d => Piconot.rule(Move(d))}
      | "|" ~> "face" ~> dir  ^^ {case d => Piconot.rule(Face(d))}
      | "|" ~> stateName      ^^ {case n => Piconot.rule(Goto(n))} )

  val dir: Parser[Dir] =
    ( "north"       ^^ {_ => North}
      | "east"      ^^ {_ => East}
      | "west"      ^^ {_ => West}
      | "south"     ^^ {_ => South}
      | "forward"   ^^ {_ => Forward}
      | "backward"  ^^ {_ => Backward}
      | "left"      ^^ {_ => Left}
      | "right"     ^^ {_ => Right})

  val stateName: Parser[StateName] = """[a-zA-Z0-9_]+""".r

 }