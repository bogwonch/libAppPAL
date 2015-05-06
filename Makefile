JC=javac
JFLAGS=-Xlint:unchecked
CLASSPATH=lib/antlr-runtime-4.5.jar:.

%.class: %.java
	$(JC) $(JFLAGS) -cp $(CLASSPATH) $<

# Java dependencies must be checked in to be built
SRC=$(shell git ls-files \*.java)

# Compile each Java source file
classes: $(SRC:.java=.class)
	
