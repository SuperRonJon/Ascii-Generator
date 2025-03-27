# Commands

-i = invert colors, brightest colored pixels have the densest characters rather than darkest

-s val = scaling factor, scales the image's height and width by val before converting to ascii. Important for high resolution images.

-f = outputs to a file instead of to the console. Output filename will be image.png.txt (image.png is whatever original image was named)

-b = removes bottom right border that sometimes occurs on certain image outputs. You'll know if you need it.

-h val = scaling factor for height individually. If specified width must also be specified and both must be greater than 0.

-w val = scaling factor for width individually. If specified height must also be specified and both must be greater than 0.


# Installation/Instructions

Download zip from [latest release here](https://github.com/SuperRonJon/Ascii-Generator/releases/).

You can run the jar itself manually like this

`java -jar ./path/to/ascii-generator.jar -is 0.3 ./path/to/image.png` which will scale image.png down to 30% original resolution and then output the text to the terminal. Scaling down images is important for high resolution images to be properly viewed in the terimanl or text editor.

New releases comes with zip file that also includes .bat and .sh scripts to easier run the jar as its own command from the command line from anywhere, like this `ascii-generator -is 0.3 ./path/to/image.png`. Instructions for this are below if needed. However generally, just unzip the jar and .bat/.sh file (windows/linux+mac respectively) to a folder on the PATH and run ascii-generator via the script from the command line.

Requires Java JRE 17+

## Command Line Installation Instructions

- unzip contents (jar and .bat/.sh file, .bat is for windows) to your home bin folder, `~/bin`, create one if needed. (`C:\Users\<your-user-name>\bin`)
- add that bin folder (`C:\Users\<your-user-name>\bin`) to your PATH. (On windows search->Edit environment variables for your account->User variables -> Path -> Edit... -> New -> `%USERPROFILE%\bin\`)
- On linux you can just put the .sh and .jar files in `/usr/local/bin/` or some other location on your PATH and remove the .sh (`sudo mv ./path/to/ascii-generator.sh /usr/local/bin/ascii-generator`) and make it executable (`sudo chmod +x /usr/local/bin/ascii-generator`)
- Now restart terminal and command should run with `ascii-generator -is 0.2 /path/to/image.png` from anywhere in the terminal.


![WindowsTerminal_oaIjW7fMUd](https://github.com/user-attachments/assets/7b8bb38a-e663-43d6-9183-f80794eac211)
