package net.sfedu.ars_maleficarum.entity.client;// Made with Blockbench 4.9.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;

public class PoisonousEssenceModel<T extends Entity> extends EntityModel<T> {
	private final ModelPart bb_main;

	public PoisonousEssenceModel(ModelPart root) {
		this.bb_main = root.getChild("bb_main");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(13, 15).addBox(-2.0F, -4.0F, -2.0F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(5, 14).addBox(1.0F, -3.0F, -2.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(13, 0).addBox(-3.0F, -2.0F, -1.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(7, 0).addBox(0.0F, -4.0F, -1.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(12, 11).addBox(-3.0F, -4.0F, -3.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(14, 19).addBox(-2.0F, -2.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(12, 7).addBox(-1.0F, -5.0F, -2.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(7, 2).addBox(-2.0F, -3.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(7, 0).addBox(-1.0F, -2.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 6).addBox(-1.0F, -1.0F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 11).addBox(-2.0F, -4.0F, -1.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(0.0F, -2.0F, -1.0F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(0, 6).addBox(0.0F, -3.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(8, 19).addBox(0.0F, -1.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 2).addBox(1.0F, -2.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(4, 18).addBox(1.0F, -4.0F, -3.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(18, 0).addBox(0.0F, -5.0F, -2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(7, 10).addBox(-3.0F, -3.0F, -2.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(17, 11).addBox(-2.0F, -1.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(17, 7).addBox(-2.0F, -5.0F, -3.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(15, 4).addBox(-2.0F, -4.0F, -4.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-2.0F, -3.0F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 16).addBox(0.0F, -4.0F, -3.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(6, 6).addBox(-2.0F, -3.0F, -3.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		bb_main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}