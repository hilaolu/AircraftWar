package application;

import aircraft.*;
import bullet.*;
import item.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ImageManager {

	private static final Map<String, BufferedImage> CLASSNAME_IMAGE_MAP = new HashMap<>();

	public static BufferedImage BACKGROUND_IMAGE;
	public static BufferedImage HERO_IMAGE;

	public static BufferedImage HERO_BULLET_IMAGE;
	public static BufferedImage ENEMY_BULLET_IMAGE;

	public static BufferedImage MOB_ENEMY_IMAGE;
	public static BufferedImage ELITE_ENEMY_IMAGE;
	public static BufferedImage BOSS_ENEMY_IMAGE;

	public static BufferedImage BLOOD_ITEM_IMAGE;
	public static BufferedImage BOMB_ITEM_IMAGE;
	public static BufferedImage BULLET_ITEM_IMAGE;

	static {
		try {

			BACKGROUND_IMAGE = ImageIO.read(new FileInputStream("AircraftWar/resources/images/bg.jpg"));

			HERO_IMAGE = ImageIO.read(new FileInputStream("AircraftWar/resources/images/hero.png"));
			HERO_BULLET_IMAGE = ImageIO.read(new FileInputStream("AircraftWar/resources/images/bullet_hero.png"));
			ENEMY_BULLET_IMAGE = ImageIO.read(new FileInputStream("AircraftWar/resources/images/bullet_enemy.png"));

			MOB_ENEMY_IMAGE = ImageIO.read(new FileInputStream("AircraftWar/resources/images/mob.png"));
			ELITE_ENEMY_IMAGE = ImageIO.read(new FileInputStream("AircraftWar/resources/images/elite.png"));
			BOSS_ENEMY_IMAGE = ImageIO.read(new FileInputStream("AircraftWar/resources/images/boss.png"));

			BLOOD_ITEM_IMAGE = ImageIO.read(new FileInputStream("AircraftWar/resources/images/prop_blood.png"));
			BOMB_ITEM_IMAGE = ImageIO.read(new FileInputStream("AircraftWar/resources/images/prop_bomb.png"));
			BULLET_ITEM_IMAGE = ImageIO.read(new FileInputStream("AircraftWar/resources/images/prop_bullet.png"));

			CLASSNAME_IMAGE_MAP.put(HeroAircraft.class.getName(), HERO_IMAGE);

			CLASSNAME_IMAGE_MAP.put(EnemyAircraft.class.getName(), MOB_ENEMY_IMAGE);
			CLASSNAME_IMAGE_MAP.put(EliteAircraft.class.getName(), ELITE_ENEMY_IMAGE);
			CLASSNAME_IMAGE_MAP.put(BossAircraft.class.getName(), BOSS_ENEMY_IMAGE);

			CLASSNAME_IMAGE_MAP.put(HeroBullet.class.getName(), HERO_BULLET_IMAGE);
			CLASSNAME_IMAGE_MAP.put(EnemyBullet.class.getName(), ENEMY_BULLET_IMAGE);

			CLASSNAME_IMAGE_MAP.put(BloodItem.class.getName(), BLOOD_ITEM_IMAGE);
			CLASSNAME_IMAGE_MAP.put(BombItem.class.getName(), BLOOD_ITEM_IMAGE);
			CLASSNAME_IMAGE_MAP.put(BulletItem.class.getName(), BLOOD_ITEM_IMAGE);

		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public static BufferedImage get(String className) {
		return CLASSNAME_IMAGE_MAP.get(className);
	}

	public static BufferedImage get(Object obj) {
		if (obj == null) {
			return null;
		}
		return get(obj.getClass().getName());
	}

}
