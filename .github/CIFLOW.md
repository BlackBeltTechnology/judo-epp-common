# Development Version and Branch Handling

## Branches

The versioning policy follows [GitFlow](https://www.atlassian.com/git/tutorials/comparing-workflows/gitflow-workflow). All development flows through a well-defined set of branch types, each with specific rules about when and how versions change.

```mermaid
gitGraph
    commit id: "init"
    branch develop
    checkout develop
    commit id: "dev-1"
    branch feature/JNG-1
    checkout feature/JNG-1
    commit id: "feat-1"
    commit id: "feat-2"
    checkout develop
    merge feature/JNG-1 id: "merge-feat-1"
    branch feature/JNG-3
    checkout feature/JNG-3
    commit id: "feat-3"
    checkout develop
    merge feature/JNG-3 id: "merge-feat-3"
    branch release/1.0-beta1
    checkout release/1.0-beta1
    commit id: "rc-1"
    branch bugfix/JNG-4
    checkout bugfix/JNG-4
    commit id: "fix-1"
    checkout release/1.0-beta1
    merge bugfix/JNG-4 id: "merge-fix"
    checkout develop
    merge release/1.0-beta1 id: "merge-release"
    checkout master
    merge release/1.0-beta1 id: "release-1.0"
```

| Branch Pattern | Base | Purpose |
|---|---|---|
| `develop` | — | Main development branch with latest sources of the active version |
| `feature/JNG-NUMBER_summary` | `develop` | New features for the next release |
| `(release/)X.Y.Z` | `develop` | Release candidates; the `release/` prefix is reserved for CI |
| `bugfix/JNG-NUMBER_summary` | release branch | Bug fixes applied during release testing; must also be applied to newer versions |
| `support/JNG-NUMBER_summary` | release branch | Minor changes to a previous release |
| `master` | — | Latest released sources |
| `hotfix/JNG-NUMBER_summary` | `master` | Emergency fixes applied to both release and master branches |

## Version Numbers

Version numbers follow semantic versioning with these rules:

| Event | Version Change |
|---|---|
| Start a `feature/` branch | No version change |
| Start a release branch from `develop` | 2nd number incremented on develop |
| Start a `bugfix/` branch | No version change (applied on release branch) |
| Start a `support/` branch | 3rd number incremented |
| Start a `hotfix/` branch | 4th number incremented |

## GitHub Actions Workflows

The CI/CD pipeline consists of several interconnected workflows that automate building, testing, releasing, and merging.

### build.yml — Main Build Pipeline

This is the primary workflow. It triggers on pushes to `develop` and pull requests targeting `develop`, `master`, `increment/*`, or `release/*` branches.

```mermaid
flowchart TD
    A["Trigger:<br/>push on develop<br/>or PR on develop/master/increment/release"] --> B{Branch type?}
    B -->|"master, release/*"| C["Set version from pom.xml<br/><i>(without -SNAPSHOT)</i>"]
    B -->|"develop, increment/*"| D["Set version:<br/>major.minor.qualifier.date_commitId_branch"]
    C --> E["Build & deploy to Nexus"]
    D --> E
    E --> F["Create git tag v&lt;version&gt;"]
    F --> G{Branch type?}
    G -->|"increment/*, release/*"| H["Create merge-pr/&lt;version&gt; tag"]
    H --> I["Triggers merge-pr-tagged.yml"]
    G -->|"develop"| J["Build changelog"]
    J --> K["Create GitHub prerelease"]
    G -->|"other"| L["Done"]
```

### merge-pr-tagged.yml — Automated PR Merging

Triggered when a `merge-pr/*` tag is pushed. Routes the merge based on version format:

```mermaid
flowchart TD
    A["Trigger: push on merge-pr/* tag"] --> B["Extract version from tag"]
    B --> C{Version format?}
    C -->|"major.minor.qualifier<br/>(release format)"| D["Merge PR to master"]
    D --> E["Triggers create-release-on-master.yml"]
    C -->|"other format<br/>(dev snapshot)"| F["Squash PR to develop"]
    F --> G["Triggers build.yml"]
    D --> H["Delete merge-pr tag"]
    F --> H
```

### create-release-on-master.yml — Release Publishing

Triggered by pushes to `master`. Creates a GitHub release with a generated changelog.

```mermaid
flowchart LR
    A["Push to master"] --> B["Get version from tag"]
    B --> C["Build changelog"]
    C --> D["Create GitHub release<br/>(latest)"]
```

### release.yml — Manual Release

Triggered manually with a version parameter. Creates pull requests for both the release and the next development version:

```mermaid
flowchart TD
    A["Manual trigger<br/>with version"] --> B{Version = 'auto'?}
    B -->|yes| C["Use version from pom.xml<br/>(without -SNAPSHOT)"]
    B -->|no| D["Use given version"]
    C --> E["Calculate next version<br/>(qualifier + 1)"]
    D --> E
    E --> F["Create PR to master<br/>with release version"]
    E --> G["Create PR to develop<br/>with next version"]
    F --> H["Triggers build.yml"]
    G --> H
```

## Development Rules

> **Important:** There is no commit without a ticket number. Every pull request and commit must include a JIRA ticket reference in `JNG-xxx` format.

Issue tracking uses [JIRA](https://blackbelt.atlassian.net/jira/dashboards).
