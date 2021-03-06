package com.sonicjumper.enhancedvisuals.handlers;

import java.awt.Color;
import java.util.Iterator;

import javax.annotation.Nullable;

import com.sonicjumper.enhancedvisuals.VisualManager;
import com.sonicjumper.enhancedvisuals.visuals.Visual;
import com.sonicjumper.enhancedvisuals.visuals.types.VisualCategory;
import com.sonicjumper.enhancedvisuals.visuals.types.VisualType;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.config.Configuration;

public class SplashHandler extends VisualHandler {

	public SplashHandler() {
		super("splash", "heart beat & splash blur effect");
	}
	
	public int splashMinDuration = 10;
	public int splashMaxDuration = 10;
	public float splashMinIntensity = 10.0F;
	public float splashMaxIntensity = 5.0F;
	
	public static int waterSubstractFactor = 10;
	
	
	@Override
	public void initConfig(Configuration config) {
		super.initConfig(config);
		splashMinDuration = config.getInt("splashMinDuration", name, splashMinDuration, 0, 10000, "minimum splash duration");
		splashMaxDuration = config.getInt("splashMaxDuration", name, splashMaxDuration, 0, 10000, "maximum additional splash duration");
		splashMinIntensity = config.getFloat("splashMinIntensity", name, splashMinIntensity, 0, 10000, "minimum splash intensity");
		splashMaxIntensity = config.getFloat("splashMaxIntensity", name, splashMaxIntensity, 0, 10000, "maximum additional splash intensity");
		
		waterSubstractFactor = config.getInt("waterSubstractFactor", name, waterSubstractFactor, 0, 100000, "increased fade out factor in water");
	}
	
	public boolean wasInWater = false;
	
	@Override
	public void onTick(@Nullable EntityPlayer player)
	{
		if(player != null)
		{
			boolean isInWater = player.isInsideOfMaterial(Material.WATER);
			if(isInWater != wasInWater)
				VisualManager.addVisualWithShading(VisualType.blur, (float) (splashMinIntensity + Math.random() * splashMaxIntensity), splashMinDuration, splashMaxDuration, Color.WHITE);
			
			if(isInWater)
			{
				for (Iterator<VisualCategory> iterator = VisualManager.visuals.getKeys().iterator(); iterator.hasNext();) {
					VisualCategory category = iterator.next();
					for (Iterator<Visual> iterator2 = VisualManager.visuals.getValues(category).iterator(); iterator2.hasNext();) {
						Visual visual = iterator2.next();
						if(visual.type.isAffectedByWater)
						{
							for (int i = 0; i < waterSubstractFactor; i++) {
								visual.onTick(player);
							}
						}
					}
				}
			}
			wasInWater = isInWater;
		}
	}
}
