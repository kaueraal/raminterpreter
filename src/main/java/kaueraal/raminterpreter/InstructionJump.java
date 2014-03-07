package kaueraal.raminterpreter;

import java.util.Vector;
import java.util.HashMap;
import java.lang.RuntimeException;

/* Both conditional and nonconditional jump instruction */
public class InstructionJump extends Instruction
{
	public static enum JumpType
	{
	 	/* Unconditional jump */
		GOTO,
		
	 	/* Jump if zero */
		GZ,
		
	 	/* Jump if greater than zero */
		GGZ,
		
	 	/* GLZ: Jump if less than zero */
		GLZ
	}

	/* Variable which is compared to zero if it is a conditional jump */
	private Variable var; 

	public void setVar(Variable var)
	{
		this.var = var;
	}

	private String label;

	public void setLabel(String label)
	{
		this.label = label;
	}

	private JumpType type;

	public void setJumpType(JumpType type)
	{
		this.type = type;
	}

	/* Tests the condition (if there is any to test) and sets the next instruction accordingly */
	public int run(Vector<Integer> memory, HashMap<String,Integer> labels, int instructionPos)
	{
		Integer target = labels.get(label);

		if(target == null)
		{
			throw new RuntimeException("Label " + label + " not defined (line " + getLine() + ")");
		}

		switch(type)
		{
			case GOTO:
				return target;
			case GZ:
				if(var.readFrom(memory) == 0)
				{
					return target;
				}
				else
				{
					return instructionPos + 1;
				}
			case GGZ:
				if(var.readFrom(memory) > 0)
				{
					return target;
				}
				else
				{
					return instructionPos + 1;
				}
			case GLZ:
				if(var.readFrom(memory) < 0)
				{
					return target;
				}
				else
				{
					return instructionPos + 1;
				}
		}

		// Stops the compiler from complaining, actually never called
		return instructionPos + 1;
	}
}
