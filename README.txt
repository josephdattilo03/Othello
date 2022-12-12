=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 1200 Game Project README
PennKey: jdatt
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. 2D Arrays - I used a 2D array as the board for Othello. In the game model I created functions to modify the board
  depending on the move made and the player that made the move. I hooked up the gui to detect relative mouse position
  and transfer the coordinates to the backend Array.
  2. File/IO - In my initial proposal, I planned to use file to add custom pieces and board to the JPanel GameBoard.
  I was a bit vague on my original idea, and I ended up changing my implementation. Instead, I created an option to
  save and load the game from a file. I tried to save the board and player turn as an XML object, but ended up just
  making my own 'serialization' method. Additionally, I created custom audio files for when the user plays a turn
  or the game ends.
  3. Collections - In my initial proposal, I thought that implementing an interface would be an interesting part of the
  project, but aside from the gui, I could not think of a way to use this. Instead, I figured that I could use
  collections to hold all the tiles that needed to be flipped on any given turn. When the turn is played, the moves
  are calculated and are used to determine if the move can be played. They are then iterated through to change the
  board to the appropriate game state.
  4. JUnit Testing - Because I created a separate class to control the logic of the game, I made a testing class to
  test if all the mechanics were working properly.

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.
  Othello - The Othello class controls the logic of the game. It contains the backend board, player turn,
  player victory, and a reader and writer to save the state.
  GameBoard - The GameBoard class controls the board graphics, sounds, and the text showing the player turn and victory.
  It extends JPanel, so it can be added to the frame. This class is in charge translating and sending the proper
  coordinates to the Othello class. Additionally, it repaints when a turn is complete to update the pieces on the board.
  Finally, I implemented custom sounds that play as a result of conditions that happen on the board.
  RunOthello - This class extends runnable and controls the GUI of the game. It is additionally responsible for
  initiating the board and status that were added to the frame.

- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?
  I stumbled a little with optimizing the logic of the function responsible for getting the tiles to be flipped.
  The hardest part of the project for me was forgetting one line of code in the repaintGraphics portion of the
  GameBoard. Not adding the super.paintComponent(g) line was a small part of the program that caused tons of artifacts
  to appear on my game when playing it. Because I couldn't figure this out for the longest time, I essentially proofed
  my entire game multiple times before finding the issue.

- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?
  I think that my separation of functionality is pretty good. The Othello class acts as the backend, the GameBoard acts
  as the frontend, and the gui acts as the layout. My encapsulation is ok, but if given the chance I might find a way
  to allow the GUI to call the Othello board without having the ability to then modify the game state.


========================
=: External Resources :=
========================

- Cite any external resources (images, tutorials, etc.) that you may have used 
  while implementing your game.

  https://alvinalexander.com/blog/post/jfc-swing/how-create-simple-swing-html-viewer-browser-java/
  https://www.geeksforgeeks.org/play-audio-file-using-java/