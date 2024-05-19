package net.sfedu.ars_maleficarum.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.sfedu.ars_maleficarum.entity.custom.TraderWitchEntity;

public class TraderWitchModel <T extends TraderWitchEntity> extends HierarchicalModel<T> {

    private final ModelPart demon;

    public TraderWitchModel(ModelPart root) {
        this.demon = root.getChild("demon");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition demon = partdefinition.addOrReplaceChild("demon", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 24.0F, 2.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition noga = demon.addOrReplaceChild("noga", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition prav1 = noga.addOrReplaceChild("prav1", CubeListBuilder.create().texOffs(119, 127).addBox(-5.1F, -3.4F, -3.7F, 8.2F, 12.0F, 5.1F, new CubeDeformation(0.0F))
                .texOffs(113, 75).addBox(-4.3F, -4.2F, -4.3F, 9.1F, 10.0F, 6.9F, new CubeDeformation(0.0F))
                .texOffs(54, 6).addBox(-7.1F, -4.2F, -4.3F, 2.8F, 6.1F, 6.9F, new CubeDeformation(0.0F))
                .texOffs(47, 39).addBox(-6.5F, 1.9F, -3.9F, 2.2F, 1.9F, 6.1F, new CubeDeformation(0.0F))
                .texOffs(99, 59).addBox(-3.5F, -4.2F, -4.8F, 9.2F, 7.9F, 7.9F, new CubeDeformation(0.0F)), PartPose.offset(3.7F, -17.1F, -3.0F));

        PartDefinition lev1 = noga.addOrReplaceChild("lev1", CubeListBuilder.create().texOffs(141, 114).addBox(-1.9F, -0.6F, -3.0F, 6.6F, 3.5F, 4.5F, new CubeDeformation(0.0F))
                .texOffs(79, 1).addBox(-1.7F, 0.4F, -3.4F, 9.8F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.4F, 5.9F));

        PartDefinition prav3 = noga.addOrReplaceChild("prav3", CubeListBuilder.create().texOffs(117, 144).addBox(-2.7F, -0.6F, -1.9F, 6.6F, 3.5F, 4.5F, new CubeDeformation(0.0F))
                .texOffs(127, 38).addBox(-2.5F, 0.4F, -2.3F, 9.8F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.8F, -3.4F, -4.4F));

        PartDefinition lev3 = noga.addOrReplaceChild("lev3", CubeListBuilder.create().texOffs(93, 127).addBox(-5.0F, -2.7F, -2.9F, 8.2F, 12.0F, 5.1F, new CubeDeformation(0.0F))
                .texOffs(116, 21).addBox(-4.2F, -3.5F, -3.5F, 9.1F, 10.0F, 6.9F, new CubeDeformation(0.0F))
                .texOffs(73, 142).addBox(-7.0F, -3.5F, -3.5F, 2.8F, 6.1F, 6.9F, new CubeDeformation(0.0F))
                .texOffs(0, 58).addBox(-6.4F, 2.6F, -3.1F, 2.2F, 1.9F, 6.1F, new CubeDeformation(0.0F))
                .texOffs(99, 96).addBox(-3.4F, -3.5F, -4.0F, 9.2F, 7.9F, 7.9F, new CubeDeformation(0.0F)), PartPose.offset(3.6F, -17.8F, 5.4F));

        PartDefinition prav2 = noga.addOrReplaceChild("prav2", CubeListBuilder.create().texOffs(45, 130).addBox(-3.9F, -2.0F, -1.0F, 1.9F, 3.0F, 2.1F, new CubeDeformation(0.0F))
                .texOffs(141, 13).addBox(-2.7F, -1.1F, -2.0F, 5.5F, 10.6F, 4.1F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -10.4F, -4.1F));

        PartDefinition lev2 = noga.addOrReplaceChild("lev2", CubeListBuilder.create().texOffs(57, 130).addBox(-3.9F, -3.3F, -1.3F, 1.9F, 3.0F, 2.1F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-2.7F, -2.4F, -2.3F, 5.5F, 10.6F, 4.1F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -9.1F, 5.4F));

        PartDefinition puzo = demon.addOrReplaceChild("puzo", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone = puzo.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(0, 28).addBox(-9.0F, -2.5F, -8.5F, 13.5F, 10.7F, 18.5F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-10.8F, -0.7F, -9.1F, 17.3F, 8.2F, 19.9F, new CubeDeformation(0.0F)), PartPose.offset(6.4F, -26.0F, 0.0F));

        PartDefinition bone2 = puzo.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(133, 101).addBox(-2.7F, -5.2F, -9.4F, 9.0F, 9.7F, 3.1F, new CubeDeformation(0.0F))
                .texOffs(54, 0).addBox(-3.6F, 0.4F, -10.1F, 14.2F, 5.5F, 1.4F, new CubeDeformation(0.0F))
                .texOffs(125, 95).addBox(-3.6F, 0.4F, 10.4F, 14.2F, 5.5F, 1.4F, new CubeDeformation(0.0F))
                .texOffs(138, 64).addBox(-2.7F, -5.2F, 8.0F, 9.0F, 9.7F, 3.1F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -25.8F, 0.0F));

        PartDefinition bone3 = puzo.addOrReplaceChild("bone3", CubeListBuilder.create().texOffs(77, 96).addBox(-0.2F, -1.3F, -7.9F, 3.5F, 7.9F, 15.6F, new CubeDeformation(0.0F))
                .texOffs(0, 113).addBox(-1.5F, -1.3F, 0.6F, 9.2F, 7.0F, 8.1F, new CubeDeformation(0.0F))
                .texOffs(107, 112).addBox(-1.5F, -1.3F, -8.0F, 9.2F, 7.0F, 8.1F, new CubeDeformation(0.0F)), PartPose.offset(-3.6F, -24.0F, 0.4F));

        PartDefinition bone4 = puzo.addOrReplaceChild("bone4", CubeListBuilder.create().texOffs(28, 130).addBox(4.4F, 2.0F, -8.3F, 3.6F, 5.1F, 8.6F, new CubeDeformation(0.0F))
                .texOffs(0, 63).addBox(-5.2F, 0.2F, -7.3F, 12.2F, 11.2F, 16.3F, new CubeDeformation(0.0F))
                .texOffs(128, 50).addBox(4.4F, 2.0F, 1.4F, 3.6F, 5.1F, 8.6F, new CubeDeformation(0.0F))
                .texOffs(98, 0).addBox(5.0F, 7.4F, -6.7F, 4.0F, 6.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -39.2F, 0.0F));

        PartDefinition bone5 = puzo.addOrReplaceChild("bone5", CubeListBuilder.create().texOffs(39, 79).addBox(-3.8F, -0.5F, -7.9F, 10.0F, 2.9F, 17.2F, new CubeDeformation(0.0F))
                .texOffs(56, 10).addBox(-4.1F, 6.3F, -8.1F, 11.5F, 11.4F, 17.9F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -41.3F, 0.0F));

        PartDefinition bone35 = puzo.addOrReplaceChild("bone35", CubeListBuilder.create().texOffs(49, 41).addBox(-2.8F, -39.8F, -7.9F, 8.0F, 21.0F, 17.2F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition ruki = demon.addOrReplaceChild("ruki", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone7 = ruki.addOrReplaceChild("bone7", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone9 = bone7.addOrReplaceChild("bone9", CubeListBuilder.create().texOffs(34, 118).addBox(-3.7F, -1.6F, -1.6F, 9.5F, 5.4F, 6.7F, new CubeDeformation(0.0F))
                .texOffs(121, 0).addBox(-4.5F, -0.1F, 1.2F, 9.3F, 8.4F, 5.4F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -39.0F, 9.0F));

        PartDefinition bone10 = bone7.addOrReplaceChild("bone10", CubeListBuilder.create().texOffs(140, 139).addBox(-1.9F, -0.2F, -2.2F, 6.9F, 6.8F, 5.2F, new CubeDeformation(0.0F))
                .texOffs(140, 122).addBox(-2.5F, -1.5F, -2.9F, 6.5F, 4.8F, 5.4F, new CubeDeformation(0.0F)), PartPose.offset(-2.3F, -29.6F, 13.5F));

        PartDefinition bone11 = bone7.addOrReplaceChild("bone11", CubeListBuilder.create().texOffs(82, 39).addBox(-3.4F, -0.7F, -3.3F, 8.1F, 4.5F, 5.3F, new CubeDeformation(0.0F)), PartPose.offset(-1.1F, -23.1F, 15.0F));

        PartDefinition bone12 = bone7.addOrReplaceChild("bone12", CubeListBuilder.create().texOffs(135, 151).addBox(-2.7F, -1.6F, -1.4F, 3.2F, 4.1F, 3.6F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, -20.7F, 12.6F));

        PartDefinition bone6 = bone7.addOrReplaceChild("bone6", CubeListBuilder.create(), PartPose.offset(3.5F, -18.0F, 12.0F));

        PartDefinition ruka1_r1 = bone6.addOrReplaceChild("ruka1_r1", CubeListBuilder.create().texOffs(152, 36).addBox(-0.2F, -19.8F, 7.2F, 2.9F, 4.1F, 2.9F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.1F, 15.5F, -12.6F, -0.2443F, 0.0F, 0.1396F));

        PartDefinition bone17 = bone7.addOrReplaceChild("bone17", CubeListBuilder.create().texOffs(145, 132).addBox(-0.8F, -0.7F, -1.8F, 2.8F, 4.3F, 2.9F, new CubeDeformation(0.0F)), PartPose.offset(1.3F, -20.0F, 16.1F));

        PartDefinition bone18 = bone7.addOrReplaceChild("bone18", CubeListBuilder.create(), PartPose.offset(1.9F, -16.5F, 15.9F));

        PartDefinition ruka1_r2 = bone18.addOrReplaceChild("ruka1_r2", CubeListBuilder.create().texOffs(0, 113).addBox(1.4F, -20.6F, 5.5F, 2.0F, 3.9F, 2.3F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, 14.0F, -15.6F, -0.5061F, 0.0F, 0.0F));

        PartDefinition bone19 = bone7.addOrReplaceChild("bone19", CubeListBuilder.create().texOffs(12, 153).addBox(-1.5F, -1.3F, -2.8F, 2.6F, 5.0F, 3.2F, new CubeDeformation(0.0F)), PartPose.offset(-0.8F, -19.7F, 16.9F));

        PartDefinition bone20 = bone7.addOrReplaceChild("bone20", CubeListBuilder.create(), PartPose.offset(-0.9F, -16.0F, 16.0F));

        PartDefinition ruka1_r3 = bone20.addOrReplaceChild("ruka1_r3", CubeListBuilder.create().texOffs(99, 59).addBox(1.4F, -20.3F, 6.2F, 2.0F, 4.7F, 2.3F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, 13.3F, -15.8F, -0.4712F, 0.0F, 0.0F));

        PartDefinition bone21 = bone7.addOrReplaceChild("bone21", CubeListBuilder.create().texOffs(47, 90).addBox(-1.7F, -0.8F, -1.5F, 2.3F, 3.3F, 2.5F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, -19.5F, 15.9F));

        PartDefinition bone22 = bone7.addOrReplaceChild("bone22", CubeListBuilder.create(), PartPose.offset(-3.5F, -17.2F, 15.8F));

        PartDefinition ruka1_r4 = bone22.addOrReplaceChild("ruka1_r4", CubeListBuilder.create().texOffs(114, 127).addBox(1.7F, -20.5F, 5.1F, 1.7F, 3.2F, 2.4F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.6F, 14.4F, -15.7F, -0.5061F, 0.0F, 0.0F));

        PartDefinition bone8 = ruki.addOrReplaceChild("bone8", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone13 = bone8.addOrReplaceChild("bone13", CubeListBuilder.create().texOffs(66, 120).addBox(-3.7F, -2.6F, -3.4F, 9.5F, 5.4F, 6.7F, new CubeDeformation(0.0F))
                .texOffs(0, 128).addBox(-4.5F, -1.1F, -4.9F, 9.3F, 8.4F, 5.4F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -38.0F, -9.0F));

        PartDefinition bone14 = bone8.addOrReplaceChild("bone14", CubeListBuilder.create().texOffs(69, 132).addBox(-2.8F, -1.1F, -2.3F, 6.5F, 4.8F, 5.4F, new CubeDeformation(0.0F))
                .texOffs(0, 141).addBox(-2.2F, 0.2F, -2.8F, 6.9F, 6.8F, 5.2F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -30.0F, -12.0F));

        PartDefinition bone15 = bone8.addOrReplaceChild("bone15", CubeListBuilder.create().texOffs(64, 99).addBox(-4.5F, 0.2F, -2.3F, 8.1F, 4.5F, 5.3F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -24.0F, -13.0F));

        PartDefinition bone16 = bone8.addOrReplaceChild("bone16", CubeListBuilder.create().texOffs(149, 77).addBox(-1.7F, -0.3F, 2.2F, 3.2F, 4.1F, 3.6F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, -22.0F, -15.0F));

        PartDefinition bone23 = bone8.addOrReplaceChild("bone23", CubeListBuilder.create(), PartPose.offset(3.6F, -18.2F, -11.0F));

        PartDefinition ruka2_r1 = bone23.addOrReplaceChild("ruka2_r1", CubeListBuilder.create().texOffs(149, 85).addBox(0.6F, -18.6F, -8.6F, 2.9F, 4.1F, 2.9F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.2F, 15.7F, 10.4F, 0.1918F, 0.0083F, 0.0968F));

        PartDefinition bone24 = bone8.addOrReplaceChild("bone24", CubeListBuilder.create().texOffs(91, 120).addBox(-1.3F, -0.1F, -1.9F, 2.8F, 4.3F, 2.9F, new CubeDeformation(0.0F)), PartPose.offset(1.8F, -20.6F, -13.6F));

        PartDefinition bone25 = bone8.addOrReplaceChild("bone25", CubeListBuilder.create(), PartPose.offset(1.7F, -16.0F, -14.0F));

        PartDefinition ruka2_r2 = bone25.addOrReplaceChild("ruka2_r2", CubeListBuilder.create().texOffs(104, 0).addBox(1.4F, -19.6F, -8.0F, 2.0F, 3.9F, 2.3F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.3F, 13.5F, 14.3F, 0.4538F, 0.0F, 0.0F));

        PartDefinition bone26 = bone8.addOrReplaceChild("bone26", CubeListBuilder.create().texOffs(0, 153).addBox(-1.4F, -1.0F, -0.7F, 2.6F, 5.0F, 3.2F, new CubeDeformation(0.0F)), PartPose.offset(-0.9F, -20.0F, -15.0F));

        PartDefinition bone27 = bone8.addOrReplaceChild("bone27", CubeListBuilder.create(), PartPose.offset(-1.1F, -15.9F, -14.3F));

        PartDefinition ruka2_r3 = bone27.addOrReplaceChild("ruka2_r3", CubeListBuilder.create().texOffs(40, 70).addBox(1.4F, -19.3F, -9.0F, 2.0F, 4.7F, 2.3F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.3F, 13.2F, 14.5F, 0.4014F, 0.0F, 0.0F));

        PartDefinition bone28 = bone8.addOrReplaceChild("bone28", CubeListBuilder.create().texOffs(57, 39).addBox(-1.3F, -0.3F, -1.0F, 2.3F, 3.3F, 2.5F, new CubeDeformation(0.0F)), PartPose.offset(-3.4F, -20.0F, -14.4F));

        PartDefinition bone29 = bone8.addOrReplaceChild("bone29", CubeListBuilder.create(), PartPose.offset(-4.0F, -17.5F, -14.5F));

        PartDefinition ruka2_r4 = bone29.addOrReplaceChild("ruka2_r4", CubeListBuilder.create().texOffs(85, 99).addBox(1.7F, -19.9F, -7.5F, 1.7F, 3.2F, 2.4F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.1F, 14.7F, 14.6F, 0.4538F, 0.0F, 0.0F));

        PartDefinition hvost = demon.addOrReplaceChild("hvost", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone30 = hvost.addOrReplaceChild("bone30", CubeListBuilder.create().texOffs(149, 0).addBox(-2.1F, -1.3F, -1.7F, 2.6F, 7.3F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 28).addBox(-3.3F, 0.2F, -1.5F, 2.4F, 10.7F, 3.6F, new CubeDeformation(0.0F))
                .texOffs(0, 66).addBox(-4.3F, 3.5F, -1.3F, 2.6F, 9.7F, 3.2F, new CubeDeformation(0.0F)), PartPose.offset(-2.9F, -26.2F, 0.0F));

        PartDefinition bone31 = hvost.addOrReplaceChild("bone31", CubeListBuilder.create().texOffs(76, 79).addBox(-2.0F, -1.4F, -1.1F, 2.3F, 8.6F, 2.8F, new CubeDeformation(0.0F))
                .texOffs(0, 90).addBox(-3.3F, 2.4F, -1.0F, 2.5F, 7.8F, 2.5F, new CubeDeformation(0.0F))
                .texOffs(40, 99).addBox(-4.6F, 6.0F, -0.8F, 2.7F, 6.2F, 2.1F, new CubeDeformation(0.0F)), PartPose.offset(-6.1F, -17.3F, 0.0F));

        PartDefinition bone32 = hvost.addOrReplaceChild("bone32", CubeListBuilder.create().texOffs(99, 96).addBox(-1.9F, -1.7F, -0.6F, 2.4F, 5.1F, 1.7F, new CubeDeformation(0.0F))
                .texOffs(48, 153).addBox(-3.2F, 0.7F, -0.5F, 2.6F, 4.2F, 1.5F, new CubeDeformation(0.0F))
                .texOffs(8, 28).addBox(-5.2F, 2.5F, -0.4F, 3.6F, 3.2F, 1.3F, new CubeDeformation(0.0F)), PartPose.offset(-9.9F, -6.7F, 0.0F));

        PartDefinition bone33 = hvost.addOrReplaceChild("bone33", CubeListBuilder.create().texOffs(99, 75).addBox(-2.1F, -1.2F, -0.6F, 3.8F, 2.6F, 1.1F, new CubeDeformation(0.0F))
                .texOffs(0, 45).addBox(-4.5F, -0.6F, -0.5F, 4.7F, 0.7F, 0.9F, new CubeDeformation(0.0F)), PartPose.offset(-15.1F, -1.9F, 0.3F));

        PartDefinition bone34 = hvost.addOrReplaceChild("bone34", CubeListBuilder.create().texOffs(0, 15).addBox(-4.9F, -1.0F, -0.5F, 7.7F, 1.8F, 0.9F, new CubeDeformation(0.0F))
                .texOffs(145, 46).addBox(-10.3F, -2.2F, -2.3F, 6.0F, 3.7F, 4.8F, new CubeDeformation(0.0F)), PartPose.offset(-17.8F, -0.8F, 0.3F));

        PartDefinition roga = demon.addOrReplaceChild("roga", CubeListBuilder.create().texOffs(107, 191).addBox(-2.3F, -2.2F, 1.8F, 4.7F, 2.4F, 3.6F, new CubeDeformation(0.0F))
                .texOffs(74, 188).addBox(-2.5F, -6.3F, 2.4F, 4.4F, 5.0F, 2.7F, new CubeDeformation(0.0F))
                .texOffs(40, 189).addBox(-2.9F, -9.5F, 2.9F, 3.8F, 6.3F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(1, 186).addBox(-3.6F, -17.5F, 3.5F, 2.4F, 6.5F, 0.8F, new CubeDeformation(0.0F))
                .texOffs(14, 185).addBox(-3.3F, -13.1F, 3.3F, 3.0F, 6.2F, 1.2F, new CubeDeformation(0.0F))
                .texOffs(115, 214).addBox(-2.3F, -2.2F, -4.6F, 4.7F, 2.4F, 3.6F, new CubeDeformation(0.0F))
                .texOffs(84, 212).addBox(-2.5F, -6.3F, -4.3F, 4.4F, 5.0F, 2.7F, new CubeDeformation(0.0F))
                .texOffs(43, 209).addBox(-2.9F, -9.5F, -4.0F, 3.8F, 6.3F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(2, 214).addBox(-3.6F, -17.5F, -3.4F, 2.4F, 6.5F, 0.8F, new CubeDeformation(0.0F))
                .texOffs(17, 207).addBox(-3.3F, -13.1F, -3.6F, 3.0F, 6.2F, 1.2F, new CubeDeformation(0.0F)), PartPose.offset(1.3F, -52.9F, 0.0F));

        PartDefinition face = demon.addOrReplaceChild("face", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition brov1 = face.addOrReplaceChild("brov1", CubeListBuilder.create().texOffs(138, 77).addBox(-0.4F, -0.3F, -0.7F, 1.4F, 1.5F, 2.5F, new CubeDeformation(0.0F))
                .texOffs(109, 75).addBox(-1.7F, 0.1F, 0.8F, 2.2F, 1.4F, 1.9F, new CubeDeformation(0.0F))
                .texOffs(34, 58).addBox(-2.0F, -0.8F, -2.4F, 3.3F, 1.7F, 2.6F, new CubeDeformation(0.0F)), PartPose.offset(4.5F, -50.9F, 3.7F));

        PartDefinition brov2 = face.addOrReplaceChild("brov2", CubeListBuilder.create().texOffs(22, 58).addBox(-1.8F, -1.0F, -0.2F, 3.3F, 1.7F, 2.6F, new CubeDeformation(0.0F))
                .texOffs(133, 114).addBox(-0.2F, -0.5F, -1.8F, 1.4F, 1.5F, 2.5F, new CubeDeformation(0.0F))
                .texOffs(84, 109).addBox(-1.5F, -0.1F, -2.7F, 2.2F, 1.4F, 1.9F, new CubeDeformation(0.0F)), PartPose.offset(4.3F, -50.7F, -3.0F));

        PartDefinition nose = face.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(67, 6).addBox(-0.4F, -0.2F, -1.4F, 2.8F, 1.3F, 2.9F, new CubeDeformation(0.0F))
                .texOffs(10, 58).addBox(-0.4F, 0.5F, -1.7F, 2.3F, 1.1F, 3.5F, new CubeDeformation(0.0F))
                .texOffs(136, 13).addBox(-0.4F, 0.3F, 1.8F, 2.1F, 1.5F, 1.5F, new CubeDeformation(0.0F))
                .texOffs(112, 21).addBox(-0.4F, 0.3F, -3.2F, 2.1F, 1.5F, 1.5F, new CubeDeformation(0.0F))
                .texOffs(98, 9).addBox(-0.4F, -0.7F, -2.1F, 1.8F, 1.8F, 4.3F, new CubeDeformation(0.0F))
                .texOffs(29, 96).addBox(-0.4F, -2.9F, -1.6F, 1.1F, 2.4F, 3.3F, new CubeDeformation(0.0F)), PartPose.offset(4.7F, -48.4F, 0.3F));

        PartDefinition sheka1 = face.addOrReplaceChild("sheka1", CubeListBuilder.create().texOffs(36, 153).addBox(2.2F, -2.0F, -2.7F, 3.1F, 3.1F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(148, 28).addBox(-0.8F, -1.1F, -2.7F, 5.6F, 4.0F, 4.2F, new CubeDeformation(0.0F))
                .texOffs(93, 144).addBox(-4.2F, -2.9F, -2.7F, 7.6F, 4.6F, 3.5F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -45.5F, 6.0F));

        PartDefinition sheka2 = face.addOrReplaceChild("sheka2", CubeListBuilder.create().texOffs(24, 153).addBox(2.2F, -1.8F, -0.7F, 3.1F, 3.1F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(24, 144).addBox(-4.2F, -2.7F, -1.2F, 7.6F, 4.6F, 3.5F, new CubeDeformation(0.0F))
                .texOffs(48, 145).addBox(-0.8F, -0.9F, -1.9F, 5.6F, 4.0F, 4.2F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -45.7F, -4.9F));

        PartDefinition guba1 = face.addOrReplaceChild("guba1", CubeListBuilder.create().texOffs(45, 130).addBox(-1.2F, -0.4F, -3.5F, 2.5F, 1.3F, 7.7F, new CubeDeformation(0.0F))
                .texOffs(12, 32).addBox(-0.6F, 0.2F, 4.2F, 1.3F, 1.2F, 2.1F, new CubeDeformation(0.0F))
                .texOffs(12, 35).addBox(-0.6F, 0.2F, -5.6F, 1.3F, 1.2F, 2.1F, new CubeDeformation(0.0F)), PartPose.offset(5.9F, -46.1F, 0.0F));

        PartDefinition guba2 = face.addOrReplaceChild("guba2", CubeListBuilder.create().texOffs(54, 132).addBox(-0.7F, -1.2F, -5.0F, 2.0F, 1.5F, 10.7F, new CubeDeformation(0.0F))
                .texOffs(125, 64).addBox(-2.8F, -0.7F, 5.5F, 3.6F, 1.0F, 1.7F, new CubeDeformation(0.0F))
                .texOffs(74, 108).addBox(-2.8F, -0.7F, -6.6F, 3.6F, 1.0F, 1.7F, new CubeDeformation(0.0F)), PartPose.offset(6.6F, -44.0F, 0.0F));

        PartDefinition podborod = face.addOrReplaceChild("podborod", CubeListBuilder.create().texOffs(76, 79).addBox(-5.5F, -1.2F, -6.0F, 11.7F, 4.0F, 12.7F, new CubeDeformation(0.0F))
                .texOffs(137, 84).addBox(4.8F, -1.0F, -3.6F, 1.6F, 2.0F, 7.9F, new CubeDeformation(0.0F)), PartPose.offset(1.1F, -43.3F, 0.0F));

        PartDefinition veko1 = face.addOrReplaceChild("veko1", CubeListBuilder.create().texOffs(0, 14).addBox(0.1F, -0.2F, -2.2F, 0.5F, 1.0F, 3.8F, new CubeDeformation(0.0F))
                .texOffs(8, 39).addBox(-0.8F, 0.1F, -2.2F, 1.1F, 1.5F, 4.3F, new CubeDeformation(0.0F)), PartPose.offset(4.2F, -48.7F, 3.9F));

        PartDefinition veko2 = face.addOrReplaceChild("veko2", CubeListBuilder.create().texOffs(8, 14).addBox(0.7F, -0.5F, -1.6F, 0.5F, 1.0F, 3.8F, new CubeDeformation(0.0F))
                .texOffs(87, 140).addBox(-0.2F, -0.2F, -2.1F, 1.1F, 1.5F, 4.3F, new CubeDeformation(0.0F)), PartPose.offset(3.6F, -48.4F, -3.2F));

        PartDefinition shota = face.addOrReplaceChild("shota", CubeListBuilder.create().texOffs(59, 118).addBox(3.9F, 0.0F, -2.3F, 1.8F, 1.5F, 5.3F, new CubeDeformation(0.0F))
                .texOffs(8, 15).addBox(3.9F, -2.2F, 1.7F, 0.5F, 0.8F, 3.8F, new CubeDeformation(0.0F))
                .texOffs(0, 90).addBox(-5.0F, -4.8F, -5.2F, 8.8F, 11.8F, 11.1F, new CubeDeformation(0.0F))
                .texOffs(0, 15).addBox(3.9F, -2.2F, -4.8F, 0.5F, 0.8F, 3.8F, new CubeDeformation(0.0F))
                .texOffs(40, 99).addBox(-4.2F, -5.4F, -4.4F, 7.1F, 9.0F, 9.5F, new CubeDeformation(0.0F))
                .texOffs(99, 39).addBox(-5.8F, -3.4F, -4.5F, 8.7F, 9.7F, 9.7F, new CubeDeformation(0.0F)), PartPose.offset(0.4F, -47.9F, 0.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
    }

    @Override
    public void setupAnim(TraderWitchEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        demon.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart root() {
        return demon;
    }
}
