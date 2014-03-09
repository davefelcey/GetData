#!/bin/bash

HOST=ec2-54-81-101-9.compute-1.amazonaws.com
curl -v -H "Accept: application/xml" -X GET http://$HOST:8080/getdata-1.0-SNAPSHOT/resource?name=Rory

