#!/bin/bash
echo "\$2"
python yowsup-cli -c config.example -s $1 " $2"