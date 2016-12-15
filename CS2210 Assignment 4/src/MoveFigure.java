import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;

public class MoveFigure {

  private Gui environment;

  /* =========================== */
    public MoveFigure(Gui env) {
  /* =========================== */
      environment = env;
  }


  /* ============================================ */
    public int moveLeft(Figure[] figs, int numFigures, int fignum, int step)
  /* ============================================ */
  /* Move the figure specified by the second argument to the left. 
     Return true if the figure could move. */
  {
      Position curr, next;
      int newx;

      curr = figs[fignum].getOffset();
      if (curr.getX() < step) return -1;
      else newx = curr.getX() - step;
      next = new Position(newx,curr.getY());

      figs[fignum].setOffset(next);
      for (int i = 0; i < numFigures; ++i) 
	  if ((i != fignum) && (figs[fignum].intersects(figs[i]))) {
	      figs[fignum].setOffset(curr);
	      return i;
	  }

      figs[fignum].setOffset(curr);
      environment.eraseFigure(figs[fignum]);
      figs[fignum].setOffset(next);
      environment.drawFigure(figs[fignum]);
      return -2;
  }


  /* ============================================ */
    public int moveRight(Figure[] figs, int numFigures, int fignum, int step)
  /* ============================================ */
  /* Move the figure specified by the secpnd argument t the right.
     Return true if the figure could move. */
  {
      Position curr, next;
      int newx;

      curr = figs[fignum].getOffset();
      if (curr.getX() > 
	  (environment.displayWidth()-figs[fignum].getWidth()-step))
	  return -1;
      else newx = curr.getX() + step;
      next = new Position(newx,curr.getY());

      figs[fignum].setOffset(next);
      for (int i = 0; i < numFigures; ++i)
	  if ((i != fignum) && (figs[fignum].intersects(figs[i]))) {
	      figs[fignum].setOffset(curr);
	      return i;
	      }

      figs[fignum].setOffset(curr);
      environment.eraseFigure(figs[fignum]);
      figs[fignum].setOffset(next);
      environment.drawFigure(figs[fignum]);
      return -2;
  }


  /* ============================================ */
    public int moveDown(Figure[] figs, int numFigures, int fignum, int step)
  /* ============================================ */
  /* Move the figure specified by the secpnd argument t the right.
     Return true if the figure could move. */
  {
      Position curr, next;
      int newy;

      curr = figs[fignum].getOffset();
      if (curr.getY() > 
	  (environment.displayHeight()-figs[fignum].getHeight()-step))
	  return -1;
      else newy = curr.getY() + step;
      next = new Position(curr.getX(),newy);

      figs[fignum].setOffset(next);
      for (int i = 0; i < numFigures; ++i)
	  if ((i != fignum) && (figs[fignum].intersects(figs[i]))) {
	      figs[fignum].setOffset(curr);
	      return i;
	  }

      figs[fignum].setOffset(curr);
      environment.eraseFigure(figs[fignum]);
      figs[fignum].setOffset(next);
      environment.drawFigure(figs[fignum]);
      return -2;
  }


  /* ============================================ */
    public int moveUp(Figure[] figs, int numFigures, int fignum, int step)
  /* ============================================ */
  /* Move the figure specified by the secpnd argument t the right.
     Return 
        -1 if figure has touched border of window
        -2 if figure can be moved
        i  if figure intersects figure i                              */
  {
      Position curr, next;
      int newy;

      curr = figs[fignum].getOffset();
      if (curr.getY() < step) return -1;
      else newy = curr.getY() - step;
      next = new Position(curr.getX(),newy);

      figs[fignum].setOffset(next);
      for (int i = 0; i < numFigures; ++i)
	  if ((i != fignum) && (figs[fignum].intersects(figs[i]))) {
	      figs[fignum].setOffset(curr);
	      return i;
	  }

      figs[fignum].setOffset(curr);
      environment.eraseFigure(figs[fignum]);
      figs[fignum].setOffset(next);
      environment.drawFigure(figs[fignum]);
      return -2;
  }
}