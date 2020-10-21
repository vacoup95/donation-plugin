package com.minecraftercity.donator;

import com.minecraftercity.donator.holograms.Commands.Create;
import com.minecraftercity.donator.holograms.Commands.Remove;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class RunnableDonator implements Runnable
{
    Donator plugin;
    Boolean hologramEnabled;

    public RunnableDonator(Donator pl, boolean hologramEnabled)
    {
        plugin = pl;
        this.hologramEnabled = hologramEnabled;
    }

    @Override
    public void run()
    {
        Config config = new Config(this.plugin);

        DonationRequester DonationAPI = new DonationRequester(this.plugin);

        HttpResponse<JsonNode> result = DonationAPI.get();
        JSONObject donations = result.getBody().getObject();

        JSONArray arr = donations.getJSONArray("donations");

        if(arr.length() > 0)
        {

            String server;
            String id;
            String username;
            JSONArray commands;

            for (int i = 0; i < arr.length(); i++) {

                server = arr.getJSONObject(i).get("server").toString();
                id = arr.getJSONObject(i).get("id").toString();
                username = arr.getJSONObject(i).get("username").toString();
                commands = arr.getJSONObject(i).getJSONArray("commands");

                Player player = Bukkit.getPlayerExact(username);
                // The idea is that this plugin is installed on all servers where donations are accepted.
                // This is to ensure commands are executed on the correct server
                if(config.server().equals(server) && commands.length() > 0 && player != null) {
                    JSONArray finalCommands = commands;
                    Bukkit.getScheduler().runTask(plugin, () -> {
                        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                        for(int x = 0; x < finalCommands.length(); x++) {
                            Bukkit.dispatchCommand(console, finalCommands.get(x).toString());
                        }
                    });
                    result = DonationAPI.patch(id);
                    this.consoleCommand(result.getBody().toPrettyString());

                    if(this.hologramEnabled) {
                        this.rebuildHologram();
                    }
                }
            }

        }
    }

    /**
     *
     * Function to update the hologram.
     * It works by removeing and then recreating the
     * hologram based on the location in the config.
     *
     */
    private void rebuildHologram()
    {
        Config config = new Config(this.plugin);
        Location location = config.getHologramLocation();
        Remove removeHologram = new Remove(this.plugin);
        removeHologram.remove();
        Create hologram = new Create(location, this.plugin);
        hologram.create();
    }

    private void consoleCommand(String msg)
    {
        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
        console.sendMessage(msg);
    }

}
