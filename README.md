# Développement Fullstack — Polytech

Dépôt de travail du cours. Il regroupe les TP du cours magistral et les TD à rendre.

## Structure

    tp/
      back/                 TP Java / Spring (projet Gradle + Spring Boot)
      front/patients-app/   TP Angular
    td/
      back/
        filmapi/            API REST de la bibliothèque de films
          src/main/java/or/polytech/filmapi/
            Controller/     endpoints REST
            Service/        logique métier
            Repository/     accès aux données (Spring Data JPA)
            Model/          entités Film et Acteur
            DTO/            objets échangés avec le client
            Mapper/         conversion entité / DTO
            Config/         configuration CORS
            utils/          exceptions, gestion des erreurs, énumération Genre
          src/main/resources/
            application.yaml  connexion PostgreSQL et JPA
            data.sql          jeu de données chargé au démarrage
        http/               requêtes HTTP (extension VSCode REST Client)
      front/
        films-app/          front Angular de la bibliothèque de films
          src/
            proxy.conf.json   relais des appels /api vers le back (port 8080)
            styles.css        style global
            app/
              Components/     composants : film-list, film-card, film-detail, film-form,
                              acteur-list, acteur-detail, not-found
              models/         interfaces Film et Acteur, calquées sur les DTO
              services/       FilmService et ActeurService (appels HTTP)
              app.routes.ts   table de routage
              app.config.ts   providers : router et client HTTP
              app.html        menu principal et <router-outlet />


## Récupération du dépôt

```bash
git clone polytech-fullstack-starter.bundle mon-depot
cd mon-depot
git remote remove origin                 # le bundle ne constitue pas un dépôt distant
git remote add origin <URL du dépôt GitHub>
git push -u origin main
```

## Démarrage

### Back des TP

```bash
cd tp/back
./gradlew build      # Windows : gradlew.bat build
./gradlew bootRun
```

Le wrapper télécharge Gradle 9.7.1 et, le cas échéant, le JDK 26 : aucune installation
manuelle n'est nécessaire. Le fichier `build.gradle` ne déclare qu'une dépendance,
`spring-boot-starter-webmvc`. Elle apporte Spring MVC, Jackson, un Tomcat embarqué ainsi
que `spring-context`, le conteneur IoC utilisé dans les premiers TP.

### Front des TP

```bash
cd tp/front
ng new tp-front      # CSS, sans SSR, « None » pour les outils IA
```

## TD 1 — API REST de la bibliothèque de films

API REST de gestion de films, stockage en mémoire.


> Les films sont stockés en mémoire : ils sont perdus à chaque redémarrage.
> Lancer d'abord le `POST` avant les autres requêtes.

### Modèle

`Film` : `id` (Long), `titre` (String, obligatoire), `realisateur` (String),
`dateSortie` (LocalDate), `genre` (énumération `Genre` : `ACTION`, `COMEDY`,
`DRAMA`, `SCIENCE_FICTION`).

### Architecture

| Couche     | Classe                   | Rôle                                                                               |
|------------|--------------------------|------------------------------------------------------------------------------------|
| Controller | `FilmController`         | Expose les endpoints REST (`@RestController`)                                      |
| Service    | `FilmService`            | Logique métier, lève `FilmNotFoundException` (`@Service`)                          |
| Repository | `FilmRepository`         | Stockage dans une `Map<Long, Film>`, id généré par un `AtomicLong` (`@Repository`) |
| Erreurs    | `GlobalExceptionHandler` | `@RestControllerAdvice` qui convertit les exceptions en `ProblemDetail`            |

### Endpoints
| Méthode  | URL           | Description             | Succès                     | Erreurs      |
|----------|---------------|-------------------------|----------------------------|--------------|
| `GET`    | `/films`      | Liste des films         | `200 OK`                   | –            |
| `GET`    | `/films/{id}` | Un film par identifiant | `200 OK`                   | `404`        |
| `POST`   | `/films`      | Création d'un film      | `201 Created` + `Location` | `400`        |
| `PUT`    | `/films/{id}` | Mise à jour d'un film   | `200 OK`                   | `400`, `404` |
| `DELETE` | `/films/{id}` | Suppression d'un film   | `204 No Content`           | `404`        |

