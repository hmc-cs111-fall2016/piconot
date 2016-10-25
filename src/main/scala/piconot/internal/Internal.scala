package piconot.internal 

import scala.language.implicitConversions
import picolib.maze.Maze
import picolib.semantics._
import scala.collection.mutable.MutableList
import scalafx.application.JFXApp
import java.io.File

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


class Internal(val mazeFilename: String) extends JFXApp {
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
		val somethingPattern = "(?<=Something\\Q(\\E)(.*)(?=\\Q)\\E)".r
		val nothingPattern = "(?<=Nothing\\Q(\\E)(.*)(?=\\Q)\\E)".r
		var somethingInputs = ""
		var nothingInputs = ""
		somethingPattern.findAllIn(somethingNothingPart).matchData foreach {
			m => somethingInputs = m.group(1)
		}
		nothingPattern.findAllIn(somethingNothingPart).matchData foreach {
			m => nothingInputs = m.group(1)
		}

		println("somethingInputs: " + somethingInputs)
		println("nothingInputs: " + nothingInputs)
		var initSurroundingsList = Array[RelativeDescription](Anything, Anything, Anything, Anything)
		
		// Create a Surroundings object for the particular state/set of instructions
		var nothingList = Nothing(initSurroundingsList, nothingInputs)
		val surroundingsInputs = Something(nothingList, somethingInputs)
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
		val pattern = "Go\\Q(\\E(.*)(?=\\Q)\\E)".r	
		var goInput = ""
		pattern.findAllIn(goPart).matchData foreach {
			m => goInput = m.group(1)
		}
		val moveDirection, nextState  = Go(startingState, surroundingsObject, goInput)

		// Create some function to build rules to go into rules
		// val rule = new RuleBuilder(startingState, surroundingsObject)
		
	}


	def Something(list: Array[RelativeDescription], input: String) = {
		 // 0 = Anything
		 // 1 = Open
		 // 2 = Blocked
		 var dirList = list
		 if (input == "N") {
		 	dirList(0) = Blocked
		 }
		 if (input == "E") {
		 	dirList(1) = Blocked
		 } 
		 if (input == "W") {
		 	dirList(2) = Blocked
		 }
		 if (input == "S") {
		 	dirList(3) = Blocked
		 }

    	 dirList
	}

	def Nothing(list: Array[RelativeDescription], input: String) = {
		 // 0 = Anything
		 // 1 = Open
		 // 2 = Blocked
		 var dirList = list
		 if (input == "N") {
		 	dirList(0) = Open
		 }
		 if (input == "E") {
		 	dirList(1) = Open
		 } 
		 if (input == "W") {
		 	dirList(2) = Open
		 }
		 if (input == "S") {
		 	dirList(3) = Open
		 }
    	 dirList
	}

	def Go(startingState: State, surroundingsObject: Surroundings, direction: String) = {
	    val program = Internal.this
	    println("startingState: " + startingState)
	    println("Direction: " + direction)
	    println("SurroundingsObject: " + surroundingsObject)
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
  
  	def addRule(rule: Rule) = rules += rule
  
	def run() = {
	  println("The number of rules in the list: " + rules.length)
	  Console.out.flush
      val maze = Maze("resources" + File.separator + mazeFilename)
      val picobot = new Picobot(maze, rules.toList) with TextDisplay with GUIDisplay
      stage = picobot.mainStage
      picobot.run()
  	}
} 