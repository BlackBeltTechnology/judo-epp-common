# common-plugin Specification

## Purpose

The `common` module is an Eclipse/OSGi plugin bundle (`hu.blackbelt.judo.eclipse.epp.package.common`) that provides branding resources and a "Contribute" command for the JUDO Eclipse packaging.

## Architecture

The plugin consists of a single command handler class `ContributeHandler` that extends `AbstractHandler` from the Eclipse Command Framework. It is registered via `plugin.xml` as a command (`hu.blackbelt.judo.eclipse.epp.package.common.contribute`) and contributed to the Help menu after the "About" entry. The bundle also includes splash screens, icons, and about dialog content used for JUDO Eclipse product branding.

Key components:
- `ContributeHandler` — Opens the Eclipse contribute page in a browser
- `plugin.xml` — Declares the command, menu contribution, and command image via Eclipse extension points
- `META-INF/MANIFEST.MF` — OSGi metadata requiring Eclipse Platform, Equinox, UI, Commands, Core Runtime, and Workbench bundles
- `plugin.properties` — Localized UI strings for the command label and description

## Requirements

### Requirement: Contribute command SHALL open the Eclipse contribute URL in a browser

The `ContributeHandler.execute()` method SHALL open `https://www.eclipse.org/contribute/` when the command is invoked.

#### Scenario: Browser available through workbench
- **GIVEN** an active workbench window with browser support
- **WHEN** the Contribute command is executed
- **THEN** the Eclipse contribute URL is opened in the workbench's internal browser and `Status.OK_STATUS` is returned

#### Scenario: Browser initialization fails
- **GIVEN** an active workbench window where `createBrowser()` throws `PartInitException`
- **WHEN** the Contribute command is executed
- **THEN** the URL is opened via `Program.launch()` as a fallback using the system's default browser

#### Scenario: No active workbench window
- **GIVEN** no active workbench window (null)
- **WHEN** the Contribute command is executed
- **THEN** an error `IStatus` is returned with the message "No active workbench window"

### Requirement: Command SHALL be registered in the Help menu

The command SHALL appear in the Eclipse Help menu after the "About" item.

#### Scenario: Menu contribution at correct location
- **GIVEN** the plugin is installed and activated
- **WHEN** the user opens the Help menu
- **THEN** the Contribute command appears after the "About" entry with a star icon (`icons/star.png`)

### Requirement: Bundle SHALL declare correct OSGi metadata

The bundle SHALL be configured as a singleton OSGi bundle targeting JavaSE-21 with exploded directory shape.

#### Scenario: Bundle manifest configuration
- **GIVEN** the plugin is deployed
- **WHEN** the OSGi framework loads the bundle
- **THEN** the bundle symbolic name is `hu.blackbelt.judo.eclipse.epp.package.common` with `singleton:=true`, execution environment is `JavaSE-21`, and bundle shape is `dir`
