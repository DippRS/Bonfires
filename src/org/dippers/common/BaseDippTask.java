package org.dippers.common;

import org.powerbot.script.rt6.ClientAccessor;
import org.powerbot.script.rt6.ClientContext;

public abstract class BaseDippTask extends ClientAccessor
{
	public BaseDippTask(ClientContext ctx) 
	{
		super(ctx);
	}
	
	public abstract boolean activate();
    public abstract void execute();
}
