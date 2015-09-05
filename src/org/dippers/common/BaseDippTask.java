package org.dippers.common;

import org.powerbot.script.rt6.ClientAccessor;
import org.powerbot.script.rt6.ClientContext;

public abstract class BaseDippTask extends ClientAccessor
{
	public BaseDippTask(ClientContext ctx) 
	{
		super(ctx);
	}
	
    public abstract String description();
	public abstract boolean activate();
    public abstract void execute();
    public abstract int priority();

	public void gameMessage(String message)
	{
		// Don't need to do anything here. Override this function if needed
	}
}
