package piconot.external.parser
import scala.util.parsing.combinator._
import piconot.external.ir._

object PiconotParser extends JavaTokenParsers with RegexParsers{
    def apply(s: String): ParseResult[List[OurRule]] = {
      parseAll(ourrules, s)
    }
    
    def ourrules: Parser[List[OurRule]] = {
      (   ourstates~"->"~ouractions~";"~ourrules ^^ {case s~"->"~a~";"~more => (List(OurRule(s,a)) ++ more) }
        | ourstates~"->"~ouractions~";" ^^ {case s~"->"~a~";" => List(OurRule(s,a))})  
    }
   
   // W = W is blocked; ~W = W is open
   def ourstates: Parser[OurState] = {
      (   wholeNumber ~ ":" ~ """(~?[N,E,W,S])*+""".r ^^ {case num ~ ":" ~ walls => OurState(num, walls)})
   }
    def ouractions: Parser[OurAction] =  {
      (   "go" ~ """[N,E,W,S]?""".r ~ "changeState" ~ wholeNumber ^^ {case "go" ~ d ~ "changeState" ~ n => BothActions(d,n)}
        | "go" ~ """[N,E,W,S]?""".r ^^ {case "go" ~ d => OnlyMove(d)}
        | "changeState" ~ wholeNumber ^^ {case "changeState" ~ n => OnlyChangeState(n)}
          )
    }   
}

