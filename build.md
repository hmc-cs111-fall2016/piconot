# Building and running Piconot

Clone this repository.

Put your rules in a file, say `Empty.bot`

Have a file room, say `resources/empty.txt`.

In the directory with our `build.sbt`. run the following command:

```
sbt "run-main piconot.external.Piconot resources/empty.txt Empty.bot"
```




