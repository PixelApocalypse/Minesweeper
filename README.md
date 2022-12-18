# Minesweeper
A clone of the Windows Minesweeper game originally released in 1990 written in the Java language using Swing components.

## Overview
Minesweeper is a logic game where mines are hidden in a grid of squares. The object is to open all safe squares in the quickest time possible. The first click will usually reveal a large area with numbers on the edges, these numbers indicate how many mines are surrounding it including diagonally. Mines can be marked with flags and you win by clearing all non-mine squares whilst losing if you click on a mine.

## Features
All classic Minesweeper elements:
 - Left Clicking will reveal a large open space or just reveal one tile based on where you clicked.
 - You can place a flag by Right Clicking on any Tile that is not current uncovered, this will reduce your mine counter by one. Right Clicking again will change it into a Question Mark which will add back the mine the flag had removed. Finally pressing it for a third time removes the Question Mark and returns it to an empty tile.
 - A live game duration display / score counter at the top of the window.
 - A incremental/decremental counter displaying the number of mines left to be marked. This can go below zero and showcases a negative value.
 - A button with a face on it with expressions changing based on what you do; A click will make it panic, clicking a mine will kill it, winning will give it sunglasses, and by default it is just happy.
 - Clicking on the face button restarts the game.
 - Original three preset difficulty levels of **Beginner** (10 Mines), **Intermediate** (40 Mines), and **Expert** (99 Mines) and the ability to set custom grid and mine counts under **Custom** difficulty.
 
New features:
 - New **Experimental** difficulty option which allows you to set mines based on a percentile of the tile count. This can make the game signifcantly harder and more interesting/rewarding. 
 - New "Scale" dropdown menu which allows you to bump up the image size if you struggle seeing the grid in it's default 16x16 px tile format.
 - Flags can have their colors changed by pressing the middle mouse button on a placed flag! This has no real practical application but it took nearly no time to implement and is there if you choose to use it! 
 - Similar to the original but not quite the same; clicking on a number after having marked all the mines will reveal non-mine squares.
 - Our window will semi-intelligently detect the overall size of the grid and if it is too large then making the window take up all the space it can on screen and also add scroll bars vertically and horizontally.
