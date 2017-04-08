#
# A simple makefile for compiling three java classes
#

# define a makefile variable for the java compiler
#
JCC = javac

# define a makefile variable for compilation flags
# the -g flag compiles with debugging information
#
JFLAGS = -g

# typing 'make' will invoke the first target entry in the makefile 
# (the default one in this case)
#
default: HuffmanNode.class FourWayCacheOptimizedHeap.class BinaryHeapArrayList.class PairingHeap.class Huffman.class DecideDataStructure.class encoder.class decoder.class

# this target entry builds the Average class
# the Average.class file is dependent on the Average.java file
# and the rule associated with this entry gives the command to create it
#
HuffmanNode.class: HuffmanNode.java
	$(JCC) $(JFLAGS) HuffmanNode.java	
	
FourWayCacheOptimizedHeap.class: FourWayCacheOptimizedHeap.java
	$(JCC) $(JFLAGS) FourWayCacheOptimizedHeap.java	
	
BinaryHeapArrayList.class: BinaryHeapArrayList.java
	$(JCC) $(JFLAGS) BinaryHeapArrayList.java

PairingHeap.class: PairingHeap.java
	$(JCC) $(JFLAGS) PairingHeap.java	
	
Huffman.class: Huffman.java
	$(JCC) $(JFLAGS) Huffman.java
	
DecideDataStructure.class: DecideDataStructure.java
	$(JCC) $(JFLAGS) DecideDataStructure.java
	
encoder.class: encoder.java
	$(JCC) $(JFLAGS) encoder.java
	
decoder.class: decoder.java
	$(JCC) $(JFLAGS) decoder.java
		
# To start over from scratch, type 'make clean'.  
# Removes all .class files, so that the next make rebuilds them
#
clean: 
	$(RM) *.class