#!/bin/bash

# Get the root directory of the Git project
git_root=$(git rev-parse --show-toplevel)

echo "Installing Git hooks..."
cp "$git_root"/git-hooks/pre-commit "$git_root"/.git/hooks/pre-commit
chmod +x "$git_root"/.git/hooks/pre-commit
echo "Git hooks installed."

echo "Setup complete."