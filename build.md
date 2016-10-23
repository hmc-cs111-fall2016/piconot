# Building and running Piconot

In order to build our DSL, make sure to be in our file directory and run
the following command for solving an empty room:
sbt "run-main piconot.external.Piconot resources/empty.txt src/main/scala/piconot/external/Empty.bot"

Run the following command for solving a maze using the right-hand rule:
sbt "run-main piconot.external.Piconot resources/maze.txt src/main/scala/piconot/external/RightHand.bot"
