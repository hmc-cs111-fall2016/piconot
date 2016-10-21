import picobot.internal._
import State._

import java.io.File
import scalafx.application.JFXApp

import picolib.maze.Maze

import picolib.semantics._

object EmptyRoom extends JFXApp {
  val emptyMaze = Maze("resources" + File.separator + "empty.txt")

  val rules = List(
    ("corner"         and "E_"   ) -> (East    , "corner"        ),
    ("corner"         and "E! S_") -> (South   , "corner"        ),
    ("corner"         and "ES!"  ) -> (StayHere, "N_sweep_move_W"),
    ("N_sweep_move_W" and "N_"   ) -> (North   , "N_sweep_move_W"),
    ("N_sweep_move_W" and "N!"   ) -> (West    , "S_sweep"       ),
    ("S_sweep"        and "S_"   ) -> (South   , "S_sweep"       ),
    ("S_sweep"        and "S!"   ) -> (StayHere, "N_sweep_move_W")
  )

  object EmptyBot extends Picobot(emptyMaze, rules) 
    with TextDisplay with GUIDisplay

  stage = EmptyBot.mainStage

  EmptyBot.run()
}
