/*
 * SimpleBoard.java
 *
 * Created on December 5, 2003, 6:26 PM
 */

/**
 *
 * @author  Chen
 */
import java.util.*;
public class SimpleBoard{
    public int[][] loc;
    public  int next_player;
    public int[] cols;
    public int m_x=0;
    public int m_y=0;
    public int winner = 0;
    public int player1 = 0;
    public static int red=0;
    public static int green=0;
    public int player2 = 0;
    public boolean out = true;
    public String movelist;
    Run ru=new Run();
    /** Creates a new instance of SimpleBoard */
    public SimpleBoard(){
       next_player = 1;
       loc = new int[6][7];
       cols = new int[7];
       winner=0;
       player1=0;
       player2=0;
       clear();
       movelist="";
       out = true;
    }
    
    public void ParseMove(String move_list){
        for (int i=0;i<move_list.length();i++) {
          // int tm = Integer.parseInt((new Character(move_list.charAt(i))).toString());
          int tm= (int) (Math.random()*7);
           Move(tm);
        }
        
    }
    public void Move(int pos) {
    	try{
    	
        if ( (pos<0) || (pos >6) || pos = null)
            System.out.println("invalid input\n\n");
        else{
            if ((cols[pos]==6)&& out ){
              //  System.out.println("Column full");
                boolean out=false;}
            else{
            m_y=pos;
            movelist+=(new Integer(pos)).toString();
            m_x= 5-cols[pos];
            cols[pos]++; 
            loc[m_x][m_y] = next_player;
           // System.out.println("test"+next_player);
            next_player = 3-next_player;
            }
        }}
    	catch(Exception e)
    	{
    	//	ru.game();
    		System.out.println("error");
    	}
    	
    
        
    }
    
    public int[][] view() {
        return loc;
    }
    public int[] viewcol(){
        return cols;
    }
    public void clear() {
        for (int i = 0; i< 6;i++)
            for (int j = 0; j<7; j++)
            {
                loc[i][j]=0;
            }
        for (int j = 0 ; j < 7; j++) cols[j] = 0;        
    }
    
    public int next() {return next_player;}
    public int[] ret_col(){return cols;}
    public boolean over() {
        String line_x="";
        String line_y="";
        String line_ld=(new Integer(loc[m_x][m_y])).toString();
        String line_rd=(new Integer(loc[m_x][m_y])).toString();
        String s = (new Integer(3-next_player)).toString();
        String sub = s+s+s+s;
        String match ="[012]*"+sub+"[012]*";
        for (int i=0; i<7; i++){
            int cell = loc[m_x][i];
            line_x+= (new Integer(cell)).toString();
        }
        for (int i=0; i<6; i++){
            int cell = loc[i][m_y];
            line_y+= (new Integer(cell)).toString();
        }
        
        int tempx=m_x;
        int tempy=m_y;
        while ( (tempx>0) && (tempy>0)){
            tempx--;tempy--;
            line_ld = (new Integer(loc[tempx][tempy])).toString() + line_ld;
        }
        
        tempx=m_x;tempy=m_y;
        while ( (tempx <5)&& (tempy <6)) {
            tempx++;tempy++;
            line_ld = line_ld+(new Integer(loc[tempx][tempy])).toString();
        }
        
        tempx=m_x;tempy=m_y;
        while ( (tempx>0) && (tempy<6)){
            tempx--;tempy++;
            line_rd = (new Integer(loc[tempx][tempy])).toString() + line_rd;
        }
        
        tempx=m_x;tempy=m_y;
        while ( (tempx <5)&& (tempy >0)) {
            tempx++;tempy--;
            line_rd = line_rd+(new Integer(loc[tempx][tempy])).toString();
        }
        
       /* System.out.println("paal"+line_x);
        System.out.println(line_y);
        System.out.println(line_ld);
        System.out.println(line_rd);
        System.out.println(sub);*/
        
        if  ( (line_x.matches(match)) ||
            (line_y.matches(match)) ||
            (line_ld.matches(match)) ||
            (line_rd.matches(match)) )
            {
              winner = 3 - next_player;
             // if (out){
            	//  System.out.println("Red playerx "+player1+""+" Green player "+player2);}

              
              if(winner==1)
              {
            	  player1=player1+1;
              }
              if(winner==2)
              {
            	  player2=player2+1;
              }
            //  System.out.println("Red playerx "+player1+""+" Green player "+player2);
              
            // return true;
            } 
        
       
        
        int z=0;
        for (int i=0; i<6; i++)
        {
            for (int j=0; j<7; j++)
            {     if (loc[i][j] == 0)  {z = 1;}}
        
        }
            
        if (z == 0)
        {
        	   for (int i = 0; i < 6; i++) {
        	        for (int j = 0; j < 7; j++) {
        	        	//System.out.println("x---------------------"+loc[i][j]);
        	            }
        	        }
        	//System.out.println("x---------------------"+loc[5][0]);
        	checkPlayerScore(loc);
        	//ru.gameOver();
        	//System.out.println("Red playerx "+player1+""+" Green player "+player2);
            return true;
        }
      
        return false;
    }
    
