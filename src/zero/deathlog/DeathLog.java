package zero.deathlog;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import zero.deathlog.events.OnDeath;

public class DeathLog extends JavaPlugin {
   PluginDescriptionFile pdffile = this.getDescription();
   public String version;
   public String name;
   public Logger log;

   public DeathLog() {
      this.version = this.pdffile.getVersion();
      this.name = this.pdffile.getName();
      this.log = getLogger();
   }

   public void onEnable() {
      log.info("plugin enabled");
      this.registerEvents();
      this.registerConfig();
   }

   public void onDisable() {
      log.info("plugin disabled");
   }

   public void registerEvents() {
      PluginManager pm = this.getServer().getPluginManager();
      pm.registerEvents(new OnDeath(this), this);
   }

   public void registerConfig() {
      File config = new File(this.getDataFolder(), "config.yml");

      if (!config.exists()) {
         this.getConfig().options().copyDefaults(true);
         this.saveConfig();
      }
   }

   public String generateMessage(String message, Location loc, Player player) {
      String msg = 
            ChatColor.translateAlternateColorCodes('&', message)
            .replace("%player%", player.getName())
            .replace("%coord-x%", String.valueOf(loc.getBlockX()))
            .replace("%coord-y%", String.valueOf(loc.getBlockY()))
            .replace("%coord-z%", String.valueOf(loc.getBlockZ()))
            .replace("%world%", loc.getWorld().getName());

      return msg;
   }
}
