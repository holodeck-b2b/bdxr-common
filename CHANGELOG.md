# Change Log
All notable changes to this project will be documented in this file.
This project adheres to [Semantic Versioning](http://semver.org/).

## 5.0.0
##### 
### Added
* Package `org.holodeckb2b.bdxr.common.datamodel` to hold interfaces representing data model classes common to multiple 
  BDXR specifications.
* Added `org.holodeckb2b.bdxr.common.datamodel.Code` and `org.holodeckb2b.bdxr.common.datamodel.Text` interface to represent
  information elements containing a values specified by a code list or text with a language indicator. 

### Changed
* Moved `Identifier`, `ProcessIdentifier` and `Extension` to `org.holodeckb2b.bdxr.common.datamodel` and their implementations 
  to `org.holodeckb2b.bdxr.common.datamodel.impl`.
* Parameterised `Extension` interface to indicate the type of the extension's content

### Removed
* Constructors in the SMP data model classes that used an instance of the interface representing the data model class as
  a parameter
* Automatic parsing of the ID scheme identifier from a string argument passed to the
  `IdentifierImpl(final String id)` and `ProcessIdentifierImpl(final String id)` constructors

## 4.0.0
##### 2022-02-24
### Changed
* The `org.holodeckb2b.bdxr.smp.datamodel.impl.IdentifierImpl(final String id)` constructor now tries to parse the
  given string into a scheme ID and identifier value using the format «schemeID»::«id value»

## 3.0.0
##### 2022-05-03
### Added
* Interface for _identifier schemes_
* Interfaces for _service groups_ (supporting both the old PEPPOL and OASIS SMP version 1.0 as well as OASIS SMP
  version 2.0 specifications)
* _equal()_ and _hashCode()_ methods to the both the interfaces (to stress these must be implemented) and implementation
  classes

### Changed
* Split meta-data classes into interfaces and implementation classes.

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

