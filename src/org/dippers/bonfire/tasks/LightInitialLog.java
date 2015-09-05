package org.dippers.bonfire.tasks;

import java.util.concurrent.Callable;

import org.dippers.common.BaseDippTask;
import org.dippers.common.Items;
import org.dippers.common.Logging;
import org.dippers.common.Useful;
import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Item;

public class LightInitialLog extends BaseDippTask 
{
	protected final int m_maxDistance;
	protected final Tile[] m_tiles;
	
	protected boolean m_startedToLight;

	public LightInitialLog(ClientContext ctx, Tile[] tiles, int maxDistance) 
	{
		super(ctx);

		m_maxDistance = maxDistance;
		m_tiles = tiles;
		m_startedToLight = false;
	}

	@Override
	public String description() {
		return "Lighting first log";
	}

	@Override
	public boolean activate() 
	{
		boolean closeToBonfire = false, inPosition = false, standingStill = false;
		
		// Check if we have logs in the inventory
		if (!m_startedToLight && !ctx.backpack.id(Items.RegLogs).isEmpty())
		{
			// We have logs, so now check if we're able to light them
			// If we're in the correct position and there is no bonfire then we should light one
			standingStill = ctx.players.local().animation() == -1;
			
			if (standingStill)
			{
				Logging.LogMsg("Standing still in LightInitalLog");
				closeToBonfire = Useful.PlayerIsCloseToObject(ctx, Items.RegFire, m_maxDistance);
			
				if (!closeToBonfire)
				{
					inPosition = Useful.PlayerIsAtTile(ctx, m_tiles);
					Logging.LogMsg("Standing still, not close to a bonfire. InPosition=" + inPosition);
				}
				else
					Logging.LogMsg("Standing still but there is a bonfire close by. Not lighting anything");
			}
		}
		
		return !closeToBonfire && inPosition && standingStill;
	}

	@Override
	public void execute() 
	{
		Logging.LogMsg("Lighting the logs. m_startedToLight=" + m_startedToLight);
		
		// The timer ran out so we're probably just stood doing nothing
		// Try lighting them
		Item log = ctx.backpack.select().id(Items.RegLogs).first().poll();
		log.interact("Light");
	}

	@Override
	public int priority() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void gameMessage(String message)
	{
		super.gameMessage(message);

		Logging.LogMsg("Received message: " + message);
		
		// Set a flag that we have started to light the logs
		if (message.contains("attempt to light"))
		{
			Logging.LogMsg("Have started to light the logs");
			m_startedToLight = true;
		}
		else if (message.contains("fire catches"))
		{
			Logging.LogMsg("Have finished lighting the logs");
			m_startedToLight = false;
		}
	}
}
