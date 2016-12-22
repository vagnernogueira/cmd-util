package br.com.vagnernogueira.cmdutil.teste;

import br.com.vagnernogueira.cmdutil.CommandUtil;

/**
 * 
 * @author Vagner Nogueira
 * 
 * 
 * Ref: http://www.javaworld.com/article/2071275/core-java/when-runtime-exec---won-t.html
 * 
 * Nota:
 * 	Utilitários criados para uso particular em 01/02/2016
 * 	Ajustes em 16/05/2016
 * 
 * TESTE com processo demorado (maior que tempo de timeout):
 * java -jar cmdutil.jar /usr/bin/java -cp /home/vagner/cmdutil.jar br.com.vagnernogueira.cmdutil.teste.ProcessoLongo
 * 	
 */
public class Main
{
	public static void main(String[] args)
	{
		/*String[] cmd = {"cmd.exe", "/C", "dir", "/a", "/b", "C:"};
		CommandUtil syncExecUtil = new CommandUtil(cmd);*/
		
		CommandUtil commandUtil = new CommandUtil(args);
		try
		{
			commandUtil.perform(2000, null);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		System.out.println(commandUtil.getOutput());
		System.out.println("---");
		System.out.println(commandUtil.getError());
	}
}
