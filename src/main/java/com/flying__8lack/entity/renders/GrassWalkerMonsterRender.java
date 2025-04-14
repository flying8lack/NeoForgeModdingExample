package com.flying__8lack.entity.renders;

import com.flying__8lack.entity.custom.GrassWalkerMonsterEntity;
import com.flying__8lack.entity.models.GrassWalkerMonsterModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import static com.flying__8lack.advancedmovementmod.MODID;

public class GrassWalkerMonsterRender
        extends MobRenderer<GrassWalkerMonsterEntity, GrassWalkerMonsterModel<GrassWalkerMonsterEntity>> {

    public GrassWalkerMonsterRender(EntityRendererProvider.Context context) {
        super(context, new GrassWalkerMonsterModel<>(context.bakeLayer(GrassWalkerMonsterModel.LAYER_LOCATION)), 0.75f);
    }

    @Override
    public ResourceLocation getTextureLocation(GrassWalkerMonsterEntity grassWalkerMonsterEntity) {

        return ResourceLocation.fromNamespaceAndPath(MODID, "textures/entity/grass_walker_monster_entity/grass_walker_monster_entity.png");
    }
}
