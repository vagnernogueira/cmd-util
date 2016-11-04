package br.com.vagnernogueira.cmdutil.teste;

public class ProcessoLongo
{
	public static void main(String[] args)
	{
		try
		{
			Thread.sleep(5000);
		}
		catch (InterruptedException e)
		{
		}
		System.out.println("OUT: ProcessoLongo terminou!");
		System.err.println("ERR: ProcessoLongo terminou!");
	}
}
