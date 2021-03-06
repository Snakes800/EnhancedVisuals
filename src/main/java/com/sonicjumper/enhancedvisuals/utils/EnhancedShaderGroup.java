package com.sonicjumper.enhancedvisuals.utils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;
import com.google.gson.JsonSyntaxException;

import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.Shader;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.client.util.JsonException;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class EnhancedShaderGroup extends ShaderGroup {

	public EnhancedShaderGroup(TextureManager p_i1050_1_, IResourceManager resourceManagerIn,
			Framebuffer mainFramebufferIn, ResourceLocation p_i1050_4_)
			throws JsonException, IOException, JsonSyntaxException {
		super(p_i1050_1_, resourceManagerIn, mainFramebufferIn, p_i1050_4_);
	}
	
	private static Field shaders = ReflectionHelper.findField(ShaderGroup.class, "listShaders", "field_148031_d");
	
	public List<Shader> getShaders()
	{
		try {
			return (List<Shader>) shaders.get(this);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			return new ArrayList<>();
		}
	}
}
