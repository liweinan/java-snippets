#!/bin/sh

set -x
mvn clean
git add *.java
git status
git commit -a -m '.'
git fetch origin
git rebase origin/master
git push origin master
