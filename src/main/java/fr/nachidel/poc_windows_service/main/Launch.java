package fr.nachidel.poc_windows_service.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

public class Launch
{
	public static boolean RUN = true;
	public static Server server;

	public static void main(String[] args)
	{
		RUN = true;

		console_commands();
		start(args);
	}

	private static void console_commands()
	{
		Thread console = new Thread(new Runnable() {

			@Override
			public void run()
			{
				BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

				do
				{
					try
					{
						String command = reader.readLine();

						if (command != null)
						{
							switch (command)
							{
							case "close":
							case "exit":
								RUN = false;
								stop(null);
								break;
							}
						}

						Thread.sleep(100);
					}
					catch (IOException | InterruptedException e)
					{
						e.printStackTrace();
					}

				}
				while (RUN);
			}
		});

		console.setName("console_commands");
		console.start();
	}

	public static void start(String[] args)
	{
		try
		{
			server = new Server(8080);
			ServletHandler servletHandler = new ServletHandler();
			server.setHandler(servletHandler);
			servletHandler.addServletWithMapping(HelloServlet.class, "/");
			server.start();
			server.join();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	public static void stop(String[] args)
	{
		try
		{
			server.stop();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
