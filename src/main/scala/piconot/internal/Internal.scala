package piconot.internal 

import scala.language.implicitConversions
import picolib.maze.Maze
import picolib.semantics._
import scala.collection.mutable.MutableList
import scalafx.application.JFXApp

//TODO: 
// - Fix Regex/make sure they work
// - Implement Go, RHSBuilder, state stuff properly
// - Handle weird implicit thing for North If(...)
// - Fix up rule builder to conform to our nextState being a String and converting that to an int for the rules

class Internal extends App {
	private val listOfRules = MutableList.empty[Rule]


	// Create objects North West South East which would be first params
	def If(somethingNothingPart: String, goPart: String): Surroundings = {
		// This is all kind of sketchy right now, but shows a direction we could
		// go. Basically, this If function would take in a string of instructions
		// (If("Nothing[N] and Something[W]", "Go North"))) we'd need to parse it
		// to find whether they specified "Nothing" or "Something" and what they
		// passed in for "Go".  Then we'd use this to call the appropriate functions
		// (Nothing, Something, Go) to generate params for our ruleBuilder which
		// would then create a rule to add to our rulesList, which would then be
		// used when running the program.
		val somethingInputs = somethingNothingPart.split("(?<=Something\()(.*)(?=\))")
		val nothingInputs = somethingNothingPart.split("(?<=Nothing\()(.*)(?=\))")

		var initSurroundingsList = [Anything, Anything, Anything, Anything]
		
		// Create a Surroundings object for the particular state/set of instructions
		var nothingList = Nothing("N", initSurroundingsList)
		val surroundingsInputs = Something("N", nothingList)

		val surroundingsObject = Surroundings(surroundingsInputs[0], surroundingsInputs[1], surroundingsInputs[2], surroundingsInputs[3])

		// Create the output values	
		val goInput = goPart.split("Go\((.*)(?=\))")
		val outputState = Go(goInput)

		// Create some function to build rules to go into listOfRules
		val rule = ruleBuilder(outputState, surroundingsObject)
		
		// Create some function to add a rule to the listOfRules field.
		// Actually this is handled in ruleBuilder
		// addRule(rule)
	}


	def Something(list: List[Surroundings]) = {
		 // 0 = Anything
		 // 1 = Open
		 // 2 = Blocked
		 var dirList = list
		 if (list.contains("N")) {
		 	dirList[0] = Blocked
		 }
		 if (list.contains("E")) {
		 	dirList[1] = Blocked
		 } 
		 if (list.contains("W")) {
		 	dirList[2] = Blocked
		 }
		 if (list.contains("S")) {
		 	dirList[3] = Blocked
		 }

    	 dirList
	}

	def Nothing(list: List[Surroundings]) = {
		 // 0 = Anything
		 // 1 = Open
		 // 2 = Blocked
		 var dirList = list
		 if (list.contains("N")) {
		 	dirList[0] = Open
		 }
		 if (list.contains("E")) {
		 	dirList[1] = Open
		 } 
		 if (list.contains("W")) {
		 	dirList[2] = Open
		 }
		 if (list.contains("S")) {
		 	dirList[3] = Open
		 }
	 
    	 dirList
	}

	def Go(direction: String) = {
		 if (direction == "North") {
		 	0
		 }
		 if (direction == "East") {
		 	1
		 } 
		 if (direction == "West") {
		 	2
		 }
		 if (direction == "South") {
		 	3
		 }
	}

	class RHSBuilder(val moveDirection: MoveDirection) {
    	def apply(nextState: String): (MoveDirection, State) =
      		(moveDirection, new State(nextState.toString))
  	}
  
  	// internal DSL names for move directions
  	object North extends RHSBuilder(North)
  	object East extends RHSBuilder(East)
  	object West extends RHSBuilder(West)
  	object South extends RHSBuilder(South)
  	// Need some StayHere object?
  
	// a class to build a rule from its parts and add the rule to the running
	// list of rules
	class RuleBuilder(val startState: State, val surroundings: Surroundings) {
	  val program = Picobor.this
	  def Go(rhs: (MoveDirection, State)) = {
	    val (moveDirection, nextState) = rhs
	    val rule = new Rule(startState, surroundings, moveDirection, nextState)
	    program.addRule(rule)
	  }
	}


	def run() = {
      val maze = Maze(mazeFilename)
      val picobot = 
        new Picobot(maze, rules.toList) with TextDisplay with GUIDisplay
      stage = picobot.mainStage
      picobot.run()
  	}
}  