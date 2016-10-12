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

object Empty extends JFXApp {
    val maze = Maze("resources" + File.separator + "empty.txt")

    val rules = runPiconot (

        state ("move_north") (
            rule(move(North)),
            rule(move(East), face(South), "move_south"),
            rule(face(West), "move_west")
        ),

        state ("move_south") (
            rule(move(South)),
            rule("move_north")
        ),

        state ("move_west") (
            rule(move(West)),
            rule("move_south")
        )
    )

    object EmptyBot extends Picobot(maze, rules)
        with TextDisplay with GUIDisplay

    stage = EmptyBot.mainStage

    EmptyBot.run()
}