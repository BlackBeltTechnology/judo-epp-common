# JUDO Eclipse Packaging Project (EPP) Common - Project Documentation

## Project Overview

**Repository:** BlackBeltTechnology/judo-epp-common
**License:** Eclipse Public License 2.0 (EPL-2.0)
**Java Version:** 21
**Build System:** Maven 3.9.9 with Tycho 4.0.13

1. Assembles a curated P2 update site of Eclipse features for JUDO Eclipse-based products
2. Provides a common OSGi plugin bundle with branding, splash screens, and a "Contribute" menu command
3. Packages the plugin into an Eclipse feature alongside Eclipse Platform, M2E Logback, and M2E PDE
4. Aggregates 100+ Eclipse features (EMF, Sirius, Xtext, JDT, DataTools, EGit, M2E, etc.) into a single installable repository
5. Targets five platform environments: Linux x86_64/aarch64, Windows x86_64, macOS x86_64/aarch64

## Directory Structure

```
judo-epp-common/
├── common/                 # Main Eclipse plugin (OSGi bundle)
│   ├── src/                # Java source (ContributeHandler)
│   ├── META-INF/           # OSGi bundle manifest
│   ├── plugin.xml          # Eclipse extension points
│   ├── icons/              # Plugin icons
│   └── splash/             # Splash screen resources
├── common-feature/         # Eclipse feature definition
│   ├── feature.xml         # Feature contents and dependencies
│   └── p2.inf             # P2 provisioning instructions
├── common-site/            # P2 update site (repository)
│   └── category.xml        # Site category and feature listing
├── .github/workflows/      # CI/CD pipeline definitions
├── .mvn/                   # Maven wrapper and JVM config
└── pom.xml                 # Parent POM with all build configuration
```

## Core Modules

### Plugin Layer

| Module | Type | Purpose |
|--------|------|---------|
| `common/` | `eclipse-plugin` | OSGi bundle `hu.blackbelt.judo.eclipse.epp.package.common` — contains `ContributeHandler` (opens Eclipse contribute page), branding resources (splash screens, icons), and about dialog content |

### Packaging Layer

| Module | Type | Purpose |
|--------|------|---------|
| `common-feature/` | `eclipse-feature` | Bundles the common plugin with Eclipse Platform, M2E Logback, and M2E PDE features into an installable unit |
| `common-site/` | `eclipse-repository` | Aggregates all features into a P2 update site for distribution — includes EMF, Sirius, Xtext, JDT, EGit, DataTools, M2E, PlantUML, AsciiDoctor, and many more |

## Technology Stack

### Core Technologies
- **Eclipse Tycho 4.0.13** — Maven plugin for building Eclipse plugins, features, and P2 repositories
- **OSGi** — Module system for the Eclipse plugin runtime
- **Eclipse P2** — Provisioning and update system for Eclipse installations
- **SWT** — Standard Widget Toolkit for UI components
- **Eclipse Command Framework** — Extension point system for UI commands and menus

### Build & Quality
- **Maven 3.9.9** with Maven Wrapper (`./mvnw`)
- **JaCoCo 0.8.12** — Code coverage
- **SonarQube** — Code quality analysis (sonar-maven-plugin 3.9.1.2184)
- **Flatten Maven Plugin 1.3.0** — CI-friendly version resolution (`${revision}`)
- **Lombok 1.18.34** — Annotation processing (available but minimally used)
- **Logback 1.5.12** — Test logging

### P2 Repository Dependencies
- Eclipse 2025-03 release train
- Eclipse Orbit 4.35.0
- Sirius 7.4.10
- M2E 2.6.1
- ECF 3.16.2
- Ecoretools 3.4.0
- EMF JSON Jackson 2.2.0

## Build Commands

```bash
# Full build (all modules)
./mvnw clean install

# Build a specific module
./mvnw -pl common clean install
./mvnw -pl common-feature clean install
./mvnw -pl common-site clean install

# Skip tests
./mvnw clean install -DskipTests

# Run tests only
./mvnw clean test

# Build only parent POM (skip all modules)
./mvnw -DskipModules=true clean install

# Update license headers
./mvnw -Pupdate-source-code-license process-sources

# Generate documentation with PlantUML diagrams
./mvnw -Pgenerate-github-asciidoc-diagrams clean process-resources
```

