# Contributing to judo-epp-common

## Development Environment Setup

### Required Tools

| Tool | Version | Notes |
|------|---------|-------|
| JDK | 21 | [Zulu JDK](https://www.azul.com/downloads/?version=java-21-lts&package=jdk) recommended |
| Maven | 3.9.9+ | Or use the included `./mvnw` wrapper |

### Verifying Your Setup

```bash
# Check Java version — should show JDK 21
java -version

# Check Maven version — should show 3.9.x+
mvn -version
```

## Build Commands

```bash
# Run tests
./mvnw clean test

# Full build (compile, test, package, install to local repo)
./mvnw clean install

# Build a specific module
./mvnw -pl common clean install
```

## Submitting an Issue

Before creating a new issue, search the [issue tracker](https://github.com/BlackBeltTechnology/judo-epp-common/issues) — your problem may already be reported or resolved.

When reporting a bug, include:

- Output of `java -version` and `mvn -version`
- Relevant `pom.xml` or `.flattened-pom.xml` content
- A minimal reproducible use case that demonstrates the failure

File new issues using the [issue form](https://github.com/BlackBeltTechnology/judo-epp-common/issues/new/choose).

## Submitting a Pull Request

This project follows [GitHub's standard forking model](https://guides.github.com/activities/forking/). Fork the repository and submit pull requests against the `develop` branch.

For details on how the CI/CD pipeline processes pull requests, see the [CI Flow documentation](.github/CIFLOW.md).

## Git Workflow

The project uses a GitFlow branching model. See [CIFLOW.md](.github/CIFLOW.md) for the full branching strategy, version numbering rules, and CI/CD pipeline details.

> **Important:** Every commit must include a JIRA ticket number (`JNG-xxx`). No commits without ticket numbers are accepted.
