package kaueraal.raminterpreter;

import java.util.Vector;
import java.util.HashMap;
import java.lang.ArithmeticException;

/* Calculates target = lop OP rop */
public class InstructionOp extends Instruction
{
	public static enum Op
	{
		ASSIGN, PLUS, MINUS, MULT, DIV
	}

	/* Where the result is written */
	private Variable target;

	public void setTarget(Variable target)
	{
		this.target = target;
	}

	/* Left operant */
	private Variable lop;

	public void setLeftOperant(Variable lop)
	{
		this.lop = lop;
	}

	/* Right operant */
	private Variable rop;

	public void setRightOperant(Variable rop)
	{
		this.rop = rop;
	}

	/* The operation to perform on lop and rop */
	private Op op;

	public void setOperationType(Op op)
	{
		this.op = op;
	}

	public int run(Vector<Integer> memory, HashMap<String,Integer> label, int instructionPos)
	{
		int left = lop.readFrom(memory);
		int right = 0;
		int result = 0;

		if(op != Op.ASSIGN)
			right = rop.readFrom(memory);

		switch(op)
		{
			case PLUS:
				result = left + right;
				break;
			case MINUS:
				result = left - right;
				break;
			case MULT:
				result = left * right;
				break;
			case DIV:
				if(right == 0)
				{
					throw new ArithmeticException("Division by zero (on line" + getLine() + ")");
				}
				result = left / right;
				break;
			case ASSIGN:
				result = left;
		}

		target.writeTo(memory, result);
		return instructionPos + 1;
	}
}
