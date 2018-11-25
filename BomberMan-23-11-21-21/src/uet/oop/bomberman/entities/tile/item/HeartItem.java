package uet.oop.bomberman.entities.tile.item;

import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.graphics.Sprite;

public class HeartItem extends Item {

    public HeartItem(int x, int y, int level, Sprite sprite) {
        super(x, y, level, sprite);
    }

//    @Override
//    public boolean collide(Entity e) {
//        // TODO: xử lý Bomber ăn Item
//        if (e instanceof Bomber) {
//            ((Bomber) e).addPowerup(this);
//            remove();
//            return true;
//        }
//        return false;
//    }

    @Override
    public void setValues() {
        _active = true;
        Game.addBomberHeartnew(1);
    }

}
