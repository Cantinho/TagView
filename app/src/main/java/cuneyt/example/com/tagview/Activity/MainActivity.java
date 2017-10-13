package cuneyt.example.com.tagview.Activity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.cunoraz.tagview.TagItem;
import com.cunoraz.tagview.TagView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cuneyt.example.com.tagview.Constants;
import cuneyt.example.com.tagview.Models.TagClass;
import cuneyt.example.com.tagview.R;

public class MainActivity extends AppCompatActivity {

    private TagView tagGroup;

    private EditText editText;

    /**
     * sample country list
     */
    private ArrayList<TagClass> tagList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tagGroup = findViewById(R.id.tag_group);
        editText = findViewById(R.id.editText);

        prepareTags();

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setTags(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tagGroup.setOnTagLongClickListener(new TagView.OnTagItemLongClickListener() {
            @Override
            public void onTagLongClick(TagItem tagItem, int position) {
                Toast.makeText(MainActivity.this, "Long Click: " + tagItem.getText(), Toast.LENGTH_SHORT).show();
            }
        });

        tagGroup.setOnTagClickListener(new TagView.OnTagItemClickListener() {
            @Override
            public void onTagClick(TagItem tagItem, int position) {
                editText.setText(tagItem.getText());
                editText.setSelection(tagItem.getText().length());//to set cursor position

            }
        });
        tagGroup.setOnTagDeleteListener(new TagView.OnTagItemDeleteListener() {

            @Override
            public void onTagDeleted(final TagView view, final TagItem tagItem, final int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("\"" + tagItem.getText() + "\" will be delete. Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        view.remove(position);
                        Toast.makeText(MainActivity.this, "\"" + tagItem.getText() + "\" deleted", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("No", null);
                builder.show();

            }
        });


    }

    private void prepareTags() {
        tagList = new ArrayList<>();
        JSONArray jsonArray;
        JSONObject temp;
        try {
            jsonArray = new JSONArray(Constants.COUNTRIES);
            for (int i = 0; i < jsonArray.length(); i++) {
                temp = jsonArray.getJSONObject(i);
                tagList.add(new TagClass(temp.getString("code"), temp.getString("name")));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setTags(CharSequence cs) {
        /**
         * for empty edittext
         */
        if (cs.toString().equals("")) {
            tagGroup.add(new ArrayList<TagItem>());
            return;
        }

        String text = cs.toString();
        ArrayList<TagItem> tagItems = new ArrayList<>();
        TagItem tagItem;


        for (int i = 0; i < tagList.size(); i++) {
            if (tagList.get(i).getName().toLowerCase().startsWith(text.toLowerCase())) {
                tagItem = new TagItem(getBaseContext(),tagList.get(i).getName());
                tagItem.setBorderRadius(10f);
                tagItem.setLayoutColor(Color.parseColor(tagList.get(i).getColor()));
                if (i % 2 == 0) // you can set deletable or not
                    tagItem.setDeletable(true);
                tagItems.add(tagItem);
            }
        }
        tagGroup.add(tagItems);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
