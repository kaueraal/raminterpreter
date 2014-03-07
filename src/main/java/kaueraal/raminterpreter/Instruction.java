package kaueraal.raminterpreter;

import java.util.Vector;
import java.util.HashMap;

/* One Instruction in the RAM (like
 * an assignment, a jump or halt). */
public abstract class Instruction
{
	/* Line of the source file in which this instruction lies,
	 * starting with one */
	private int line;

	public void setLine(int line)
	{
		this.line = line;
	}

	public void setLine(IntWrapper line)
	{
		this.line = line.value;
	}

	public int getLine()
	{
		return this.line;
	}

	/* Returns the index of the next instruction to run 
	 * or -1 to terminate. */
	public abstract int run(Vector<Integer> memory, HashMap<String,Integer> labels, int instructionPos);
}
