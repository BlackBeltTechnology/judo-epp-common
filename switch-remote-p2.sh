#!/bin/sh
mvn validate -Dtycho.mode=maven -P update-target-versions -f common-targetdefinition/pom.xml
