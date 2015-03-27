package ir.cfgraph;

import ir.cfgraph.CodePoint;
import ir.ops.Statement;

public class LinearCodePoint extends CodePoint
{
	private Statement statement;
	private CodePoint successor;
	
	public LinearCodePoint(Statement statement)
	{
		this.statement = statement;
	}

	public CodePoint getSuccessor()
	{
		return successor;
	}

	public void setSuccessor(CodePoint successor)
	{
		this.successor = successor;
	}
	
	public Statement getStatement()
	{
		return statement;
	}
}
