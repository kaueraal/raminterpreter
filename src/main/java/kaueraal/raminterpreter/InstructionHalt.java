package kaueraal.raminterpreter;

import java.util.Vector;
import java.util.HashMap;

/* HALT instruction, terminates the program */
public class InstructionHalt extends Instruction
{
	/* Returns -1 to terminate the program */
	public int run(Vector<Integer> memory, HashMap<String,Integer> labels, int instructionPos) 
	{
		return -1;
	}
}
