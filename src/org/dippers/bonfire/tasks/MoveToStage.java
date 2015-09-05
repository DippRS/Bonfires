package org.dippers.bonfire.tasks;

import java.util.concurrent.Callable;

import org.dippers.common.BaseDippTask;
import org.dippers.common.Items;
import org.dippers.common.Logging;
import org.dippers.common.Useful;
import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.Tile;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.TileMatrix;

public class MoveToStage extends BaseDippTask 
{
	protected final int m_maxDistance;
	protected final Tile[] m_tiles;
	
	public MoveToStage(ClientContext ctx, Tile[] tiles, int maxDistance) 
	{
		super(ctx);

		m_maxDistance = maxDistance;
		m_tiles = tiles;
	}

	@Override
	public String description()
	{
		return "Moving to location";
	}

	@Override
	public boolean activate() 
	{
		boolean inPosition = false;
		
		// Check to see if we're already close to a bonfire
		boolean closeToBonfire = Useful.PlayerIsCloseToObject(ctx, Items.RegFire, m_maxDistance);
		
		if (!closeToBonfire)
		{
			// We're not close to a bonfire... See if we're stood somewhere we can light a fire
			inPosition = Useful.PlayerIsAtTile(ctx, m_tiles);
		}
		
		// If we're walking already then don't activate
		boolean walking = ctx.players.local().animation() != -1;
		
		// If we are not close to a bonfire and aren't in position, and we're not already walking then we should activate
		return !closeToBonfire && !inPosition && !walking;
	}

	@Override
	public void execute() 
	{
		final Tile randTile = m_tiles[Random.nextInt(0, m_tiles.length)];
		
		// See if we can just move to the tile by clicking on the viewport
		final TileMatrix tileMtx = randTile.matrix(ctx);
		if (tileMtx.inViewport())
		{
			Logging.LogMsg("Walking to tile");
			
			tileMtx.interact("Walk here");
			
			final int recheckAmount = Random.nextInt(5, 10);
			
			// Wait until we've started moving, or up to about 1-2 seconds, before returning
			// This will prevent spam-clicking on the tile
			Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return ctx.players.local().animation() != -1;
                }
            }, 250, recheckAmount);
		}
		else
		{
			// It's not in the viewport so use the minimap
			// TODO ?
		}
	}

	@Override
	public int priority() {
		// TODO Auto-generated method stub
		return 0;
	}

}
