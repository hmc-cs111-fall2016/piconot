package piconot.internal

import PicoBotExtender._

import java.io.File
import scalafx.application.JFXApp

import picolib.maze.Maze
import picolib.semantics._

/**
 *  This is an intentionally bad internal language, but it uses all the parts of
 *  the picolib library that you might need to implement your language
 */

object EmptyRoom extends JFXApp {
  val emptyMaze = Maze("resources" + File.separator + "empty.txt")

  val rules : List[Rule] = List(
    
    /////////////////////////////////////////////////////////
    // State 0: go West
    /////////////////////////////////////////////////////////

    // as long as West is unblocked, go West
    "0W" -> "go W changeState 0",

    // can't go West anymore, so try to go North (by transitioning to State 1)
    "0~W" -> "go nowhere changeState 1",

    /////////////////////////////////////////////////////////
    // State 1: go North
    /////////////////////////////////////////////////////////

    // as long as North is unblocked, go North
    "1N" -> "go N changeState 1",

    // can't go North any more, so try to go South (by transitioning to State 2)
    "1~NS" -> "go S changeState 2",

    /////////////////////////////////////////////////////////
    // States 2 & 3: fill from North to South, West to East
    /////////////////////////////////////////////////////////

    // State 2: fill this column from North to South
    "2S" -> "go S changeState 2",

    // can't go South anymore, move one column to the East and go North
    // (by transitioning to State 3)
    "2E~S" -> "go E changeState 3",

    // State 3: fill this column from South to North
    "3N" -> "go N changeState 3",

    // can't go North anymore, move one column to the East and go South
    // (by transitioning to State 2)
    "3~NE" -> "go E changeState 2"
  )

  object EmptyBot extends Picobot(emptyMaze, rules)
    with TextDisplay with GUIDisplay

  stage = EmptyBot.mainStage

  EmptyBot.run()

}
