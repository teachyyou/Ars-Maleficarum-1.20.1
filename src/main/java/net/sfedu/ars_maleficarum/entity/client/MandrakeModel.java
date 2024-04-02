package net.sfedu.ars_maleficarum.entity.client;// Made with Blockbench 4.9.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.sfedu.ars_maleficarum.entity.animations.ModAnimationDefenitions;
import net.sfedu.ars_maleficarum.entity.custom.MandrakeEntity;

public class MandrakeModel<T extends MandrakeEntity> extends HierarchicalModel<T> {
	private final ModelPart mandrake;

	public MandrakeModel(ModelPart root) {
		this.mandrake = root.getChild("mandrake");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition mandrake = partdefinition.addOrReplaceChild("mandrake", CubeListBuilder.create(), PartPose.offset(1.0F, 24.0F, 0.0F));

		PartDefinition rukalev = mandrake.addOrReplaceChild("rukalev", CubeListBuilder.create().texOffs(4, 17).addBox(0.37F, 0.39F, -0.6333F, 0.9F, 1.2F, 0.9F, new CubeDeformation(0.0F))
		.texOffs(5, 12).addBox(0.67F, 1.19F, -0.4333F, 0.8F, 1.4F, 0.6F, new CubeDeformation(0.0F))
		.texOffs(1, 18).addBox(1.37F, 1.79F, -0.2333F, 0.5F, 1.3F, 0.5F, new CubeDeformation(0.0F))
		.texOffs(5, 15).addBox(0.37F, 0.49F, 0.2667F, 0.7F, 1.1F, 0.4F, new CubeDeformation(0.0F))
		.texOffs(0, 16).addBox(0.07F, -0.01F, -0.5333F, 0.8F, 0.6F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(4, 20).addBox(0.57F, 0.29F, -0.9333F, 0.5F, 1.0F, 0.3F, new CubeDeformation(0.0F))
		.texOffs(7, 19).addBox(-0.03F, -0.31F, -0.4333F, 0.8F, 0.7F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(2, 20).addBox(1.27F, 2.19F, -0.5333F, 0.5F, 0.6F, 0.4F, new CubeDeformation(0.0F))
		.texOffs(3, 12).addBox(1.07F, 1.79F, -0.6333F, 0.3F, 1.9F, 0.2F, new CubeDeformation(0.0F))
		.texOffs(3, 15).addBox(1.37F, 2.49F, 0.0667F, 0.3F, 1.3F, 0.3F, new CubeDeformation(0.0F))
		.texOffs(2, 12).addBox(1.67F, 3.19F, 0.2667F, 0.2F, 1.0F, 0.2F, new CubeDeformation(0.0F))
		.texOffs(1, 14).addBox(1.37F, 3.29F, -0.2333F, 0.3F, 1.2F, 0.3F, new CubeDeformation(0.0F))
		.texOffs(1, 12).addBox(1.37F, 3.89F, -0.3333F, 0.2F, 1.2F, 0.2F, new CubeDeformation(0.0F))
		.texOffs(0, 14).addBox(0.77F, 0.99F, 0.5667F, 0.2F, 2.0F, 0.2F, new CubeDeformation(0.0F))
		.texOffs(0, 12).addBox(0.67F, 2.39F, 0.4667F, 0.1F, 1.4F, 0.1F, new CubeDeformation(0.0F)), PartPose.offset(0.23F, -6.59F, 0.6333F));

		PartDefinition rukaprav = mandrake.addOrReplaceChild("rukaprav", CubeListBuilder.create().texOffs(26, 12).addBox(-0.4083F, -0.3611F, -0.85F, 0.8F, 1.0F, 1.4F, new CubeDeformation(0.0F))
		.texOffs(29, 17).addBox(-0.8083F, -0.1611F, -0.65F, 0.6F, 0.3F, 0.8F, new CubeDeformation(0.0F))
		.texOffs(26, 18).addBox(-1.1083F, 0.1389F, -0.55F, 0.7F, 0.7F, 0.8F, new CubeDeformation(0.0F))
		.texOffs(28, 19).addBox(-1.3083F, 0.6389F, -0.65F, 0.6F, 0.7F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(28, 16).addBox(-0.7083F, 1.2389F, -0.45F, 0.1F, 1.4F, 0.4F, new CubeDeformation(0.0F))
		.texOffs(25, 16).addBox(-1.1083F, 1.2389F, -0.55F, 0.4F, 1.2F, 0.7F, new CubeDeformation(0.0F))
		.texOffs(29, 12).addBox(-1.2083F, 2.2389F, -0.75F, 0.3F, 0.8F, 0.4F, new CubeDeformation(0.0F))
		.texOffs(29, 16).addBox(-1.3083F, 2.9389F, -0.85F, 0.2F, 0.6F, 0.3F, new CubeDeformation(0.0F))
		.texOffs(30, 15).addBox(-1.3083F, 3.5389F, -0.85F, 0.1F, 0.2F, 0.1F, new CubeDeformation(0.0F))
		.texOffs(30, 16).addBox(-1.2083F, 2.0389F, -0.15F, 0.4F, 0.5F, 0.4F, new CubeDeformation(0.0F))
		.texOffs(26, 20).addBox(-1.3083F, 2.3389F, -0.05F, 0.3F, 0.9F, 0.4F, new CubeDeformation(0.0F))
		.texOffs(26, 12).addBox(-1.2083F, 2.9389F, 0.25F, 0.5F, 0.8F, 0.2F, new CubeDeformation(0.0F))
		.texOffs(31, 12).addBox(-1.2083F, 3.2389F, 0.35F, 0.2F, 1.0F, 0.2F, new CubeDeformation(0.0F))
		.texOffs(31, 19).addBox(-1.2083F, 4.2389F, 0.45F, 0.1F, 0.6F, 0.1F, new CubeDeformation(0.0F))
		.texOffs(31, 14).addBox(-0.9083F, 1.1389F, 0.35F, 0.2F, 1.8F, 0.2F, new CubeDeformation(0.0F))
		.texOffs(25, 19).addBox(-1.4083F, 0.8389F, -0.75F, 0.2F, 0.9F, 0.3F, new CubeDeformation(0.0F))
		.texOffs(29, 15).addBox(-0.9083F, 3.5389F, 0.15F, 0.3F, 0.4F, 0.2F, new CubeDeformation(0.0F))
		.texOffs(28, 15).addBox(-0.6083F, 2.0389F, -0.25F, 0.1F, 1.4F, 0.1F, new CubeDeformation(0.0F)), PartPose.offset(-1.9917F, -6.3389F, 0.55F));

		PartDefinition tusha = mandrake.addOrReplaceChild("tusha", CubeListBuilder.create().texOffs(23, 24).addBox(-1.3F, -1.2F, -0.9F, 2.5F, 3.2F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(18, 24).addBox(0.6F, -0.5F, -1.0F, 0.6F, 1.9F, 0.5F, new CubeDeformation(0.0F))
		.texOffs(18, 24).addBox(-1.4F, -0.9F, -1.0F, 0.3F, 2.2F, 2.2F, new CubeDeformation(0.0F))
		.texOffs(24, 30).addBox(-1.1F, -1.4F, -0.7F, 2.0F, 0.2F, 1.6F, new CubeDeformation(0.0F))
		.texOffs(20, 29).addBox(-1.5F, -0.9F, -1.1F, 1.3F, 1.0F, 0.2F, new CubeDeformation(0.0F))
		.texOffs(21, 31).addBox(-1.1F, 0.0F, -1.1F, 1.3F, 0.8F, 0.2F, new CubeDeformation(0.0F))
		.texOffs(16, 29).addBox(0.9F, 1.0F, -0.6F, 0.5F, 1.1F, 1.8F, new CubeDeformation(0.0F))
		.texOffs(30, 30).addBox(-0.7F, 0.7F, -1.0F, 0.9F, 1.5F, 0.1F, new CubeDeformation(0.0F))
		.texOffs(13, 29).addBox(-1.5F, 0.5F, -1.1F, 0.3F, 1.2F, 1.1F, new CubeDeformation(0.0F))
		.texOffs(24, 30).addBox(0.6F, -1.1F, 1.1F, 0.5F, 0.6F, 0.1F, new CubeDeformation(0.0F))
		.texOffs(27, 22).addBox(-0.8F, -0.7F, 0.8F, 1.8F, 1.1F, 0.5F, new CubeDeformation(0.0F))
		.texOffs(21, 24).addBox(0.0F, -0.3F, 0.8F, 1.2F, 1.7F, 0.4F, new CubeDeformation(0.0F))
		.texOffs(13, 25).addBox(-1.5F, 0.8F, -0.5F, 0.8F, 1.1F, 1.7F, new CubeDeformation(0.0F))
		.texOffs(15, 28).addBox(0.9F, -1.3F, -1.1F, 0.4F, 1.4F, 0.7F, new CubeDeformation(0.0F)), PartPose.offset(-0.8F, -5.6F, 0.3F));

		PartDefinition nogaprav = mandrake.addOrReplaceChild("nogaprav", CubeListBuilder.create().texOffs(2, 23).addBox(0.0F, -0.7F, -0.45F, 0.3F, 4.2F, 0.5F, new CubeDeformation(0.0F))
		.texOffs(10, 23).addBox(-0.2F, -0.7F, -0.55F, 0.5F, 2.3F, 0.1F, new CubeDeformation(0.0F))
		.texOffs(12, 23).addBox(-0.2F, 1.6F, -0.55F, 0.1F, 1.3F, 0.1F, new CubeDeformation(0.0F))
		.texOffs(4, 23).addBox(-0.5F, -0.1F, -0.25F, 0.2F, 3.6F, 0.5F, new CubeDeformation(0.0F))
		.texOffs(0, 22).addBox(0.4F, -0.7F, -0.25F, 0.2F, 4.2F, 0.7F, new CubeDeformation(0.0F))
		.texOffs(13, 25).addBox(0.2F, 3.2F, -0.35F, 0.3F, 0.3F, 0.5F, new CubeDeformation(0.0F))
		.texOffs(8, 22).addBox(-0.2F, -0.7F, -0.15F, 0.1F, 4.2F, 0.4F, new CubeDeformation(0.0F))
		.texOffs(11, 25).addBox(-0.4F, 3.3F, -0.25F, 0.4F, 0.2F, 0.8F, new CubeDeformation(0.0F))
		.texOffs(9, 24).addBox(-0.4F, -0.1F, -0.55F, 0.1F, 1.0F, 0.1F, new CubeDeformation(0.0F))
		.texOffs(6, 23).addBox(-0.2F, -0.7F, 0.45F, 0.5F, 2.9F, 0.2F, new CubeDeformation(0.0F))
		.texOffs(10, 26).addBox(0.6F, -0.7F, 0.65F, 0.1F, 1.9F, 0.2F, new CubeDeformation(0.0F))
		.texOffs(9, 26).addBox(0.5F, -0.7F, -0.75F, 0.3F, 1.7F, 0.2F, new CubeDeformation(0.0F))
		.texOffs(0, 18).addBox(0.1F, -0.7F, 0.15F, 0.2F, 3.4F, 0.2F, new CubeDeformation(0.0F)), PartPose.offset(-1.7F, -3.5F, 0.4F));

		PartDefinition listva = mandrake.addOrReplaceChild("listva", CubeListBuilder.create(), PartPose.offset(-0.9F, -8.9F, -0.1F));

		PartDefinition listva_0 = listva.addOrReplaceChild("listva_0", CubeListBuilder.create().texOffs(5, 2).addBox(-0.2333F, -0.3667F, -0.15F, 0.5F, 0.9F, 0.3F, new CubeDeformation(0.0F))
		.texOffs(11, 8).addBox(-0.5333F, -0.9667F, -0.15F, 0.7F, 1.1F, 0.3F, new CubeDeformation(0.0F))
		.texOffs(7, 2).addBox(-0.6333F, -1.1667F, -0.05F, 0.7F, 1.2F, 0.1F, new CubeDeformation(0.0F))
		.texOffs(13, 0).addBox(-0.3333F, -0.7667F, -0.05F, 0.6F, 1.3F, 0.1F, new CubeDeformation(0.0F))
		.texOffs(7, 8).addBox(-0.0333F, -0.1667F, -0.05F, 0.4F, 0.9F, 0.1F, new CubeDeformation(0.0F)), PartPose.offset(-0.4667F, -1.2333F, 0.05F));

		PartDefinition listva_1 = listva.addOrReplaceChild("listva_1", CubeListBuilder.create(), PartPose.offset(-0.4692F, -1.1583F, -0.4989F));

		PartDefinition lictva1_r1 = listva_1.addOrReplaceChild("lictva1_r1", CubeListBuilder.create().texOffs(12, 6).addBox(-1.4F, -10.3F, 11.8F, 0.3F, 0.9F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(10, 7).addBox(-1.7F, -10.9F, 11.9F, 0.5F, 1.2F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(17, 5).addBox(-1.9F, -11.1F, 11.9F, 0.5F, 1.1F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(13, 2).addBox(-1.8F, -11.0F, 11.8F, 0.5F, 1.1F, 0.2F, new CubeDeformation(0.0F))
		.texOffs(7, 9).addBox(-1.5F, -10.4F, 11.7F, 0.3F, 0.9F, 0.3F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.3692F, 10.0583F, -7.4011F, 0.0F, -0.7854F, 0.0F));

		PartDefinition listva_2 = listva.addOrReplaceChild("listva_2", CubeListBuilder.create().texOffs(5, 6).addBox(-0.05F, -0.2667F, -0.0333F, 0.1F, 1.1F, 0.4F, new CubeDeformation(0.0F))
		.texOffs(1, 7).addBox(-0.05F, -0.8667F, -0.3333F, 0.1F, 1.5F, 0.6F, new CubeDeformation(0.0F))
		.texOffs(13, 4).addBox(-0.05F, -1.2667F, -0.6333F, 0.1F, 1.4F, 0.7F, new CubeDeformation(0.0F))
		.texOffs(6, 4).addBox(-0.15F, -1.0667F, -0.5333F, 0.3F, 1.3F, 0.7F, new CubeDeformation(0.0F))
		.texOffs(13, 6).addBox(-0.15F, -0.4667F, -0.2333F, 0.3F, 1.1F, 0.5F, new CubeDeformation(0.0F)), PartPose.offset(0.05F, -1.3333F, -0.6667F));

		PartDefinition listva_3 = listva.addOrReplaceChild("listva_3", CubeListBuilder.create().texOffs(3, 2).addBox(-0.05F, -0.2667F, -0.4083F, 0.1F, 1.1F, 0.5F, new CubeDeformation(0.0F))
		.texOffs(11, 1).addBox(-0.05F, -0.8667F, -0.3083F, 0.1F, 1.5F, 0.7F, new CubeDeformation(0.0F))
		.texOffs(9, 1).addBox(-0.05F, -1.2667F, -0.1083F, 0.1F, 1.4F, 0.8F, new CubeDeformation(0.0F))
		.texOffs(10, 4).addBox(-0.15F, -1.0667F, -0.2083F, 0.3F, 1.3F, 0.8F, new CubeDeformation(0.0F))
		.texOffs(3, 9).addBox(-0.15F, -0.4667F, -0.3083F, 0.3F, 1.1F, 0.6F, new CubeDeformation(0.0F)), PartPose.offset(-0.05F, -1.3333F, 0.9083F));

		PartDefinition listva_4 = listva.addOrReplaceChild("listva_4", CubeListBuilder.create(), PartPose.offset(0.4265F, -1.6F, -0.2043F));

		PartDefinition lictva4_r1 = listva_4.addOrReplaceChild("lictva4_r1", CubeListBuilder.create().texOffs(6, 6).addBox(-0.7F, -10.9F, 11.2F, 0.3F, 0.9F, 0.5F, new CubeDeformation(0.0F))
		.texOffs(8, 6).addBox(-0.7F, -11.5F, 10.9F, 0.3F, 1.1F, 0.7F, new CubeDeformation(0.0F))
		.texOffs(1, 2).addBox(-0.6F, -11.7F, 10.8F, 0.1F, 1.2F, 0.7F, new CubeDeformation(0.0F))
		.texOffs(8, 4).addBox(-0.6F, -11.3F, 11.1F, 0.1F, 1.3F, 0.6F, new CubeDeformation(0.0F))
		.texOffs(0, 7).addBox(-0.6F, -10.7F, 11.4F, 0.1F, 0.9F, 0.4F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.4735F, 10.5F, -7.6957F, 0.0F, -0.7854F, 0.0F));

		PartDefinition listva_5 = listva.addOrReplaceChild("listva_5", CubeListBuilder.create(), PartPose.offset(-0.6059F, -1.0417F, 0.4212F));

		PartDefinition lictva5_r1 = listva_5.addOrReplaceChild("lictva5_r1", CubeListBuilder.create().texOffs(10, 4).addBox(-11.9F, -10.0F, 4.0F, 0.2F, 0.7F, 0.1F, new CubeDeformation(0.0F))
		.texOffs(8, 8).addBox(-12.2F, -10.6F, 4.0F, 0.4F, 1.1F, 0.1F, new CubeDeformation(0.0F))
		.texOffs(15, 3).addBox(-12.5F, -11.0F, 4.0F, 0.5F, 1.0F, 0.1F, new CubeDeformation(0.0F))
		.texOffs(15, 1).addBox(-12.4F, -10.8F, 3.9F, 0.5F, 0.9F, 0.3F, new CubeDeformation(0.0F))
		.texOffs(15, 5).addBox(-12.1F, -10.2F, 3.9F, 0.3F, 0.7F, 0.3F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.5059F, 9.9417F, -8.3212F, 0.0F, 0.3927F, 0.0F));

		PartDefinition listva_6 = listva.addOrReplaceChild("listva_6", CubeListBuilder.create(), PartPose.offset(0.4972F, -1.325F, 0.5971F));

		PartDefinition lictva6_r1 = listva_6.addOrReplaceChild("lictva6_r1", CubeListBuilder.create().texOffs(3, 4).addBox(-12.0F, -10.4F, -0.3F, 0.1F, 0.9F, 0.4F, new CubeDeformation(0.0F))
		.texOffs(16, 3).addBox(-12.0F, -11.0F, -0.2F, 0.1F, 1.3F, 0.6F, new CubeDeformation(0.0F))
		.texOffs(4, 4).addBox(-12.0F, -11.4F, 0.0F, 0.1F, 1.2F, 0.7F, new CubeDeformation(0.0F))
		.texOffs(3, 7).addBox(-12.1F, -11.2F, -0.1F, 0.3F, 1.1F, 0.7F, new CubeDeformation(0.0F))
		.texOffs(5, 8).addBox(-12.1F, -10.6F, -0.2F, 0.3F, 0.9F, 0.5F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.4028F, 10.225F, -8.4971F, 0.0F, 0.7854F, 0.0F));

		PartDefinition listva_7 = listva.addOrReplaceChild("listva_7", CubeListBuilder.create(), PartPose.offset(0.3087F, -0.7F, -0.44F));

		PartDefinition lictva7_r1 = listva_7.addOrReplaceChild("lictva7_r1", CubeListBuilder.create().texOffs(1, 4).addBox(-0.9F, -10.2F, 11.0F, 0.2F, 0.6F, 0.4F, new CubeDeformation(0.0F))
		.texOffs(0, 5).addBox(-0.9F, -9.8F, 11.2F, 0.2F, 0.4F, 0.3F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.5913F, 9.6F, -7.46F, 0.0F, -0.7854F, 0.0F));

		PartDefinition listva_8 = listva.addOrReplaceChild("listva_8", CubeListBuilder.create(), PartPose.offset(-0.5489F, -0.6F, 0.7764F));

		PartDefinition lictva8_r1 = listva_8.addOrReplaceChild("lictva8_r1", CubeListBuilder.create().texOffs(2, 6).addBox(-12.4F, -10.1F, 4.4F, 0.4F, 0.6F, 0.2F, new CubeDeformation(0.0F))
		.texOffs(12, 4).addBox(-12.2F, -9.7F, 4.3F, 0.3F, 0.4F, 0.2F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.4489F, 9.5F, -8.6764F, 0.0F, 0.3927F, 0.0F));

		PartDefinition listva_9 = listva.addOrReplaceChild("listva_9", CubeListBuilder.create(), PartPose.offset(-0.5399F, -0.75F, -0.2279F));

		PartDefinition lictva9_r1 = listva_9.addOrReplaceChild("lictva9_r1", CubeListBuilder.create().texOffs(17, 1).addBox(-12.2F, -9.9F, -1.4F, 0.2F, 0.4F, 0.3F, new CubeDeformation(0.0F))
		.texOffs(15, 0).addBox(-12.3F, -10.3F, -1.6F, 0.2F, 0.6F, 0.4F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.4399F, 9.65F, -7.6721F, 0.0F, 0.7854F, 0.0F));

		PartDefinition listva_10 = listva.addOrReplaceChild("listva_10", CubeListBuilder.create().texOffs(2, 10).addBox(0.075F, -0.35F, -0.1F, 0.3F, 0.4F, 0.2F, new CubeDeformation(0.0F))
		.texOffs(1, 5).addBox(0.175F, -0.75F, -0.1F, 0.4F, 0.6F, 0.2F, new CubeDeformation(0.0F)), PartPose.offset(0.425F, -0.55F, 0.2F));

		PartDefinition listva_11 = listva.addOrReplaceChild("listva_11", CubeListBuilder.create(), PartPose.offset(0.7221F, -0.9167F, -0.0748F));

		PartDefinition lictva11_r1 = listva_11.addOrReplaceChild("lictva11_r1", CubeListBuilder.create().texOffs(0, 10).addBox(-10.6F, -10.5F, 4.0F, 0.4F, 0.6F, 0.2F, new CubeDeformation(0.0F))
		.texOffs(0, 6).addBox(-10.7F, -10.0F, 4.0F, 0.3F, 0.4F, 0.2F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.1779F, 9.8167F, -7.8252F, 0.0F, 0.3927F, 0.0F));

		PartDefinition koreshki = listva.addOrReplaceChild("koreshki", CubeListBuilder.create().texOffs(0, 9).addBox(-0.5F, -9.6F, 0.0F, 0.2F, 0.5F, 0.2F, new CubeDeformation(0.0F))
		.texOffs(4, 0).addBox(-1.1F, -10.2F, 0.3F, 0.3F, 1.4F, 0.3F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-1.0F, -10.2F, -0.6F, 0.3F, 1.4F, 0.3F, new CubeDeformation(0.0F))
		.texOffs(2, 0).addBox(-1.2F, -10.0F, -0.2F, 0.3F, 1.2F, 0.3F, new CubeDeformation(0.0F)), PartPose.offset(0.9F, 8.9F, 0.1F));

		PartDefinition kor1_r1 = koreshki.addOrReplaceChild("kor1_r1", CubeListBuilder.create().texOffs(12, 0).addBox(-1.2F, -9.7F, 11.7F, 0.2F, 0.9F, 0.2F, new CubeDeformation(0.0F))
		.texOffs(6, 0).addBox(-0.7F, -10.4F, 11.6F, 0.3F, 1.6F, 0.3F, new CubeDeformation(0.0F))
		.texOffs(17, 0).addBox(-0.9F, -9.6F, 11.4F, 0.2F, 0.6F, 0.2F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, 0.0F, -8.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition kor5_r1 = koreshki.addOrReplaceChild("kor5_r1", CubeListBuilder.create().texOffs(0, 3).addBox(-11.7F, -9.7F, 3.9F, 0.1F, 0.9F, 0.3F, new CubeDeformation(0.0F))
		.texOffs(5, 4).addBox(-12.0F, -9.5F, 4.2F, 0.2F, 0.6F, 0.2F, new CubeDeformation(0.0F))
		.texOffs(0, 2).addBox(-10.8F, -9.8F, 4.0F, 0.2F, 0.7F, 0.2F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, 0.0F, -8.0F, 0.0F, 0.3927F, 0.0F));

		PartDefinition kor6_r1 = koreshki.addOrReplaceChild("kor6_r1", CubeListBuilder.create().texOffs(8, 0).addBox(-12.1F, -10.1F, -0.4F, 0.3F, 1.3F, 0.3F, new CubeDeformation(0.0F))
		.texOffs(10, 0).addBox(-12.1F, -9.7F, -1.2F, 0.2F, 0.9F, 0.2F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, 0.0F, -8.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition nogalev = mandrake.addOrReplaceChild("nogalev", CubeListBuilder.create().texOffs(0, 27).addBox(0.0F, -0.6F, -0.65F, 0.5F, 4.2F, 0.4F, new CubeDeformation(0.0F))
		.texOffs(11, 27).addBox(-0.4F, -0.6F, -0.65F, 0.2F, 3.4F, 0.4F, new CubeDeformation(0.0F))
		.texOffs(6, 29).addBox(-0.3F, 1.4F, -0.55F, 0.7F, 2.2F, 0.4F, new CubeDeformation(0.0F))
		.texOffs(9, 28).addBox(0.3F, 0.1F, -0.05F, 0.2F, 2.3F, 0.3F, new CubeDeformation(0.0F))
		.texOffs(7, 27).addBox(-0.3F, 0.0F, -0.05F, 0.4F, 1.5F, 0.3F, new CubeDeformation(0.0F))
		.texOffs(5, 29).addBox(-0.5F, 0.0F, 0.55F, 0.5F, 1.7F, 0.1F, new CubeDeformation(0.0F))
		.texOffs(2, 28).addBox(0.2F, 0.0F, 0.55F, 0.3F, 3.2F, 0.2F, new CubeDeformation(0.0F))
		.texOffs(3, 28).addBox(-0.2F, -0.6F, -0.95F, 0.2F, 3.0F, 0.1F, new CubeDeformation(0.0F))
		.texOffs(4, 31).addBox(-0.3F, 3.4F, -0.45F, 0.6F, 0.2F, 0.7F, new CubeDeformation(0.0F))
		.texOffs(9, 30).addBox(-0.5F, 3.2F, -0.65F, 0.3F, 0.4F, 1.1F, new CubeDeformation(0.0F))
		.texOffs(5, 27).addBox(-0.1F, 0.0F, 0.45F, 0.4F, 1.2F, 0.4F, new CubeDeformation(0.0F))
		.texOffs(4, 28).addBox(-0.7F, 0.0F, 0.05F, 0.1F, 2.6F, 0.2F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.6F, 0.5F));

		PartDefinition bash = mandrake.addOrReplaceChild("bash", CubeListBuilder.create().texOffs(27, 0).addBox(-0.6341F, -1.7818F, -0.7568F, 1.4F, 2.0F, 0.7F, new CubeDeformation(0.0F))
		.texOffs(22, 0).addBox(-0.9341F, -0.4818F, -1.0568F, 1.9F, 0.5F, 0.5F, new CubeDeformation(0.0F))
		.texOffs(19, 4).addBox(-0.8341F, -1.7818F, -0.9568F, 0.7F, 0.6F, 1.4F, new CubeDeformation(0.0F))
		.texOffs(25, 9).addBox(-0.9341F, -2.0818F, -0.6568F, 0.7F, 0.4F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(20, 0).addBox(-0.8341F, -1.8818F, -0.0568F, 0.6F, 0.4F, 0.9F, new CubeDeformation(0.0F))
		.texOffs(24, 3).addBox(0.2659F, -1.8818F, 0.0432F, 0.6F, 0.4F, 0.8F, new CubeDeformation(0.0F))
		.texOffs(23, 5).addBox(0.2659F, -2.0818F, -0.6568F, 0.7F, 0.4F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(19, 6).addBox(0.1659F, -1.7818F, -0.9568F, 0.7F, 0.6F, 1.4F, new CubeDeformation(0.0F))
		.texOffs(22, 7).addBox(0.5659F, -0.8818F, -0.9568F, 0.3F, 0.8F, 1.5F, new CubeDeformation(0.0F))
		.texOffs(28, 6).addBox(-0.8341F, -0.8818F, -0.9568F, 0.3F, 0.8F, 1.5F, new CubeDeformation(0.0F))
		.texOffs(28, 9).addBox(-0.1341F, -1.0818F, -1.0568F, 0.3F, 0.5F, 1.6F, new CubeDeformation(0.0F))
		.texOffs(26, 6).addBox(-0.5341F, -0.8818F, -0.9568F, 1.3F, 0.3F, 0.4F, new CubeDeformation(0.0F))
		.texOffs(22, 3).addBox(-0.1341F, -1.7818F, -0.8568F, 0.3F, 0.7F, 1.4F, new CubeDeformation(0.0F))
		.texOffs(20, 2).addBox(-0.7341F, -1.5818F, 0.8432F, 1.3F, 1.6F, 0.2F, new CubeDeformation(0.0F))
		.texOffs(20, 0).addBox(0.6659F, -1.1818F, -0.6568F, 0.2F, 0.3F, 0.3F, new CubeDeformation(0.0F))
		.texOffs(25, 7).addBox(-0.9341F, -1.5818F, -1.0568F, 0.5F, 0.8F, 0.7F, new CubeDeformation(0.0F))
		.texOffs(23, 1).addBox(-0.9341F, -1.3818F, 0.2432F, 0.3F, 1.2F, 0.5F, new CubeDeformation(0.0F))
		.texOffs(25, 1).addBox(-0.2341F, -1.4818F, 0.8432F, 0.7F, 0.8F, 0.3F, new CubeDeformation(0.0F))
		.texOffs(30, 3).addBox(0.0659F, -0.6818F, 0.8432F, 0.6F, 0.3F, 0.3F, new CubeDeformation(0.0F))
		.texOffs(19, 4).addBox(-1.0341F, -0.4818F, -0.1568F, 0.3F, 0.8F, 0.4F, new CubeDeformation(0.0F))
		.texOffs(20, 8).addBox(0.6659F, -1.3818F, -0.0568F, 0.3F, 1.6F, 0.4F, new CubeDeformation(0.0F))
		.texOffs(26, 3).addBox(-0.7341F, -1.7818F, -0.1568F, 1.5F, 1.8F, 1.1F, new CubeDeformation(0.0F)), PartPose.offset(-0.9659F, -7.0182F, -0.0432F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(MandrakeEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.animateWalk(ModAnimationDefenitions.MANDRAKE_RUN, limbSwing, limbSwingAmount, 2.0f, 2.5f);
		this.animate(entity.idleAnimationState, ModAnimationDefenitions.MANDRAKE_IDLE, ageInTicks, 1f);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		mandrake.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return mandrake;
	}
}