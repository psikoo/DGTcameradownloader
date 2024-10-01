
# DGT camera downloader

DGT camera downloader is a java swing application that allows the user to systematically download images from a URL (in this case, the intended use is to download images from https://infocar.dgt.es/etraffic/data/camaras/{ID}.jpg).

Additionally, the application has the ability to create a timelapse with the images downloaded. To use this feature, FFmpeg (full_build) must be downloaded separately.

Known issues that will not be fixed, there is a hard coded limit of 10000 photos per camera when deleting duplicates. You do not need more than 10000 let's be honest.


## Build the app yourself

#### Build information:
This project uses Maven as its build tool for Java. To build from source,  you must install Maven.

#### Build steps:

- Download the code as a [zip](https://github.com/psikoo/DGTcameradownloader/archive/refs/heads/main.zip), or clone the repository from its [git](https://github.com/psikoo/DGTcameradownloader.git).

- Run this command from the main project directory (DGTcameradownloader-main or DGTcameradownloader depending on your download method):

Build compile .class, create project information site, compile jar, compile jar with dependencies, run maven.cmd:
```bash
mvn clean validate compile site assembly:assembly -DdescriptorId=jar-with-dependencies exec:exec
```
Build compile .class, compile jar, compile jar with dependencies:
```bash
mvn clean validate compile assembly:assembly -DdescriptorId=jar-with-dependencies
```
