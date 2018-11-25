package uet.oop.bomberman.entities.tile;

import developGame.Audios;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.graphics.Sprite;

public class Portal extends Tile {
        // âm thanh khi qua cửa.
        Audios soundPassgame = new Audios("passgame.wav", false); 
        protected Board _board;
    
	public Portal(int x, int y, Board _board, Sprite sprite) {
		super(x, y, sprite);
                this._board = _board; 
	}
	
	@Override
	public boolean collide(Entity e) {
		// TODO: xử lý khi Bomber đi vào
                // đã sửa. 
                if(e instanceof  Bomber) {
			
			if(_board.detectNoEnemies() == false)
				return false;
                        try{
                            if (_board.getNumLevel() < 5){
                                soundPassgame.playAgain();
                                _board.nextLevel();
                            }else{
                                Board.checkWinGame = true; 
                                System.out.println("Victory");
                            }
                        }catch(Exception ex){
                            Board.checkWinGame = true; 
                            System.out.println("Victory");
                        }
			
			return true;
		}
		
		return false;
	}

}
