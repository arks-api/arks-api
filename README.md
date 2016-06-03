# ARKS

#### Content Based Search Add-on API Implemented for Hadoop Ecosystem :mag:

---


## Table of Contents

- [Introduction](#introduction)
- [Documentation](#documentation)
- [Conributions](#contribution)
- [License](#license)



## Introduction

Unstructured data like doc, pdf, ePub is lengthy to search and filter for desired information. We need to go through every file manually for finding information. It is very time consuming and frustrating. It doesnt need to be done this way if we can use high computing power to achieve much faster content retrieval.

We can use features of big data management system like Hadoop to organize unstructured data dynamically and return desired information. Hadoop provides features like Map Reduce, HDFS, HBase to filter data as per user input. Finally we can
develop Hadoop Addon for content search and filtering on unstructured data. This
addon will be able to provide APIs for different search results and able to download full file, part of files which are actually related to that topic.
This Addon can be used by other industries and government authorities to use Hadoop
for their data retrieval as per their requirement.

Current Systems Focus on Search by Title, Author, etc. Which Is time consuming and
finding relevant content from those documents is tedious task. So there is a need of such a system which shall find the relevant con tents to the end user.

Here objective is to find the relevant content from the huge number of PDF files present on Hadoop Distributed File System (HDFS)


## Documentation

Refer [doc](https://github.com/arks-api/arks-api/tree/master/doc) for detail documentation


## Architecture

![Architecture Diagram](https://raw.githubusercontent.com/arks-api/arks-api/master/doc/assignments/final-report-new-format/cbsa-layer-architecture.png)


## Data Flow

![Data Flow Diagram](https://raw.githubusercontent.com/arks-api/arks-api/master/doc/assignments/final-report-new-format/data_flow.png)


## Contribution
If you want to contribute, please read the [contribution guidelines](contributing.md).


## License

[Apache License - Version 2.0, January 2004](https://github.com/arks-api/arks-api/blob/master/LICENSE.txt)
