package org.thoughtcrime.securesms.preferences.widgets;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.preference.PreferenceDialogFragmentCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import org.thoughtcrime.securesms.R;

public class ColorPickerPreferenceDialogFragmentCompat extends PreferenceDialogFragmentCompat {

  private GridView gridView;

  public static ColorPickerPreferenceDialogFragmentCompat newInstance(String key) {
    ColorPickerPreferenceDialogFragmentCompat fragment = new ColorPickerPreferenceDialogFragmentCompat();
    Bundle                                b      = new Bundle(1);
    b.putString(PreferenceDialogFragmentCompat.ARG_KEY, key);
    fragment.setArguments(b);
    return fragment;
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    ColorPickerPreference pref = getColorPickerPreference();

    AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
    builder.setTitle(pref.getDialogTitle());

    View view = LayoutInflater.from(getContext()).inflate(R.layout.color_picker_dialog, null);
    gridView = view.findViewById(R.id.grid);
    gridView.setAdapter(new ColorAdapter(getContext(), pref.getColors()));
    gridView.setNumColumns(pref.getColumns());
    gridView.setOnItemClickListener((parent, view1, position, id) -> {
      int color = (int) parent.getItemAtPosition(position);
      getColorPickerPreference().setColor(color);
      onClick(getDialog(), DialogInterface.BUTTON_POSITIVE);
      dismiss();
    });

    builder.setView(view);
    builder.setNegativeButton(android.R.string.cancel, null);

    return builder.create();
  }

  @Override
  public void onDialogClosed(boolean positiveResult) {}

  ColorPickerPreference getColorPickerPreference() {
    return (ColorPickerPreference) getPreference();
  }

  private static class ColorAdapter extends BaseAdapter {

    private final Context context;
    private final int[]   colors;

    private ColorAdapter(Context context, int[] colors) {
      this.context = context;
      this.colors  = colors;
    }

    @Override
    public int getCount() {
      return colors.length;
    }

    @Override
    public Object getItem(int position) {
      return colors[position];
    }

    @Override
    public long getItemId(int position) {
      return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      ImageView view = (ImageView) convertView;

      if (view == null) {
        view = (ImageView) LayoutInflater.from(context).inflate(R.layout.color_picker_item, parent, false);
      }

      view.setBackgroundColor(colors[position]);
      view.setTag(colors[position]);

      return view;
    }
  }
}
