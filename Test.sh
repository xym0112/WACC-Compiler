#!/bin/bash

make;
find valid -name "*.wacc" -type f -print0 -exec ./grun antlr.WACC prog {} \;