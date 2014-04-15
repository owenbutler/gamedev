#!/bin/sh
ssh obutler@owenbutler.org "mkdir -p sites/reboot/JGE_ARTIFACT_ID/"
scp -r target/jnlp/* obutler@owenbutler.org:/home/obutler/sites/reboot/JGE_ARTIFACT_ID/
scp target/jnlp/.htaccess obutler@owenbutler.org:/home/obutler/sites/reboot/JGE_ARTIFACT_ID/
