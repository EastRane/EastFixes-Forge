package me.eastrane.eastfixes.mixin;

import com.cerbon.bosses_of_mass_destruction.entity.custom.gauntlet.GauntletEntity;
import com.cerbon.bosses_of_mass_destruction.entity.custom.gauntlet.LaserAction;
import net.minecraft.world.entity.ai.attributes.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.UUID;

@Mixin(LaserAction.class)
public class GauntletLaserMixin {
    @Unique
    private final UUID LASER_DAMAGE_MODIFIER_UUID = UUID.fromString("b5f20efc-54ff-4cd0-852c-376da0db705e");

    @Redirect(method="applyLaserToEntities",
            at = @At(value = "INVOKE",
                    target = "Lcom/cerbon/bosses_of_mass_destruction/entity/custom/gauntlet/GauntletEntity;getAttributeValue(Lnet/minecraft/world/entity/ai/attributes/Attribute;)D"))
    private double clearBaseDamage(GauntletEntity instance, Attribute attribute) {
        return 0;
    }

    @Redirect(method="applyLaserToEntities",
            at = @At(value = "INVOKE", ordinal = 0, target = "Lnet/minecraft/world/entity/ai/attributes/AttributeInstance;setBaseValue(D)V"))
    private void createLaserModifier(AttributeInstance instance, double p_22101_) {
        instance.addTransientModifier(new AttributeModifier(LASER_DAMAGE_MODIFIER_UUID, "Laser Damage", 0.75, AttributeModifier.Operation.MULTIPLY_BASE));
    }

    @Redirect(method="applyLaserToEntities",
        at = @At(value = "INVOKE", ordinal = 1, target = "Lnet/minecraft/world/entity/ai/attributes/AttributeInstance;setBaseValue(D)V"))
    private void removeLaserModifier(AttributeInstance instance, double p_22101_) {
        instance.removeModifier(LASER_DAMAGE_MODIFIER_UUID);
    }

}
