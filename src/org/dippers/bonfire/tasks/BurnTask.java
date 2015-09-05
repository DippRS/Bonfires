package org.dippers.bonfire.tasks;

import org.dippers.common.BaseDippTask;
import org.powerbot.script.Tile;
import org.powerbot.script.rt6.ClientContext;

public class BurnTask extends BaseDippTask
{
	// A 4x4 grid where we can make bonfires
	static final Tile[] BONFIRE_TILES = {
			new Tile(3185, 3479),
			new Tile(3186, 3479),
			new Tile(3187, 3479),
			new Tile(3185, 3480),
			new Tile(3186, 3480),
			new Tile(3187, 3480),
			new Tile(3185, 3481),
			new Tile(3186, 3481),
			new Tile(3187, 3481),
			new Tile(3185, 3482),
			new Tile(3186, 3482),
			new Tile(3187, 3482),
	};
	
	static final int MAX_DISTANCE = 3;
	
	public BurnTask(ClientContext ctx) 
	{
		super(ctx);
		
		// Create our sub-tasks for moving to location, lighting logs and using the bonfire
		m_subtasks.add(new MoveToStage(ctx, BONFIRE_TILES, MAX_DISTANCE));
		m_subtasks.add(new LightInitialLog(ctx, BONFIRE_TILES, MAX_DISTANCE));
	}

	@Override
	public String description() 
	{
		if (m_activeSubtask == null)
			return "Burning Logs";
		else
			return "Burning Logs: " + m_activeSubtask.description();
	}

	@Override
	public boolean activate() 
	{
		boolean inventEmpty = ctx.backpack.select().count() == 0;
		return !inventEmpty && !ctx.bank.opened();
	}

	@Override
	public void execute() 
	{
		for (BaseDippTask task : m_subtasks)
		{
			if (task.activate())
			{
				m_activeSubtask = task;
				task.execute();
			}
		}
	}

	@Override
	public int priority() {
		// TODO Auto-generated method stub
		return 0;
	}

}
