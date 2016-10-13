package com.amsen.par.cewlrency.view.fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.amsen.par.cewlrency.BuildConfig;
import com.amsen.par.cewlrency.R;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Pär Amsen 2016
 */
public class DebugDialogFragment extends AppCompatDialogFragment {
    @BindView(R.id.version)
    TextView versionView;
    @BindView(R.id.userId)
    TextView userIdView;

    private String userId;
    private String version;

    @OnClick(R.id.copyVersion)
    public void onClickCopyVersion() {
        ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("versionCode and versionName", version);
        clipboard.setPrimaryClip(clip);

        Toast.makeText(getContext(), "Copied " + version, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.copyUserId)
    public void onClickCopyUserId() {
        ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("userId", userId);
        clipboard.setPrimaryClip(clip);

        Toast.makeText(getContext(), "Copied " + userId, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.close)
    public void onClickClose() {
        dismissAllowingStateLoss();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dialog_debug, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        userId = Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        version = String.format(Locale.US, "{code: \"%d\", name: \"%s\"}", BuildConfig.VERSION_CODE, BuildConfig.VERSION_NAME);

        userIdView.setText(String.format("{uuid: \"%s\"}", userId));
        this.versionView.setText(version);
    }
}
