package piconot.internal 


Object Internal extends App {
	private val listOfRules = MutableList.empty[Rule]

	def If(somethingNothingPart: String, goPart: String): Surroundings = {
		// This is all kind of sketchy right now, but shows a direction we could
		// go. Basically, this If function would take in a string of instructions
		// (If("Nothing[N] and Something[W]", "Go North"))) we'd need to parse it
		// to find whether they specified "Nothing" or "Something" and what they
		// passed in for "Go".  Then we'd use this to call the appropriate functions
		// (Nothing, Something, Go) to generate params for our ruleBuilder which
		// would then create a rule to add to our rulesList, which would then be
		// used when running the program.

		var dirList = [0, 0, 0, 0]
		
		// Create a Surroundings object for the particular state/set of instructions
		var nothingList= Nothing("N", dirList)
		val finalSurroundingsList = Something("N", nothinglist)

		// Create the output values
		val outputState = Go("North")

		// Create some function to build rules to go into listOfRules
		val rule = ruleBuilder(finalsurroudninglist, outputState)
		
		// Create some function to add a rule to the listOfRules field.
		addRule(rule)
	}

	def Something(list: String): List[Int] = {
		 // 0 = Anything
		 // 1 = Open
		 // 2 = Blocked
		 var dirList = [0, 0, 0, 0]
		 if (list.contains("N")) {
		 	dirList[0] = 2
		 }
		 if (list.contains("E")) {
		 	dirList[1] = 2
		 } 
		 if (list.contains("W")) {
		 	dirList[2] = 2
		 }
		 if (list.contains("S")) {
		 	dirList[3] = 2
		 }

    	 dirList
	}
	def Nothing(list: String) = {
		 // 0 = Anything
		 // 1 = Open
		 // 2 = Blocked
		 var dirList = [0, 0, 0, 0]
		 if (list.contains("N")) {
		 	dirList[0] = 1
		 }
		 if (list.contains("E")) {
		 	dirList[1] = 1
		 } 
		 if (list.contains("W")) {
		 	dirList[2] = 1
		 }
		 if (list.contains("S")) {
		 	dirList[3] = 1
		 }
    	 
    	 dirList
	}

	// ruleBuilder
}
  