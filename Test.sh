#!/bin/bash

make;
#find invalid/semanticErr/exit/badCharExit.wacc -name "*.wacc" -type f -print0 -exec ./compile {} \;

find valid -name "*.wacc" -type f -print0 -exec ./compile {} \;

#find valid -name "*.wacc" -type f -print0 -exec ./grun antlr.WACC prog {} \;

