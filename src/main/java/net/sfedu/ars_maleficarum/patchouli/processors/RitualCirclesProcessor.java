package net.sfedu.ars_maleficarum.patchouli.processors;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.sfedu.ars_maleficarum.block.custom.chalkSymbols.ritualCoreEntity.RitualCoreEntity;
import net.sfedu.ars_maleficarum.ritual.RitualTypes;
import net.sfedu.ars_maleficarum.ritual.ritualTemplates.CircleRitual;
import vazkii.patchouli.api.IComponentProcessor;
import vazkii.patchouli.api.IVariable;
import vazkii.patchouli.api.IVariableProvider;

import java.util.Map;

public class RitualCirclesProcessor implements IComponentProcessor {
    CircleRitual ritual;
    RitualCoreEntity.ChalkType smallType;
    RitualCoreEntity.ChalkType mediumType;
    RitualCoreEntity.ChalkType largeType;

    RitualCoreEntity.ChalkType coreType;



    @Override
    public void setup(Level level, IVariableProvider iVariableProvider) {
        ritual=RitualTypes.getDefaultByName(new ResourceLocation(iVariableProvider.get("ritual").asString()));
        smallType = ritual.getSmallCircleType();
        mediumType = ritual.getMediumCircleType();
        largeType = ritual.getLargeCircleType();
        coreType = ritual.getCoreType();
        System.out.println(ritual.getName() + " WOWOWO SMALL =" + smallType + ", MEDIUM = " + mediumType + ", LARGE = " + largeType);
        System.out.println(ritual.getName() + " WOWOWO REQUIRE SMALL =" + ritual.doesRequireSmallCircle() + ", REQUIRE MEDIUM = " + ritual.doesRequireMediumCircle() + ", REQUIRE LARGE = " + ritual.doesRequireSmallCircle());
    }

    private String getSizePath(RitualCoreEntity.CircleSize size) {

        RitualCoreEntity.ChalkType type = switch(size) {
            case LARGE -> largeType;
            case MEDIUM -> mediumType;
            case SMALL -> smallType;
            default -> coreType;
        };

        String colorPath = type.getSerializedName();
        String sizePath = size.getSerializedName();

        if ((size== RitualCoreEntity.CircleSize.SMALL && ritual.doesRequireMediumCircle()) || (size== RitualCoreEntity.CircleSize.MEDIUM && ritual.doesRequireLargeCircle())) sizePath+="_joints";
        return "ars_maleficarum:textures/books/shadow_grimoire/util_textures/circles/" + colorPath + "_" + sizePath + ".png";
    }

    private boolean checkForRequirement(String key) {
        switch (key) {
            case "small" ->  {
                return ritual.doesRequireSmallCircle();
            }
            case "medium" ->  {
                return ritual.doesRequireMediumCircle();
            }
            case "large" ->  {
                return ritual.doesRequireLargeCircle();
            }
            default -> {
                return true;
            }
        }
    }

    @Override
    public IVariable process(Level level, String key) {
        if((key.startsWith("small")|| key.startsWith("medium") || key.startsWith("large") || key.startsWith("core"))) {
            return checkForRequirement(key) ? IVariable.wrap(getSizePath(RitualCoreEntity.CircleSize.valueOf(key.toUpperCase()))) : IVariable.empty();
        }
        else if ((key.startsWith("components"))) {
            String s = "";
            for (Map.Entry<Item,Integer> component : ritual.getComponents().entrySet()) {
                s += "$(li)" + component.getKey().getDescription().getString();
                if (component.getValue() > 1) s+= " ("+component.getValue() + ")$()";
            }
            return IVariable.wrap(s);
        }
        else if (key.startsWith("name")) {
            return IVariable.wrap(ritual.getName());
        }

        else if (key.endsWith("header_size")) {
            if (key.startsWith("1header_size")) return IVariable.wrap(ritual.getName().length()<22);
            else if (key.startsWith("085header_size")) return IVariable.wrap(ritual.getName().length()>=22 && ritual.getName().length() < 26);
            else return IVariable.wrap(ritual.getName().length()>=26);
        }
        return null;
    }
}
