ANTLR4=java -jar tools/antlr-4.5-complete.jar
JC=javac
JFLAGS=-Xlint:unchecked -source 1.7 -target 1.7
CLASSPATH=lib/antlr-runtime-4.5.jar:.

%.class: %.java
	$(JC) $(JFLAGS) -cp $(CLASSPATH) $<

# Java dependencies must be checked in to be built
SRC=$(shell git ls-files \*.java)

GRAMMAR=apppal/logic/grammar/AppPAL.g4
PARSER_SRC=apppal/logic/grammar/AppPALBaseListener.java \
					 apppal/logic/grammar/AppPALBaseVisitor.java  \
					 apppal/logic/grammar/AppPALLexer.java        \
					 apppal/logic/grammar/AppPALListener.java     \
					 apppal/logic/grammar/AppPALParser.java       \
					 apppal/logic/grammar/AppPALVisitor.java
MANIFEST=Manifest.txt
LINTER_MANIFEST=Linter/Manifest.txt
SCHEMA_MANIFEST=Schema/Manifest.txt
TARGET_JAR=AppPAL.jar
LINTER_JAR=Lint.jar
SCHEMA_JAR=Schema.jar

# WARNING: use GNU make or build each step in all in order ;-)
all: | parser classes jar

jar: $(TARGET_JAR) $(LINTER_JAR) $(SCHEMA_JAR)

format: $(shell find apppal -name \*.java)
	@google-java-format -i $?

# Compile each Java source file
classes: $(PARSER_SRC:.java=.class) $(SRC:.java=.class) 

# Build the parser files
parser: $(PARSERSRC)

$(PARSER_SRC): $(GRAMMAR)
	$(ANTLR4) -visitor -package apppal.logic.grammar $<

$(TARGET_JAR): classes $(MANIFEST)
	jar cfm $(@) $(MANIFEST) apppal lib

$(LINTER_JAR): classes $(LINTER_MANIFEST)
	jar cfm $(@) $(LINTER_MANIFEST) apppal lib

$(SCHEMA_JAR): classes $(SCHEMA_MANIFEST)
	jar cfm $(@) $(SCHEMA_MANIFEST) apppal lib

clean:
	$(RM) $(shell find . -name \*.class) $(PARSER_SRC) $(TARGET_JAR) $(LINTER_JAR)

