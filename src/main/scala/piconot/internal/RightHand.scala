package piconot.internal

import PicoBotExtender._

import java.io.File
import scalafx.application.JFXApp

import picolib.maze.Maze
import picolib.semantics._

/**
 *  This uses our internal DSL syntax to make the rules for picobot
 */

object RightHand extends JFXApp {
  val emptyMaze = Maze("resources" + File.separator + "maze.txt")

  val rules : List[Rule] = List(
    
    "0E" -> "go E changeState 1",
    "0N" -> "go N changeState 0",
    "0W" -> "go W changeState 2",
    "0S" -> "go S changeState 3",
    
    "1S" -> "go S changeState 3",
    "1E" -> "go E changeState 1",
    "1N" -> "go N changeState 0",
    "1W" -> "go W changeState 2",
    
    "2N" -> "go N changeState 0",
    "2W" -> "go W changeState 2",
    "2S" -> "go S changeState 3",
    "2E" -> "go E changeState 1",
    
    "3W" -> "go W changeState 2",
    "3S" -> "go S changeState 3",
    "3E" -> "go E changeState 1",
    "3N" -> "go N changeState 0"

  )

  object EmptyBot extends Picobot(emptyMaze, rules)
    with TextDisplay with GUIDisplay

  stage = EmptyBot.mainStage

  EmptyBot.run()

}
