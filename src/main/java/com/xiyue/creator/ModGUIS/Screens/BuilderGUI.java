package com.xiyue.creator.ModGUIS.Screens;

import com.xiyue.creator.Creator;
import com.xiyue.creator.ModGUIS.Button.ItemStackButton;
import com.xiyue.creator.MyRecipe.BuilderRecipe.BuilderInput;
import com.xiyue.creator.MyRecipe.BuilderRecipe.BuilderRecipe;
import com.xiyue.creator.MyRecipe.RegisterRecipe;
import com.xiyue.creator.networking.BuilderBlockNetworking.BuildingPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

import javax.swing.plaf.ListUI;
import java.util.List;

public class BuilderGUI extends Screen {
    private static final Component TITLE = Component.translatable("gui." + Creator.MODID + ".title.builder");

    private final ClientLevel level;

    //格子大小
    private static final int SLOT_SIZE = 16;
    //默认一行9个
    private static final int COLS = 9;

    private int gridStartY;

    public BuilderGUI(ClientLevel level) {
        super(Component.empty());
        this.level = level;
    }

    private static class ListUtils {
        private static void moveToFront(List<RecipeHolder<BuilderRecipe>> list, RecipeHolder<BuilderRecipe> element) {
            if (list == null || list.isEmpty()) return;
            int index = list.indexOf(element);
            if (index > 0) {
                list.remove(index);
                list.addFirst(element);
            }
        }
    }

    @Override
    protected void init() {
        super.init();
        if (level == null) return;

        List<RecipeHolder<BuilderRecipe>> list = level.getRecipeManager().getRecipesFor(RegisterRecipe.BUILDER_TYPE.get(), new BuilderInput(), level);
        if (list.isEmpty()) return;

//        for (int i = 0; i < list.size(); i++) {
//            if (list.get(i).id().getPath().contains("frames")) {
//                ListUtils.moveToFront(list, list.get(i));
//            }
//        }

        int rows = (int) Math.ceil(list.size() / (float)COLS);
        int maxCol = Math.min(list.size(), COLS);
        int StartX = (width - maxCol * SLOT_SIZE) / 2;
        int StartY = (height - rows * SLOT_SIZE) / 2;
        gridStartY = StartY;
        int count = 0;
        for (int row = 0; row < rows; row++) {
            int colsInRow = Math.min(9, list.size() - count);
            for (int col = 0; col < colsInRow; col++) {
                RecipeHolder<BuilderRecipe> recipe = list.get(count);
                ItemStack stack = recipe.value().assemble(new BuilderInput(), level.registryAccess());

                addRenderableWidget(new ItemStackButton(
                        StartX + col * SLOT_SIZE, StartY + row * SLOT_SIZE, SLOT_SIZE, SLOT_SIZE,
                        stack,
                        button -> {
                            if (Minecraft.getInstance().player != null) {
                                PacketDistributor.sendToServer(new BuildingPacket(recipe.id().toString(),true));
                                onClose();
                            }
                        }
                ));
                count++;
            }
        }
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.render(graphics, mouseX, mouseY, partialTick);
    }

    public void renderBackground(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        graphics.fillGradient(0, 0, width, height, 0xC0101010, 0xD0101010);
        graphics.drawCenteredString(this.font, TITLE, width / 2, gridStartY - 20, 0xFFFFFF);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }
}
