package com.odaria.api.bungee.commands;

import com.odaria.api.bungee.OdariaAPIBungee;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class DenyPartyInvitCommand extends Command {
    public DenyPartyInvitCommand() {
        super("denypartyinvit");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer) {
            if(args.length == 1) {
                ProxiedPlayer player = (ProxiedPlayer)sender;
                String playerLead = args[0];

                OdariaAPIBungee odariaAPIBungee = OdariaAPIBungee.INSTANCE;
                if(odariaAPIBungee.playerHaveInvitation(player.getDisplayName(), playerLead)) {
                    player.sendMessage(new TextComponent("Vous avez refusé l'invitation de groupe de " + playerLead));
                    odariaAPIBungee.getPartyInvitation().remove(player.getDisplayName(), playerLead);
                    ProxiedPlayer playerLeader = odariaAPIBungee.getProxy().getPlayer(playerLead);
                    if(playerLeader != null) {
                        playerLeader.sendMessage(new TextComponent(player.getDisplayName() + " a refusé votre invitation de groupe"));
                    }
                } else {
                    player.sendMessage(new TextComponent("Vous n'avez aucune invitation de cette personne"));
                }
            }
        }
    }
}
