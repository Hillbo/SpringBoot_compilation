#!/bin/zsh
echo remove-target--------------------------------------------
cd /Users/hillbo/Documents/Code/personal/hillbo/app-bundle/hillbo-bundle && rm -rf ./target

echo remove-lib-----------------------------------------------
cd /Users/hillbo/Documents/Code/personal/hillbo/app-bundle/hillbo-bundle/WebContent/WEB-INF && rm -rf ./lib

echo app-parent----------------------------------------------
cd /Users/hillbo/Documents/Code/personal/hillbo/app-parent && mvn clean install -Dmaven.test.skip=true

echo app-platform----------------------------------------------
cd /Users/hillbo/Documents/Code/personal/hillbo/app-platform && mvn clean install -Dmaven.test.skip=true

echo app-test----------------------------------------------
cd /Users/hillbo/Documents/Code/personal/hillbo/app-test && mvn clean install -Dmaven.test.skip=true

echo app-bundle--------------------------------------------
cd /Users/hillbo/Documents/Code/personal/hillbo/app-bundle && mvn clean install -Dmaven.test.skip=true
