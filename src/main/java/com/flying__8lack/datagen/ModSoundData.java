package com.flying__8lack.datagen;

import com.flying__8lack.sound.ModSound;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;

import static com.flying__8lack.advancedmovementmod.MODID;

public class ModSoundData extends SoundDefinitionsProvider {
    protected ModSoundData(PackOutput output, ExistingFileHelper helper) {
        super(output, MODID, helper);
    }

    @Override
    public void registerSounds() {
        this.add(ModSound.HUM_60HZ.value(), SoundDefinition.definition()
                .with(
                        sound(MODID+":60hz_hum")
                                .volume(0.2f)
                                .pitch(1.0f)

                )

        );

    }
}
