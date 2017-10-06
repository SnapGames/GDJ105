# GDJ105

[![TravisCI](https://travis-ci.org/SnapGames/GDJ105.svg?branch=develop)](https://travis-ci.org/SnapGames/GDJ105 "open the TravisCI compilation trend") [![Dependency Status](https://www.versioneye.com/user/projects/59d22ffc0fb24f00420314b1/badge.svg?style=flat-square)](https://www.versioneye.com/user/projects/59d22ffc0fb24f00420314b1 "Open on VersionEye")

## Game Development Java Basics 105

This project is part of the [gdj105](https://classroom.google.com/c/NzI2ODQ3NjU2MFpa/t/NzI2Nzg0MjgxNFpa) course from [GameDev Basics
Java](https://classroom.google.com/c/NzI2ODQ3NjU2MFpa "Open the official on-line course") 
provided by the [SnapGames](http://snapgames.fr) site. 

### Goal

As we have got now some great piece of game code, we need to start buildig a real framework by refactoring the code and adding some new objects.
Graphical objects to create some new States : 
- Let's create `TextObject`, `BackgroundObject` to build `TitleState`, 
- and to enhance the `PlayState`, the `JaugeObject` and `ItemContainerObject` will be used to display HUD. 
- But also adds some important concept like collision system with `QuadTree`, a resource manager, and the main entities like `Player`, `Enemy`, `Eatable`,  etc...
- And add a `CameraObject` to manage viewport target following. 

## Compile

To compile the full project, please execute the following command :

    $> mvn clean install


## Execute

to execute the the compiled jar, please execute the command bellow :

    $> mvn exec:java

or :

    $> java -jar gdj105-0.0.1-SNAPSHOT.jar

## Edit

Import this project as an Existing Maven Project into your prefered IDE, 
(like [Eclipse](http://www.eclipse.org/downloads "open the eclipse official web download page") ?)


## Some screen shots ?

### Title State

Here are some screenshots from the Title screen from the `TitleState`:

![TitleState without debug information](src/main/docs/images/screenshot 81537552334024.jpg "TitleState without debug information") ![TitleState with to much debug information](src/main/docs/images/screenshot 81534842800074.jpg "TitleState with to much debug information")

- Just press the <kbd>SPACE</kbd>/<kbd>ENTER</kbd> to start the demo,
- <kbd>D</kbd> / <kbd>F9</kbd> switch between DEBUG modes 


**Debug display mode**

| Mode  | Description                                                                                        |
|:-----:|:---------------------------------------------------------------------------------------------------|
| **1**	| Only display yellow squares with a numeric id, and display FPS                                     |
| **2**	| Display yellow squares and highlight the moving direction with a green line on the direction side, |
| **3**	| Show full of information in a panel for each active object (default).                              |

 
### Play State

The `PlayState` is only a capabilities demonstration purpose state.

![PlayState with minimum debug info](src/main/docs/images/screenshot 81542505497219.jpg "PlayState with minimum debug info") ![PlayState without any debug info](src/main/docs/images/screenshot 81547820149296.jpg "PlayState without any debug info");

Use the following keys:

- <kbd>D</kbd> / <kbd>F9</kbd> switch between DEBUG modes (see previous table)
    
- <kbd>H</kbd> display a help panel to show keyboard shortcuts :)    
- <kbd>UP</kbd>, <kbd>DOWN</kbd>, <kbd>LEFT</kbd>, <kbd>RIGHT</kbd> to move the blue square which is the `Player` game object,
- <kbd>SHIFT</kbd> / <kbd>CTRL</kbd> with  cursor key will accelerate move,
- <kbd>PG-UP</kbd>, <kbd>PG-DOWN</kbd> will increase decrease number of `Enemy` and `Eatable` game objects (to raise-up your energy), 
- <kbd>SHIFT</kbd> / <kbd>CTRL</kbd> with <kbd>PG-UP<kbd> and <kbd>PG-DOWN</kbd> keys will accelerate increasing.

Have Fun !

Send a mail to [SnapGames](mailto:contact@snapgames.fr?subject=gdj105 "send a mail to your tutor")
