# Documentation

Modifier le grade du joueur
```java
Not done
```

Ranks
```java
Ranks.ADMINISTRATOR_PLUS
Ranks.ADMINISTRATOR
Ranks.MODERATOR
Ranks.DEVELOPER
Ranks.GUIDE
Ranks.BUILDER
Ranks.VIP
Ranks.PLAYER
Ranks.getById(int id) -- Retourne le rank en Ã  partir de l'id (mÃªme id que la bdd)
```

Verifier si le joueur à une permission
```java
new AccountProvider(String player).hasPermission(String permission)
```

Rejoindre un mini jeu
```java 
JoinGame.Action(ServerType type, Player player, int minRam, int maxRam)
```

Ouvrir le menu des amis
```java 
OpenFriendsGUI.Action(Player player)
```

Ouvrir le menu du groupe
```java 
OpenPartyGUI.Action(Player player)
```

Teleporter les joueurs du groupe dans le mÃªme serveur que le leader
```java
TeleportAllPlayersParty.Action(Player leaderPlayer)
```

Changer le statut du serveur
```java 
ChangeServerStateSender.Action(int port, ServerState state)
```

Liste des statuts du serveur (ServerState)
```java
ServerState.STARTING
ServerState.OPEN
ServerState.RUNNING
ServerState.SHUTDOWN
```

Liste des types de serveurs (ServerType)
```java
ServerType.SKYWARS_HUB
ServerType.SKYWARS_SOLO
ServerType.SKYWARS_DUO
ServerType.SKYWARS_MEGA
ServerType.PAINTBALL_HUB
ServerType.PAINTBALL_GAME
ServerType.HUNGERGAME_HUB
ServerType.HUNGERGAME_GAME
ServerType.GODOFODARIA_GAME
ServerType.getByName(String name)
```

Recuperer les informations de connexion de la Base de données
```java
Not Done
```

Recuperer le groupe du joueur
```java
new PartyProvider(String playerName).getPlayerParty();
```

Methodes de PlayersParty
```java
getPlayers() -- Retourne List<String> contenant les noms des joueurs dans le groupe
getLeader() -- Recupere un String --> le nom du Chef du groupe
```

#### Coins

Recuperer les coins du joueur (retourne un integer)
```java
GetCoins.Action(Player player);
```

Retire des coins au joueur
```java
RemoveCoins.Action(Player player, int amount);
```

Ajouter des coins au joueur
```java
GiveCoins.Action(Player player, int amount);
```

Modifier les coins du joueur
```java
SetCoins.Action(Player player, int amount);
```
