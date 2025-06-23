#!/bin/bash

if [ "$1" == "" ]; then 
  echo "$0 USAGE $0 <url>"
  echo "         Testing URL's: http://localhost:8080            # Local Deployment"
  echo "                        https://edb.apps.fortidemo.ch"      
  echo "            
  exit 1
fi

URL="http://localhost:8080"
API="api"
PYTHONDEV=/tmp/pythondev 

# ---  GENERATE USERS TEST DATA ---
if [ ! -f /tmp/random_users.json ]; then 
  echo "ERROR: $0 User data not generated yet, please run ./gen_test_data.sh first"
  exit 0
fi

# adding users
i=1
for n in $(cat /tmp/random_users.json | jq -r '.[] | .firstName + ":" + .lastName + ":" + .email'); do
  fst=$(echo $n | awk -F: '{ print $1 }')
  lst=$(echo $n | awk -F: '{ print $2 }')
  eml=$(echo $n | awk -F: '{ print $3 }')

  id=$(curl -k -X POST -H "Content-Type: application/json" \
    -d "{\"firstName\":\"$fst\",\"lastName\":\"$lst\",\"email\":\"$eml\"}" \
    ${URL}/${API}/employees 2>/dev/null | jq -r '.id')

    emlsuf=$(echo $eml | awk -F'@' '{ print $NF}')
    eml="${fst}.${lst}@$emlsuf"

  let i=i+1

done

echo "$i users added"

exit


