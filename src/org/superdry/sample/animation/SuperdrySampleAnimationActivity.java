/******
 * SuperdrySampleAnimation
 * 
 * The MIT License
 * Copyright (c) 2011 superdry
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package org.superdry.sample.animation;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SuperdrySampleAnimationActivity extends Activity {

    private static final int[] resId = {
            android.R.anim.accelerate_decelerate_interpolator,
            android.R.anim.accelerate_interpolator, android.R.anim.anticipate_interpolator,
            android.R.anim.anticipate_overshoot_interpolator, android.R.anim.bounce_interpolator,
            android.R.anim.cycle_interpolator, android.R.anim.decelerate_interpolator,
            android.R.anim.linear_interpolator, android.R.anim.overshoot_interpolator
    };

    private int animResId;
    private int repeatMode;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.message);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(R.string.goto_github, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri
                        .parse("https://github.com/superdry/SuperdrySampleAnimation"));
                startActivity(intent);
            }
        });
        builder.create().show();

        ArrayAdapter<String> anim_adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item);
        anim_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        anim_adapter.add("AlphaAnimation");
        anim_adapter.add("RotateAnimation");
        anim_adapter.add("ScaleAnimation");
        anim_adapter.add("TranslateAnimation");
        Spinner anim_spinner = (Spinner) findViewById(R.id.anim_spinner);
        anim_spinner.setAdapter(anim_adapter);
        anim_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Spinner spinner = (Spinner) parent;
                String item = (String) spinner.getSelectedItem();
                if (item.equals("AlphaAnimation")) {
                    animResId = R.anim.anim_alpha;
                } else if (item.equals("RotateAnimation")) {
                    animResId = R.anim.anim_rotate;
                } else if (item.equals("ScaleAnimation")) {
                    animResId = R.anim.anim_scale;
                } else if (item.equals("TranslateAnimation")) {
                    animResId = R.anim.anim_trans;
                }
                setAnimation(animResId, repeatMode);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                setAnimation(animResId, repeatMode);
            }
        });

        ArrayAdapter<String> repert_adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item);
        repert_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        repert_adapter.add("restart");
        repert_adapter.add("reverse");
        Spinner repeat_spinner = (Spinner) findViewById(R.id.repeat_spinner);

        repeat_spinner.setAdapter(repert_adapter);
        repeat_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Spinner spinner = (Spinner) parent;
                String item = (String) spinner.getSelectedItem();
                if (item.equals("restart")) {
                    repeatMode = Animation.RESTART;
                } else if (item.equals("reverse")) {
                    repeatMode = Animation.REVERSE;
                }
                setAnimation(animResId, repeatMode);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                setAnimation(animResId, repeatMode);
            }
        });

    }

    private void setAnimation(int resAnimId, int repeatMode) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        for (int i = 0; i < resId.length; i++) {
            View v = inflater.inflate(R.layout.animation, null);
            TextView title = (TextView) v.findViewById(R.id.title);
            title.setText(getResources().getText(resId[i]));
            ImageView image_icon = (ImageView) v.findViewById(R.id.image);
            Animation anim_icon = AnimationUtils.loadAnimation(this, resAnimId);
            anim_icon.setInterpolator(this, resId[i]);
            anim_icon.setRepeatMode(repeatMode);
            image_icon.setAnimation(anim_icon);
            layout.addView(v);
        }

        ScrollView mainLayout = (ScrollView) findViewById(R.id.mainLayout);
        mainLayout.removeAllViews();
        mainLayout.addView(layout);

    }
}
