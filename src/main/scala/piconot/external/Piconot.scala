package picobot.external

import parser.PicoParser
import picolib.maze.Maze
import picolib.semantics._

import scalafx.application.JFXApp
import scala.io.Source.fromFile

object Piconot extends JFXApp {
  val args = parameters.raw  // from the sample solution 

  if (args.length != 2)
    println("Usage: sbt \"run-main picobot.external.Piconot" + 
            "path/to/room.txt path/to/program.bot\"")
  
  val maze = Maze(args(0))
  val picode = fromFile(args(1)).mkString

  val rules = PicoParser(picode).get.sortedRules

  object Bot extends Picobot(maze, rules) with TextDisplay with GUIDisplay

  stage = Bot.mainStage

  Bot.run()
}
