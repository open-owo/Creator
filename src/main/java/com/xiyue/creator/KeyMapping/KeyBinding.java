package com.xiyue.creator.KeyMapping;

import com.mojang.blaze3d.platform.InputConstants;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.neoforged.neoforge.common.util.Lazy;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {
    public static final Lazy<net.minecraft.client.KeyMapping> BUILD_KEY = Lazy.of(() -> new net.minecraft.client.KeyMapping("key.creator.build", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_C, "key.categories.creator"));
}
