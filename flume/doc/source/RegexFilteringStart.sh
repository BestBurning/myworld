#!/usr/bin/env bash
SOURCE_DIR=$(cd `dirname $0`; pwd)
CONF_DIR=$SOURCE_DIR/../conf
cd $SOURCE_DIR

sh ~/top/apache-flume-1.7.0-bin/bin/flume-ng agent -c $CONF_DIR -f $SOURCE_DIR/RegexFiltering.conf -n a1  -Dflume.root.logger=DEBUG,console
