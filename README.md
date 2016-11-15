Celestial pole motion
=====================
This is a wee graphical application to **visualise the movement of the celestial pole** (J2000.0) with respect to the ICRS due to the precession and nutation of the Earth. There is a **basic GUI** which lets you switch between **north** and **south** pole and lets you control the time speed. The program uses the IAU2000 equations for the two sets of angles and yields good results for at least a span of 6000 years. It also features the latitude lines and a background with some of the most important Hipparcos stars.

[Project page](http://tonisagrista.com/projects/celestial-pole/)

##Download
You may get a downloadable package that runs on _Windows_ and _Linux_ in http://tonisagrista.com/page/celestial-pole-motion. 

##Run
In order to run this, compile and execute the Main class. Remember to add the libraries jvm argument, depending on you OS flavour:

>-Djava.library.path=native/linux

or
>-Djava.library.path=native/windows
