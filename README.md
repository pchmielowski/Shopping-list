[![CircleCI](https://circleci.com/gh/pchmielowski/Shopping-list.svg?style=svg)](https://circleci.com/gh/pchmielowski/Shopping-list)
[![codecov](https://codecov.io/gh/pchmielowski/Shopping-list/branch/master/graph/badge.svg)](https://codecov.io/gh/pchmielowski/Shopping-list)

# Gradle modules:

* `app` - Top level module - DI setup.
* `view` - `Activities`, `Fragments`, `ViewModels`, `Adapters` and resources.
* `persistence` - Room database and data reading/writing logic.
* `domain` - Common classes, mainly interfaces, which are implemented in higher level modules. Common DTOs.

# Dependencies between modules

Every module depends on the one below it.
None of modules depends on the module above or on the same level.

<table style="text-align:center;" cellpadding="5">
  <tr>
    <td colspan="2" bgcolor="#DDD">:app</td>
  </tr>
  <tr>
    <td bgcolor="#BBB">:view</td>
    <td bgcolor="#999">:persistence</td>
  </tr>
  <tr>
    <td colspan="2" bgcolor="#888">:domain</td>
  </tr>
</table>
