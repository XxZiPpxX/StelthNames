package com.stealthnames.jm;

import net.minecraft.server.level.ServerPlayer;
import journeymap.client.api.ClientPlugin;
import journeymap.client.api.IClientAPI;
import journeymap.client.api.IClientPlugin;
import journeymap.client.api.event.ClientEvent;
import journeymap.client.api.display.Waypoint;

@ClientPlugin
public class JMIntegration implements IClientPlugin {
    private static IClientAPI api;

    @Override
    public void initialize(IClientAPI jmClientApi) {
        api = jmClientApi;
    }

    @Override
    public String getModId() {
        return "stealthnames";
    }

    @Override
    public void onEvent(ClientEvent event) {
        // no-op
    }

    public static void hide(ServerPlayer player) {
        if (api == null) return;
        Waypoint wp = api.getWaypointManager().getPlayerWaypoint(player.getName().getString());
        if (wp != null) {
            wp.setVisibility(Waypoint.Visibility.HIDDEN);
            api.getWaypointManager().save(wp);
        }
    }

    public static void show(ServerPlayer player) {
        if (api == null) return;
        Waypoint wp = api.getWaypointManager().getPlayerWaypoint(player.getName().getString());
        if (wp != null) {
            wp.setVisibility(Waypoint.Visibility.NORMAL);
            api.getWaypointManager().save(wp);
        }
    }
}
