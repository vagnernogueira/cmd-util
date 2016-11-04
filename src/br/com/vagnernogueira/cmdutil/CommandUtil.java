package br.com.vagnernogueira.cmdutil;

import java.io.PrintStream;
import java.util.function.Consumer;

/**
 * @author Vagner Nogueira
 */
public class CommandUtil
{
	private Process process;

	private int exitValue;

	private String error;

	private String output;

	private String[] cmd;

	private WatchDog watchDog;

	public CommandUtil(String... commandAndArgs)
	{
		if (commandAndArgs.length == 0)
		{
			throw new IllegalArgumentException("commandAndArgs vazio");
		}
		this.cmd = normalizeCmd(commandAndArgs);
	}

	public void perform(long timeTimeOut, Consumer<String> failOnDestroyHaldler) throws Exception
	{
		try
		{
			Runtime runtime = Runtime.getRuntime();
			process = runtime.exec(cmd);
			watchDog = new WatchDog(process, timeTimeOut, failOnDestroyHaldler);
			StringStreamConsumer errorStringStreamConsumerHelper = new StringStreamConsumer(process.getErrorStream());
			StringStreamConsumer outputStringStreamConsumerHelper = new StringStreamConsumer(process.getInputStream());
			exitValue = process.waitFor();
			errorStringStreamConsumerHelper.join();
			outputStringStreamConsumerHelper.join();
			watchDog.cancel();
			error = errorStringStreamConsumerHelper.toString();
			output = outputStringStreamConsumerHelper.toString();
		}
		catch (Exception e)
		{
			throw new Exception("Falha ao tentar executar Processo Runtime", e);
		}
	}

	// executa sem contagem de tempo de timeout
	public void perform(Consumer<String> failOnDestroyHaldler) throws Exception
	{
		perform(0, failOnDestroyHaldler);
	}

	public void performWithRedirect(long timeTimeOut, PrintStream exitStream, Consumer<String> failOnDestroyHaldler) throws Exception
	{
		try
		{
			Runtime runtime = Runtime.getRuntime();
			process = runtime.exec(cmd);
			watchDog = new WatchDog(process, timeTimeOut, failOnDestroyHaldler);
			StringStreamConsumerWithRedirect errorStringStreamConsumerHelper = new StringStreamConsumerWithRedirect(process.getErrorStream(), exitStream);
			StringStreamConsumerWithRedirect outputStringStreamConsumerHelper = new StringStreamConsumerWithRedirect(process.getInputStream(), exitStream);
			exitValue = process.waitFor();
			errorStringStreamConsumerHelper.join();
			outputStringStreamConsumerHelper.join();
			watchDog.cancel();
		}
		catch (Exception e)
		{
			throw new Exception("Falha ao tentar executar Processo Runtime", e);
		}
	}

	// remove caracteres de controle
	private String[] normalizeCmd(String[] cmd)
	{
		for (int i = 0; i < cmd.length; i++)
		{
			cmd[i] = cmd[i].replaceAll("\\p{Cntrl}", "");
		}
		return cmd;
	}

	public boolean isOverTime()
	{
		return watchDog.isOverTime();
	}

	public boolean isFailOnDestroy()
	{
		return watchDog.isFailOnDestroy();
	}

	public Process getProcess()
	{
		return process;
	}

	public void setProcess(Process process)
	{
		this.process = process;
	}

	public int getExitValue()
	{
		return exitValue;
	}

	public void setExitValue(int exitValue)
	{
		this.exitValue = exitValue;
	}

	public String getError()
	{
		return error;
	}

	public void setError(String error)
	{
		this.error = error;
	}

	public String getOutput()
	{
		return output;
	}

	public void setOutput(String output)
	{
		this.output = output;
	}
}
