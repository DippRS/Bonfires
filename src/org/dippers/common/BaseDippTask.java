package org.dippers.common;

import java.util.ArrayList;
import java.util.List;

import org.powerbot.script.rt6.ClientAccessor;
import org.powerbot.script.rt6.ClientContext;

public abstract class BaseDippTask extends ClientAccessor
{
	protected List<BaseDippTask> m_subtasks = new ArrayList<BaseDippTask>();
	protected BaseDippTask m_activeSubtask;
	
	public BaseDippTask(ClientContext ctx) 
	{
		super(ctx);
		m_activeSubtask = null;
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
