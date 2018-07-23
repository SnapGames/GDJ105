# GDJ105

[![TravisCI](https://travis-ci.org/SnapGames/GDJ105.svg?branch=develop)](https://travis-ci.org/SnapGames/GDJ105 "open the TravisCI compilation trend")

## Game Development Java Basics 105

This project is part of the [gdj105](https://classroom.google.com/c/NzI2ODQ3NjU2MFpa/t/NzI2Nzg0MjgxNFpa) course from [GameDev Basics
Java](https://classroom.google.com/c/NzI2ODQ3NjU2MFpa "Open the official on-line course") 
provided by the [SnapGames](http://snapgames.fr) site. 

### Goal

As we have got now some great piece of game code, we need to start buildig a real framework by refactoring the code and adding some new objects.
Graphical objects to create some new States : 
- Let's create `TextObject`, `BackgroundObject` to build `TitleState`, 

## Compile

To compile the full project, please execute the following command :

```bash
$> mvn clean install
```

## Execute

to execute the the compiled jar, please execute the command bellow :

```bash
$> mvn exec:java
```

or :

```bash
$> java -jar gdj105-0.0.1-SNAPSHOT.jar
```

## Edit

Import this project as an Existing Maven Project into your prefered IDE, 
(like [Eclipse](http://www.eclipse.org/downloads "open the eclipse official web download page") ?)


## Some screen shots ?

### Title State

Here are some screenshots from the Title screen from the `TitleState`:

![TitleState with to much debug information](src/main/docs/images/gdj105-screenshot-titlestate-debug.jpg "TitleState with to much debug information") ![TitleState without debug information](src/main/docs/images/gdj105-screenshot-titlestate.jpg "TitleState without debug information")

- Just press the <kbd>SPACE</kbd>/<kbd>ENTER</kbd> to start the demo,
- <kbd>D</kbd> / <kbd>F9</kbd> switch between DEBUG modes 


**Debug display mode**

| Mode  | Description                                                                                        |
|:-----:|:---------------------------------------------------------------------------------------------------|
| **1**	| Only display yellow squares with a numeric id, and display FPS                                     |
| **2**	| Display yellow squares and highlight the moving direction with a green line on the direction side, |
| **3**	| Show full of information in a panel for each active object (default).                              |


Have Fun !

Send a mail to [SnapGames](mailto:contact@snapgames.fr?subject=gdj105 "send a mail to your tutor")
