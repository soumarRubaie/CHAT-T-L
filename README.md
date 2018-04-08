# CHAT-T-L
## Usage (sous Eclipse)
Le client (package Client) et le serveur (package Serverr) sont gérés comme une seul et même programme. Ils partagent les classes communes sous "Structures" (Message, Usager, etc.).

### Run arguments
*Pour lancer le serveur, les run argments sont: `serveur tcpPort udpPort`
*Et pour le client: `client tcpPort udpPort`

### Exporter un exécutable (.jar) sous Éclipse

** Le main doit avoir été lancé au moins une fois et/ou un "Run Configuration" doit exister pour pouvoir exporter**
**ASTUCE: Créer un run configuration sous Éclipse qui ne comporte aucun argument, et exportez celui-ci en JAR. Pour pourrez alors spécifier lorsque vous lancez le jar si vous voulez qu'il soit client ou serveur et quels ports utiliser.**

* Right-click sur Main.java
* Sous "Java", choisir "Executable jar file" et puis "Next"
* Sous "Launch configuration", choisir la configuration correspondante, avec un "Export destination" au choix.
* Pour "Library handling", peut importe dans notre cas. Laisser option par défaut fonctionne...

Le jar exécuté contient en fait tout le programme (server + client), c'est le run argument initial ("serveur" ou "client") qui détermine quel thread sera démarré.

## Usage (général, en port UDP/envoie de messages simples)

### Usage - client & serveur en .jar
Dans la fenêtre client, écrire un message à envoyer. Celui-ci sera envoyé au serveur (e.g. localhost:8001). Les détails sont affichés en console, e.g:

```
anonymous@anonymous:~/Documents/TODELETE/testClientServer$ java -jar serveur.jar serveur 8000 8001
Demarrage thread TCP...
server:tcpHost on port:8000
Demarrage thread UDP...
udpserver on port:8001
Msg de client:ID:99 author:999 salleId:1 fasdfasdf
```

et pour la console client:


```

anonymous@anonymous:~/testClientServer$ java -jar client.jar client 8000 8001
fasdfasdf
Msg origine: ID:99 author:999 salleId:1 fasdfasdf
From server:ID:99 author:999 salleId:1 fasdfasdf
```

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

# Authentification & POSTS
Cette section détail l'authentification et le POSTS qui sont envoyés du client au serveur. Authentification est la 1ere méthode implémentée en POSTS et sert d'exemple.
"" Sommaire des composantes
* Démarre avec un btnLogin.addListener(...) dans lequel le code spécifie les actions a prendre
* Le btnListener() appel une méthode (static) de Requests.java
* La métho de R.java prend les params donnés par le listener et le encode en un URLParams pour requête POST
* Le SocketTCP recoit une requête au endpoint correspondant. Il prends les actions nécessaires côté serveur.
* R.java recoit une code réponse du serveur (Utils.ERR_CODES). Si tout va bien, il recoit 200.
* Le ERR_CODE (ou alors 200) remonte jusqu'au listener qui a initié la requête.
* Le listener prend les actions nécessaires (loader la home page par exemple apres un login).


