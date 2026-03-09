# judo-epp-common

[![Build](https://github.com/BlackBeltTechnology/judo-epp-common/actions/workflows/build.yml/badge.svg?branch=develop)](https://github.com/BlackBeltTechnology/judo-epp-common/actions/workflows/build.yml)

JUDO Eclipse Packaging Project (EPP) Common is a mirror site and foundation for building JUDO Eclipse-based products. It assembles a curated set of Eclipse features and plugins into a P2 update site that can be used to install or extend Eclipse installations for JUDO development.

## Modules

The project is organized into three Maven/Tycho modules that form a layered build pipeline:

```mermaid
graph TD
    A["common<br/><i>eclipse-plugin</i>"] -->|bundled into| B["common-feature<br/><i>eclipse-feature</i>"]
    B -->|included in| C["common-site<br/><i>eclipse-repository</i>"]
    C -->|produces| D["P2 Update Site"]

    style A fill:#e1f5fe
    style B fill:#fff3e0
    style C fill:#e8f5e9
    style D fill:#f3e5f5
```

| Module | Packaging | Description |
|--------|-----------|-------------|
| `common` | `eclipse-plugin` | The main OSGi bundle (`hu.blackbelt.judo.eclipse.epp.package.common`). Contains the `ContributeHandler` command, splash screens, icons, and branding resources. |
| `common-feature` | `eclipse-feature` | Eclipse feature that bundles the common plugin together with Eclipse Platform, M2E Logback, and M2E PDE features. |
| `common-site` | `eclipse-repository` | P2 update site definition. Aggregates dozens of Eclipse features (EMF, Sirius, Xtext, JDT, M2E, EGit, DataTools, etc.) into a single installable repository. |

> **Note:** The `common-targetdefinition` module referenced in older documentation is currently disabled.

## Build

### Prerequisites

- **JDK 21** (Zulu JDK recommended)
- **Maven 3.9.9+** (or use the included Maven Wrapper)

### Commands

```bash
# Full build (all modules)
./mvnw clean install

# Build a single module
./mvnw -pl common clean install

# Skip tests
./mvnw clean install -DskipTests

# Build only the parent POM (skip all modules)
./mvnw -DskipModules=true clean install
```

### Build Architecture

The build uses **Tycho 4.0.13** to bridge Maven and Eclipse/OSGi. Dependencies are resolved from P2 repositories (not Maven Central) configured in the parent `pom.xml`. The target platform supports five environments:

```mermaid
flowchart LR
    subgraph "Build Pipeline"
        POM["pom.xml<br/>Parent POM"] --> Tycho["Tycho 4.0.13"]
        Tycho --> Plugin["eclipse-plugin<br/><i>common</i>"]
        Tycho --> Feature["eclipse-feature<br/><i>common-feature</i>"]
        Tycho --> Repo["eclipse-repository<br/><i>common-site</i>"]
    end

    subgraph "P2 Repositories"
        E1["Eclipse 2025-03"]
        E2["Eclipse Orbit 4.35.0"]
        E3["Sirius 7.4.10"]
        E4["M2E 2.6.1"]
        E5["+ 10 more"]
    end

    POM --> E1
    POM --> E2
    POM --> E3
    POM --> E4
```

| Target Platform | OS | Windowing | Architecture |
|---|---|---|---|
| Linux GTK | linux | gtk | x86_64, aarch64 |
| Windows | win32 | win32 | x86_64 |
| macOS Cocoa | macosx | cocoa | x86_64, aarch64 |

### Maven Profiles

| Profile | Purpose |
|---------|---------|
| `modules` | Default profile — includes common, common-feature, common-site modules |
| `sign-artifacts` | GPG-signs build artifacts for release |
| `release-judong` | Deploys to JUDO Nexus repository |
| `release-central` | Deploys to Maven Central (Sonatype OSSRH) |
| `release-dummy` | Deploys to local filesystem (for testing) |
| `generate-github-asciidoc-diagrams` | Generates HTML documentation with PlantUML diagrams |
| `update-source-code-license` | Updates EPL-2.0 license headers in source files |

## P2 Update Site Contents

The `common-site` module produces an update site containing a comprehensive set of Eclipse features organized into several categories:

```mermaid
graph TD
    subgraph "Core Platform"
        Platform["Eclipse Platform & SDK"]
        JDT["JDT - Java Development"]
        PDE["PDE - Plugin Development"]
        Equinox["Equinox P2 & OSGi"]
    end

    subgraph "Modeling"
        EMF["EMF SDK & Tools"]
        Sirius["Sirius Designer"]
        UML["UML2 SDK"]
        OCL["OCL SDK"]
        Xtext["Xtext & Xtend SDK"]
        Ecore["Ecoretools & Xcore"]
    end

    subgraph "Build & VCS"
        M2E["M2E - Maven Integration"]
        Buildship["Buildship - Gradle"]
        EGit["EGit & JGit"]
    end

    subgraph "Data & Web"
        DataTools["Eclipse DataTools"]
        WebTools["Web Tools (JST/WST)"]
    end

    subgraph "Extras"
        AsciiDoc["AsciiDoctor Editor"]
        PlantUML["PlantUML"]
        Terminal["Terminal"]
        EclEmma["EclEmma - Coverage"]
    end
```

## Contributing

Everyone is welcome to contribute! Please read [CONTRIBUTING.md](CONTRIBUTING.md) for details.

## License

This project is licensed under the [Eclipse Public License 2.0](https://www.eclipse.org/org/documents/epl-2.0/EPL-2.0.txt).
