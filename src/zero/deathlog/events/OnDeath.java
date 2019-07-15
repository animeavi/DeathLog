package zero.deathlog.events;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import zero.deathlog.DeathLog;

public class OnDeath implements Listener {
   private DeathLog plugin;
   private FileConfiguration config;

   public OnDeath(DeathLog plugin) {
      this.plugin = plugin;
      this.config = plugin.getConfig();
   }

   @EventHandler
   public void playerDeath(PlayerDeathEvent event) {
      Player player = event.getEntity();
      Location loc = event.getEntity().getLocation();

      plugin.log.info(plugin.generateMessage(config.getString("log-message"), loc, player));

      if (config.getBoolean("player-chat.enabled", false)) {
         if (player != null && player.isOnline()) {
            player.sendMessage(plugin.generateMessage(config.getString("player-chat.message"), loc, player));
         }
      }
   }
}
