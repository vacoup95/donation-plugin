package com.minecraftercity.donator.holograms.Commands;

import com.google.gson.*;
import com.minecraftercity.donator.Config;
import com.minecraftercity.donator.DonationRequester;
import com.minecraftercity.donator.Donator;
import com.minecraftercity.donator.holograms.HologramMaker;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import org.bukkit.ChatColor;
import org.bukkit.Location;

public class Create
{
    private final Donator donator;
    private final Location location;

    public Create(Location location, Donator donator)
    {
        this.location = location; 
        this.donator = donator;
    }

    public HttpResponse<JsonNode> top()
    {
        DonationRequester DonationAPI = new DonationRequester(this.donator);
        return DonationAPI.top();
    }

    public void create()
    {
        HttpResponse<JsonNode> result = this.top();
        Config config = new Config(this.donator);

        HologramMaker hologramMaker = new HologramMaker();
        HologramMaker hologram = hologramMaker.create(this.donator, this.location);

        String donations = result.getBody().toString();
        JsonObject jsonObject = new JsonParser().parse(donations).getAsJsonObject();
        JsonArray array = jsonObject.getAsJsonArray("donations");

        hologram.addLine("" + ChatColor.YELLOW + ChatColor.BOLD + ChatColor.UNDERLINE + " Newest donations! ");

        hologram.addLine("");

        int i = 1;

        for (JsonElement pa : array) {

            JsonObject obj = pa.getAsJsonObject();
            String username = obj.get("username").getAsString();
            String gross = obj.get("gross").getAsString();

            hologram.addLine("" + ChatColor.RED + i + ". " + ChatColor.BLUE + username + " " + ChatColor.GOLD + "$ " + ChatColor.GREEN + gross );
            i++;
        }

        config.setHologramLocation(this.location);
        config.save();
    }
}
