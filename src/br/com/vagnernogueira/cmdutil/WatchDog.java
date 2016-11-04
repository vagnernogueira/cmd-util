package br.com.vagnernogueira.cmdutil;

import java.lang.management.ManagementFactory;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;

/**
 * @author Vagner Nogueira
 */
public class WatchDog
{
	private Timer timer;

	private boolean overTime = false;

	private boolean failOnDestroy = false;

	public WatchDog(Process process, long time, Consumer<String> failOnDestroyHaldler)
	{
		super();
		timer = new Timer();
		if (time > 0)
		{
			timer.schedule(new TimerTask() {
				@Override
				public void run()
				{
					if (process != null && process.isAlive())
					{
						process.destroy();
						if (process.isAlive())
						{
							process.destroyForcibly();
						}
						if (process.isAlive())
						{
							failOnDestroy = true;
							if (failOnDestroyHaldler != null)
							{
								failOnDestroyHaldler.accept(ManagementFactory.getRuntimeMXBean().getName().split("@")[0]);
							}
						}
						overTime = true;
					}
				}
			}, time);
		}
	}

	public void cancel()
	{
		timer.cancel();
	}

	public boolean isOverTime()
	{
		return overTime;
	}

	public boolean isFailOnDestroy()
	{
		return failOnDestroy;
	}
}
