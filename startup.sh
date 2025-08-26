#!/bin/sh
set -a
. /opt/cyberlab/.env
set +a
exec java -jar /opt/cyberlab/build/libs/app.jar