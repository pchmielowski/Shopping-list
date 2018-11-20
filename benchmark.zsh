#!/usr/bin/zsh

git branch --color=never | grep --color=never \* >> ../out
git rev-parse --short HEAD >> ../out

repeat 20; do
    ./gradlew clean
    ./gradlew assembleDebug | grep "BUILD SUCCESSFUL" >> ../out
done
