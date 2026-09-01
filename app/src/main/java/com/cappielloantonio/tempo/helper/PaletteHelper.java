package com.cappielloantonio.tempo.helper;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.ColorUtils;
import androidx.palette.graphics.Palette;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.cappielloantonio.tempo.glide.CustomGlideRequest;

/**
 * Keys a screen's header wash to the cover art it is showing.
 * <p>
 * The wash always paints, starting flat in the page colour, and is replaced once the
 * cover resolves and yields a usable colour. Missing art, a failed load or a palette
 * with nothing in it all simply leave the flat version in place.
 */
public final class PaletteHelper {
    /** How far the sampled colour is pulled toward the page ground. */
    private static final float TOWARD_GROUND = 0.55f;

    /** Big enough to sample reliably, small enough not to be worth caching separately. */
    private static final int SAMPLE_SIZE = 256;

    private PaletteHelper() {
    }

    public static void applyHeaderWash(@NonNull View target, @Nullable String coverArtId, @ColorInt int ground) {
        if (coverArtId == null) return;

        Glide.with(target)
                .asBitmap()
                .load(CustomGlideRequest.createUrl(coverArtId, SAMPLE_SIZE))
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Palette.from(resource).generate(palette -> {
                            if (palette == null) return;

                            int seed = palette.getDominantColor(ground);

                            // Blending toward the page colour keeps this a wash rather than a
                            // block, and works the same way whichever theme is running.
                            target.setBackground(wash(ColorUtils.blendARGB(seed, ground, TOWARD_GROUND), ground));
                        });
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                        // Nothing to release: the wash is a drawable we built, not the bitmap.
                    }
                });
    }

    private static GradientDrawable wash(@ColorInt int top, @ColorInt int ground) {
        return new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[]{top, ground, ground});
    }
}
