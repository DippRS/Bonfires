package org.dippers.bonfire.tasks;

import org.dippers.common.BaseDippTask;
import org.dippers.common.Items;
import org.dippers.common.Logging;
import org.powerbot.script.Condition;
import org.powerbot.script.MessageEvent;
import org.powerbot.script.MessageListener;
import org.powerbot.script.Random;
import org.powerbot.script.Tile;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.GameObject;
import org.powerbot.script.rt6.Item;

public class BurnTask extends BaseDippTask
{
	// A 4x4 grid where we can make bonfires
	Tile[] BONFIRE_TILES = {
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
	
	public BurnTask(ClientContext ctx) {
		super(ctx);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String description() {
		return "Burning Logs";
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
		boolean foundFire = ctx.objects.select().id(Items.RegFire).isEmpty();
		if (!foundFire)
		{
			Logging.LogMsg("No fire found - starting our own.");
			Logging.LogMsg("Walking to tile.");
			
			// We need to make our own fire
			// Walk to a tile close by
			int tile = Random.nextInt(0, BONFIRE_TILES.length);
			ctx.movement.step(BONFIRE_TILES[tile]);
			
			Condition.sleep(2500);
			
			Logging.LogMsg("Lighting logs.");
			
			// Light the logs
			Item log = ctx.backpack.select().id(Items.RegLogs).first().poll();
			log.interact("Light");

			// Wait for them to be lit
			while (ctx.players.local().animation() != -1)
			{
				Condition.sleep(750);
			}

			Logging.LogMsg("Starting to add things to the bonfire.");
			
			// Now add our other logs to the bonfire
			Item otherLog = ctx.backpack.select().id(Items.RegLogs).first().poll();
			otherLog.interact("Craft");
		}
		else
		{
			Logging.LogMsg("Found a fire - will add to the bonfire.");
			
			// There is a fire, so select that one
			GameObject fire = ctx.objects.nearest().poll();
			fire.interact("Use");
		}
	}

	@Override
	public int priority() {
		// TODO Auto-generated method stub
		return 0;
	}

}
