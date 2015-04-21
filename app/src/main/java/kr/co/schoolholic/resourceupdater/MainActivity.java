package kr.co.schoolholic.resourceupdater;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.internal.widget.ViewUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import kr.co.schoolholic.util.Trace;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, UpdateActivity.class);
        startActivityForResult(intent, 123);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setBackgroundImage() {
        ImageView iv = (ImageView) findViewById(R.id.background);
//        iv.setBackground();
    }
}
