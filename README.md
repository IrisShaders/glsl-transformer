# GLSL Parsing with ANTLR4

This repo is based on https://github.com/gabriele-tomassetti/antlr-mega-tutorial which is part of the nice [Java Setup section of the ANTLR Mega Tutorial](https://tomassetti.me/antlr-mega-tutorial/#java-setup).

## CLI Use

The instructions are from the original repo and provide info on how to use the project

```
# this README assumes that you have installed Gradle in your system
# to generate the JetBrains IntelliJ IDEA project
./gradlew idea
# you can then use standard interface of IntelliJ IDEA to build and run the program
# alternatively, if you just want to use gradle
# to generate the ANTLR4 Parser
./gradlew generateGrammarSource
# to compile the program
./gradlew compileJava
# to create a JAR with all dependencies
./gradlew fatJar
# ... and then run the program
java -jar .\build\libs\markup-example-gradle-all.jar
# to run the tests
./gradlew test
```
