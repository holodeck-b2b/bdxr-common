# Change Log
All notable changes to this project will be documented in this file.
This project adheres to [Semantic Versioning](http://semver.org/).

## 3.0.0
##### Unreleased
### Added
* Meta-data class for _identifier schemes_

### Changed
* The identifier classes `Identifier` and `ProcessIdentifier` now use the new `IdScheme` class

## 2.0.0
##### 2021-04-15
### Added
* "Copy" constructors to all data model classes that use another instance to initialise

### Changed 
To create a better separation between the generic 4-Corner datamodel classes and the SMP client related code the project was split into two projects.
This projects now only contains generic code not related to specific functionality. The SMP client code has been moved to a new project: 
[BDXR SMP Client](https://github.com/holodeck-b2b/bdxr-smp-client)

### Removed
* The possibility to include multiple process identifiers in `org.holodeckb2b.bdxr.smp.datamodel.ProcessInfo` as this is not
  possible in the SMP data model

## 1.1.0
##### 2019-03-06
### Added
* Support for the OASIS SMP v1 format
* Example client for looking up SMP registrations in the CEF connectivity test environment

### Fixed
* Incorrect host name generation for executing request in the PEPPOL network   

## 1.0.0
##### 2018-09-19
### Added
* Initial release. 

