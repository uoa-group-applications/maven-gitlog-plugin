Maven GitLog Plugin
===================
### This plugin aims to enhance Kanban Flow across multiple teams in UOA.

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/nz.ac.auckland.groupapps.maven/maven-gitlog-plugin/badge.svg)](https://maven-badges.herokuapp.com/maven-central/nz.ac.auckland.groupapps.maven/maven-gitlog-plugin) [![Build Status](https://travis-ci.org/uoa-group-applications/maven-gitlog-plugin.svg?branch=master)](https://travis-ci.org/uoa-group-applications/maven-gitlog-plugin)
[![Coverage Status](https://coveralls.io/repos/uoa-group-applications/maven-gitlog-plugin/badge.png)](https://coveralls.io/r/uoa-group-applications/maven-gitlog-plugin)
[![Code Climate](https://codeclimate.com/github/uoa-group-applications/maven-gitlog-plugin/badges/gpa.svg)](https://codeclimate.com/github/uoa-group-applications/maven-gitlog-plugin)
[![Test Coverage](https://codeclimate.com/github/uoa-group-applications/maven-gitlog-plugin/badges/coverage.svg)](https://codeclimate.com/github/uoa-group-applications/maven-gitlog-plugin)



The plugin will do:
<ol>
<li>Automatically generate a release note based on previous commit messages, and include the release note into the package.</li>
<li>If "DeployedArtifact" be configured as true in current artifact, the plugin will scan all dependencies, and merge the release notes into local release note if there is one.</li>
<li>The plugin will include all commits if there is no "IssuePrefix" configuration. Otherwise, the release note will only contain the commits if the short message contains "IssuePrefix".</li>
</ol>
