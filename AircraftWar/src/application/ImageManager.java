package application;

import aircraft.*;
import bullet.*;
import item.*;

import application.difficulty.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ImageManager {

	private static final Map<String, BufferedImage> CLASSNAME_IMAGE_MAP = new HashMap<>();

	public static BufferedImage BACKGROUND_IMAGE;

	public static BufferedImage MILD_IMAGE;
	public static BufferedImage MODERATE_IMAGE;
	public static BufferedImage SEVERE_IMAGE;

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

			MILD_IMAGE = ImageIO.read(new FileInputStream("AircraftWar/resources/images/bg.jpg"));
			MODERATE_IMAGE = ImageIO.read(new FileInputStream("AircraftWar/resources/images/bg2.jpg"));
			SEVERE_IMAGE = ImageIO.read(new FileInputStream("AircraftWar/resources/images/bg3.jpg"));

			HERO_IMAGE = ImageIO.read(new FileInputStream("AircraftWar/resources/images/hero.png"));
			HERO_BULLET_IMAGE = ImageIO.read(new FileInputStream("AircraftWar/resources/images/bullet_hero.png"));
			ENEMY_BULLET_IMAGE = ImageIO.read(new FileInputStream("AircraftWar/resources/images/bullet_enemy.png"));

			MOB_ENEMY_IMAGE = ImageIO.read(new FileInputStream("AircraftWar/resources/images/mob.png"));
			ELITE_ENEMY_IMAGE = ImageIO.read(new FileInputStream("AircraftWar/resources/images/elite.png"));
			BOSS_ENEMY_IMAGE = ImageIO.read(new FileInputStream("AircraftWar/resources/images/boss.png"));

			BLOOD_ITEM_IMAGE = ImageIO.read(new FileInputStream("AircraftWar/resources/images/prop_blood.png"));
			BOMB_ITEM_IMAGE = ImageIO.read(new FileInputStream("AircraftWar/resources/images/prop_bomb.png"));
			BULLET_ITEM_IMAGE = ImageIO.read(new FileInputStream("AircraftWar/resources/images/prop_bullet.png"));

			CLASSNAME_IMAGE_MAP.put(Moderate.class.getName(), MODERATE_IMAGE);
			CLASSNAME_IMAGE_MAP.put(Mild.class.getName(), MILD_IMAGE);
			CLASSNAME_IMAGE_MAP.put(Severe.class.getName(), SEVERE_IMAGE);

			CLASSNAME_IMAGE_MAP.put(HeroAircraft.class.getName(), HERO_IMAGE);

			CLASSNAME_IMAGE_MAP.put(TrivialEnemy.class.getName(), MOB_ENEMY_IMAGE);
			CLASSNAME_IMAGE_MAP.put(EliteEnemy.class.getName(), ELITE_ENEMY_IMAGE);
			CLASSNAME_IMAGE_MAP.put(BossEnemy.class.getName(), BOSS_ENEMY_IMAGE);

			CLASSNAME_IMAGE_MAP.put(HeroBullet.class.getName(), HERO_BULLET_IMAGE);
			CLASSNAME_IMAGE_MAP.put(EnemyBullet.class.getName(), ENEMY_BULLET_IMAGE);

			CLASSNAME_IMAGE_MAP.put(BloodItem.class.getName(), BLOOD_ITEM_IMAGE);
			CLASSNAME_IMAGE_MAP.put(BombItem.class.getName(), BOMB_ITEM_IMAGE);
			CLASSNAME_IMAGE_MAP.put(BulletItem.class.getName(), BULLET_ITEM_IMAGE);

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
