#!/bin/sh

# This is setup for a MAC shell script
# Don't forget to make the file executable by "chmod +x <filename>"
echo

curl --request POST --header "content-type: text/xml" -d @helloWorldSoapEnvelope.xml http://localhost:9080/geo-ws-1.0

echo
echo