#!/bin/bash
for i in {1..100}
do
  id=$i
  temperature=$(($RANDOM % 10 + 20))
  humidity=$((100-($RANDOM % 10)))
  pressure=$(($RANDOM % 10 + 10))
  timestamp=$(date "+%H:%M:%S")
	curl -d "{\"id\": $id, \"temperature\": $temperature, \"humidity\": $humidity, \"pressure\": $pressure, \"timestamp\": \"$timestamp\"}" -X POST -H "Content-Type: application/json" http://localhost:8888/api/1/tables/measurements/row
	echo ""
	sleep 1
done