#!/bin/bash
export VERSION=$1
export BASE_URL=$2
export BASE_PATH=$3
export USERNAME=$4
export PASSWORD=$5
export NUM_OF_PROC=$6

export BASE_DIR="$(cd "$(dirname "$0")"; pwd)";
export REPOSITORY_DIR="$BASE_DIR/$BASE_PATH"

callUpload() {
  echo -n "Uploading $REPOSITORY_DIR$1 to $BASE_URL/$VERSION$1"
  curl --write-out ' Response code: %{http_code}\n' --silent --output /dev/null --user "$USERNAME:$PASSWORD" --upload-file $REPOSITORY_DIR$1 $BASE_URL/$VERSION$1
}
export -f callUpload

path_length=`echo -n "$REPOSITORY_DIR" | wc -c`; path_length=$(($path_length + 1))

find $REPOSITORY_DIR -type f -print0 | xargs -0 -I file echo "file" | cut -c$path_length- | xargs -I processing_file | parallel --jobs $NUM_OF_PROC --env my_func callUpload
