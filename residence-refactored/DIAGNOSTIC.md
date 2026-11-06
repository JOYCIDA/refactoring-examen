# Diagnostic du code historique

Défauts relevés dans `residence-legacy` avant le refactoring.

| Défaut | Emplacement | Principe violé |
|---|---|---|
| Une seule classe gère l'inscription, l'attribution, le bail, les paiements, les relances, le rapport et les notifications | `GestionResidence` | S |
| Le bail met en forme sa propre quittance | `Bail.genererQuittance` | S |
| La résidence lit le catalogue, crée les logements et stocke la liste | `Residence.initialiser` | S |
| Ajouter un type de logement oblige à modifier plusieurs `switch` / `if` | `Residence.initialiser`, `GestionResidence.ajouterLogement`, `CalculateurLoyer.calculerLoyer`, `GestionResidence.genererRapport` | O |
| Ajouter un profil oblige à modifier tous les `if` sur le profil (remise, solvabilité, caution, frais, préavis, pénalité) | `CalculateurLoyer`, `GestionResidence.attribuerLogement`, `payerLoyer`, `donnerPreavis` | O |
| Ajouter une option oblige à ajouter un booléen partout | `Bail`, `CalculateurLoyer.calculerLoyer`, `GestionResidence.attribuerLogement` | O |
| Ajouter un canal oblige à modifier deux blocs de code dupliqués | `GestionResidence.notifier`, `GestionResidence.relancerImpayes` | O |
| Types, profils, canaux, statuts et options codés en `String` (`equals("BOURSIER")`, `equals("COLOCATION")`...) | `GestionResidence`, `CalculateurLoyer`, `Etudiant`, `Bail` | O |
| `Colocation` refuse `loger()` et `liberer()` (exception) : elle ne peut pas remplacer un `Logement` | `Colocation` | L |
| Tests `instanceof Colocation` pour savoir comment loger ou libérer | `GestionResidence.attribuerLogement`, `sortir`, `annulerBail` | L |
| `T1.getColocataires()` renvoie `null` et `T1.getSurfaceBalcon()` renvoie 0, alors que `Studio` lance une exception pour les mêmes méthodes | `T1`, `Studio` | L |
| L'interface impose à tous les logements les méthodes de colocation, de balcon et d'entretien | `ILogement` | I |
| `Studio`, `T1`, `T2` implémentent des méthodes qui ne les concernent pas (`UnsupportedOperationException`) | `Studio`, `T1`, `T2` | I |
| `catch` vide pour ignorer les logements sans balcon | `GestionResidence.genererRapport` | I |
| Dépendance directe au singleton de persistance | `GestionResidence` (`Database.getInstance()`) | D |
| Les services de notification sont créés ou appelés en dur (`new SmsService()`, `new CourrierService()`, `EmailService.envoyer` statique) | `GestionResidence.notifier`, `relancerImpayes` | D |
| État global modifiable en `static` (liste des logements, compteurs, journaux) | `Residence.logements`, `Etudiant.compteur`, `GestionResidence.compteurBail`, `SmsService`, `CourrierService`, `EmailService`, `Database` | D |
| Nombres magiques (14, 350, 1.15, 0.95, 45, 35, 25, 0.05...) | `CalculateurLoyer`, `GestionResidence` | lisibilité |

## Palier 7

Extensions faites : profil `SPORTIF`, option local vélo et canal `PUSH`.

Fichiers existants modifiés : 5 (`Profil`, `Option`, `ProfilStrategyFactory`, `AssembleurLoyer`, `Bail`).
Nouvelles classes : `SportifStrategy`, `DecorateurLocalVelo` et le test `ExtensionPartieBTest`.

Canal `PUSH` : 2 fichiers existants modifiés (`Canal` pour ajouter la valeur, `NotificationFactory` pour ajouter le `case`).
Nouvelles classes : `PushService`, `CanalPush` et le test `NotificationFactoryTest`.

`DUPLEX` n'est pas fait.

## Palier 5 (architecture et notifications)

- `GestionResidence` ne fait plus que déléguer : `InscriptionService`, `AttributionService`, `CycleVieBailService`, `RelanceService`, `RapportService`.
- La quittance est mise en forme par `QuittanceFormatter` (plus dans `Bail`).
- Le singleton `Database` est remplacé par `BaseMemoire`, qui implémente `EtudiantRepository` et `BailRepository`, injectés par constructeur.
- Les services d'envoi (`EmailService`, `SmsService`, `CourrierService`, `PushService`) sont des instances avec leur propre journal ; les canaux sont choisis par `NotificationFactory`.
- Les compteurs `Etudiant.compteur`, `GestionResidence.compteurBail`, `SmsService.compteurSms` et les journaux statiques ont disparu.
- Reste un `static` modifiable : `Residence.logements` (partie A, `LogementRepository`).
