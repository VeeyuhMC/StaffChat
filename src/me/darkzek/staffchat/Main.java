package me.darkzek.staffchat;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import me.darkzek.staffchat.gui.playerChatController;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;

public class Main extends JavaPlugin implements Listener{
	static Main m;
	playerChatController gui = new playerChatController();
	Logger log = this.getLogger();
	Boolean vault = true;
	String sequence;
	static Plugin plugin;
	boolean override = false;
	String startChar;
    public static Permission perms = null;
    public static Chat chat = null;
    public static Main setInstance() {
    	return m;
    }
	public void onEnable() {
		m = this;
		plugin = this;
		this.getServer().getPluginManager().registerEvents(this, this);
		this.saveConfig();
		if (this.getConfig().contains("StartingCharacter")) {
			try {
				startChar = this.getConfig().getString("StartingCharacter");
			} catch (Exception e) {
				e.printStackTrace();
				log.severe("Error: StartingCharacter has to be a String! Reverting to @!");
				this.getConfig().set("StartingCharacter", '@');
				startChar = this.getConfig().getString("StartingCharacter");
			}
		} else {
			this.getConfig().set("StartingCharacter", '@');
			startChar = this.getConfig().getString("StartingCharacter");
		}
		if (!this.getConfig().contains("PlayerMsgColorOverride")) {
			this.getConfig().set("PlayerMsgColorOverride", false);
		} else {
			try {
				override = this.getConfig().getBoolean("PlayerMsgColorOverride");
			} catch (Exception e) {
				log.info("PlayerMsgColorOverride must a true or false! Reverting to false!");
				this.getConfig().set("PlayerMsgColorOverride", false);
			}
		}
        if (!setupPermissions() ) {
            log.info("No Vault found. Vault is needed. Please install vault or request the non-vault version of this plugin at https://www.spigotmc.org/resources/staff-chat.15592");
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        } else {
        	log.info("Hooked into Vault!");
        }
        setupChat();
		if (this.getConfig().contains("Sequence")) {
			try {
				sequence = this.getConfig().getString("Sequence");
			} catch (Exception e) {
				log.severe("Error: Sequence has to be a String! Reverting to Defaults!");
				this.getConfig().set("Sequence", "&cStaffChat: {PLAYERNAME}: &6{MESSAGE}");
				sequence = this.getConfig().getString("Sequence");
			}
		} else {
			this.getConfig().set("Sequence", "&cStaffChat: {PLAYERNAME}: &6{MESSAGE}");
			sequence = this.getConfig().getString("Sequence");
		}
		this.saveConfig();
	}
    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }
    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return perms != null;
    }
    
	@EventHandler(priority = EventPriority.LOW)
	public void playerChat(AsyncPlayerChatEvent e) {
		String msg = e.getMessage();
		if (!msg.startsWith(startChar + "")) {
			return;
		}
		Player pl = e.getPlayer();
		if(!perms.has(pl, "StaffChat.Chat")) {
            return;
        }

		//Getting message ready
		String _sequence = sequence;
		_sequence =_sequence.replace("{PLAYERNAME}", pl.getDisplayName());
		msg = msg.replaceFirst(startChar, "");
		Bukkit.broadcastMessage(e.getFormat());
		e.getFormat()
		if (override) {
			_sequence =_sequence.replace("{MESSAGE}", msg);
		} else {
			_sequence =_sequence.replace("{MESSAGE}", msg);
		}
		_sequence = ChatColor.translateAlternateColorCodes('&', _sequence);
		this.getServer().broadcast(_sequence, "StaffChat.Hear");
		e.setCancelled(true);
	}
}
