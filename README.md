[![CircleCI](https://circleci.com/gh/pchmielowski/Shopping-list.svg?style=svg)](https://circleci.com/gh/pchmielowski/Shopping-list)

Modules:

* `app` - top level module - DI setup
* `view` - `Activities`, `Fragments`, `ViewModels`, `Adapters` and resources
* `persistence` - Room database
* `system` - system APIs - location, permissions etc. Currently empty ;)
* `domain` - common classes, mainly interfaces. `view` depends on `domain` but does know anything about `persistence` and `system`.
