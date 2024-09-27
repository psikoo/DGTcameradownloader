To start the program run DGTCstart.cmd

Photo tab:
Add a camera url in the blue box
Start > start downloading photos (C:\ProgramData\dgtcd\assets\photos)
Stop > stops downloading photos
Reload > reloads the table ui (to see new photos in the list)
Folder > opens the folder where the photos are stored

Video tab:
Add a camera url in the blue box
Video > create a video (C:\ProgramData\dgtcd\assets\videos)
Duplicate > deletes duplicate photos based on MD5 hash
Reload > reloads the table ui (to see new photos in the list)
Folder > opens the folder where the photos are stored

Video requirements:
To use the video feature you need ffmpeg, download it from here (https://github.com/GyanD/codexffmpeg/releases/tag/2024-09-26-git-f43916e217 (full_build)).
After downloading it extract it and copy ffmpeg.exe from the /bin directry to /DGTCD/lib/ffmpeg

Launch parameters:
-delay 180 //delay between photo downloads in seconds
-camera https://infocar.dgt.es/etraffic/data/camaras/598.jpg //start the program with a camera connected

download ffmpeg and add add the bins to lib/ffmpeg (no path needed)
https://github.com/GyanD/codexffmpeg/releases/tag/2024-09-26-git-f43916e217 (full_build)