package piconot.internal
import picolib.semantics._
import scala.util.parsing.combinator._

object Parser extends JavaTokenParsers { 
  def dir: Parser[Any] = "N" | "E" | "W" | "S" | "N*" | "E*" | "W*" | "S*" | "_"
  
  def freeDirections : Parser[List[Any]] =  
   "(" ~> repsep(dir, ",") <~ ")" ^^ { (fd: List[Any]) => fd }
   
  def occDirections : Parser[List[Any]] =  
   "[" ~> repsep(dir, ",") <~ "]" ^^ { (fd: List[Any]) => fd }
   
  def nextState : Parser[Any] = "" | wholeNumber
  
  def eval : Parser[Any] = freeDirections ~ occDirections ~ ":" ~ dir ~ nextState
}