 package uet.oop.bomberman;

import developGame.Audios;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.gui.Frame;
import uet.oop.bomberman.input.Keyboard;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import javax.sound.sampled.Clip;


/**
 * Tạo vòng lặp cho game, lưu trữ một vài tham số cấu hình toàn cục,
 * Gọi phương thức render(), update() cho tất cả các entity
 */
public class Game extends Canvas {

	public static final int TILES_SIZE = 16,
							WIDTH = TILES_SIZE * (31 / 2),
							HEIGHT = 13 * TILES_SIZE;

	public static int SCALE = 3;
	
	public static final String TITLE = "BombermanGame";
	// sửa số bomb.
	private static final int BOMBRATE = 1;
	private static final int BOMBRADIUS = 1;
	private static final double BOMBERSPEED = 1.0;
	private static final int BOMBERHEART = 3; 
        
	public static final int TIME = 200;
	public static final int POINTS = 0;
	
	protected static int SCREENDELAY = 3;

	protected static int bombRate = BOMBRATE;
	protected static int bombRadius = BOMBRADIUS;
	protected static double bomberSpeed = BOMBERSPEED;
	protected static int bomberHeart = BOMBERHEART;
	
	protected int _screenDelay = SCREENDELAY;
	
	private Keyboard _input;
	private boolean _running = false;
	private boolean _paused = true;
	
	private Board _board;
	private Screen screen;
	private Frame _frame;
	
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

        //public int _live;
        // thuộc tính âm thanh. 
        public Audios ostGame = new Audios("Level1.wav", true); 
        
        
	public Game(Frame frame) {
		_frame = frame;
		_frame.setTitle(TITLE);
		
		screen = new Screen(WIDTH, HEIGHT);
		_input = new Keyboard();
		
		_board = new Board(this, _input, screen);
                //_live=3;
		addKeyListener(_input);
                
	}
	
	
	private void renderGame() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		screen.clear();
		
		_board.render(screen);
		
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = screen._pixels[i];
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		_board.renderMessages(g);
		
		g.dispose();
		bs.show();
	}
	
	private void renderScreen() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		screen.clear();
		
		Graphics g = bs.getDrawGraphics();
		
		_board.drawScreen(g);

		g.dispose();
		bs.show();
	}

	private void update() {
		_input.update();
                _frame.setHeartBomber(bomberHeart);
                _frame.setAmountEnemy(_board.getAmountEnemy());
                if(_input.pause && _paused==false)
                {
                    pause();
                    _screenDelay=3;
           
                }
                
                else if(_input.continuee&&_paused)
                {
                    ostGame.MainClip.loop(Clip.LOOP_CONTINUOUSLY);
                    ostGame.Player();
                    _screenDelay=0;                   
                }
                
                if((_input.space && _screenDelay==3 && bomberHeart==0) || (_board.checkWinGame && _input.space && _screenDelay == 3)){
       
                    _board.clearEntity();
                    _board.resetGame();
                   _frame.setHeartBomber(BOMBERHEART);
                   
                }
               
               else if(!_paused)
                   _board.update();
	}
	
	public void start() {
		_running = true;
                
                ostGame.Player();
		
                // the end serfdom. 
		long  lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0; //nanosecond, 60 frames per second
		double delta = 0;
		int frames = 0;
		int updates = 0;
		requestFocus();
                _frame.setHeartBomber(bomberHeart);
		while(_running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1) {
				update();
				updates++;
				delta--;
			}

                        if (_paused) {
                            if (_screenDelay <= 0) {
                                _board.setShow(-1);
                                _paused = false;
                            }
                            // kiem tra xem phát hiện được win game hay chưa. 
                            else if (_screenDelay == 3 && _board.checkWinGame){
                                _board.setShow(4);
                                _paused=true;
                            }
                            // else if này không có tác dụng. 
//                            else if(_screenDelay == 3 && _board.getShow()!=1 && _board.getShow()!=2){
//                              _board.setShow(3);
//                              _paused=true;
//                            }
                            renderScreen();
                        }
                        else 
                            renderGame();

			frames++;
			if(System.currentTimeMillis() - timer > 1000) {
				_frame.setTime(_board.subtractTime());
				_frame.setPoints(_board.getPoints());
				timer += 1000;
				_frame.setTitle(TITLE + " | " + updates + " rate, " + frames + " fps");
				updates = 0;
				frames = 0;
				
				if(_board.getShow() == 2)
					--_screenDelay;
			}
		}
          
	}
	
        public void addBomberHeart(int i){
            bomberHeart += i; 
        }
        
        public static void addBomberHeartnew(int i){
            bomberHeart += i;
        }
        
        public int getBomberHeart() {
            return bomberHeart;
        }

        public void setBomberHeart(int bomberHeart) {
            Game.bomberHeart = bomberHeart;
        }
        
	public static double getBomberSpeed() {
		return bomberSpeed;
	}
	
	public static int getBombRate() {
		return bombRate;
	}
	
	public static int getBombRadius() {
		return bombRadius;
	}
	
	public static void addBomberSpeed(double i) {
		bomberSpeed += i;
	}
	
	public static void addBombRadius(int i) {
		bombRadius += i;
	}
	
	public static void addBombRate(int i) {
		bombRate += i;
	}

	public void resetScreenDelay() {
		_screenDelay = SCREENDELAY;
	}
        
	public Board getBoard() {
		return _board;
	}

	public boolean isPaused() {
		return _paused;
	}
	
	public void pause() {
                // add chức năng tắt nhạc.
                
                ostGame.PauseAudio();
                // ... 
		_paused = true;
                
	}
	
         
}
