package piconot.external

import scalafx.application.JFXApp
import java.io.File
import piconot.external.parser._
import picolib._

object Piconot {
    def main(args: Array[String]) {
    
    val empty = Maze(args(0))
    val source = scala.io.Source.fromFile(args(1))
    val text = try source.mkString finally source.close()
    val rules = PiconotParser(text)

    object RunBot extends Picobot(empty, rules) with TextDisplay
  
//    stage = EmptyBot.mainStage
  
    RunBot.run()
  }


}

