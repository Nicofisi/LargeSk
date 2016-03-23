package pl.pickaxe.largesk.bungee;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import pl.pickaxe.largesk.LargeSk;
import pl.pickaxe.largesk.util.Xlog;

public class LargeMessenger implements PluginMessageListener
{	
	Plugin lsk = LargeSk.getPlugin();
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
		if ( ! channel.equals("BungeeCord"))
		{
			return;
		}
		ByteArrayDataInput in = ByteStreams.newDataInput(message);
		String subchannel = in.readUTF();
		if (subchannel.equals("Forward"))
		{
			String moresub = in.readUTF();
			if (moresub.equals("LargeSkEff"))
			{
				String msg = in.readUTF();
				Xlog.logInfo(channel + " > " + subchannel + " > " + moresub + " >> " + msg);
				srv.getPluginManager().callEvent(new EvtPluginMessageReceived(msg));
			}
		}
	}
}
	 
