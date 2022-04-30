package com.binklac.bukkit.sample;

import com.binklac.bukkit.sample.Events.PlayerOpEvent;
import com.mojang.authlib.GameProfile;
import org.bukkit.Bukkit;

public class EventFireHelper {
    static public void firePlayerOpEvent(GameProfile profile){
        PlayerOpEvent event = new PlayerOpEvent(profile);
        Bukkit.getServer().getPluginManager().callEvent(event);
    }
}