### Gestion des erreurs

Les erreurs sont renvoyées au format **ProblemDetail** (`application/problem+json`) :

- **404** : film inexistant, via `FilmNotFoundException` interceptée par `GlobalExceptionHandler` ;
- **400** : film invalide (titre absent ou vide), via la validation `@NotBlank` + `@Valid`.

## TD 2 — Persistance JPA, DTO, CORS

L'API du TD 1 est branchée sur PostgreSQL. Une entité `Acteur` est ajoutée, liée à `Film`
par une relation ManyToMany.

### Lancement

Prérequis : PostgreSQL sur `localhost:5432` et une base `fimapi-db`.

```bash
export DB_PASSWORD='mot_de_passe_postgres'
cd td/back/filmapi
./gradlew bootRun
```

Les tables sont recréées à chaque démarrage (`ddl-auto: create-drop`) et remplies par
`data.sql` (5 films, 5 acteurs). Les requêtes de test sont dans `td/back/http/`.

### Choix techniques

- **DTO** : le contrôleur n'expose aucune entité. `FilmDto` sert à la liste, `FilmDetailDto`
  (avec les acteurs) au détail, `FilmCreationDto` et `ActeurCreationDto` à la création.
- **Relation** : `Film` est le côté propriétaire (`@JoinTable`), `Acteur` le côté inverse
  (`mappedBy`). Les associations se modifient donc à partir du film.
- **Requêtes personnalisées** : films d'un acteur et acteurs d'un film, écrites par
  convention de nommage puis avec `@Query`.
- **CORS** : seule l'origine `http://localhost:4200` est autorisée (`CorsConfig`).
- **Erreurs** : `404` et `400` au format ProblemDetail, pour les films comme pour les acteurs.

### Nouveaux endpoints

Les endpoints films du TD 1 sont conservés ; `GET /films/{id}` renvoie maintenant le film
avec ses acteurs.

| Méthode  | URL                              | Description                | Succès                     | Erreurs      |
|----------|----------------------------------|----------------------------|----------------------------|--------------|
| `GET`    | `/films/{id}/acteurs`            | Acteurs d'un film          | `200 OK`                   | `404`        |
| `POST`   | `/films/{id}/acteurs/{acteurId}` | Associe un acteur au film  | `204 No Content`           | `404`        |
| `DELETE` | `/films/{id}/acteurs/{acteurId}` | Dissocie un acteur du film | `204 No Content`           | `404`        |
| `GET`    | `/acteurs`                       | Liste des acteurs          | `200 OK`                   | –            |
| `GET`    | `/acteurs/{id}`                  | Détail d'un acteur         | `200 OK`                   | `404`        |
| `POST`   | `/acteurs`                       | Création d'un acteur       | `201 Created` + `Location` | `400`        |
| `PUT`    | `/acteurs/{id}`                  | Mise à jour d'un acteur    | `200 OK`                   | `400`, `404` |
| `DELETE` | `/acteurs/{id}`                  | Suppression d'un acteur    | `204 No Content`           | `404`        |
| `GET`    | `/acteurs/{id}/films`            | Films d'un acteur          | `200 OK`                   | `404`        |

## TD 3 — Front Angular de la bibliothèque de films

Application Angular qui consomme l'API : consultation, création, modification et
suppression des films, consultation des acteurs, association et dissociation d'un acteur
à un film.

### Démarrer le projet

#### Prérequis

| Outil       | Version              |
|-------------|----------------------|
| PostgreSQL  | sur `localhost:5432` |
| Node.js     | 24 (npm inclus)      |
| Angular CLI | 22                   |

#### 1. Récupérer le dépôt

```bash
git clone https://github.com/zahid-abdelouahed/Application-Test.git
cd Application-Test
```

#### 2. Créeation de la base de données

```bash
psql -U postgres -c 'CREATE DATABASE "fimapi-db";'
```

L'API se connecte avec l'utilisateur `postgres`. Les tables sont créées et remplies
automatiquement à chaque démarrage avec (`data.sql`).

#### 3. Démarrer le back — terminal 1

```bash
export DB_PASSWORD='motdepassede-labasededonnees-cree'
cd td/back/filmapi
./gradlew bootRun   
```

#### 4. Démarrer le front — terminal 2

