package com.binklac.bukkit.sample;

import com.binklac.bukkit.sample.ClassModifier.PlayerListModifier;
import com.binklac.bukkit.sample.EventHandles.PlayerOpEvenListener;
import com.binklac.jhook.JHook;
import com.binklac.jhook.Utils;
import net.minecraft.server.players.PlayerList;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class SamplePlugin extends JavaPlugin {
    @Override
    public void onDisable() {
        getLogger().info("Goodbye world!");
    }

    @Override
    public void onEnable() {
        try {
            if (!JHook.Instance().ModifyClass(PlayerList.class.getCanonicalName(), PlayerListModifier.GetModifiedByteCode())) {
                 getLogger().warning("The class cannot be modified, you need to install jdk or upgrade to a version after Java SE 9 to use the program normally.!");
            }

            Utils.addSelfToUrlClassLoaderUrls(PlayerList.class.getClassLoader());
        } catch (IOException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

        Bukkit.getPluginManager().registerEvents(new PlayerOpEvenListener(getLogger()), this);
    }
}
