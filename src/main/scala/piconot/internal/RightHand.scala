package piconot.internal

import java.io.File
import scalafx.application.JFXApp

import picolib.maze.Maze
import picolib.semantics._

object RightHand extends InternalDSL {
	val rules = List (
	Condition(CurrentState(0), Free("E", "", "", ""), Occupied("", "", "", ""), Next("E"), 1).set,
	Condition(CurrentState(0), Free("N", "", "", ""), Occupied("E", "", "", ""), Next("N"), 0).set,
	Condition(CurrentState(0), Free("", "", "", ""), Occupied("N", "E", "", ""), Next("_"), 3).set,
	
	Condition(CurrentState(1), Free("S", "", "", ""), Occupied("", "", "", ""), Next("S"), 2).set,
	Condition(CurrentState(1), Free("E", "", "", ""), Occupied("S", "", "", ""), Next("E"), 0).set,
	Condition(CurrentState(1), Free("", "", "", ""), Occupied("S", "E", "", ""), Next("_"), 1).set,
	
	Condition(CurrentState(2), Free("N", "", "", ""), Occupied("", "", "", ""), Next("N"), -2).set,
	Condition(CurrentState(2), Free("W", "", "", ""), Occupied("N", "", "", ""), Next("W"), 0).set,
	Condition(CurrentState(2), Free("", "", "", ""), Occupied("N", "W", "", ""), Next("_"), -1).set, 
	
	Condition(CurrentState(3), Free("W", "", "", ""), Occupied("", "", "", ""), Next("W"), -1).set,
	Condition(CurrentState(3), Free("S", "", "", ""), Occupied("W", "", "", ""), Next("S"), 0).set,
	Condition(CurrentState(3), Free("", "", "", ""), Occupied("W", "S", "", ""), Next("_"), -3).set  
	)
	runPicobot(rules, "maze.txt")
}