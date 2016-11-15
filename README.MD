## A lexer, parser and semantic analyser for WACC programs written in Java using ANTLR 4

## Running the front end compiler

Example valid and invalid WACC programs are in the valid and invalid directories respectively.
### Usage:

```sh
# Make the compiler
$ make
```

```sh
# Run the compiler on an valid wacc program
$ ./compile valid/advanced/ticTacToe.wacc
```

```sh
# Run the compiler on an invalid wacc program
$ ./compile invalid/semanticErr/expressions/boolOpTypeErr.wacc
```

```sh
# Removes all generated files
$ make clean
```

```sh
# Basic system testing
$ bash Test.sh
```

### Developers

* Andreas Asprou
* Vinamra Agrawal
* Bálint Babik
