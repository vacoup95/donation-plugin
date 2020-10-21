package com.minecraftercity.donator.holograms.Commands;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.minecraftercity.donator.Config;
import com.minecraftercity.donator.DonationRequester;
import com.minecraftercity.donator.Donator;
import com.minecraftercity.donator.holograms.HologramMaker;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.inventory.ItemStack;

public class Rebuild
{
    private final Donator donator;

    public Rebuild(Donator donator)
    {
        this.donator = donator;
    }

    public void execute()
    {
        Config config = new Config(this.donator);
        Location location = config.getHologramLocation();
        Remove removeHologram = new Remove(this.donator);
        removeHologram.remove();
        Create hologram = new Create(location, this.donator);
        hologram.create();
    }
}
