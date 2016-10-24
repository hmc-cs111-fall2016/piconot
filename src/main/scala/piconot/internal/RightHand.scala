package piconot.internal

import java.io.File
import scalafx.application.JFXApp

import Piconot._
import picolib.semantics._
import picolib.maze.Maze

/**
 *  This is an intentionally bad internal language, but it uses all the parts of
 *  the picolib library that you might need to implement your language
 */

object RightHand extends JFXApp {
    val maze = Maze("resources" + File.separator + "maze.txt")
    val rules = runPiconot (

        state ("0") (
            rule(move(Right)),
            rule(face(Left))
        )
    )

    object RightHandBot extends Picobot(maze, rules)
        with TextDisplay with GUIDisplay

    stage = RightHandBot.mainStage

    RightHandBot.run()
}