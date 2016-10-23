package piconot.internal 

import scala.language.implicitConversions
import picolib.maze.Maze
import picolib.semantics._
import scala.collection.mutable.MutableList
import scalafx.application.JFXApp


// Internal syntax: 
// If ("N", "Nothing(N)","Go(North)")
// If ("N", Something(N) and Nothing(W)", "Go(West)")
// If ("N", Something(N, W), "Go(East)")
// If ("W", Nothing(S), "Go(South)")

//TODO: 
// - Fix Regex/make sure they work
// - Fix up rule builder to conform to our nextState being a String and converting that to an int for the rules


//TODO: 
// Create our own rule builder.  We only have 4 possible states (N, E, W, S) for starting and nextstate,
// and we can set these in the new rules based on the input for Go. See the current Go definition below
// to see what I mean.  Move the Go method outside of the class it's in - don't need to really encapsulate it
// in something else.


class Internal extends App {
	private val rules = MutableList.empty[Rule]

	// Create objects North West South East which would be first params
	def If(startDirection: String, somethingNothingPart: String, goPart: String) = {
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

		var initSurroundingsList = Array[RelativeDescription](Anything, Anything, Anything, Anything)
		
		// Create a Surroundings object for the particular state/set of instructions
		var nothingList = Nothing(initSurroundingsList)
		val surroundingsInputs = Something(nothingList)

		val surroundingsObject = Surroundings(surroundingsInputs(0), surroundingsInputs(1), surroundingsInputs(2), surroundingsInputs(3))
		
		var startingState = State("0")
		if (startDirection == "E") {
			startingState = State("1")
		}
		if (startDirection == "W") {
			startingState = State("2")
		}
		if (startDirection == "S") {
			startingState = State("3")
		}

		// Create the output values	
		val goInput = goPart.split("Go\((.*)(?=\))")
		val moveDirection, nextState  = Go(startingState, surroundingsObject, goInput)

		// Create some function to build rules to go into rules
		// val rule = new RuleBuilder(startingState, surroundingsObject)
		
	}


	def Something(list: Array[RelativeDescription]) = {
		 // 0 = Anything
		 // 1 = Open
		 // 2 = Blocked
		 var dirList = list
		 if (list.contains("N")) {
		 	dirList(0) = Blocked
		 }
		 if (list.contains("E")) {
		 	dirList(1) = Blocked
		 } 
		 if (list.contains("W")) {
		 	dirList(2) = Blocked
		 }
		 if (list.contains("S")) {
		 	dirList(3) = Blocked
		 }

    	 dirList
	}

	def Nothing(list: Array[RelativeDescription]) = {
		 // 0 = Anything
		 // 1 = Open
		 // 2 = Blocked
		 var dirList = list
		 if (list.contains("N")) {
		 	dirList(0) = Open
		 }
		 if (list.contains("E")) {
		 	dirList(1) = Open
		 } 
		 if (list.contains("W")) {
		 	dirList(2) = Open
		 }
		 if (list.contains("S")) {
		 	dirList(3) = Open
		 }
    	 dirList
	}

	def Go(startingState: State, surroundingsObject: Surroundings, direction: String) = {
	    val program = Internal.this
	    if (direction == "North") {
  		 	val rule = new Rule(startingState, surroundingsObject, North, State("0"))
  		 	program.addRule(rule)
  		}
  		else if (direction == "East") {
  			val rule = new Rule(startingState, surroundingsObject, East, State("1"))
  			program.addRule(rule)

  		} 
  		else if (direction == "West") {
  			val rule = new Rule(startingState, surroundingsObject, West, State("2"))
  			program.addRule(rule)

  		}
  		else if (direction == "South") {
  			val rule = new Rule(startingState, surroundingsObject, South, State("3"))
  			program.addRule(rule)
  		}	    
	}
  
	// a class to build a rule from its parts and add the rule to the running
	// list of rules
	// class RuleBuilder(val startState: State, val surroundings: Surroundings) {
	//   val program = Internal.this
	//   val rule = new Rule(startState, surroundings, moveDirection, nextState)

	// }

  	def addRule(rule: Rule) = rules += rule
  
	def run() = {
      val maze = Maze(mazeFilename)
      val picobot = 
        new Picobot(maze, rules.toList) with TextDisplay with GUIDisplay
      stage = picobot.mainStage
      picobot.run()
  	}
}  