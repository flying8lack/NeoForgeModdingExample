package com.flying__8lack.entity.models;

import com.flying__8lack.entity.animation.GrassWalkerMonsterAnimation;
import com.flying__8lack.entity.custom.GrassWalkerMonsterEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.monster.Monster;

import static com.flying__8lack.advancedmovementmod.MODID;

public class GrassWalkerMonsterModel<T extends GrassWalkerMonsterEntity> extends HierarchicalModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(MODID, "grass_walker_monster_entity"), "main");
    private final ModelPart root;
    private final ModelPart left_leg;
    private final ModelPart back_left;
    private final ModelPart back_left2;
    private final ModelPart right_leg;
    private final ModelPart body;
    private final ModelPart head;

    public GrassWalkerMonsterModel(ModelPart root) {
        this.root = root.getChild("root");
        this.left_leg = this.root.getChild("left_leg");
        this.back_left = this.root.getChild("back_left");
        this.back_left2 = this.root.getChild("back_left2");
        this.right_leg = this.root.getChild("right_leg");
        this.body = this.root.getChild("body");
        this.head = this.root.getChild("head");

    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(1.0F, 24.0F, 9.0F));

        PartDefinition left_leg = root.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.offset(-13.0F, 0.0F, 0.0F));

        PartDefinition cube_r1 = left_leg.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(38, 25).addBox(-3.0F, -1.0F, -1.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(19.0F, 0.0F, -14.0F, 0.2182F, 0.0F, 0.0F));

        PartDefinition cube_r2 = left_leg.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 44).addBox(-1.0F, -10.0F, -1.0F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(18.0F, -8.0F, -16.0F, -0.4363F, 0.0F, 0.0F));

        PartDefinition cube_r3 = left_leg.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(32, 45).addBox(-2.0F, -9.0F, -1.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(19.0F, 0.0F, -13.0F, 0.3054F, 0.0F, 0.0F));

        PartDefinition back_left = root.addOrReplaceChild("back_left", CubeListBuilder.create(), PartPose.offsetAndRotation(5.0F, -5.8426F, -2.7907F, -0.2182F, 0.0F, 0.0F));

        PartDefinition cube_r4 = back_left.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(38, 35).addBox(-3.0F, -1.0F, -1.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 5.8426F, -0.2093F, 0.2182F, 0.0F, 0.0F));

        PartDefinition cube_r5 = back_left.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(16, 44).addBox(-1.0F, -10.0F, -1.0F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.1574F, -2.2093F, -0.4363F, 0.0F, 0.0F));

        PartDefinition cube_r6 = back_left.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(48, 45).addBox(-2.0F, -9.0F, -1.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 5.8426F, 0.7907F, 0.3054F, 0.0F, 0.0F));

        PartDefinition back_left2 = root.addOrReplaceChild("back_left2", CubeListBuilder.create(), PartPose.offsetAndRotation(-7.0F, -5.8426F, -2.7907F, -0.2182F, 0.0F, 0.0F));

        PartDefinition cube_r7 = back_left2.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(38, 40).addBox(-3.0F, -1.0F, -1.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 5.8426F, -0.2093F, 0.2182F, 0.0F, 0.0F));

        PartDefinition cube_r8 = back_left2.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(24, 44).addBox(-1.0F, -10.0F, -1.0F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.1574F, -2.2093F, -0.4363F, 0.0F, 0.0F));

        PartDefinition cube_r9 = back_left2.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(54, 25).addBox(-2.0F, -9.0F, -1.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 5.8426F, 0.7907F, 0.3054F, 0.0F, 0.0F));

        PartDefinition right_leg = root.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.offset(-26.0F, 0.0F, 0.0F));

        PartDefinition cube_r10 = right_leg.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(38, 30).addBox(-3.0F, -1.0F, -1.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(19.0F, 0.0F, -14.0F, 0.2182F, 0.0F, 0.0F));

        PartDefinition cube_r11 = right_leg.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(8, 44).addBox(-1.0F, -10.0F, -1.0F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(18.0F, -8.0F, -16.0F, -0.4363F, 0.0F, 0.0F));

        PartDefinition cube_r12 = right_leg.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(40, 45).addBox(-2.0F, -9.0F, -1.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(19.0F, 0.0F, -13.0F, 0.3054F, 0.0F, 0.0F));

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r13 = body.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(0, 0).addBox(-15.0F, -9.0F, -1.0F, 16.0F, 9.0F, 16.0F, new CubeDeformation(1.0F)), PartPose.offsetAndRotation(5.0F, -18.0F, -13.0F, -0.1309F, 0.0F, 0.0F));

        PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition head_r1 = head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(0, 25).addBox(-1.0F, -2.0F, -4.0F, 2.0F, 2.0F, 17.0F, new CubeDeformation(1.0F)), PartPose.offsetAndRotation(-2.0F, -20.0F, -27.0F, 0.1745F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
        root.render(poseStack, buffer, packedLight, packedOverlay, color);
    }

    @Override
    public ModelPart root() {
        return root;
    }

    @Override
    public void setupAnim(T t, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.applyHeadRotation(netHeadYaw);

        this.animateWalk(GrassWalkerMonsterAnimation.walk, limbSwing, limbSwingAmount, 3.5f, 3.5f);

        this.animate(t.idleAnimationState,GrassWalkerMonsterAnimation.idle, ageInTicks, 1.25f);


    }

    private void applyHeadRotation(float headYaw) {
        headYaw = Mth.clamp(headYaw, -18f, 18f);

        this.head.yRot = headYaw * ((float)Math.PI / 180f);
    }
}
