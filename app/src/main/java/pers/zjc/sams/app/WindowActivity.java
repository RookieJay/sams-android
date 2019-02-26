package pers.zjc.sams.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.zp.android.zlib.base.BaseActivity;
import com.zp.android.zlib.base.BaseFragment;
import com.zp.android.zlib.di.BaseComponent;

import java.util.List;

import pers.zjc.sams.R;


public class WindowActivity extends BaseActivity {

    public static final String KEY_FRAGMENT = "key_fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String clz = getIntent().getStringExtra(KEY_FRAGMENT);
        if (TextUtils.isEmpty(clz)) {
            throw new IllegalArgumentException("the fragment is null.");
        }
        try {
            Fragment fragment = Fragment.instantiate(this, clz);
            Bundle data = getIntent().getExtras();
            Bundle bundle = data == null ? new Bundle() : data;
            fragment.setArguments(bundle);
            replace(R.id.container, fragment, fragment.getClass().getSimpleName());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                if (fragment instanceof BaseFragment) {
                    ((BaseFragment)fragment).onNewIntent(intent);
                }
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected BaseComponent createComponent() {
        return null;
    }

    @Override
    protected void inject(BaseComponent component) { }
}
