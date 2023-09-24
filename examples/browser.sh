#!/bin/bash
for i in {1..100}
do
  id=$i
  case "$(($RANDOM % 6))" in
    0) name="Firefox"
      ;;
    1) name="Chrome"
      ;;
    2) name="Safari"
      ;;
    3) name="Internet Explorer"
      ;;
    4) name="Edge"
      ;;
    5) name="Opera"
      ;;
  esac
  timestamp=$(date "+%Y-%m-%d %H:%M:%S")
	curl -d "{\"id\": $id, \"name\": \"$name\", \"time\": \"$timestamp\"}" -X POST -H "Content-Type: application/json" http://localhost:8888/api/1/tables/browsers/row
	echo ""
	sleep $(($RANDOM % 5))
done