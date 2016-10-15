package piconot.internal

import java.io.File
import scalafx.application.JFXApp

import picolib.maze.Maze
import picolib.semantics._

object Empty extends InternalDSL {
	val rules = List (
	Condition(CurrentState(0), Free("W", "", "", ""), Occupied("", "", "", ""), Next("W"), 0).set,
	Condition(CurrentState(0), Free("", "", "", ""), Occupied("W", "", "", ""), Next("_"), 1).set,
	Condition(CurrentState(1), Free("N", "", "", ""), Occupied("", "", "", ""), Next("N"), 0).set,
	Condition(CurrentState(1), Free("S", "", "", ""), Occupied("N", "", "", ""), Next("S"), 1).set,
	Condition(CurrentState(2), Free("S", "", "", ""), Occupied("", "", "", ""), Next("S"), 0).set,
	Condition(CurrentState(2), Free("E", "", "", ""), Occupied("S", "", "", ""), Next("E"), 1).set,
	Condition(CurrentState(3), Free("N", "", "", ""), Occupied("", "", "", ""), Next("N"), 0).set,
	Condition(CurrentState(3), Free("E", "", "", ""), Occupied("N", "", "", ""), Next("E"), -1).set  
	)
	runPicobot(rules, "empty.txt")
}