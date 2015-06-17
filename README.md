UOA GitLog Plugin
===

[![Build Status](https://travis-ci.org/uoa-group-applications/uoa-gitlog-plugin.svg?branch=master)](https://travis-ci.org/uoa-group-applications/uoa-gitlog-plugin)
[![Coverage Status](https://coveralls.io/repos/uoa-group-applications/uoa-gitlog-plugin/badge.svg)](https://coveralls.io/r/uoa-group-applications/uoa-gitlog-plugin)

This plugin aims to enhance Kanban Flow across multiple teams in UOA.

The plugin will do:

1. Automatically generate a release note based on previous commit messages, and include the release note into the package.
2. If "DeployedArtifact" be configured as true in current artifact, the plugin will scan all dependencies, and merge the release notes into local release note if there is one.
3. The plugin will include all commits if there is no "IssuePrefix" configuration. Otherwise, the release note will only contain the commits if the short message contains "IssuePrefix".

