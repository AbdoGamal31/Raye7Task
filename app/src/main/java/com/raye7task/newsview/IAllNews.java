package com.raye7task.newsview;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.raye7task.R;
import com.raye7task.newsmodel.NewsGroupedByDay;

import java.util.List;

import static com.raye7task.MyApplication.getContext;

public interface IAllNews {

    default void showLoadingIndicator() {
    }

    default void hideLoadingIndicator() {
    }

    default void openExternalWebView(String URL, Context context) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URL));
        context.startActivity(browserIntent);
    }

    default void displayNewsGroupedByDay(List<NewsGroupedByDay> newsGroupedByDayList) {
    }

    default Dialog buildShowErrorOverlay(Context context,String errorMessage, int errorIcon) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.error_overlay);

        TextView errorDescription = (TextView) dialog.findViewById(R.id.tv_overlay_des);
        errorDescription.setText(errorMessage);
        ImageView image = (ImageView) dialog.findViewById(R.id.iv_overlay_icon);
        image.setImageResource(errorIcon);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        ImageButton imageButton=(ImageButton)dialog.findViewById(R.id.btn_overlay_cancel);
        imageButton.setOnClickListener(view -> dialog.dismiss());
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        return dialog;
    }

    void showErrorOverlay(String errorMessage, int errorIcon,boolean buttonVisibility);
}
