# Step tracker proof of concept

## Capacitor plugins
- Nothing match my need, so I implement it myself.
  - [background-mode](https://github.com/katzer/cordova-plugin-background-mode) does not provide enough control (or I can't find workaround).
  - [pedometer](https://github.com/leecrossley/cordova-plugin-pedometer) get killed when app is killed.

## Potential problem
- battery consumption
- notification
- version compatibility
- os just decided to kill the service (can't replicate the bug)
  - probably service priority.
- easy to abuse (just shake the phone)