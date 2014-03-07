# Random-Access Machine Interpreter

This program is an interpreter for (my) dialect of code for a random-access machine (RAM), which is basically a typical RAM dialect except the addition of multiplication and division.

## Example

`simpleprogram.ram` containing

	// Simple program
	// Sums all numbers until 5 and writes the result into R0
	 
	// Prepare registers. The first line is not necessary since unset
	// registers are treated as 0.
	R0 = 0
	R1 = 5

	LOOP:
		// Jump to END if R1 is 0
		GZ R1 END

		// Update registers
		R0 = R0 + R1
		R1 = R1 - 1
		
		// Jump to the beginning of the loop
		GOTO LOOP 

	END:
		// Terminate the program
		HALT

ran with

	java -jar target/raminterpreter-1.0-jar-with-dependencies.jar examples/simpleprogram.ram

produces the output

	>> line 6: R0 = 0
	R0            0

	>> line 7: R1 = 5
	R0            0
	R1            5

	>> line 11: GZ R1 END
	R0            0
	R1            5

	>> line 14: R0 = R0 + R1
	R0            5
	R1            5

	>> line 15: R1 = R1 - 1
	R0            5
	R1            4

	>> line 18: GOTO LOOP
	R0            5
	R1            4

	>> line 11: GZ R1 END
	R0            5
	R1            4

		*snip*

	>> line 11: GZ R1 END
	R0           15
	R1            0

	>> line 22: HALT
	R0           15
	R1            0

For other examples have a look into the `examples/` folder.

## Operations

The interpreter supports the basic operations

	ADDRESS = ADDRESS
	ADDRESS = ADDRESS + ADDRESS
	ADDRESS = ADDRESS - ADDRESS
	ADDRESS = ADDRESS * ADDRESS
	ADDRESS = ADDRESS / ADDRESS

where `ADDRESS` either is a direct address `RX`, an indirect address `(RX)`, with `X` as an integer equal or greater zero, or an integer if the address is on the right side of an expression.

Labels can be created with 

	LABEL:

which can be used as jump targets in

	GOTO LABEL
	GZ ADDRESS LABEL
	GGZ ADDRESS LABEL
	GLZ ADDRESS LABEL

with `GZ` meaning "`GOTO` if zero", `GGZ` meaning "`GOTO` greater than zero" and `GLZ` meaning "`GOTO` if less then zero". 

	HALT

just terminates the program.

Except these commands C/C++-style and bash-style comments are also allowed.


## Limitations of this interpreter

The integers used in this interpreter are signed java ints, so these are not unlimited as a RAM usually requires, although supporting numbers until 2147483647 (2^31 - 1). Depending on your java implementation the maximal memory size is roughly this size as well.

Beneath those technical aspects the interpreter is quite slow. Most if not all things are done naively, so don't treat this interpreter as some high-end crazy fast interpreter.

## Building the interpreter

For building the interpreter Maven 3.X and a Java JDK (Version depending on Maven, for a recent Maven you need Java 1.6+) are required. When these prerequisites are met you can build the interpreter using

	mvn package

## Running RAM programs

	java -jar target/raminterpreter-1.0-jar-with-dependencies.jar program.ram

Reading from stdin is also supported:

	java -jar target/raminterpreter-1.0-jar-with-dependencies.jar < program.ram
