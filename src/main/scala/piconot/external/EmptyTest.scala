package piconot.external
import java.io.File
import scalafx.application.JFXApp

import picolib.maze.Maze
import picolib.semantics._
import piconot.external.parser._

/**
 *  This is an intentionally bad internal language, but it uses all the parts of
 *  the picolib library that you might need to implement your language
 */

object EmptyRoom extends JFXApp {
  
  val emptyMaze = Maze("resources" + File.separator + "empty.txt")

  val rules = semantics.eval(PiconotParser("0:~W -> go W; 0:W -> changeState 1; 1:~N -> go N; 1:N~S -> changeState 2; 2:~S -> go S; 2:~ES -> go E changeState 3; 3:~N -> go N; 3:N~E -> go E changeState 2;").get)

  object EmptyBot extends Picobot(emptyMaze, rules)
    with TextDisplay with GUIDisplay

  stage = EmptyBot.mainStage

  EmptyBot.run()

}
