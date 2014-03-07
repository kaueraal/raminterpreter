package kaueraal.raminterpreter;

/* Wrapper for an int, so we can do call by reference (since javas autoboxing is quite broken..) */
public class IntWrapper
{
	public int value;

	public IntWrapper()
	{
		value = 0;
	}

	public IntWrapper(int i)
	{
		value = i;
	}
}
