#!/bin/bash

make;
find valid -name "*.wacc" -type f -exec ./compile {} \;