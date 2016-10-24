// package piconot.external.parser

// import scala.language.postfixOps
// import scala.util.parsing.combinator.RegexParsers

// import picolib.semantics._

// /*
//  * Our Syntax
//  *
//  * rule ::= state "if" pattern "go" nextState
//  * state ::= "North" | "South" | "East" | "West"
//  * pattern ::= something
//  *             | nothing
//  *             | something "and" nothing
//  * something ::= "something" dir
//  * nothing ::= "nothing" dir
//  * dir ::= [N | E | W | S]*
//  * nextState ::= state
//  *
//  *
//  */

//  object PiconotParser extends RegexParsers {

//    // for parsing comments
//    override protected val whiteSpace = """(\s|#.*)+""".r

//    // parsing interface
//    def apply(s: String): ParseResult[List[Rule]] = parseAll(program, s)

//    def program: Parser[List[Rule]] = rule*

//    def rule: Parser[Rule] =
//      state ~ "if" ~ pattern ~ "go" ~ nextState
//      { case stateNow ~ "if" ~ surroundings ~ "go"  ~ Dir ⇒
//         Rule(DirToState(stateNow), surroundings, Dir, DirToState(Dir) }

//    //def state: Parser[State] =

//    def DirToState(dir: MoveDirection): State =
//      ( (North ^^^ 0)
//      | (South ^^^ 1)
//      | (East ^^^ 2)
//      | (West ^^^ 3) )

//  }

