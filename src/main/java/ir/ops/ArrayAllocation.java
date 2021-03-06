package ir.ops;

import ir.visitor.IrVisitor;

public class ArrayAllocation implements Expression
{
	private DataType type;
	private Expression size;
	
	public ArrayAllocation(DataType type, Expression size)
	{
		this.type = type;
		this.size = size;
	}

	@Override
	public void accept(IrVisitor visitor)
	{
		visitor.visit(this);
	}
	
	public Expression getSize()
	{
		return size;
	}

	@Override
	public String toString()
	{
		return "new int[" + size + "]";
	}
}
