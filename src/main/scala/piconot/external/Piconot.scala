package piconot.external
import java.io.File
import scalafx.application.JFXApp

import picolib.maze.Maze
import picolib.semantics._
import piconot.external.parser._

object Piconot {
  def main(args: Array[String]) {
    
    val emptyMaze = Maze(args(0))
    println("Hello, world!")
    // From http://stackoverflow.com/a/1284446
    val source = scala.io.Source.fromFile(args(1))
    println("Hello, world2!")
    val lines = try source.mkString finally source.close()
    println("Hello, world3!")
//    print(lines)
    val rules = semantics.eval(PiconotParser(lines).get)
    print(rules)
    object RunBot extends Picobot(emptyMaze, rules) with TextDisplay
  
//    stage = EmptyBot.mainStage
  
    RunBot.run()
  }


}