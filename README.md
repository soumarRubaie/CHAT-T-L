# CHAT-T-L
## Usage (sous Eclipse)
Le client (clientUDP) et le serveur (tp3) sont gérés comme 2 projets séparés. Ils partagent les classes communes sous "commons" (Message, Usager, etc.) Ils sont donc à lancer séparément et à compiler séparément.

Quelques notes:
* Par défaut, le serveur TCP écoute sous le port 8000/[endpoints]
* L'écoute pour UDP se fait sur le port 8001
* Le serveur roule sur localhost
* Le client UDP peut prendre 1 paramètres (optionel), le numéro de port UDP si celui-ci est différent de 8001
* Le serveur peut prendre 1 ou 2 paramètres (optionel), le numéro de port UDP et le numéro de port TCP si on veut utiliser autre chose que 8001 et 8000, respectivement (voir commands de lancement ci-bas)

### Exporter un exécutable (.jar) sous Éclipse

** Le main doit avoir été lancé au moins une fois et/ou un "Run Configuration" doit exister pour pouvoir exporter.**
* Si ce n'est pas déjà fait, rouler le main correspondant au moins une fois (Mainserver.java ou mainserverUDP.java pour le client).
* Right-click sur l'exécutable à créer (clientUDP ou tp3 pour le serveur principal), ensuite "Export..."
* Sous "Java", choisir "Executable jar file" et puis "Next"
* Sous "Launch configuration", choisir la configuration correspondante (créée automatiquement lors du dernier lancement ou alors créée par vous-même manuellement), avec un "Export destination" au choix.
* Pour "Library handling", peut importe dans notre cas. Laisser option par défaut fonctionne...
* Fonctionne de manière similaire pour les 2 (client et serveur).

## Usage (général, en port UDP/envoie de messages simples)

### Usage - client & serveur en .jar
Dans la fenêtre client, écrire un message à envoyer. Celui-ci sera envoyé au serveur (e.g. localhost:8001). Les détails sont affichés en console, e.g:


```
anonymous@anonymous:~/Documents/TODELETE/testClientServer$ java -jar server.java
Demarrage thread TCP...
server:tcpHost on port:8000
Demarrage thread UDP...
udpserver on port:8001
Msg de client:ID:99 author:999 salleId:1 fasdfasdf
```

et pour la console client:


```

anonymous@anonymous:~/testClientServer$ java -jar client.java
fasdfasdf
Msg origine: ID:99 author:999 salleId:1 fasdfasdf
From server:ID:99 author:999 salleId:1 fasdfasdf
```

Les consoles print à l'écran ce qui se produit pour donner une idée des processus et vérifier si l'information voyage effectivement.

#### Autre configurations de lancement
Par défaut UDP == port 8001 et TCP == port 8000. Cependant, des arguments optionels peuvent être fournis lors du lancement (ligne de commande ou dans les run configuration de Eclipse):

* Client: **java -jar client.java [port UDP]**
* Serveur: **java -jar server.java [port UDP] [port TCP]**

**Même port UDP nécessaire entre client-serveur!**

### Usage port TCP (creation salle, usagers, etc.)
Structure générale: 
`
http://localhost:8000/[nom du endpoint]?param1=[value1]&param2=[value2]
`

####Enpoints fonctionels:
* Création d'une salle:`http://localhost:8000/creationSalle?salleNom=[nom de la salle]`
* Création d'une usager:`http://localhost:8000/creationUsager?username=[nom usager]&password=[password choisi]`
* Assigner/inscrire usager à une salle:`http://localhost:8000/suscribeUsagerSalle?userId=[usager id]&salleId=[salle id]`

#### Commandes pour fins d'examples:
Dans un browser, taper les commandes suivantes:

*Créer les usagers:
```
http://localhost:8000/creationUsager?username=userid_0&password=secrepass
http://localhost:8000/creationUsager?username=userId_1&password=123123pass
```
*Creer 2 salle:
```
http://localhost:8000/creationSalle?salleNom=sports_0
http://localhost:8000/creationSalle?salleNom=voyage_1
```
*Abonner les usagers à la salle1
```
http://localhost:8000/suscribeUsagerSalle?userId=0&salleId=1
http://localhost:8000/suscribeUsagerSalle?userId=1&salleId=1
```
## Erreurs connues:
* Si le serveur n'est pas fermé proprement, il ne sera pas possible de re-démarrer le serveur sur le même port. Une erreur de type **Exception in thread "main" java.net.BindException: Address already in use** . Il faut alors arrêter le processus correspondant sur votre système (e.g. gestionnaire de tâches). Ou alors choisir un autre port pour démarrer l'application (avec argumenrs optionels comme ci-haut).
