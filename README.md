# JTriss
Active development years: 2012

## Summary
Attempt to port TRISS project (http://github.com/rtshadow/Triss) to Java. Please, refer to TRISS readme for more info.

This project was never finished. I did some simplifications to TRISS design while porting and, as it turned out later, that was a bad decision.

## Working with source code
You will need:
* Java 7 JDK: www.oracle.com/technetwork/java/javase/downloads/index.html
* gradle 1.6 (or newer) build tool: www.gradle.org/ 

### Compilation
Just do ```gradle build``` in root directory. gradle will download all required dependencies, compile classes and run unit tests.

### Editing
I strongly recommend IntelliJ for editing the source. The community edition is free for non-commercial purposes and can be downloaded here: www.jetbrains.com/idea/
Execute ```gradle idea```, which will generate all required files. Then simply open the project in IntelliJ.

### Running system (acceptance) tests
Simply do ```gradle runSystemTests```

## Usage
Sources in ```jtriss-system-test``` present what can be done with JTriss.
