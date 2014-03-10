#!/bin/bash

HOST=localhost

curl -v -H "Accept: application/xml" -X GET http://$HOST:8080/getdata-1.0-SNAPSHOT/resource?name=Rory
