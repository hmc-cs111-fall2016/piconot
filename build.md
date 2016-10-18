# Building and running Piconot

Enter this in the command line to run our external DSL.

Empty bot:
```sbt "run-main piconot.external.Piconot resources/empty.txt src/main/scala/piconot/external/Empty.bot"```

Our RightHand.bot doesn't work, but you can type this to get errors:
```sbt "run-main piconot.external.Piconot resources/maze.txt src/main/scala/piconot/external/RightHand.bot"```