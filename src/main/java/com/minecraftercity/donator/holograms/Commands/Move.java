package com.minecraftercity.donator.holograms.Commands;

import com.minecraftercity.donator.Config;
import com.minecraftercity.donator.Donator;
import com.minecraftercity.donator.holograms.HologramMaker;
import org.bukkit.entity.Player;

public class Move
{
    private final Donator donator;
    private final Player player;

    public Move(Donator donator, Player player)
    {
        this.donator = donator;
        this.player = player;
    }

    public void move()
    {
        HologramMaker hologram = new HologramMaker();
        hologram.move(this.donator, this.player.getLocation());

        Config config = new Config(this.donator);
        config.setHologramLocation(this.player.getLocation());
    }
}
