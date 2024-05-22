package net.sfedu.ars_maleficarum.entity.client;// Made with Blockbench 4.10.1
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.sfedu.ars_maleficarum.entity.animations.ModANimationsGluttonyDemon;
import net.sfedu.ars_maleficarum.entity.animations.TraderWitchAnimations;
import net.sfedu.ars_maleficarum.entity.animations.TraderWitchAnimations2;
import net.sfedu.ars_maleficarum.entity.animations.TraderWitchAnimations3;
import net.sfedu.ars_maleficarum.entity.custom.TraderWitchEntity;

public class TraderWitchModel<T extends TraderWitchEntity> extends HierarchicalModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor

	private final ModelPart body;
	public TraderWitchModel(ModelPart root) {
		this.body = root.getChild("mode");

	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition mode = partdefinition.addOrReplaceChild("mode", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition plash = mode.addOrReplaceChild("plash", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone66 = plash.addOrReplaceChild("bone66", CubeListBuilder.create().texOffs(41, 101).addBox(2.1F, -21.8F, -5.2F, 2.7F, 3.9F, 9.9F, new CubeDeformation(0.0F))
				.texOffs(129, 55).addBox(-4.8F, -21.8F, -5.2F, 2.7F, 3.9F, 9.9F, new CubeDeformation(0.0F))
				.texOffs(23, 102).addBox(3.4F, -21.2F, 0.4F, 2.3F, 3.2F, 6.3F, new CubeDeformation(0.0F))
				.texOffs(129, 70).addBox(3.1F, -20.2F, 6.3F, 2.3F, 2.5F, 1.6F, new CubeDeformation(0.0F))
				.texOffs(89, 86).addBox(1.7F, -18.4F, 6.3F, 3.1F, 3.1F, 2.2F, new CubeDeformation(0.0F))
				.texOffs(30, 78).addBox(-2.4F, -17.0F, 7.5F, 4.8F, 3.1F, 1.5F, new CubeDeformation(0.0F))
				.texOffs(43, 78).addBox(-4.8F, -18.4F, 6.3F, 3.1F, 3.1F, 2.2F, new CubeDeformation(0.0F))
				.texOffs(68, 16).addBox(-4.2F, -19.5F, 5.7F, 8.0F, 3.4F, 1.8F, new CubeDeformation(0.0F))
				.texOffs(114, 27).addBox(-5.4F, -20.2F, 6.3F, 2.3F, 2.5F, 1.6F, new CubeDeformation(0.0F))
				.texOffs(40, 58).addBox(-5.7F, -21.2F, 0.4F, 2.3F, 3.2F, 6.3F, new CubeDeformation(0.0F))
				.texOffs(52, 58).addBox(-4.2F, -21.1F, 3.7F, 8.0F, 3.2F, 2.4F, new CubeDeformation(0.0F)), PartPose.offset(0.9F, 0.0F, 0.0F));

		PartDefinition bone67 = plash.addOrReplaceChild("bone67", CubeListBuilder.create().texOffs(91, 90).addBox(-7.2F, -20.7F, -5.4F, 2.9F, 2.8F, 10.8F, new CubeDeformation(0.0F))
				.texOffs(1, 117).addBox(4.3F, -20.7F, -5.4F, 2.3F, 2.8F, 10.8F, new CubeDeformation(0.0F)), PartPose.offset(0.9F, 0.0F, 0.0F));

		PartDefinition bone70 = plash.addOrReplaceChild("bone70", CubeListBuilder.create().texOffs(0, 3).addBox(5.5F, -9.1F, -3.6F, 1.1F, 6.9F, 7.4F, new CubeDeformation(0.0F))
				.texOffs(74, 101).addBox(5.0F, -3.3F, -4.5F, 2.0F, 2.8F, 2.6F, new CubeDeformation(0.0F)), PartPose.offset(0.9F, 0.0F, 0.0F));

		PartDefinition bone69 = plash.addOrReplaceChild("bone69", CubeListBuilder.create().texOffs(1, 19).addBox(5.9F, -19.4F, -3.6F, 0.6F, 10.3F, 7.4F, new CubeDeformation(0.0F)), PartPose.offset(0.9F, 0.0F, 0.0F));

		PartDefinition bone71 = plash.addOrReplaceChild("bone71", CubeListBuilder.create().texOffs(0, 18).addBox(4.1F, -10.0F, -5.0F, 2.5F, 6.3F, 1.4F, new CubeDeformation(0.0F)), PartPose.offset(0.9F, 0.0F, 0.0F));

		PartDefinition bone68 = plash.addOrReplaceChild("bone68", CubeListBuilder.create().texOffs(50, 118).addBox(4.0F, -19.4F, 2.6F, 2.9F, 10.3F, 3.4F, new CubeDeformation(0.0F))
				.texOffs(158, 64).addBox(3.8F, -19.1F, -5.1F, 2.6F, 12.1F, 1.5F, new CubeDeformation(0.0F)), PartPose.offset(0.9F, 0.0F, 0.0F));

		PartDefinition bone72 = plash.addOrReplaceChild("bone72", CubeListBuilder.create().texOffs(103, 10).addBox(4.8F, -5.7F, -4.8F, 2.1F, 3.6F, 2.1F, new CubeDeformation(0.0F)), PartPose.offset(0.9F, 0.0F, 0.0F));

		PartDefinition bone73 = plash.addOrReplaceChild("bone73", CubeListBuilder.create().texOffs(92, 74).addBox(5.1F, -2.4F, -4.0F, 2.0F, 2.3F, 8.7F, new CubeDeformation(0.0F)), PartPose.offset(0.9F, 0.0F, 0.0F));

		PartDefinition bone74 = plash.addOrReplaceChild("bone74", CubeListBuilder.create().texOffs(131, 91).addBox(-6.9F, -2.0F, 2.9F, 2.5F, 1.9F, 3.9F, new CubeDeformation(0.0F))
				.texOffs(127, 9).addBox(4.0F, -2.0F, 2.9F, 2.4F, 1.9F, 3.9F, new CubeDeformation(0.0F)), PartPose.offset(0.9F, 0.0F, 0.0F));

		PartDefinition bone75 = plash.addOrReplaceChild("bone75", CubeListBuilder.create().texOffs(88, 27).addBox(-2.6F, -2.5F, 5.3F, 5.2F, 2.4F, 2.6F, new CubeDeformation(0.0F)), PartPose.offset(0.9F, 0.0F, 0.0F));

		PartDefinition bone76 = plash.addOrReplaceChild("bone76", CubeListBuilder.create().texOffs(132, 1).addBox(-5.2F, -2.2F, 4.6F, 4.3F, 2.1F, 2.7F, new CubeDeformation(0.0F))
				.texOffs(91, 21).addBox(0.9F, -2.2F, 4.6F, 4.3F, 2.1F, 2.7F, new CubeDeformation(0.0F)), PartPose.offset(0.9F, 0.0F, 0.0F));

		PartDefinition bone77 = plash.addOrReplaceChild("bone77", CubeListBuilder.create().texOffs(57, 39).addBox(4.3F, -10.4F, 3.2F, 2.7F, 8.5F, 2.3F, new CubeDeformation(0.0F)), PartPose.offset(0.9F, 0.0F, 0.0F));

		PartDefinition bone78 = plash.addOrReplaceChild("bone78", CubeListBuilder.create().texOffs(0, 109).addBox(1.2F, -18.9F, 4.8F, 3.8F, 16.9F, 1.6F, new CubeDeformation(0.0F))
				.texOffs(76, 82).addBox(-4.7F, -18.9F, 4.8F, 3.8F, 16.9F, 1.6F, new CubeDeformation(0.0F)), PartPose.offset(0.9F, 0.0F, 0.0F));

		PartDefinition bone79 = plash.addOrReplaceChild("bone79", CubeListBuilder.create().texOffs(88, 104).addBox(-2.2F, -15.6F, 5.6F, 4.7F, 13.6F, 1.8F, new CubeDeformation(0.0F)), PartPose.offset(0.9F, 0.0F, 0.0F));

		PartDefinition bone80 = plash.addOrReplaceChild("bone80", CubeListBuilder.create().texOffs(56, 22).addBox(-7.2F, -10.4F, 3.2F, 2.9F, 8.5F, 2.3F, new CubeDeformation(0.0F)), PartPose.offset(0.9F, 0.0F, 0.0F));

		PartDefinition bone81 = plash.addOrReplaceChild("bone81", CubeListBuilder.create().texOffs(113, 100).addBox(-8.9F, -2.4F, -1.8F, 2.2F, 2.3F, 6.5F, new CubeDeformation(0.0F)), PartPose.offset(0.9F, 0.0F, 0.0F));

		PartDefinition bone82 = plash.addOrReplaceChild("bone82", CubeListBuilder.create().texOffs(74, 72).addBox(-5.1F, -19.2F, -5.0F, 2.7F, 2.6F, 0.9F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone83 = plash.addOrReplaceChild("bone83", CubeListBuilder.create().texOffs(21, 112).addBox(-7.7F, -17.9F, -5.0F, 2.1F, 3.3F, 1.6F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone84 = plash.addOrReplaceChild("bone84", CubeListBuilder.create().texOffs(137, 28).addBox(-8.2F, -3.2F, -4.0F, 2.0F, 2.6F, 4.6F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone85 = plash.addOrReplaceChild("bone85", CubeListBuilder.create().texOffs(34, 113).addBox(-7.3F, -17.6F, 1.4F, 1.3F, 15.3F, 2.7F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone86 = plash.addOrReplaceChild("bone86", CubeListBuilder.create().texOffs(98, 0).addBox(-6.5F, -19.2F, -5.2F, 2.3F, 3.6F, 1.8F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone87 = plash.addOrReplaceChild("bone87", CubeListBuilder.create().texOffs(155, 56).addBox(-8.4F, -11.8F, -5.0F, 1.7F, 3.8F, 1.5F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone88 = plash.addOrReplaceChild("bone88", CubeListBuilder.create().texOffs(71, 107).addBox(-8.1F, -4.0F, -4.5F, 1.8F, 2.8F, 2.6F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone89 = plash.addOrReplaceChild("bone89", CubeListBuilder.create().texOffs(68, 0).addBox(-8.0F, -8.3F, -4.7F, 1.6F, 5.5F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone90 = plash.addOrReplaceChild("bone90", CubeListBuilder.create().texOffs(108, 89).addBox(-7.0F, -19.7F, -4.1F, 1.0F, 2.3F, 7.9F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone91 = plash.addOrReplaceChild("bone91", CubeListBuilder.create().texOffs(88, 6).addBox(-6.2F, -19.4F, 2.6F, 3.1F, 10.3F, 3.4F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone92 = plash.addOrReplaceChild("bone92", CubeListBuilder.create().texOffs(102, 104).addBox(-7.9F, -9.7F, -3.3F, 1.3F, 7.5F, 5.9F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone93 = plash.addOrReplaceChild("bone93", CubeListBuilder.create().texOffs(63, 124).addBox(-8.6F, -15.3F, -4.8F, 1.7F, 4.0F, 1.6F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone94 = plash.addOrReplaceChild("bone94", CubeListBuilder.create().texOffs(0, 52).addBox(-8.2F, -18.2F, -3.6F, 1.2F, 11.0F, 5.4F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition balashon = mode.addOrReplaceChild("balashon", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone34 = balashon.addOrReplaceChild("bone34", CubeListBuilder.create().texOffs(0, 69).addBox(-1.3F, -0.2F, 0.2F, 2.5F, 5.3F, 0.3F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, -13.0F, -5.0F));

		PartDefinition bone35 = balashon.addOrReplaceChild("bone35", CubeListBuilder.create().texOffs(10, 18).addBox(-1.2F, 0.05F, 0.1F, 2.5F, 4.2F, 0.4F, new CubeDeformation(0.0F)), PartPose.offset(4.9F, -9.75F, -5.0F));

		PartDefinition bone37 = balashon.addOrReplaceChild("bone37", CubeListBuilder.create().texOffs(6, 60).addBox(-5.9F, -0.2F, -4.3F, 11.7F, 8.2F, 8.6F, new CubeDeformation(0.0F)), PartPose.offset(0.9F, -14.0F, 0.0F));

		PartDefinition bone38 = balashon.addOrReplaceChild("bone38", CubeListBuilder.create().texOffs(128, 123).addBox(-6.0F, -4.1F, -4.5F, 11.8F, 6.2F, 8.8F, new CubeDeformation(0.0F)), PartPose.offset(0.9F, -16.0F, 0.0F));

		PartDefinition bone39 = balashon.addOrReplaceChild("bone39", CubeListBuilder.create().texOffs(119, 72).addBox(-1.1F, -0.2F, -0.2F, 2.2F, 3.8F, 0.4F, new CubeDeformation(0.0F)), PartPose.offset(-4.1F, -13.0F, -4.7F));

		PartDefinition bone40 = balashon.addOrReplaceChild("bone40", CubeListBuilder.create().texOffs(71, 84).addBox(-1.0F, 0.0F, -0.2F, 2.0F, 4.0F, 0.3F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -13.2F, -4.6F));

		PartDefinition bone41 = balashon.addOrReplaceChild("bone41", CubeListBuilder.create().texOffs(58, 65).addBox(-0.6F, -0.2F, -0.2F, 1.2F, 3.2F, 0.4F, new CubeDeformation(0.0F)), PartPose.offset(-0.4F, -13.0F, -4.7F));

		PartDefinition bone42 = balashon.addOrReplaceChild("bone42", CubeListBuilder.create().texOffs(67, 110).addBox(-0.7F, -0.2F, 0.0F, 1.6F, 1.5F, 0.3F, new CubeDeformation(0.0F)), PartPose.offset(0.9F, -13.0F, -4.8F));

		PartDefinition bone43 = balashon.addOrReplaceChild("bone43", CubeListBuilder.create().texOffs(82, 11).addBox(-0.8F, -0.2F, -0.3F, 1.9F, 3.0F, 0.4F, new CubeDeformation(0.0F)), PartPose.offset(2.6F, -13.0F, -4.6F));

		PartDefinition bone65 = balashon.addOrReplaceChild("bone65", CubeListBuilder.create().texOffs(10, 0).addBox(-0.5F, -0.2F, -1.8F, 0.3F, 4.0F, 3.4F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, -8.7F, -1.4F));

		PartDefinition bone61 = balashon.addOrReplaceChild("bone61", CubeListBuilder.create().texOffs(105, 65).addBox(-1.0F, 0.0F, -0.1F, 1.9F, 5.8F, 0.4F, new CubeDeformation(0.0F)), PartPose.offset(2.8F, -11.0F, -5.0F));

		PartDefinition bone60 = balashon.addOrReplaceChild("bone60", CubeListBuilder.create().texOffs(23, 13).addBox(-0.1F, -0.2F, -0.7F, 0.3F, 5.6F, 1.4F, new CubeDeformation(0.0F)), PartPose.offset(-5.3F, -10.0F, -3.9F));

		PartDefinition bone59 = balashon.addOrReplaceChild("bone59", CubeListBuilder.create().texOffs(63, 116).addBox(-0.9F, -0.1F, 0.1F, 1.6F, 6.9F, 0.3F, new CubeDeformation(0.0F)), PartPose.offset(1.1F, -11.6F, -5.0F));

		PartDefinition bone58 = balashon.addOrReplaceChild("bone58", CubeListBuilder.create().texOffs(123, 78).addBox(-0.5F, -0.1F, 0.0F, 1.2F, 5.1F, 0.3F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, -11.0F, -5.0F));

		PartDefinition bone57 = balashon.addOrReplaceChild("bone57", CubeListBuilder.create().texOffs(0, 96).addBox(-0.9F, 0.0F, 0.1F, 2.0F, 4.8F, 0.2F, new CubeDeformation(0.0F)), PartPose.offset(-2.1F, -10.0F, -5.0F));

		PartDefinition bone56 = balashon.addOrReplaceChild("bone56", CubeListBuilder.create().texOffs(40, 86).addBox(-1.1F, -0.2F, 0.0F, 2.2F, 4.6F, 0.4F, new CubeDeformation(0.0F)), PartPose.offset(-4.1F, -9.2F, -5.0F));

		PartDefinition bone55 = balashon.addOrReplaceChild("bone55", CubeListBuilder.create().texOffs(0, 48).addBox(-0.2F, -0.1F, -0.6F, 0.2F, 6.3F, 1.4F, new CubeDeformation(0.0F)), PartPose.offset(-5.1F, -14.0F, -4.0F));

		PartDefinition bone54 = balashon.addOrReplaceChild("bone54", CubeListBuilder.create().texOffs(0, 1).addBox(-0.2F, -0.1F, -1.8F, 0.2F, 5.3F, 3.4F, new CubeDeformation(0.0F)), PartPose.offset(-5.1F, -14.0F, -1.4F));

		PartDefinition bone53 = balashon.addOrReplaceChild("bone53", CubeListBuilder.create().texOffs(19, 42).addBox(-0.1F, -0.1F, -1.0F, 0.1F, 9.6F, 2.2F, new CubeDeformation(0.0F)), PartPose.offset(-5.1F, -14.0F, 1.2F));

		PartDefinition bone52 = balashon.addOrReplaceChild("bone52", CubeListBuilder.create().texOffs(7, 78).addBox(-0.1F, -0.1F, -0.6F, 0.1F, 8.6F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.1F, -14.0F, 2.9F));

		PartDefinition bone51 = balashon.addOrReplaceChild("bone51", CubeListBuilder.create().texOffs(17, 117).addBox(-0.1F, 0.0F, -0.7F, 0.1F, 9.2F, 1.2F, new CubeDeformation(0.0F)), PartPose.offset(-5.1F, -14.1F, 4.0F));

		PartDefinition bone101 = mode.addOrReplaceChild("bone101", CubeListBuilder.create().texOffs(18, 0).addBox(-0.5F, -6.9F, -0.8F, 0.8F, 18.8F, 0.8F, new CubeDeformation(0.0F))
				.texOffs(94, 59).addBox(-1.3F, -10.9F, -1.8F, 2.7F, 1.2F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(30, 83).addBox(-0.3F, 7.2F, -1.2F, 0.8F, 1.8F, 0.9F, new CubeDeformation(0.0F))
				.texOffs(110, 9).addBox(-0.6F, 11.5F, -0.5F, 0.7F, 1.5F, 0.6F, new CubeDeformation(0.0F))
				.texOffs(15, 0).addBox(0.0F, 9.8F, -0.9F, 0.4F, 1.8F, 0.6F, new CubeDeformation(0.0F))
				.texOffs(112, 23).addBox(-0.9F, 7.8F, -1.0F, 1.0F, 2.9F, 0.5F, new CubeDeformation(0.0F))
				.texOffs(87, 4).addBox(-0.7F, 5.9F, -0.9F, 1.1F, 2.3F, 0.8F, new CubeDeformation(0.0F))
				.texOffs(116, 21).addBox(0.1F, 5.6F, -0.7F, 0.6F, 1.7F, 0.8F, new CubeDeformation(0.0F))
				.texOffs(47, 105).addBox(-0.6F, 2.1F, -1.2F, 0.85F, 3.95F, 0.8F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-0.3F, 8.9F, -0.1F, 0.3F, 3.1F, 0.3F, new CubeDeformation(0.0F))
				.texOffs(72, 93).addBox(-1.05F, 4.7F, -0.55F, 1.05F, 2.8F, 0.75F, new CubeDeformation(0.0F))
				.texOffs(72, 89).addBox(-0.9F, 0.6F, -1.8F, 0.95F, 2.75F, 0.8F, new CubeDeformation(0.0F))
				.texOffs(64, 89).addBox(-1.0F, -3.55F, -2.3F, 1.15F, 2.5F, 0.8F, new CubeDeformation(0.0F))
				.texOffs(35, 104).addBox(-0.5F, -5.95F, -1.9F, 0.95F, 2.7F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(26, 95).addBox(-1.4F, -1.9F, -2.5F, 1.15F, 3.85F, 1.2F, new CubeDeformation(0.0F))
				.texOffs(102, 104).addBox(-0.4F, -8.45F, -1.4F, 1.65F, 3.3F, 1.1F, new CubeDeformation(0.0F))
				.texOffs(93, 0).addBox(-0.1F, 2.1F, -0.5F, 1.25F, 2.45F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(35, 95).addBox(-0.3F, -0.2F, -0.7F, 0.85F, 5.85F, 0.8F, new CubeDeformation(0.0F))
				.texOffs(41, 106).addBox(-0.6F, -2.3F, -0.3F, 0.95F, 2.95F, 0.7F, new CubeDeformation(0.0F))
				.texOffs(103, 17).addBox(-1.3F, -5.3F, 0.1F, 1.25F, 3.75F, 0.5F, new CubeDeformation(0.0F))
				.texOffs(108, 59).addBox(-1.7F, -6.1F, 0.0F, 1.05F, 2.15F, 0.9F, new CubeDeformation(0.0F))
				.texOffs(128, 83).addBox(-2.1F, -8.6F, 0.2F, 1.25F, 3.05F, 1.1F, new CubeDeformation(0.0F))
				.texOffs(68, 12).addBox(-1.8F, -9.7F, -0.2F, 1.25F, 1.35F, 0.9F, new CubeDeformation(0.0F))
				.texOffs(91, 40).addBox(-0.8F, -4.4F, -0.9F, 0.55F, 8.15F, 0.6F, new CubeDeformation(0.0F))
				.texOffs(68, 8).addBox(-1.8F, -2.9F, -0.9F, 0.45F, 3.25F, 0.6F, new CubeDeformation(0.0F))
				.texOffs(10, 8).addBox(-1.4F, -3.6F, -0.8F, 0.65F, 1.15F, 0.4F, new CubeDeformation(0.0F))
				.texOffs(75, 9).addBox(-1.4F, -0.1F, -0.8F, 0.65F, 2.45F, 0.4F, new CubeDeformation(0.0F))
				.texOffs(6, 48).addBox(0.4F, -3.2F, -0.4F, 1.25F, 2.25F, 1.1F, new CubeDeformation(0.0F))
				.texOffs(17, 21).addBox(0.8F, -5.9F, -0.2F, 1.25F, 3.35F, 1.3F, new CubeDeformation(0.0F))
				.texOffs(39, 58).addBox(1.1F, -7.9F, 0.1F, 1.35F, 3.35F, 1.5F, new CubeDeformation(0.0F))
				.texOffs(106, 22).addBox(0.3F, -9.6F, 0.8F, 1.65F, 2.75F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 83).addBox(-1.0F, -10.45F, -2.3F, 1.75F, 3.2F, 1.5F, new CubeDeformation(0.0F))
				.texOffs(81, 107).addBox(-0.8F, -8.9F, -1.7F, 1.1F, 2.0F, 1.9F, new CubeDeformation(0.0F))
				.texOffs(57, 101).addBox(0.4F, -2.1F, -0.6F, 0.95F, 3.55F, 0.6F, new CubeDeformation(0.0F))
				.texOffs(73, 5).addBox(-1.2F, -10.0F, -1.2F, 2.3F, 0.9F, 2.7F, new CubeDeformation(0.0F))
				.texOffs(49, 115).addBox(-0.3F, -0.8F, 0.3F, 0.95F, 3.65F, 0.6F, new CubeDeformation(0.0F)), PartPose.offset(-4.3F, -13.0F, -7.6F));

		PartDefinition bashka = mode.addOrReplaceChild("bashka", CubeListBuilder.create().texOffs(40, 85).addBox(-3.1F, -27.8F, -4.2F, 8.0F, 7.7F, 7.5F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition nozdri = bashka.addOrReplaceChild("nozdri", CubeListBuilder.create().texOffs(76, 11).addBox(2.2F, -0.3F, -0.4F, 0.5F, 0.6F, 1.2F, new CubeDeformation(0.0F))
				.texOffs(81, 4).addBox(1.3F, -0.7F, -0.7F, 1.0F, 1.4F, 1.5F, new CubeDeformation(0.0F))
				.texOffs(87, 0).addBox(-1.9F, -0.7F, -0.7F, 1.0F, 1.4F, 1.5F, new CubeDeformation(0.0F))
				.texOffs(72, 11).addBox(-2.3F, -0.3F, -0.4F, 0.5F, 0.6F, 1.2F, new CubeDeformation(0.0F)), PartPose.offset(0.7F, -22.0F, -5.0F));

		PartDefinition gubaverh = bashka.addOrReplaceChild("gubaverh", CubeListBuilder.create().texOffs(14, 39).addBox(-0.1F, -22.3F, -5.0F, 3.4F, 0.4F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(115, 18).addBox(-0.5F, -23.3F, -4.8F, 4.2F, 1.6F, 0.8F, new CubeDeformation(0.0F)), PartPose.offset(-0.7F, 1.1F, 0.0F));

		PartDefinition podborodok = bashka.addOrReplaceChild("podborodok", CubeListBuilder.create().texOffs(39, 101).addBox(-1.3F, -0.7F, -0.6F, 3.0F, 2.0F, 1.8F, new CubeDeformation(0.0F))
				.texOffs(54, 80).addBox(-1.7F, -0.4F, -1.1F, 3.8F, 1.4F, 2.3F, new CubeDeformation(0.0F))
				.texOffs(48, 75).addBox(-1.9F, -0.9F, -0.4F, 4.2F, 1.0F, 1.6F, new CubeDeformation(0.0F)), PartPose.offset(0.7F, -19.5F, -4.4F));

		PartDefinition guba = bashka.addOrReplaceChild("guba", CubeListBuilder.create().texOffs(20, 118).addBox(-1.5F, -20.8F, -5.1F, 4.8F, 0.6F, 1.1F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition jeki = bashka.addOrReplaceChild("jeki", CubeListBuilder.create().texOffs(125, 56).addBox(5.9F, -23.6F, -4.5F, 3.2F, 1.9F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(75, 76).addBox(0.9F, -23.6F, -4.5F, 3.2F, 1.9F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.1F, 0.0F, 0.0F));

		PartDefinition nose = bashka.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(76, 0).addBox(-0.8F, 0.1F, -1.5F, 1.6F, 2.5F, 1.5F, new CubeDeformation(0.0F))
				.texOffs(67, 80).addBox(-1.3F, -0.5F, -0.8F, 2.6F, 2.4F, 1.4F, new CubeDeformation(0.0F))
				.texOffs(93, 93).addBox(-1.1F, -1.5F, 0.0F, 2.2F, 3.1F, 1.6F, new CubeDeformation(0.0F)), PartPose.offset(0.9F, -23.7F, -5.9F));

		PartDefinition bone23 = bashka.addOrReplaceChild("bone23", CubeListBuilder.create().texOffs(102, 9).addBox(-1.6F, -24.5F, -5.0F, 0.8F, 1.8F, 0.6F, new CubeDeformation(0.0F)), PartPose.offset(0.9F, 0.0F, 0.0F));

		PartDefinition bone24 = bashka.addOrReplaceChild("bone24", CubeListBuilder.create().texOffs(34, 83).addBox(0.8F, -24.5F, -5.0F, 0.8F, 1.8F, 0.8F, new CubeDeformation(0.0F)), PartPose.offset(0.9F, 0.0F, 0.0F));

		PartDefinition bone25 = bashka.addOrReplaceChild("bone25", CubeListBuilder.create().texOffs(5, 40).addBox(-1.9F, -24.2F, -4.5F, 3.8F, 0.3F, 0.3F, new CubeDeformation(0.0F)), PartPose.offset(0.9F, 0.0F, 0.0F));

		PartDefinition vekoniz = bashka.addOrReplaceChild("vekoniz", CubeListBuilder.create().texOffs(83, 8).addBox(3.3F, -0.2F, -4.6F, 1.2F, 0.3F, 0.8F, new CubeDeformation(0.0F))
				.texOffs(67, 117).addBox(-3.9F, 0.1F, -4.6F, 8.4F, 0.4F, 1.4F, new CubeDeformation(0.0F))
				.texOffs(3, 37).addBox(-3.9F, -0.2F, -4.6F, 1.2F, 0.3F, 0.8F, new CubeDeformation(0.0F)), PartPose.offset(0.6F, -24.0F, 0.0F));

		PartDefinition veko = bashka.addOrReplaceChild("veko", CubeListBuilder.create().texOffs(67, 114).addBox(-3.9F, -0.2F, 0.4F, 8.2F, 0.5F, 1.3F, new CubeDeformation(0.0F)), PartPose.offset(0.7F, -25.0F, -5.3F));

		PartDefinition bone26 = bashka.addOrReplaceChild("bone26", CubeListBuilder.create().texOffs(38, 83).addBox(0.6F, -26.6F, -5.4F, 0.6F, 1.5F, 1.2F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition brov1 = bashka.addOrReplaceChild("brov1", CubeListBuilder.create().texOffs(15, 41).addBox(-1.4F, -0.9F, -1.1F, 1.3F, 1.5F, 1.3F, new CubeDeformation(0.0F))
				.texOffs(30, 86).addBox(-1.4F, -0.2F, -0.7F, 3.1F, 0.8F, 0.9F, new CubeDeformation(0.0F)), PartPose.offset(3.2F, -25.7F, -4.4F));

		PartDefinition brov2 = bashka.addOrReplaceChild("brov2", CubeListBuilder.create().texOffs(108, 18).addBox(0.2F, -1.2F, -0.8F, 1.3F, 1.5F, 1.3F, new CubeDeformation(0.0F))
				.texOffs(73, 13).addBox(-1.6F, -0.5F, -0.4F, 3.1F, 0.8F, 0.9F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, -25.4F, -4.7F));

		PartDefinition bone27 = bashka.addOrReplaceChild("bone27", CubeListBuilder.create().texOffs(12, 49).addBox(-0.9F, -26.4F, -5.3F, 1.8F, 4.6F, 1.1F, new CubeDeformation(0.0F)), PartPose.offset(0.9F, -0.2F, 0.0F));

		PartDefinition armorHead = mode.addOrReplaceChild("armorHead", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.4F, -25.9F, -0.1F, -0.0698F, 0.0F, 0.0F));

		PartDefinition bone96 = armorHead.addOrReplaceChild("bone96", CubeListBuilder.create(), PartPose.offset(0.0F, -0.5073F, -0.7907F));

		PartDefinition armorHead_r1 = bone96.addOrReplaceChild("armorHead_r1", CubeListBuilder.create().texOffs(133, 39).addBox(-2.6939F, -7.7767F, -4.6042F, 7.952F, 5.864F, 7.796F, new CubeDeformation(0.0F))
				.texOffs(132, 9).addBox(-2.8939F, -7.9767F, -4.8042F, 8.352F, 6.264F, 8.196F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.6091F, 0.0283F, -0.0031F));

		PartDefinition armorHead_r2 = bone96.addOrReplaceChild("armorHead_r2", CubeListBuilder.create().texOffs(0, 79).addBox(-3.6159F, -4.8506F, -3.9509F, 9.796F, 5.62F, 9.796F, new CubeDeformation(0.0F))
				.texOffs(63, 54).addBox(-3.8159F, -5.0506F, -4.1509F, 10.196F, 6.02F, 10.196F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2164F, 0.0283F, -0.0031F));

		PartDefinition bone97 = armorHead.addOrReplaceChild("bone97", CubeListBuilder.create(), PartPose.offset(0.0F, -0.5073F, -0.7907F));

		PartDefinition armorHead_r3 = bone97.addOrReplaceChild("armorHead_r3", CubeListBuilder.create().texOffs(95, 33).addBox(-1.9499F, -10.5286F, -6.8767F, 6.464F, 5.42F, 6.464F, new CubeDeformation(0.0F))
				.texOffs(0, 95).addBox(-2.1499F, -10.7286F, -7.0767F, 6.864F, 5.82F, 6.864F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.0018F, 0.0283F, -0.0031F));

		PartDefinition bone98 = armorHead.addOrReplaceChild("bone98", CubeListBuilder.create(), PartPose.offset(0.0F, -0.5073F, -0.7907F));

		PartDefinition armorHead_r4 = bone98.addOrReplaceChild("armorHead_r4", CubeListBuilder.create().texOffs(1, 41).addBox(-0.8059F, -12.3174F, -6.0839F, 4.176F, 2.088F, 4.176F, new CubeDeformation(0.0F))
				.texOffs(118, 27).addBox(-1.0059F, -12.5174F, -6.2839F, 4.576F, 2.488F, 4.576F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.0018F, 0.0283F, -0.0031F));

		PartDefinition bone99 = armorHead.addOrReplaceChild("bone99", CubeListBuilder.create(), PartPose.offset(0.0F, -0.5073F, -0.7907F));

		PartDefinition armorHead_r5 = bone99.addOrReplaceChild("armorHead_r5", CubeListBuilder.create().texOffs(117, 66).addBox(0.0941F, -9.5965F, 9.6434F, 2.376F, 2.376F, 2.588F, new CubeDeformation(0.0F))
				.texOffs(20, 121).addBox(-0.1059F, -9.7965F, 9.4434F, 2.776F, 2.776F, 3.788F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1763F, 0.0283F, -0.0031F));

		PartDefinition bone95 = armorHead.addOrReplaceChild("bone95", CubeListBuilder.create(), PartPose.offset(0.0F, -0.5073F, -0.7907F));

		PartDefinition armorHead_r6 = bone95.addOrReplaceChild("armorHead_r6", CubeListBuilder.create().texOffs(57, 40).addBox(-4.3379F, -2.1186F, -4.4119F, 11.24F, 2.388F, 10.84F, new CubeDeformation(0.0F))
				.texOffs(9, 40).addBox(-6.5479F, 0.0751F, -6.5179F, 15.66F, 1.044F, 15.66F, new CubeDeformation(0.0F))
				.texOffs(56, 23).addBox(-4.4379F, -2.2186F, -4.5119F, 11.44F, 2.588F, 11.04F, new CubeDeformation(0.0F))
				.texOffs(7, 21).addBox(-6.7479F, -0.1249F, -6.7179F, 16.06F, 1.0F, 16.06F, new CubeDeformation(0.0F))
				.texOffs(125, 98).addBox(-0.3379F, -2.7186F, -4.8119F, 3.24F, 2.688F, 1.9F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2164F, 0.0283F, -0.0031F));

		PartDefinition armorHead_r7 = bone95.addOrReplaceChild("armorHead_r7", CubeListBuilder.create().texOffs(0, 139).addBox(-8.6359F, 1.0321F, -9.6499F, 19.836F, 1.0F, 20.88F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.4F, 0.0F, -0.2164F, 0.0283F, -0.0031F));

		PartDefinition hair = mode.addOrReplaceChild("hair", CubeListBuilder.create().texOffs(129, 70).addBox(-3.8F, -1.9F, -5.0F, 9.4F, 1.9F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -26.0F, 0.0F));

		PartDefinition bone6 = hair.addOrReplaceChild("bone6", CubeListBuilder.create().texOffs(79, 9).addBox(-2.3F, 2.1F, -1.1F, 1.0F, 2.4F, 0.4F, new CubeDeformation(0.0F))
				.texOffs(71, 9).addBox(2.5F, 2.1F, -0.8F, 1.2F, 1.4F, 0.4F, new CubeDeformation(0.0F)), PartPose.offset(1.6F, -2.9F, -4.7F));

		PartDefinition bone7 = hair.addOrReplaceChild("bone7", CubeListBuilder.create().texOffs(4, 0).addBox(2.4F, 3.1F, -0.6F, 0.4F, 2.1F, 0.3F, new CubeDeformation(0.0F))
				.texOffs(60, 116).addBox(-2.0F, 3.1F, -0.2F, 0.8F, 3.2F, 0.5F, new CubeDeformation(0.0F)), PartPose.offset(-1.6F, -3.9F, -5.2F));

		PartDefinition bone8 = hair.addOrReplaceChild("bone8", CubeListBuilder.create().texOffs(4, 3).addBox(-1.7F, 2.4F, -1.1F, 0.3F, 1.0F, 0.3F, new CubeDeformation(0.0F))
				.texOffs(26, 0).addBox(0.3F, 2.4F, -0.8F, 0.6F, 1.7F, 0.2F, new CubeDeformation(0.0F)), PartPose.offset(2.1F, -3.2F, -4.8F));

		PartDefinition bone9 = hair.addOrReplaceChild("bone9", CubeListBuilder.create().texOffs(103, 59).addBox(-0.2F, 0.9F, -1.1F, 1.3F, 1.6F, 0.5F, new CubeDeformation(0.0F)), PartPose.offset(-2.3F, -1.7F, -4.3F));

		PartDefinition bone10 = hair.addOrReplaceChild("bone10", CubeListBuilder.create().texOffs(13, 8).addBox(0.0F, 0.3F, -0.4F, 0.3F, 1.0F, 0.4F, new CubeDeformation(0.0F)), PartPose.offset(-1.1F, -1.1F, -5.2F));

		PartDefinition bone11 = hair.addOrReplaceChild("bone11", CubeListBuilder.create().texOffs(105, 27).addBox(-1.3F, 4.8F, 3.8F, 0.8F, 4.4F, 1.3F, new CubeDeformation(0.0F)), PartPose.offset(6.6F, -5.1F, -3.8F));

		PartDefinition hairrr_r1 = bone11.addOrReplaceChild("hairrr_r1", CubeListBuilder.create().texOffs(125, 72).addBox(-0.2F, -0.9F, -0.8F, 0.7F, 4.0F, 0.9F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.3F, 5.5F, -0.5F, 0.0F, 0.0F, 0.0175F));

		PartDefinition bone12 = hair.addOrReplaceChild("bone12", CubeListBuilder.create().texOffs(111, 66).addBox(0.3F, 6.6F, -1.9F, 0.8F, 4.1F, 1.4F, new CubeDeformation(0.0F))
				.texOffs(12, 109).addBox(-2.5F, 6.1F, 5.6F, 2.5F, 5.5F, 1.6F, new CubeDeformation(0.0F)), PartPose.offset(4.9F, -6.9F, -2.1F));

		PartDefinition bone13 = hair.addOrReplaceChild("bone13", CubeListBuilder.create().texOffs(24, 0).addBox(0.4F, 2.2F, -1.3F, 0.4F, 5.0F, 0.4F, new CubeDeformation(0.0F))
				.texOffs(22, 0).addBox(0.8F, 1.7F, 2.3F, 0.3F, 5.9F, 0.3F, new CubeDeformation(0.0F)), PartPose.offset(5.2F, -2.5F, -1.0F));

		PartDefinition bone14 = hair.addOrReplaceChild("bone14", CubeListBuilder.create().texOffs(31, 95).addBox(-1.7F, 2.2F, 1.9F, 0.9F, 4.4F, 0.8F, new CubeDeformation(0.0F))
				.texOffs(110, 28).addBox(-1.5F, 2.7F, -1.4F, 0.7F, 3.8F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(6.8F, -3.0F, 0.0F));

		PartDefinition bone15 = hair.addOrReplaceChild("bone15", CubeListBuilder.create().texOffs(88, 93).addBox(4.4F, 4.3F, 5.5F, 0.8F, 5.5F, 1.4F, new CubeDeformation(0.0F))
				.texOffs(0, 37).addBox(-5.1F, 4.3F, 0.1F, 0.4F, 5.2F, 1.9F, new CubeDeformation(0.0F)), PartPose.offset(0.7F, -5.1F, -2.6F));

		PartDefinition bone16 = hair.addOrReplaceChild("bone16", CubeListBuilder.create().texOffs(83, 0).addBox(1.9F, 2.0F, -3.3F, 0.5F, 2.5F, 1.3F, new CubeDeformation(0.0F))
				.texOffs(23, 6).addBox(1.7F, 2.0F, 1.7F, 0.4F, 4.6F, 1.2F, new CubeDeformation(0.0F))
				.texOffs(133, 24).addBox(1.8F, 2.0F, 2.5F, 0.6F, 4.7F, 2.7F, new CubeDeformation(0.0F)), PartPose.offset(-6.1F, -2.8F, -1.6F));

		PartDefinition bone17 = hair.addOrReplaceChild("bone17", CubeListBuilder.create().texOffs(18, 27).addBox(-0.6F, 1.1F, -2.3F, 0.4F, 4.1F, 1.5F, new CubeDeformation(0.0F))
				.texOffs(113, 9).addBox(1.2F, 1.1F, 5.5F, 4.8F, 6.1F, 1.6F, new CubeDeformation(0.0F)), PartPose.offset(-3.8F, -1.9F, -2.0F));

		PartDefinition bone18 = hair.addOrReplaceChild("bone18", CubeListBuilder.create().texOffs(43, 117).addBox(-0.3F, 2.6F, 1.5F, 1.4F, 5.6F, 0.9F, new CubeDeformation(0.0F))
				.texOffs(72, 97).addBox(-0.8F, 2.6F, -0.9F, 0.3F, 5.8F, 0.8F, new CubeDeformation(0.0F))
				.texOffs(2, 0).addBox(-0.5F, 2.6F, -2.3F, 0.3F, 3.4F, 0.2F, new CubeDeformation(0.0F)), PartPose.offset(-3.8F, -3.4F, 1.8F));

		PartDefinition bone100 = hair.addOrReplaceChild("bone100", CubeListBuilder.create().texOffs(96, 48).addBox(-4.4F, 0.7F, -3.3F, 8.9F, 3.4F, 6.9F, new CubeDeformation(0.0F)), PartPose.offset(0.9F, -1.0F, 0.0F));

		PartDefinition bone19 = hair.addOrReplaceChild("bone19", CubeListBuilder.create().texOffs(10, 23).addBox(-0.3F, 0.9F, -0.3F, 1.4F, 2.2F, 0.3F, new CubeDeformation(0.0F)), PartPose.offset(3.4F, -1.7F, -5.4F));

		PartDefinition bone20 = hair.addOrReplaceChild("bone20", CubeListBuilder.create().texOffs(22, 95).addBox(0.0F, -0.1F, -0.8F, 0.8F, 5.4F, 0.9F, new CubeDeformation(0.0F)), PartPose.offset(5.6F, -0.2F, 0.0F));

		PartDefinition bone21 = hair.addOrReplaceChild("bone21", CubeListBuilder.create().texOffs(122, 46).addBox(-0.4F, -2.1F, -1.4F, 0.8F, 4.8F, 2.3F, new CubeDeformation(0.0F)), PartPose.offset(5.2F, 1.3F, 3.8F));

		PartDefinition bone22 = hair.addOrReplaceChild("bone22", CubeListBuilder.create().texOffs(0, 75).addBox(-0.8F, 0.8F, -0.8F, 0.5F, 5.3F, 2.1F, new CubeDeformation(0.0F)), PartPose.offset(-3.2F, -1.6F, 0.0F));

		PartDefinition visiuchka = mode.addOrReplaceChild("visiuchka", CubeListBuilder.create(), PartPose.offset(0.9F, -17.0F, -4.0F));

		PartDefinition bone36 = visiuchka.addOrReplaceChild("bone36", CubeListBuilder.create().texOffs(99, 0).addBox(-5.7F, -0.4F, -0.9F, 11.9F, 0.7F, 7.7F, new CubeDeformation(0.0F)), PartPose.offset(-0.7F, 3.5F, 0.0F));

		PartDefinition bone28 = visiuchka.addOrReplaceChild("bone28", CubeListBuilder.create(), PartPose.offset(-1.2F, -0.5F, -0.6F));

		PartDefinition cube_r1 = bone28.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(6, 0).addBox(-1.5901F, -0.292F, -0.4F, 3.0F, 0.3F, 0.2F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.4F, 0.0F, 0.1F, 0.0F, 0.0F, 0.4014F));

		PartDefinition bone29 = visiuchka.addOrReplaceChild("bone29", CubeListBuilder.create(), PartPose.offset(1.4F, -0.6F, -1.0F));

		PartDefinition cube_r2 = bone29.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(6, 1).addBox(-1.5279F, -0.087F, -0.1F, 2.8F, 0.3F, 0.2F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.2F, 0.0F, 0.2F, 0.0F, 0.0F, -0.3665F));

		PartDefinition bone30 = visiuchka.addOrReplaceChild("bone30", CubeListBuilder.create(), PartPose.offset(-0.1F, 0.7F, -1.0F));

		PartDefinition cube_r3 = bone30.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(6, 3).addBox(-0.1042F, -0.605F, -0.2F, 0.3F, 0.9F, 0.2F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.1F, 0.3F, 0.2F, 0.0F, 0.0F, 0.1047F));

		PartDefinition bone32 = visiuchka.addOrReplaceChild("bone32", CubeListBuilder.create(), PartPose.offset(-0.1F, 0.3F, -0.5F));

		PartDefinition cube_r4 = bone32.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(11, 2).addBox(-0.1446F, -0.3673F, -0.3F, 0.7F, 0.7F, 0.4F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.2F, -0.2F, -0.3F, 0.0F, 0.0F, 0.7505F));

		PartDefinition bone31 = visiuchka.addOrReplaceChild("bone31", CubeListBuilder.create(), PartPose.offset(0.1F, 0.9F, -0.6F));

		PartDefinition cube_r5 = bone31.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(68, 0).addBox(-0.0952F, -0.7017F, -0.3F, 0.3F, 1.3F, 0.4F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.2F, -0.2F, 0.0F, 0.0F, -0.0349F));

		PartDefinition bone33 = visiuchka.addOrReplaceChild("bone33", CubeListBuilder.create(), PartPose.offset(0.1F, 0.1F, -0.7F));

		PartDefinition cube_r6 = bone33.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(15, 8).addBox(-0.0909F, -0.489F, -0.4F, 0.5F, 0.9F, 0.5F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.1F, 0.0F, -0.1F, 0.0F, 0.0F, -0.1047F));

		PartDefinition ruka = mode.addOrReplaceChild("ruka", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone4 = ruka.addOrReplaceChild("bone4", CubeListBuilder.create().texOffs(36, 125).addBox(-0.7F, -1.2F, -6.2F, 2.9F, 3.1F, 6.9F, new CubeDeformation(0.0F)), PartPose.offset(-6.1F, -13.4F, -0.6F));

		PartDefinition bone5 = ruka.addOrReplaceChild("bone5", CubeListBuilder.create().texOffs(119, 85).addBox(-1.0F, -1.0F, -2.2F, 2.0F, 6.4F, 3.8F, new CubeDeformation(0.0F)), PartPose.offset(-5.1F, -18.0F, -1.0F));

		PartDefinition bigpalecc1 = ruka.addOrReplaceChild("bigpalecc1", CubeListBuilder.create(), PartPose.offset(-3.1F, -13.5F, -7.6F));

		PartDefinition _r1 = bigpalecc1.addOrReplaceChild("_r1", CubeListBuilder.create().texOffs(146, 59).addBox(-0.7F, -0.7F, -1.4F, 1.4F, 1.5F, 2.5F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3806F, -0.0522F, 0.1296F));

		PartDefinition bone = ruka.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(92, 71).addBox(-1.1F, -1.6F, -2.0F, 1.7F, 3.1F, 2.4F, new CubeDeformation(0.0F)), PartPose.offset(-5.8F, -13.1F, -6.0F));

		PartDefinition bigpalecc2 = ruka.addOrReplaceChild("bigpalecc2", CubeListBuilder.create().texOffs(82, 71).addBox(-1.4F, -0.8F, -1.1F, 2.5F, 2.0F, 2.3F, new CubeDeformation(0.0F)), PartPose.offset(-3.6F, -14.0F, -6.5F));

		PartDefinition bone2 = ruka.addOrReplaceChild("bone2", CubeListBuilder.create(), PartPose.offset(-5.9F, -12.8F, -8.0F));

		PartDefinition _r2 = bone2.addOrReplaceChild("_r2", CubeListBuilder.create().texOffs(112, 83).addBox(-1.0F, -1.6F, -1.2F, 2.0F, 2.7F, 2.2F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1766F, -0.4427F, -0.0947F));

		PartDefinition bone3 = ruka.addOrReplaceChild("bone3", CubeListBuilder.create(), PartPose.offset(-4.5F, -12.8F, -8.0F));

		PartDefinition _r3 = bone3.addOrReplaceChild("_r3", CubeListBuilder.create().texOffs(64, 85).addBox(-0.3F, -1.3F, -1.5F, 2.0F, 2.1F, 1.1F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.4F, 0.0F, 0.0F, 0.1519F, -0.4512F, -0.0374F));

		PartDefinition nogi = ruka.addOrReplaceChild("nogi", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition noga2 = nogi.addOrReplaceChild("noga2", CubeListBuilder.create(), PartPose.offset(0.9F, 0.0F, 0.0F));

		PartDefinition bone105 = noga2.addOrReplaceChild("bone105", CubeListBuilder.create().texOffs(117, 21).addBox(-2.0F, -0.4F, -2.5F, 4.4F, 2.4F, 2.9F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, -2.0F, -2.0F));

		PartDefinition bone109 = noga2.addOrReplaceChild("bone109", CubeListBuilder.create().texOffs(108, 59).addBox(-4.8F, -3.3F, -2.7F, 3.9F, 1.0F, 4.8F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone107 = noga2.addOrReplaceChild("bone107", CubeListBuilder.create().texOffs(107, 72).addBox(-1.6F, 1.6F, -2.4F, 3.5F, 5.4F, 4.3F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, -8.0F, 0.0F));

		PartDefinition bone106 = noga2.addOrReplaceChild("bone106", CubeListBuilder.create().texOffs(57, 102).addBox(-2.0F, -0.9F, -1.6F, 4.4F, 2.9F, 3.8F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, -2.0F, 0.0F));

		PartDefinition noga1 = nogi.addOrReplaceChild("noga1", CubeListBuilder.create(), PartPose.offset(0.9F, 0.0F, 0.0F));

		PartDefinition bone102 = noga1.addOrReplaceChild("bone102", CubeListBuilder.create().texOffs(47, 69).addBox(-2.3F, -2.4F, -1.5F, 4.4F, 2.4F, 2.9F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 0.0F, -3.0F));

		PartDefinition bone103 = noga1.addOrReplaceChild("bone103", CubeListBuilder.create().texOffs(60, 72).addBox(-2.1F, -0.3F, 0.3F, 3.9F, 1.0F, 4.8F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, -3.0F, -3.0F));

		PartDefinition bone104 = noga1.addOrReplaceChild("bone104", CubeListBuilder.create().texOffs(124, 35).addBox(-1.9F, 1.6F, -2.4F, 3.5F, 5.5F, 4.3F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, -8.0F, 0.0F));

		PartDefinition bone108 = noga1.addOrReplaceChild("bone108", CubeListBuilder.create().texOffs(134, 82).addBox(0.7F, -2.9F, -1.6F, 4.4F, 2.9F, 3.8F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}


	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		//bb_main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return body;
	}

	@Override
	public void setupAnim(TraderWitchEntity pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.animateWalk(TraderWitchAnimations.ANIMATION_WALK, pLimbSwing, pLimbSwingAmount, 2.0f, 2.5f);
		this.animate(pEntity.idleAnimationState, TraderWitchAnimations3.ANIMATION_IDLE, pAgeInTicks, 1f);
		this.animate(pEntity.attackAnimationState, TraderWitchAnimations2.ANIMATION_ATTACK, pAgeInTicks, 1f);
	}
}