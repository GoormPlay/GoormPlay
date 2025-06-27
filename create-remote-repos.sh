#!/bin/bash

GITHUB_OWNER="GoormPlay"

cd split-repos || exit

for dir in *; do
  if [ -d "$dir" ]; then
    echo "📦 리포 생성 중: $GITHUB_OWNER/$dir"
    gh repo create "$GITHUB_OWNER/$dir" --private --confirm --description "$dir for GoormPlay MSA"
  fi
done
