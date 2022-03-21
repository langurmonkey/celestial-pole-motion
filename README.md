Celestial pole motion
=====================
This is a wee graphical application to **visualise the movement of the celestial pole** (J2000.0) with respect to the ICRS due to the precession and nutation of the Earth. There is a **basic GUI** which lets you switch between **north** and **south** pole and lets you control the time speed. The program uses the IAU2000 equations for the two sets of angles and yields good results for at least a span of 6000 years. It also features the latitude lines and a background with some of the most important Hipparcos stars.

[Project page](http://tonisagrista.com/project/celestial-pole/)


## Run from code

**WARNING:** The project won't run with some modern versions of the JVM because it uses old libraries. We recommend using `AdoptOpenJDK 8` available from the [Adoptium website](https://adoptium.net/archive.html?variant=openjdk8&jvmVariant=hotspot).

In order to run the application, first clone the repository.

```
$ git clone https://github.com/langurmonkey/celestial-pole-motion.git
$ cd celestial-pole-motion
```

To run the application on **Linux**, execute the following command:

```
$ gradlew run
```

To run the application on **Windows** systems, run the following:

```
$ gradlew runWindows
```
## Project page
The project page is https://tonisagrista.com/projects/celestial-pole/.

## Download package
You may get a downloadable package that runs on _Windows_ and _Linux_ in http://tonisagrista.com/project/celestial-pole. 

## Licensing
This software is distributed under the [GPLv3](https://www.gnu.org/licenses/quick-guide-gplv3.html) license.
