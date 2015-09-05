package org.dippers.bonfire;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import org.dippers.common.*;
import org.dippers.bonfire.tasks.*;
import org.powerbot.script.MessageEvent;
import org.powerbot.script.MessageListener;
import org.powerbot.script.PaintListener;
import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import org.powerbot.script.rt6.ClientContext;

@Script.Manifest(name="DippBonfire", description="Makes bonfires at G.E.")
public class BonfireMain extends PollingScript<ClientContext>  implements MessageListener, PaintListener
{
	protected static final String VERSION = "0.0.0.1";
	protected static final Font TAHOMA = new Font("Tahoma", Font.PLAIN, 12);
	
	protected List<BaseDippTask> m_tasks = new ArrayList<BaseDippTask>();
	protected BaseDippTask m_activeTask;
	
	@Override
	public void start() 
	{
		Logging.LogMsg("DippBonfire v" + VERSION + " starting up.");
		
		// Add all the tasks we require
		m_tasks.add(new BankTask(ctx));
		m_tasks.add(new BurnTask(ctx));
		
		Logging.LogMsg(m_tasks.size() + " Tasks set up.");
		
		m_activeTask = null;
	}
	
	@Override
	public void poll() 
	{
		for (BaseDippTask task : m_tasks)
		{
			if (task.activate())
			{
				m_activeTask = task;
				task.execute();
			}
		}
	}

    @Override
    public void repaint(Graphics graphics) 
    {
        final Graphics2D g = (Graphics2D) graphics;
        g.setFont(TAHOMA);

        g.setColor(Color.BLACK);
        g.fillRect(5, 5, 125, 45);

        g.setColor(Color.WHITE);

        if (m_activeTask != null)
        	g.drawString("Active Task: " + m_activeTask.description(), 10, 20);
        
        g.drawString("Second line of paint", 10, 40);
    }

	@Override
	public void messaged(MessageEvent arg0) 
	{
		// Just pass the message along to the tasks
		for (BaseDippTask task : m_tasks)
			task.gameMessage(arg0.text());
	}
}
