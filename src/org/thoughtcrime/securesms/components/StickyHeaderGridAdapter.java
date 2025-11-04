package org.thoughtcrime.securesms.components;

import android.view.View;
import android.view.ViewGroup;

public abstract class StickyHeaderGridAdapter {
    public abstract static class ItemViewHolder {
        public final View itemView;
        public ItemViewHolder(View v) { itemView = v; }
    }
    public abstract static class HeaderViewHolder {
        public final View itemView;
        public HeaderViewHolder(View v) { itemView = v; }
    }

    public abstract HeaderViewHolder onCreateHeaderViewHolder(ViewGroup parent, int headerType);
    public abstract ItemViewHolder onCreateItemViewHolder(ViewGroup parent, int itemType);
    public abstract void onBindHeaderViewHolder(HeaderViewHolder holder, int section);
    public abstract void onBindItemViewHolder(ItemViewHolder holder, int section, int offset);
    public abstract int getSectionCount();
    public abstract int getSectionItemCount(int section);
}