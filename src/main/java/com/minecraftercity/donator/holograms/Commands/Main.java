package com.minecraftercity.donator.holograms.Commands;

import com.minecraftercity.donator.Donator;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Main implements CommandExecutor
{
    private final Donator donator;
    private final Boolean enabled;
    private Player player;

    public Main(Donator donator, Boolean enabled)
    {
        this.donator = donator;
        this.enabled = enabled;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String alias, String[] args)
    {
        if (commandSender instanceof Player) {
            this.player = (Player) commandSender;
        }
        if (this.enabled && commandSender != null) {
            Class<?> c = Main.class;
            try {
                Method method = c.getDeclaredMethod(args[0]);
                System.out.println(method);
                method.invoke(this);
                return true;
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                commandSender.sendMessage("No such command /dono " + args[0] + " use /dono help for a list of commands");
                return true;
            }
        }
        if(!this.enabled) {
            commandSender.sendMessage("Holograms plugin not installed, please install hologram plugin in order to use holograms.");
            return true;
        }
        return false;
    }

    public void create()
    {
        Location location = this.player.getLocation();
        Create hologram = new Create(location, this.donator);
        hologram.create();
        this.player.sendMessage("Hologram created");
    }

    public void reload()
    {
        this.donator.reloadConfig();
        this.donator.saveConfig();

        this.player.sendMessage("Plugin reloaded");
    }

    public void update()
    {
        Rebuild hologram = new Rebuild(this.donator);
        hologram.execute();
        this.player.sendMessage("Hologram updated");
    }

    public void move()
    {
        Move hologram = new Move(this.donator, this.player);
        hologram.move();
        this.player.sendMessage("Hologram moved");
    }

    public void remove()
    {
        Remove hologram = new Remove(this.donator);
        hologram.remove();
        this.player.sendMessage("Hologram removed");
    }
}
