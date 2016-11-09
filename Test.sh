#!/bin/bash

make;
find valid/advanced/binarySortTree.wacc -name "*.wacc" -type f -print0 -exec ./compile {} \;