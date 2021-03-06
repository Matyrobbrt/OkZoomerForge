package com.matyrobbrt.okzoomer.api;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.ServiceLoader;

/**
 * The interface for interacting with OkZoomer's API.
 */
public interface OkZoomerAPI {

    /**
     * OkZoomer's mod ID
     */
    String MOD_ID = "okzoomer";

    /**
     * The singleton OkZoomer API instance.
     */
    OkZoomerAPI INSTANCE = Util.make(() -> {
        final var loader = ServiceLoader.load(OkZoomerAPI.class).iterator();
        if (!loader.hasNext()) {
            throw new NullPointerException("No OkZoomerApi was found on the classpath");
        }
        final var api = loader.next();
        if (loader.hasNext()) {
            throw new IllegalArgumentException("More than one OkZoomerApi was found!");
        }
        return api;
    });

    /**
     * Registers a {@link ZoomInstance} to the registry.
     *
     * @param instance the instance to register
     */
    @CanIgnoreReturnValue
    ZoomInstance registerZoom(ZoomInstance instance);

    /**
     * Initializes a zoom instance. It must be {@link #registerZoom(ZoomInstance) registered} to registry before being functional.
     *
     * @param instanceId         the ID for this zoom instance
     * @param defaultZoomDivisor the default zoom divisor. It will be this instance's initial zoom divisor value
     * @param transition         the zoom instance's transition mode. {@link com.matyrobbrt.okzoomer.api.transitions.InstantTransitionMode} is used if null
     * @param modifier           the zoom instance's mouse modifier. If null, no mouse modifier will be applied
     * @param overlay            the zoom instance's zoom overlay. If null, no zoom overlay will be applied
     */
    ZoomInstance createZoomInstance(ResourceLocation instanceId, float defaultZoomDivisor, TransitionMode transition, @Nullable MouseModifier modifier, @Nullable ZoomOverlay overlay);
}
