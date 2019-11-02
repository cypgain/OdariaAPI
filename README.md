# Documentation

## Commandes

```java
/acceptpartyinvit <fromPlayer>
/denypartyinvit <fromPlayer>
/changeplayerrank <playerName> <rankId>
/closeallservers
/seeallservers
/hub
/loadaccountfromdatabase <playerName>
/saveaccounttodatabase <playerName>
```

## Scripting

### Général

Rejoindre un mini jeu
```java 
JoinGame.Action(ServerType type, Player player, int minRam, int maxRam)
```

### Serveurs

Changer le statut du serveur
```java 
ChangeServerStateSender.Action(ServerState state)
```

Envoyer le nombre maximum de joueurs
```java 
SendMaxPlayersSender.Action(int maxPlayers)
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

### Grades

Modifier le grade du joueur
```java
ChangePlayerRank.Action(String playerName, int rankId);
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

### Amis

Ouvrir le menu des amis
```java 
OpenFriendsGUI.Action(Player player)
```

### Groupe

Ouvrir le menu du groupe
```java 
OpenPartyGUI.Action(Player player)
```

Teleporter les joueurs du groupe dans le mÃªme serveur que le leader
```java
TeleportAllPlayersParty.Action(Player leaderPlayer)
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

### Base de données

Recuperer les informations de connexion de la Base de données
```java
GetDatabaseCredentials.Action();
```

Methodes de DatabaseCredentials
```java
toURL() -- retourne url de connexion mysql
getHost()
getUser()
getPass()
getDbName()
getPort()
```

### Coins

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

### Odabox

Recuperer les OdaBox du joueur (retourne un integer)
```java
new AccountProvider(String playerName).getOdabox();
```

Ajouter des OdaBox au joueur
```java
new AccountProvider(String playerName).addOdabox(int amount);
```

Retirer des OdaBox au joueur
```java
new AccountProvider(String playerName).removeOdabox(int amount);
```

