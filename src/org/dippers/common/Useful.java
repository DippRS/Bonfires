package org.dippers.common;

import org.powerbot.script.Tile;
import org.powerbot.script.rt6.ClientContext;

public class Useful 
{
	public static boolean PlayerIsCloseToObject(ClientContext ctx, int id, int maxDistance)
	{
		return !ctx.objects.select().id(id).within(maxDistance).isEmpty();
	}
	
	public static boolean PlayerIsAtTile(ClientContext ctx, Tile tiles[])
	{
		boolean inPosition = false;
		
		final Tile playersLoc = ctx.players.local().tile();
		
		for (final Tile t : tiles)
		{
			if (playersLoc.distanceTo(t) == 0)
			{
				inPosition = true;
				break;
			}
		}
		
		return inPosition;
	}
}
