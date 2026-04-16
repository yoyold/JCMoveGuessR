## JCMoveGuessR

<p align="center">
  <img src="https://i.ibb.co/Pc4CdbP/Screenshot-2024-03-10-120723kl.png">
</p>

<p align="center">
  <a href="https://github.com/yoyold/JCMoveGuessR/blob/main/LICENSE">
    <img src="https://img.shields.io/github/license/yoyold/JCMoveGuessR" alt="License: MIT">
  </a>
  <a href="https://github.com/yoyold/JCMoveGuessR/commits/main">
    <img src="https://img.shields.io/github/last-commit/yoyold/JCMoveGuessR" alt="Last Commit">
  </a>
  <a href="https://github.com/yoyold/JCMoveGuessR/issues">
    <img src="https://img.shields.io/github/issues/yoyold/JCMoveGuessR" alt="Open Issues">
  </a>
  <img src="https://img.shields.io/badge/Java-17-blue?logo=openjdk" alt="Java 17">
  <a href="https://deepsource.io/gh/yoyold/JCMoveGuessR/?ref=repository-badge">
    <img src="https://deepsource.io/gh/yoyold/JCMoveGuessR.svg/?label=active+issues&token=&show_trend=true" alt="DeepSource">
  </a>
</p>

JCMoveGuessR is a survival game where the player is a neutral piece on a chessboard.

It parses a PGN-file and after each move the player must not be on a square that is occupied by a piece. Sounds easy? The crux is that the player does not see a board. Therefore, they have to memorize the current position to make a decision which squares are save to move to next.
