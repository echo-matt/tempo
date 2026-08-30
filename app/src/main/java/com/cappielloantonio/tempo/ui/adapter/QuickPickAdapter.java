package com.cappielloantonio.tempo.ui.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cappielloantonio.tempo.databinding.ItemHomeQuickPickBinding;
import com.cappielloantonio.tempo.glide.CustomGlideRequest;
import com.cappielloantonio.tempo.interfaces.ClickCallback;
import com.cappielloantonio.tempo.subsonic.models.AlbumID3;
import com.cappielloantonio.tempo.util.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The shortcut grid at the top of the home screen. It shows the head of the
 * recently-played list, so it shares that feed rather than fetching its own.
 */
public class QuickPickAdapter extends RecyclerView.Adapter<QuickPickAdapter.ViewHolder> {
    private static final int MAX_ITEMS = 6;

    private final ClickCallback click;

    private List<AlbumID3> albums;

    public QuickPickAdapter(ClickCallback click) {
        this.click = click;
        this.albums = Collections.emptyList();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemHomeQuickPickBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AlbumID3 album = albums.get(position);

        holder.item.quickPickNameLabel.setText(album.getName());

        CustomGlideRequest.Builder
                .from(holder.itemView.getContext(), album.getCoverArtId(), CustomGlideRequest.ResourceType.Album)
                .build()
                .into(holder.item.quickPickCoverImageView);
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }

    public void setItems(List<AlbumID3> albums) {
        // Keep the grid to a couple of rows; it is a shortcut, not a catalogue.
        this.albums = albums == null
                ? Collections.emptyList()
                : new ArrayList<>(albums.subList(0, Math.min(albums.size(), MAX_ITEMS)));

        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemHomeQuickPickBinding item;

        ViewHolder(ItemHomeQuickPickBinding item) {
            super(item.getRoot());

            this.item = item;

            itemView.setOnClickListener(v -> onClick());
        }

        private void onClick() {
            Bundle bundle = new Bundle();
            bundle.putParcelable(Constants.ALBUM_OBJECT, albums.get(getBindingAdapterPosition()));

            click.onAlbumClick(bundle);
        }
    }
}
