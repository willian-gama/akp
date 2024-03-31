#!/bin/sh

PROPERTIES="local.properties"

touch $PROPERTIES

{
  echo "api_key=$API_KEY"
} >> $PROPERTIES

cat $PROPERTIES