```bash
cd td/front/films-app
ng serve
```
#### 5. Utiliser l'application

Ouvrir `http://localhost:4200`. Le menu donne accès aux films et aux acteurs.

### Branchement front / back

Le front appelle des URL relatives en `/api/...`. Le serveur de développement Angular les
relaie vers `http://localhost:8080` grâce à `src/proxy.conf.json` (déclaré dans
`angular.json`), en retirant le préfixe `/api` : `/api/films` devient `/films` côté Spring.
Le navigateur ne voit donc qu'une seule origine. Le proxy n'est lu qu'au démarrage de
`ng serve`.

### Routes

| URL                   | Composant      | Rôle                            |
|-----------------------|----------------|---------------------------------|
| `/films`              | `FilmList`     | Liste des films                 |
| `/films/nouveau`      | `FilmForm`     | Création                        |
| `/films/:id/modifier` | `FilmForm`     | Modification                    |
| `/films/:id`          | `FilmDetail`   | Détail, acteurs, liens          |
| `/acteurs`            | `ActeurList`   | Liste des acteurs               |
| `/acteurs/:id`        | `ActeurDetail` | Détail d'un acteur et ses films |
| `**`                  | `NotFound`     | Page introuvable                |

`/` redirige vers `/films`. `films/nouveau` est déclarée avant `films/:id` pour ne pas être
prise pour un identifiant.

### Choix techniques

- **Communication** : `FilmCard` reçoit le film par `input.required<Film>()` et émet
  `supprimer` par `output<Film>()`. C'est `FilmList` qui demande confirmation et appelle
  le service : la carte n'appelle jamais l'API.
- **Lectures** : consommées avec `toSignal`, donc sans `subscribe` manuel ni désabonnement.
- **Écritures** (POST, PUT, DELETE) : `subscribe` avec les callbacks `next` et `error`.
- **Erreurs** : chaque composant a un signal `erreur` affiché dans le template. Les
  lectures utilisent `catchError` avec une valeur de repli (liste vide ou `null`) : API
  éteinte, l'application affiche un message au lieu de rester bloquée.
- **Mise en forme** : les films sortis avant 2000 reçoivent la classe `ancien` via
  `ngClass`, les dates sont affichées en `dd/MM/yyyy` avec `DatePipe`.
- **Recherche**  : champ de recherche dans la liste des films, filtrage local
  par titre avec un `computed`.
- **Interface `FilmSaisie`** : équivalent front de `FilmCreationD

### Notions utilisés

- **`toObservable`** : fait l'inverse de `toSignal` : il transforme un signal en
  Observable, qui émet à chaque changement du signal. L'option `initialValue` de
  `toSignal` évite la valeur `undefined` avant la première réponse.
- **`switchMap`** : à chaque nouvelle valeur émise, lance une nouvelle requête et
  abandonne la précédente.
- **Signal `version`** : `toSignal` ne s'abonne qu'une fois, donc le `GET` n'est envoyé
  qu'une fois. `version` est un simple compteur (`signal(0)`) qui sert de déclencheur :
  après une suppression, une association ou une dissociation, on l'incrémente
  (`version.update(v => v + 1)`) pour demander une nouvelle lecture. Sa valeur n'est
  jamais affichée, seul son changement compte.
- **Signal `source`** (`FilmDetail`) : le film doit être rechargé dans deux cas, quand
  l'`id` de la route change et quand `version` est incrémenté. `source` est un `computed`
  qui regroupe les deux dans un seul objet : `{ id: filmId(), version: version() }`.
  Dès que l'un des deux change, `source` produit un nouvel objet.
- **`confirm()`** : boîte de dialogue native du navigateur, affichée avant toute
  suppression.

## Rendus

| Tag   | Contenu                                        |
|-------|------------------------------------------------|
| `td1` | API REST, stockage en mémoire                  |
| `td2` | persistance JPA, DTO, CORS                     |
| `td3` | front Angular branché sur l'API                |

La régularité et la lisibilité des commits ainsi que la mise à jour du `README.md` sont
prises en compte dans l'évaluation.
to`, sans `id` ni
  `acteurs`, utilisée pour le POST et le PUT à la place de `Partial<Film>`.

### Notions utilisées
