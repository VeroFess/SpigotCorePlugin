package com.binklac.bukkit.sample.Events;

import com.mojang.authlib.GameProfile;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerOpEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final GameProfile EventProfile;

    public PlayerOpEvent(GameProfile  profile){
        EventProfile = profile;
    }

    public GameProfile getEventProfile() {
        return EventProfile;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
