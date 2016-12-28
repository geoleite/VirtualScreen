// Copyright (C) Bill Clagett 1997.
package br.com.easynet.virtualscreen.portScan;

import java.net.*;
import java.io.IOException;


/** 
 * Representation of a specific port on a specific machine
 *
 * @author  Bill Clagett (bill.clagett@sitesonthe.net)
 * @version %I%, %G% 
*/
public class Port
{
	InetAddress _host = null;
	int _port = -1;

	public Port(InetAddress host, int port)
	{
		_host = host;
		_port = port;
	}
 
	/**
	* Trys to connection to the port.  
	* If it succeeds, a message is printed to System.out.
	*/
	public boolean scan()
	{
		try 
		{
			Socket s = new Socket(_host, _port);
                        s.getOutputStream().write(0);
                        s.getOutputStream().flush();
			//System.out.println("listener on " + _host +":"+_port );        
			s.close();
                        return true;
		}
/*catch (ConnectException e) 
		{
		}
		catch (IOException e) 
		{
			System.out.println(e + " while trying" + _host + ":" +_port);        
		}
*/
		// Begin JDK 1.0 version
		catch (SocketException e) 
		{
		}
		// End JDK 1.0 version
		catch (IOException e) 
		{
			System.out.println(e + " while trying" + _host + ":" +_port);        
		}
                return false;
	}
}
