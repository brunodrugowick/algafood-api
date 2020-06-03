#!/bin/bash

count=0
for i in {3..20}
do
	curl --request POST --url http://localhost:8080/cuisines --header 'content-type: application/json' --data '{"name": "Cuisine '${i}'"}'
	echo
	((count++))
done

echo "Created ${count} cuisines"
