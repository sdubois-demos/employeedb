#!/bin/bash

URL="http://localhost:8080"
API="api"
PYTHONDEV=/tmp/pythondev 
TEST_DATA=/tmp/random_users.json
NUMBER=$1

if [ "$NUMBER" == "" ]; then 
  echo "USAGE: $0 <number-of-users>"; exit 0
fi

[ -f $TEST_DATA ] && rm -f $TEST_DATA

# ---  GENERATE USERS TEST DATA ---
if [ ! -d $PYTHONDEV ]; then 
  python3 -m venv $PYTHONDEV
  pip3 install faker > /dev/null 2>&1
fi

source $PYTHONDEV/bin/activate
python3 gen_test_data.py $NUMBER $TEST_DATA
