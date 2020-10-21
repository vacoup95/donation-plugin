package com.minecraftercity.donator;

import org.bukkit.Location;

public class Config
{
    private Donator donator;

    public Config(Donator donator)
    {
        this.donator = donator;
    }

    public void save()
    {
        this.donator.saveConfig();
    }

    public String passKey()
    {
        return this.donator.getConfig().getString("api.pass-key");
    }

    public String baseUrl()
    {
        return this.donator.getConfig().getString("api.base");
    }

    public String server()
    {
        return this.donator.getConfig().getString("server");
    }

    public void setHologramLocation(Location location)
    {
        this.donator.getConfig().set("hologram.location", location);
    }

    public void removeLocation()
    {
        this.donator.getConfig().set("hologram.location", "");
    }

    public Location getHologramLocation()
    {
        return this.donator.getConfig()
                .getLocation("hologram.location");
    }

    public Integer interval()
    {
        return this.donator.getConfig().getInt("api.poll-interval");
    }

}
