package br.com.vagnernogueira.cmdutil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

/**
 * @author Vagner Nogueira
 */
public class StringStreamConsumerWithRedirect extends Thread
{
	private InputStream inputStream;

	private PrintStream exitStream;

	public StringStreamConsumerWithRedirect(InputStream inputStream, PrintStream exitStream)
	{
		this.inputStream = inputStream;
		this.exitStream = exitStream;
		start();
	}

	@Override
	public void run()
	{
		try
		{
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String line = null;
			while ((line = bufferedReader.readLine()) != null)
			{
				exitStream.println(line);
			}
		}
		catch (IOException e)
		{
			exitStream.println("IOException: " + e.getMessage());
		}
	}
}
