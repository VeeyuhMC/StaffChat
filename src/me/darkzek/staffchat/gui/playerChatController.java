package me.darkzek.staffchat.gui;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;

import me.darkzek.staffchat.Main;

public class playerChatController implements CommandExecutor,Listener{
	Main m;
	public void onEnable() {
		m = Main.setInstance();
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("chat")) {
			
		}
		return false;
	}
	
	
	
}
