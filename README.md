
# DGT camera downloader

DGT camera downloader is a java swing application that allows the user to systematically download images from a URL (in this case, the intended use is to download images from https://infocar.dgt.es/etraffic/data/camaras/{ID}.jpg).

Additionally, the application has the ability to create a timelapse with the images downloaded. To use this feature, FFmpeg (full_build) must be downloaded separately.

Known issues that will not be fixed, there is a hard coded limit of 10000 photos per camera when deleting duplicates. You do not need more than 10000 photos let's be honest.


## Build the app yourself

**Build information:**

This project uses Maven as its build tool for Java. To build from source  you must install Maven.

**Build steps:**

- Download the code as a [zip](https://github.com/psikoo/DGTcameradownloader/archive/refs/heads/main.zip), or clone the repository from its [git](https://github.com/psikoo/DGTcameradownloader.git).

- Run this command from the main project directory (DGTcameradownloader-main or DGTcameradownloader depending on your download method):

Build compile .class, create project information site, compile jar, compile jar with dependencies, run mavenInstall.cmd:

```bash
mvn clean validate compile site assembly:assembly -DdescriptorId=jar-with-dependencies exec:exec
```

Build compile .class, compile jar, compile jar with dependencies:
```bash
mvn clean validate compile assembly:assembly -DdescriptorId=jar-with-dependencies
```

- mavenInstall.cmd:
mavenInstall.cmd is a utility script, its main purpose is to create a folder in the user's desktop named "DGTCD" and copy the compiled .jar with dependencies, the README and the DGTCDstart.cmd script into it. This allows for a smother user experience when building this project, as all you would need to run the app is inside this new folder. This script is run automatically when building the project (to stop it from running, remove the "exec:exec" argument from the build command). Additionally, the script also starts the app to see if it's working.
## Running the app

Once you have built the app or downloaded it from the [releases](https://github.com/psikoo/DGTcameradownloader/releases/tag/release), you can simply run the app by running DGTCDstart.cmd. This simple script runs the following command: 

```bash
java -jar dgtcd.jar -camera https://infocar.dgt.es/etraffic/data/camaras/598.jpg -delay 180
```
DGTCDstart.cmd can easily be edited to suit your needs, information about the comand line arguments can be found below.

**IMPORTANT!**
**Video requirements:**

In order to be able to use the video feature you will need [FFmpeg (full_build)](https://github.com/GyanD/codexffmpeg/releases/tag/2024-09-26-git-f43916e217). After downloading it extract it and copy ffmpeg.exe (located in the /bin directry) to DGTCD/lib/ffmpeg.
## Using the application:

**Photo tab:**

A camera url needs to be added to the blue text field.
- Start > start downloading photos (C:\ProgramData\dgtcd\assets\photos)
- Stop > stops downloading photos
- Reload > reloads the table ui (to see new photos in the list)
- Folder > opens the folder where the photos are stored

**Video tab:**

A camera url needs to be added to the blue text field.
- Video > create a video (C:\ProgramData\dgtcd\assets\videos)
- Duplicate > deletes duplicate photos based on MD5 hash
- Reload > reloads the table ui (to see new photos in the list)
- Folder > opens the folder where the photos are stored

**Launch parameters:**

- `-delay {seconds}` | Delay between photo downloads in seconds, example `-delay 180`.

- `-camera {cameraURL}` | Start the program with a camera connected, example `-camera https://infocar.dgt.es/etraffic/data/camaras/598.jpg`.
