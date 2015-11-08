package roots.urban.com.urbanroots;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;

public class ShortcutActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shortcut);

        createIcon();
        finish();
    }

    private void createIcon(){
        Intent.ShortcutIconResource icon =
                Intent.ShortcutIconResource.fromContext(this, R.drawable.main_logo);

        Intent intent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");

        Intent launchIntent = new Intent(this, ReportActivity.class);

        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, launchIntent);
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "UrbanRoot Report");
        intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);

        setResult(RESULT_OK, intent);
    }
}
