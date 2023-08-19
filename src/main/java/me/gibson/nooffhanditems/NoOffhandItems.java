package me.gibson.nooffhanditems;

import org.bukkit.plugin.java.JavaPlugin;

public final class NoOffhandItems extends JavaPlugin {


    @Override
    public void onEnable() {
        this.saveDefaultConfig();
      //  new OffhandChecker(this).runTaskTimer(this, 0L, 20L); // 20L = 1 second
        // Register the reload command executor
        getServer().getPluginManager().registerEvents(new OffhandListener(this), this);

        this.getCommand("blockoffhandreload").setExecutor(new ReloadConfigCommand(this));
    }

}
