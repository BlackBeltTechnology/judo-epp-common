# common-feature Specification

## Purpose

The `common-feature` module defines an Eclipse feature (`hu.blackbelt.judo.eclipse.epp.package.common.feature`) that bundles the common plugin with its required platform features into an installable unit for Eclipse.

## Architecture

The feature is defined in `feature.xml` and includes:
- The `hu.blackbelt.judo.eclipse.epp.package.common` plugin
- Three included features: `org.eclipse.platform`, `org.eclipse.m2e.logback.feature`, `org.eclipse.m2e.pde.feature`
- License reference to `org.eclipse.license` feature
- P2 touchpoint instructions in `p2.inf`

The feature acts as the installable unit that users add to their Eclipse installations. It transitively pulls in the Eclipse Platform and M2E features needed by the common plugin.

## Requirements

### Requirement: Feature SHALL include the common plugin

The feature SHALL package the `hu.blackbelt.judo.eclipse.epp.package.common` plugin.

#### Scenario: Plugin inclusion
- **GIVEN** the feature is built by Tycho
- **WHEN** the feature is resolved
- **THEN** the `hu.blackbelt.judo.eclipse.epp.package.common` plugin is included with version `0.0.0` (resolved at install time)

### Requirement: Feature SHALL include platform dependencies

The feature SHALL include Eclipse Platform and M2E features as transitive dependencies.

#### Scenario: Included features
- **GIVEN** the feature is installed into an Eclipse installation
- **WHEN** P2 resolves dependencies
- **THEN** `org.eclipse.platform`, `org.eclipse.m2e.logback.feature`, and `org.eclipse.m2e.pde.feature` are also installed

### Requirement: Feature SHALL reference the Eclipse license

The feature SHALL use the `org.eclipse.license` feature for license presentation.

#### Scenario: License display
- **GIVEN** the feature is presented in the Eclipse installation dialog
- **WHEN** the user views the license
- **THEN** the Eclipse Public License 2.0 text is displayed from the referenced license feature
