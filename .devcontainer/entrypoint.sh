#!/bin/bash

# Dynamically detect the Docker group ID from the mounted socket
DOCKER_GID=$(stat -c '%g' /var/run/docker.sock)

# Add the group if it doesn't exist
groupadd -for -g $DOCKER_GID docker

# Add the user to the Docker group
usermod -aG docker $UNAME

# Start the shell (or any other command you want to run)
exec /bin/bash "$@"
