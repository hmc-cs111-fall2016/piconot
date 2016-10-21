import picobot.internal._
import State._

import java.io.File
import scalafx.application.JFXApp

import picolib.maze.Maze

import picolib.semantics._

object MazeRoom extends JFXApp {
  val maze = Maze("resources" + File.separator + "maze.txt")

  val rules = List(
    ("face_north" and "E! N_") -> (North, "face_north"),
    ("face_north" and "E_") -> (East, "face_east"),
    ("face_north" and "EN!") -> (StayHere, "face_west"),

    ("face_east" and "S! E_") -> (East, "face_east"),
    ("face_east" and "S_") -> (South, "face_south"),
    ("face_east" and "ES!") -> (StayHere, "face_north"),

    ("face_south" and "W! S_") -> (South, "face_south"),
    ("face_south" and "W_") -> (West, "face_west"),
    ("face_south" and "WS!") -> (StayHere, "face_east"),

    ("face_west" and "N! W_") -> (West, "face_west"),
    ("face_west" and "N_") -> (North, "face_north"),
    ("face_west" and "NW!") -> (StayHere, "face_south")
  )

  object MazeBot extends Picobot(maze, rules) 
    with TextDisplay with GUIDisplay

  stage = MazeBot.mainStage

  MazeBot.run()
}
