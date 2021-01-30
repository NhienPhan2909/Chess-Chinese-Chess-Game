/*
 * An enumeration of all xiangqi pieces
 * @author: Nhien Phan
 */
public enum XiangQiPieceType {
  
  X("Xiangqi king"),
  G("Guard"),
  E("Elephant"),
  H("Horse"),
  R("Rook"),
  C("Cannon"),
  S("Soldier");
  
  public final String name;
  
  XiangQiPieceType(String name) {
    this.name = name;
  }
}