### Maven Profiles

| Profile | Purpose |
|---------|---------|
| `modules` | Default — includes common, common-feature, common-site. Deactivate with `-DskipModules=true` |
| `sign-artifacts` | GPG-signs artifacts using `sign-maven-plugin` |
| `release-judong` | Deploys to JUDO Nexus (snapshots and releases) |
| `release-central` | Deploys to Maven Central via Sonatype OSSRH with auto-release |
| `release-dummy` | Deploys to local filesystem `/tmp/` for testing |
| `generate-github-asciidoc-diagrams` | Generates HTML docs with PlantUML via asciidoctor-maven-plugin |
| `update-source-code-license` | Updates EPL-2.0 license headers in all source files |

## Key Configuration Files

| File | Purpose |
|------|---------|
| `pom.xml` | Parent POM — all P2 repository URLs, Tycho version, build profiles, plugin management |
| `common/META-INF/MANIFEST.MF` | OSGi bundle metadata — symbolic name, version, required bundles, execution environment |
| `common/plugin.xml` | Eclipse extension points — commands, menu contributions, command images |
| `common/plugin.properties` | Localized strings for plugin UI elements |
| `common/build.properties` | Controls which files are included in the plugin JAR |
| `common-feature/feature.xml` | Feature definition — included features and plugins |
| `common-feature/p2.inf` | P2 touchpoint instructions for feature installation |
| `common-site/category.xml` | P2 site definition — all features and bundles in the update site |
| `.mvn/jvm.config` | JVM args for Maven: 2GB heap, module opens for OSGi compatibility |
| `.mvn/extensions.xml` | Maven Wagon extensions (file, WebDAV) for artifact transport |
| `logback-test.xml` | Logging configuration for test execution |

## Development Environment

**Required:**
- Java 21 JDK (Zulu JDK recommended)
- Maven 3.9.9+ (or use `./mvnw`)

**JVM Configuration** (automatically applied via `.mvn/jvm.config`):
- Minimum 1GB, maximum 2GB heap
- `--add-opens java.base/java.lang=ALL-UNNAMED` for OSGi runtime compatibility
- P2 mirrors disabled (`-Dtycho.disableP2Mirrors=true`)

## Git Workflow

- **Main Branch:** `develop`
- **Release Branch:** `master` (latest released sources)
- **Versioning:** 4.35.0-SNAPSHOT (CI-friendly via `${revision}` property)
- **Branch naming:** `feature/JNG-NUMBER_summary`, `bugfix/JNG-NUMBER_summary`, `hotfix/JNG-NUMBER_summary`
- **Every commit must include a JIRA ticket number** (`JNG-xxx` format)
- PRs target `develop` except for release merges to `master`

## Important Notes

1. This is a **Tycho/P2 project**, not a standard Maven Java project — dependencies resolve from P2 repositories, not Maven Central
2. The `${revision}` property in pom.xml enables CI-friendly versioning; the `flatten-maven-plugin` resolves it during build
3. The `common` bundle uses `Eclipse-BundleShape: dir` — it deploys as an exploded directory, not a JAR
4. P2 repository URLs in `pom.xml` are pinned to specific versions (e.g., Eclipse 2025-03, Orbit 4.35.0) — updating Eclipse target versions requires changing these URLs
5. The `category.xml` in `common-site` is the master list of everything included in the update site — adding/removing Eclipse features requires editing this file
6. CI runs on a self-hosted `judong` runner with a 120-minute timeout

## Related Documentation

- [README.md](README.md) — Project overview and module descriptions
- [CONTRIBUTING.md](CONTRIBUTING.md) — Setup instructions and contribution guidelines
- [.github/CIFLOW.md](.github/CIFLOW.md) — CI/CD pipeline details, branching strategy, and version numbering
