package piconot.external

import scalafx.application.JFXApp
import java.io.File
import picolib.maze.Maze
import piconot.external.parser._

object Piconot {
    def main(args: Array[String]) {
    
    val empty = Maze(args(0))
    val source = scala.io.Source.fromFile(args(1))
    val text = try source.mkString finally source.close()
    val rules = semantics.eval(PiconotParser(text).get)

    object RunBot extends Picobot(empty, rules) with TextDisplay
  
//    stage = EmptyBot.mainStage
  
    RunBot.run()
  }


}

