package org.dippers.bonfire.tasks;

import org.dippers.common.BaseDippTask;
import org.powerbot.script.rt6.ClientContext;

public class BankTask extends BaseDippTask 
{
	public BankTask(ClientContext ctx) 
	{
		super(ctx);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String description() 
	{
		return "Banking";
	}

	@Override
	public boolean activate() 
	{
		// We should bank to withdraw more logs when the inventory is full
		boolean inventEmpty = ctx.backpack.select().count() == 0;		
		return inventEmpty || ctx.bank.opened();
	}

	@Override
	public void execute() 
	{
		// Check to see if the bank is open
		if (!ctx.bank.opened()) 
		{
            // Bank wasn't open, so open it
            ctx.bank.open();
        }
		else
		{
			// Bank is open, use the preset 2 to withdraw more logs. Use it by a keypress
			ctx.bank.presetGear(2, true);
		}
	}

	@Override
	public int priority() {
		// TODO Auto-generated method stub
		return 0;
	}

}
