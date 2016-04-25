# Scrabble
A network scrabble game that uses JavaFX and sockets.

This new project is a multiplayer scrabble game that works over the network.

It utilizes a turn based system of playing to place your scrabble tiles onto the board to make a word.

Using FTP the game sends the turns through a socketserver connection to communicate with the 2 games.

To test this game on one machine:

While this game can be put in a java jar, it still isn't fully functional so the only place to test is in in Intellij

To start, run ScrabbleApplicationFX twice so you have two of these windows open

![Screenshot](http://i.imgur.com/jxPScN0.png)

Your intellij should look like this

Now click host on the window you want to host and connect with "localhost" in the ip box

The game should be started now!

To play, click on the tile you want to play, then click where you want it on the board and then use the "End turn" button

#Screenshots
![Screenshot](http://i.imgur.com/bmOt6Sr.png)

This is what the screen looks like for a player who's waiting on their opponent

![Screenshot](http://i.imgur.com/g3a80M5.png)

This is what the screen looks like for a player currently placing his tiles

![Screenshot](http://i.imgur.com/juzWEBn.png)

The game uses a local txt file containing words to check if the words you make on the board are real


