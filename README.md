# Développement Fullstack — Polytech

Dépôt de travail du cours. Il regroupe les TP du cours magistral et les TD à rendre.

## Structure

    tp/
      back/     projet Gradle + Spring Boot préconfiguré : TP Java / Spring
      front/    répertoire vide, destiné au projet créé par « ng new » : TP Angular
    td/
      back/     TD : API REST de la bibliothèque de films
        filmapi/  projet Spring Boot de l'API (TD 1)
        http/   requêtes HTTP, exécutées avec l'extension VSCode REST Client
      front/    TD : front Angular de la bibliothèque de films

L'ouverture du dossier racine dans VSCode déclenche la proposition des extensions
recommandées.

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

API REST de gestion de films, stockage en mémoire (tag `td1`).

### Lancement

```bash
cd td/back/filmapi
./gradlew bootRun
```

L'API écoute sur `http://localhost:8080`. Les requêtes de test sont dans
`td/back/http/films.http` (extension REST Client).

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

Les erreurs sont renvoyées au formation/problem+json`) :

- **404** : film inexistant, via `Fitée par `GlobalExceptionHandler` ;
- **400** : film invalide (titre absent ou vide), via la validation `@NotBlank` + `@Valid`.

## Rendus

| Tag   | Contenu                                        |
|-------|------------------------------------------------|
| `td1` | API REST, stockage en mémoire                  |
| `td2` | persistance JPA, DTO, CORS                     |
| `td3` | front Angular branché sur l'API                |

La régularité et la lisibilité des commits ainsi que la mise à jour du `README.md` sont
prises en compte dans l'évaluation.
