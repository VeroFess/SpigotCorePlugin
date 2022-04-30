package com.binklac.bukkit.sample.EventHandles;

import com.binklac.bukkit.sample.Events.PlayerOpEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.logging.Logger;

public class PlayerOpEvenListener implements Listener {
    private final Logger logger;

    public PlayerOpEvenListener(Logger logger){
        this.logger = logger;
    }

    @EventHandler
    public void onPlayerOp(PlayerOpEvent event) {
        logger.warning("SetOp -> " + event.getEventProfile().getName());
    }
}
