package com.stealthnames.commands;

import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.Team;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import com.stealthnames.jm.JMIntegration;

@Mod.EventBusSubscriber(modid = "stealthnames")
public class CommandRegistrar {

    @SubscribeEvent
    public static void onCommandRegister(RegisterCommandsEvent event) {

        // /hide
        event.getDispatcher().register(
            Commands.literal("hide")
                .requires(src -> src.hasPermission(0))
                .executes(ctx -> {
                    ServerPlayer player = ctx.getSource().getPlayer();
                    Scoreboard scoreboard = player.getScoreboard();
                    PlayerTeam team = scoreboard.getPlayerTeam("stealth_hidden");

                    if (team == null) {
                        team = scoreboard.addPlayerTeam("stealth_hidden");
                        team.setNameTagVisibility(Team.Visibility.NEVER);
                    }

                    scoreboard.addPlayerToTeam(player.getScoreboardName(), team);

                    JMIntegration.hide(player);  // Interacción con JourneyMap

                    ctx.getSource().sendSuccess(() -> Component.literal("Tu nombre ha sido ocultado."), false);
                    return 1;
                })
        );

        // /show
        event.getDispatcher().register(
            Commands.literal("show")
                .requires(src -> src.hasPermission(0))
                .executes(ctx -> {
                    ServerPlayer player = ctx.getSource().getPlayer();
                    Scoreboard scoreboard = player.getScoreboard();
                    PlayerTeam team = scoreboard.getPlayerTeam("stealth_hidden");

                    if (team != null) {
                        scoreboard.removePlayerFromTeam(player.getScoreboardName(), team);
                    }

                    JMIntegration.show(player);  // Reaparece en JourneyMap si lo soporta

                    ctx.getSource().sendSuccess(() -> Component.literal("Tu nombre vuelve a ser visible."), false);
                    return 1;
                })
        );
    }
}
