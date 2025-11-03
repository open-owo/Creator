package com.xiyue.creator.Datagen.Provider;

import com.xiyue.creator.Creator;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;

public class MySoundProver extends SoundDefinitionsProvider {
    protected MySoundProver(PackOutput output, ExistingFileHelper helper) {
        super(output, Creator.MODID, helper);
    }

    @Override
    public void registerSounds() {
    }
}
