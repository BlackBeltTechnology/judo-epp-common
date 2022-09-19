#!/bin/sh
mvn validate -Dtycho.mode=maven -P update-target-versions -DlocalP2 -f common-targetdefinition/pom.xml