    private static void checkPlayerScore(int[][] loc) {
		int ii,  ri, ci, di;
        String checkGrid[] = new String[25];

        // copy rows:
        for(ri = 0; ri < 6; ri++) {
          String temp = "";
          for(ci = 0; ci < 7; ci++) {
            temp += loc[ri][ci];
          }
          checkGrid[ri] = temp;
        }
        // copy columns:
        for(ci = 0; ci < 7; ci++) {
          String temp = "";
          for(ri = 0; ri < 6; ri++) {
            temp += loc[ri][ci];
          }
          checkGrid[ci + 6] = temp;
        }
        // copy first diagonals:
        for(di = 0; di < 6; di++) {
          String temp = "";
          for(ri = 0; ri < 6; ri++) {
            ci = di - 2;
            ri = 0;
            while(ci < 0) {
              ri++;
              ci++;
            }
            for(; ri < 6; ri++, ci++) {
              if( ci > 6 ) continue;
              temp += loc[ri][ci];
            }
          }
          checkGrid[di+13] = temp;
        }
        // diagonals in the other direction:
        for(di = 0; di < 6; di++) {
          String temp = "";
          for(ri = 0; ri < 6; ri++) {
            ci = 8 - di;
            ri = 0;
            while(ci >  6) {
              ri++;
              ci--;
            }
            for(; ri < 6; ri++, ci--) {
              if( ci < 0 ) continue;
              temp += loc[ri][ci];
            }
          }
          checkGrid[di+19] = temp;
        }
        for(ii = 0; ii < 25; ii++) {
          System.out.println("Checking '" + checkGrid[ii] + "'");
          if (checkGrid[ii].contains("1111"))
          {
        	  red=red+1; System.out.println("Player A wins!");
          }
          if (checkGrid[ii].contains("11111"))
          {
        	  red=red+1; System.out.println("Player A wins!");
          }
          if (checkGrid[ii].contains("111111"))
          {
        	  red=red+1; System.out.println("Player A wins!");
          }
        	  
        	  
        	 
          if (checkGrid[ii].contains("2222"))
        	  {System.out.println("Player B wins!");
        	 green=green+1;
              if (checkGrid[ii].contains("22222"))
              {
            	  green=green+1;System.out.println("Player B wins!");
              }
              if (checkGrid[ii].contains("222222"))
              {
            	  green=green+1;System.out.println("Player B wins!");
              }
        	  }
        	  }
        System.out.println("Red"+red+"Green"+green);
        if (red>green){
        System.out.println("Red is the Winner");	
        }else if(red<green){
        	System.out.println("Green is the winner");
        }else if (red == green){
        	System.out.println("Draw");
        }
        }
    
   
	
    
   

	

	public String toString(){
        String ret = "   0 1 2 3 4 5 6\n";
        for (int i = 0; i < 6; i++){
            ret += (new Integer(i)).toString()+ ": ";
            for (int j = 0; j< 7; j++){
                ret+=(new Integer(loc[i][j])).toString();
                ret+=" ";
            }
            ret+="\n";
        }
        return ret;
    }
}
