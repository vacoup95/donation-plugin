package com.minecraftercity.donator;

import com.minecraftercity.donator.holograms.Commands.Create;
import com.minecraftercity.donator.holograms.Commands.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class Donator extends JavaPlugin implements Listener
{
    private boolean useHolographicDisplays;

    @Override
    public void onEnable()
    {
        this.saveDefaultConfig();
        this.process();

        useHolographicDisplays = Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays");

        if(useHolographicDisplays) {
            getLogger().info("*** HolographicDisplays is installed and enabled***");
            Config config = new Config(this);
            Location location = config.getHologramLocation();

            if(location != null) {
                Create hologram = new Create(location, this);
                hologram.create();
            }
        }

        Objects.requireNonNull(this.getCommand("dono"))
                .setExecutor(new Main(this, useHolographicDisplays));

        getServer().getPluginManager().registerEvents(this,this);
    }

    /**
     *  Start up Runnable that creates and runs tasks.
     */
    public void process()
    {
        this.getServer().getScheduler().runTaskTimerAsynchronously(
            this,
            new RunnableDonator(this, useHolographicDisplays),
            Long.parseLong(String.valueOf(new Config(this).interval() * 20)),
            Long.parseLong(String.valueOf(new Config(this).interval() * 20))
        );
    }
}
