package pl.pickaxe.largesk.bungee;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import pl.pickaxe.largesk.LargeSk;

public class LargeMessenger implements PluginMessageListener
{	
	Plugin lsk = LargeSk.getPluginInstance();
	Server srv = Bukkit.getServer();
	
	public LargeMessenger getMessenger()
	{
		return new LargeMessenger();
	}
	
	public void registerMessenger()
	{
		srv.getMessenger().registerOutgoingPluginChannel(lsk, "BungeeCord");
	    srv.getMessenger().registerIncomingPluginChannel(lsk, "BungeeCord", this);
	}

	@Override
	public void onPluginMessageReceived(String channel, Player player, byte[] message)
	{
	
		if ( ! channel.equals("BungeeCord")) return;
		
		ByteArrayDataInput in = ByteStreams.newDataInput(message);
		String sub = in.readUTF();
		
		if ( ! sub.equals("LargeSkEff")) return;
		
		String msg = in.readUTF();
		srv.getPluginManager().callEvent(new EvtPluginMessageReceived(msg));
	}
}
	 
