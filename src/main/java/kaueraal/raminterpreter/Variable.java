package kaueraal.raminterpreter;

import java.util.Vector;
import java.lang.IllegalArgumentException;

/* Variable which can be either directly
 * a number, a direct address or an indirect address. */
public class Variable
{
	public static enum Mode
	{
		NUMBER, DIRECT, INDIRECT
	}

	private Mode mode;

	/* Either the contained number, or the address of the cell to look at */
	private int data;

	/* Line in the code where this variable is */
	private int line;


	public Variable(Mode mode, int data, IntWrapper line)
	{
		this(mode, data, line.value);
	}

	public Variable(Mode mode, int data, int line)
	{
		this.mode = mode;
		this.data = data;
		this.line = line;
	}

	/* Write num to the (in)direct address, writing is usually filtered during parsing, so
	 * only writing to address is allowed but not writing to an value. */
	public void writeTo(Vector<Integer> memory, int num)
	{
		/* Position in memory to write to */
		int targetPos = 0;

		if(mode == Mode.DIRECT)
		{
			targetPos = data;
		}
		else if(mode == Mode.INDIRECT)
		{
			if(memory.size() >= data + 1)
			{
				if(memory.elementAt(data) < 0)
				{
					throw new IllegalArgumentException("Negative address.");
				}
				else
				{
					targetPos = memory.elementAt(data);
				}
			}
			else // Default to 0 if the cell isn't alreade used
			{
				targetPos = 0;
			}
		}
		else // => mode == Mode.NUMBER, shouldn't happen since it isn't allowed during parsing
		{
			throw new IllegalArgumentException("An integer is an invalid address to write to.");
		}

		// Allocate memory, if we have to
		while(memory.size() < targetPos + 1)
		{
			memory.add(0);
		}
		
		memory.setElementAt(num, targetPos);
	}

	/* Read a value from the (in)direct address or the integer itself if it
	 * is one. */
	public int readFrom(Vector<Integer> memory)
	{
		if(mode == Mode.NUMBER)
		{
			return data;
		}
		else if(mode == Mode.DIRECT)
		{
			if(memory.size() >= data + 1)
			{
				return memory.elementAt(data);
			}
			else // unused cell: Treated as zero
			{
				return 0;
			}
		}
		else // => mode == Mode.INDIRECT
		{
			if(memory.size() >= data + 1)
			{
				if(memory.size() >= memory.elementAt(data) + 1)
				{
					if(memory.elementAt(data) < 0)
					{
						throw new IllegalArgumentException("Negative address.");
					}
					else
					{
						return memory.elementAt(memory.elementAt(data));
					}
				}
				else // unused cell as target: Treat as zero
				{
					return 0;
				}
			}
			else // unused cell: Treated as zero
			{
				if(memory.size() >= 1)
				{
					return memory.elementAt(0);
				}
				else // And again 
				{
					return 0;
				}
			}
		}
	}
}
