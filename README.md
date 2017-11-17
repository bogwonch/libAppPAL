# libAppPAL

This is the main repo for the AppPAL authorization logic.

**WARNING: This is no longer under development.**

Whilst you should be able to get it up and running with a simple `make` I make no claims that anything here is particularly usable and it may break weirdly, or have special hacks put in.

If you're interested in AppPAL and want to know more, or you want to try and use it for something send an email to [josephhallett@gmail.com](mailto:josephhallett@gmail.com).  I'm happy to answer questions!  AppPAL (and SecPAL) was part of my PhD thesis and I'm alway interested to hear if(!) and how people are using it.

## Usage

To compile:

    make

To run the main AppPAL logic:

    java -jar AppPAL.jar -h

To run the AppPAL program linter:
    java -jar Lint.jar -h
  
To run the (very minimal) tests:

    cd tests
    bash ./run_tests.sh

