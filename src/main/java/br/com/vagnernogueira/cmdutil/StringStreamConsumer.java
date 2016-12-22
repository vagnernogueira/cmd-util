package br.com.vagnernogueira.cmdutil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author Vagner Nogueira
 */
public class StringStreamConsumer extends Thread
{
	private InputStream inputStream;

	private StringBuilder stringBuilder;

	public StringStreamConsumer(InputStream inputStream)
	{
		this.inputStream = inputStream;
		stringBuilder = new StringBuilder();
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
				stringBuilder.append(line);
				stringBuilder.append("\n");
			}
			if (stringBuilder.length() > 0)
			{
				stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			}
		}
		catch (IOException e)
		{
			if (stringBuilder.length() > 0)
			{
				stringBuilder.append("\n");
			}
			stringBuilder.append("IOException: ");
			stringBuilder.append(e.getMessage());
		}
	}

	@Override
	public String toString()
	{
		return stringBuilder.toString();
	}
}
