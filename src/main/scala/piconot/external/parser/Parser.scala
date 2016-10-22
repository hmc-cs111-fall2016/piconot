import scala.util.parsing.combinator._
// import piconot.ir._


// object PicoParser extends JavaTokenParsers with PackratParsers{

//     // parsing interface
//     def apply(s: String): ParseResult[Rule] = parseAll(expr, s)

//     // expressions
//     lazy val expr: PackratParser[Rule] = 
//       (   "Label"~name~":"~body ^^ {case "Label"~n~":"~b ⇒ Label(n)(b)}  )
        
//     // factors
//     lazy val body: PackratParser[Rule] =
//       (   "Face"~direction ^^ {case "Face"~d ⇒ Face(d)}  
//         | "Go until"~surrounding ^^ {case "Go until"~s => Go.until(s)}
//         | "Jump"~expr            ^^ {case "Jump"~e => Jump(e)})
      
//     lazy val direction: PackratParser[Rule] = 
//       (   "North"   ^^ {case "North" => NORTH}
//         | "West"   ^^ {case "West" => WEST}
//         | "South"   ^^ {case "South" => SOUTH}
//         | "East"   ^^ {case "East" => EAST})

//     lazy val surrounding: PackratParser[Rule] = 
//       (   "BACK_WALL"               ^^ {case "BACK_WALL" => BACK_WALL}
//         | "RIGHT_WALL"              ^^ {case "RIGHT_WALL" => RIGHT_WALL}
//         | "LEFT_WALL"               ^^ {case "LEFT_WALL" => LEFT_WALL}
//         | "FRONT_WALL"              ^^ {case "FRONT_WALL" => FRONT_WALL}
//         | "RIGHT_LEFT_WALL"         ^^ {case "RIGHT_LEFT_WALL" => RIGHT_LEFT_WALL}
//         | "RIGHT_FRONT_WALL"        ^^ {case "RIGHT_FRONT_WALL" => RIGHT_FRONT_WALL}
//         | "RIGHT_BACK_WALL"         ^^ {case "RIGHT_BACK_WALL" => RIGHT_BACK_WALL}
//         | "RIGHT_LEFT_FRONT_WALL"   ^^ {case "RIGHT_LEFT_FRONT_WALL" => RIGHT_LEFT_FRONT_WALL}
//         | "RIGHT_LEFT_BACK_WALL"    ^^ {case "RIGHT_LEFT_BACK_WALL" => RIGHT_LEFT_BACK_WALL}
//         | "RIGHT_FRONT_BACK_WALL"   ^^ {case "RIGHT_FRONT_BACK_WALL" => RIGHT_FRONT_BACK_WALL}
//         | "LEFT_FRONT_WALL"         ^^ {case "LEFT_FRONT_WALL" => LEFT_FRONT_WALL}
//         | "LEFT_BACK_WALL"          ^^ {case "LEFT_BACK_WALL" => LEFT_BACK_WALL}
//         | "LEFT_FRONT_BACK_WALL"    ^^ {case "LEFT_FRONT_BACK_WALL" => LEFT_FRONT_BACK_WALL}
//         | "FRONT_BACK_WALL"         ^^ {case "FRONT_BACK_WALL" => FRONT_BACK_WALL})

//     // does this just consume our desired string or all the tokens in the world
//     def name: Parser[String] = stringLiteral ^^ {case s => s}
    
//  }