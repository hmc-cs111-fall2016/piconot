package picobot.external

import parser.PicoParser
import picolib.maze.Maze
import picolib.semantics._

import scala.util.parsing.combinator._
import scalafx.application.JFXApp
import scala.io.Source.fromFile

object Piconot extends JFXApp {
  def doubleError(msg: String) = {
    println(s"\n$msg\n")  
    throw new Exception(s"Was not able to parse the code: $msg")
  }

  val args = parameters.raw  // from the sample solution 

  if (args.length != 2)
    println("Usage: sbt \"run-main picobot.external.Piconot" + 
            "path/to/room.txt path/to/program.bot\"")
  
  val maze = Maze(args(0))
  val picode = fromFile(args(1)).mkString

  var rules = List[Rule]()
  PicoParser(picode) match {
    case PicoParser.Success(r,_)   => rules = r.sortedRules
    case PicoParser.Failure(msg,_) => doubleError(msg)
    case PicoParser.Error(msg,_)   => doubleError(msg)
  }

  object Bot extends Picobot(maze, rules) with TextDisplay with GUIDisplay

  stage = Bot.mainStage

  Bot.run()
}
