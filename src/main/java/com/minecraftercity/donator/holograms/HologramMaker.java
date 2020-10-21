package com.minecraftercity.donator.holograms;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.minecraftercity.donator.Donator;
import org.bukkit.Location;

public class HologramMaker
{
    public Hologram hologram;

    public void addLine(String line)
    {
        this.hologram.appendTextLine(line);
    }

    public HologramMaker create(Donator donator, Location location)
    {
        this.hologram = HologramsAPI.createHologram(donator, location);
        return this;
    }

    public void move(Donator donator, Location location)
    {
        for (Hologram hologram : HologramsAPI.getHolograms(donator)) {
            hologram.teleport(location);
        }
    }

    public void remove(Donator donator)
    {
        for (Hologram hologram : HologramsAPI.getHolograms(donator)) {
            hologram.delete();
        }
    }
}
