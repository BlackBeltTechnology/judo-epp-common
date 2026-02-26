# common-site Specification

## Purpose

The `common-site` module produces a P2 update site (Eclipse repository) that aggregates the JUDO common feature along with a comprehensive set of Eclipse features and bundles into a single installable repository for building JUDO Eclipse products.

## Architecture

The site is defined in `category.xml` which lists all features and bundles to include. The Tycho `eclipse-repository` packaging type processes this file to produce a P2 repository. The included content spans several categories:

- **Core Platform**: Eclipse Platform, SDK, PDE, Equinox, JDT
- **Modeling**: EMF SDK, Sirius, UML2, OCL, Xtext, Xtend, Ecoretools, Xcore, Emfatic
- **Build & VCS**: M2E (Maven), Buildship (Gradle), EGit/JGit
- **Data & Web**: Eclipse DataTools, JST/WST web tools, JPA tools
- **Extras**: AsciiDoctor editor, PlantUML, Terminal, EclEmma, ANSI Console, LSP4J/LSP4E

Additionally, specific OSGi bundles are included directly (Jackson, Guava, SLF4J, EMF JSON Jackson, M2E components, Commonmark).

## Requirements

### Requirement: Site SHALL include the JUDO common feature

The repository SHALL include the `hu.blackbelt.judo.eclipse.epp.package.common.feature`.

#### Scenario: Custom feature availability
- **GIVEN** the P2 repository is built
- **WHEN** an Eclipse installation points to this repository
- **THEN** the `hu.blackbelt.judo.eclipse.epp.package.common.feature` is available for installation

### Requirement: Site SHALL include all Eclipse modeling features

The repository SHALL include EMF, Sirius, UML2, OCL, Xtext, and Ecoretools features needed for JUDO modeling development.

#### Scenario: Modeling tool availability
- **GIVEN** the P2 repository is built
- **WHEN** a developer installs from this site
- **THEN** EMF SDK, Sirius (runtime, specifier, AQL, properties), UML2 SDK, OCL SDK, Xtext SDK, Ecoretools design, and Ecore Xcore SDK features are all available

### Requirement: Site SHALL include Java and build tool features

The repository SHALL include JDT, M2E, Buildship, and PDE for Java/Maven/Gradle development.

#### Scenario: Java development tool availability
- **GIVEN** the P2 repository is built
- **WHEN** a developer installs from this site
- **THEN** Eclipse JDT, M2E (Maven integration), Buildship (Gradle), PDE, and EclEmma (code coverage) are available

### Requirement: Site SHALL include version control features

The repository SHALL include EGit, JGit, and GitFlow features.

#### Scenario: Git integration availability
- **GIVEN** the P2 repository is built
- **WHEN** a developer installs from this site
- **THEN** EGit, JGit, JGit LFS, and EGit GitFlow features are available

### Requirement: Site SHALL include required OSGi bundles

The repository SHALL include standalone OSGi bundles not covered by features, such as Jackson, Guava, SLF4J, and M2E components.

#### Scenario: Bundle availability
- **GIVEN** the P2 repository is built
- **WHEN** P2 resolves dependencies for installed features
- **THEN** Jackson (core, databind, annotations, Guava datatype, JAXB module), Guava, SLF4J API, EMF JSON Jackson, and Commonmark bundles are resolvable from this repository
