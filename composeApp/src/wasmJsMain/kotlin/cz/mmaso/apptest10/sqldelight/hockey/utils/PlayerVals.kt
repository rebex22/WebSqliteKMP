package cz.mmaso.apptest10.sqldelight.hockey.aux

interface PlayerVals {
  enum class Shoots {
    RIGHT,
    LEFT,
  }

  enum class Position {
    LEFT_WING,
    RIGHT_WING,
    CENTER,
    DEFENSE,
    GOALIE,
  }
}
