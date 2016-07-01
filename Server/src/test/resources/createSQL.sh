#!/usr/bin/env bash

mysql -u "Andrii" "-p111" -Bse "create database TaxiProjectTest;"
mysql -u "Andrii" "-p111" -Bse "source src/test/resources/TestSchema.sql;"
