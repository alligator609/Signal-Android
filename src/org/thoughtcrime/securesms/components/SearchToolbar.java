package org.thoughtcrime.securesms.components;


import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.annotation.MainThread;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;

import org.thoughtcrime.securesms.R;
import org.thoughtcrime.securesms.util.ViewUtil;

public class SearchToolbar extends Toolbar {

  private static final String TAG = SearchToolbar.class.getSimpleName();

  private FilterListener listener;

  private SearchView searchView;

  public SearchToolbar(Context context) {
    super(context);
    initialize();
  }

  public SearchToolbar(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    initialize();
  }

  public SearchToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    initialize();
  }

  private void initialize() {
    inflate(getContext(), R.menu.search, getMenu());

    MenuItem searchItem = getMenu().findItem(R.id.action_search);
    this.searchView = (SearchView) searchItem.getActionView();

    ImageView searchClose = ViewUtil.findById(searchView, R.id.search_close_btn);
    searchClose.setColorFilter(getContext().getResources().getColor(R.color.grey_500), PorterDuff.Mode.MULTIPLY);

    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
      @Override
      public boolean onQueryTextSubmit(String query) {
        searchView.clearFocus();
        return true;
      }

      @Override
      public boolean onQueryTextChange(String newText) {
        if (listener != null) listener.onFilterChanged(newText);
        return true;
      }
    });

    searchView.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
  }

  public void setListener(FilterListener listener) {
    this.listener = listener;
  }

  public void onFilter(String text) {
    if (!TextUtils.equals(text, searchView.getQuery())) {
      searchView.setQuery(text, false);
    }
  }

  @MainThread
  public void setPrompt(int resId) {
    searchView.setQueryHint(getContext().getString(resId));
  }

  @MainThread
  public void setIcon(@NonNull Drawable drawable) {
    searchView.setIconifiedByDefault(true);

    ImageView icon = ViewUtil.findById(searchView, R.id.search_mag_icon);
    icon.setImageDrawable(drawable);

    searchView.setOnSearchClickListener(v -> {
      searchView.setIconifiedByDefault(true);
      if (listener != null) listener.onSearchClicked();
    });
  }

  public interface FilterListener {
    void onFilterChanged(String query);
    void onSearchClicked();
  }
}
