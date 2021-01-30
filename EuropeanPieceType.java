/*
 * An enumeration of all chess pieces
 * @author: Nhien Phan
 */
public enum EuropeanPieceType {
  
  P("Pawn"),
  R("Rook"),
  N("Knight"),
  B("Bishop"),
  Q("Queen"),
  K("King");
  
  public final String name;
  
  EuropeanPieceType(String name) {
    this.name = name;
  }
}
