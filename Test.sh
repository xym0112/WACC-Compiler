#!/bin/bash

make;
find valid -name "*.wacc" -type f -print0 -exec ./compile {} \; -exec printf "\n" \;
find invalid -name "*.wacc" -type f -print0 -exec ./compile {} \; -exec printf "\n" \;

#find valid -name "*.wacc" -type f -print0 -exec ./compile {} \; -exec printf "\n" \;
