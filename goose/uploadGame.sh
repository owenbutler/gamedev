#!/bin/sh
ssh obutler@owenbutler.org "mkdir -p sites/reboot/goose/"
scp -r target/jnlp/* obutler@owenbutler.org:/home/obutler/sites/reboot/goose/
scp target/jnlp/.htaccess obutler@owenbutler.org:/home/obutler/sites/reboot/goose/
