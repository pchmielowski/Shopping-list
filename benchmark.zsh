#!/usr/bin/zsh

git branch --color=never | grep --color=never \* >> ../out

repeat 1; do
    ./gradlew clean
    ./gradlew assembleDebug | grep "BUILD SUCCESSFUL" >> ../out
done
