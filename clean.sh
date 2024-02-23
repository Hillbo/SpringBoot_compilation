#!/bin/zsh
echo remove-target--------------------------------------------
cd /Users/hillbo/Documents/Code/hillbo/app-bundle/hillbo-bundle && rm -rf ./target

echo remove-lib-----------------------------------------------
cd /Users/hillbo/Documents/Code/hillbo/app-bundle/hillbo-bundle/WebContent/WEB-INF && rm -rf ./lib

echo app-parent----------------------------------------------
cd /Users/hillbo/Documents/Code/hillbo/app-parent && mvn clean

echo app-platform----------------------------------------------
cd /Users/hillbo/Documents/Code/hillbo/app-platform && mvn clean

echo app-test----------------------------------------------
cd /Users/hillbo/Documents/Code/hillbo/app-test && mvn clean

echo app-bundle----------------------------------------------
cd /Users/hillbo/Documents/Code/hillbo/app-bundle && mvn clean