#!/bin/bash
for i in {1..100}
do
  id=$i
  case "$(($RANDOM % 3))" in
    0) name="Biden"
      ;;
    1) name="Trump"
      ;;
    2) name="Other"
      ;;
  esac
  timestamp=$(date "+%Y-%m-%d %H:%M:%S")
	curl -d "{\"id\": $id, \"name\": \"$name\", \"time\": \"$timestamp\"}" -X POST -H "Content-Type: application/json" http://localhost:8888/api/1/tables/votes/row
	echo ""
	sleep $(($RANDOM % 5))
done