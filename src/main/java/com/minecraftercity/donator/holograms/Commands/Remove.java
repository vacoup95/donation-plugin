package com.minecraftercity.donator.holograms.Commands;

import com.minecraftercity.donator.Config;
import com.minecraftercity.donator.Donator;
import com.minecraftercity.donator.holograms.HologramMaker;
import org.bukkit.entity.Player;

public class Remove
{
    private final Donator donator;

    public Remove(Donator donator)
    {
        this.donator = donator;
    }

    public void remove()
    {
        Config config = new Config(this.donator);
        config.removeLocation();

        HologramMaker hologram = new HologramMaker();
        hologram.remove(this.donator);
    }
}
