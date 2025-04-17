package es.ifp.melodykeys;

import android.os.Bundle;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private HorizontalScrollView scrollView;

    // White keys
    private Button keyC3, keyD3, keyE3, keyF3, keyG3, keyA3, keyB3;
    private Button keyC4, keyD4, keyE4, keyF4, keyG4, keyA4, keyB4;
    private Button keyC5, keyD5, keyE5, keyF5, keyG5, keyA5, keyB5;
    private Button keyC6, keyD6, keyE6, keyF6, keyG6, keyA6, keyB6;
    private Button keyC7, keyD7, keyE7, keyF7, keyG7, keyA7, keyB7;

    // Black keys
    private Button keyCs3, keyDs3, keyFs3, keyGs3, keyAs3;
    private Button keyCs4, keyDs4, keyFs4, keyGs4, keyAs4;
    private Button keyCs5, keyDs5, keyFs5, keyGs5, keyAs5;
    private Button keyCs6, keyDs6, keyFs6, keyGs6, keyAs6;
    private Button keyCs7, keyDs7, keyFs7, keyGs7, keyAs7;

    // TextViews (labels)
    private TextView labelC3, labelD3, labelE3, labelF3, labelG3, labelA3, labelB3;
    private TextView labelC4, labelD4, labelE4, labelF4, labelG4, labelA4, labelB4;
    private TextView labelC5, labelD5, labelE5, labelF5, labelG5, labelA5, labelB5;
    private TextView labelC6, labelD6, labelE6, labelF6, labelG6, labelA6, labelB6;
    private TextView labelC7, labelD7, labelE7, labelF7, labelG7, labelA7, labelB7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        scrollView = findViewById(R.id.scrollView);

        // All keys init
        initializeAllPianoKeys();

        // TextViews init
        initializeTextViewLabelsOnPianoKeys();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void initializeAllPianoKeys() {
        // White keys
        keyC3 = findViewById(R.id.keyC3);
        keyD3 = findViewById(R.id.keyD3);
        keyE3 = findViewById(R.id.keyE3);
        keyF3 = findViewById(R.id.keyF3);
        keyG3 = findViewById(R.id.keyG3);
        keyA3 = findViewById(R.id.keyA3);
        keyB3 = findViewById(R.id.keyB3);
        keyC4 = findViewById(R.id.keyC4);
        keyD4 = findViewById(R.id.keyD4);
        keyE4 = findViewById(R.id.keyE4);
        keyF4 = findViewById(R.id.keyF4);
        keyG4 = findViewById(R.id.keyG4);
        keyA4 = findViewById(R.id.keyA4);
        keyB4 = findViewById(R.id.keyB4);
        keyC5 = findViewById(R.id.keyC5);
        keyD5 = findViewById(R.id.keyD5);
        keyE5 = findViewById(R.id.keyE5);
        keyF5 = findViewById(R.id.keyF5);
        keyG5 = findViewById(R.id.keyG5);
        keyA5 = findViewById(R.id.keyA5);
        keyB5 = findViewById(R.id.keyB5);
        keyC6 = findViewById(R.id.keyC6);
        keyD6 = findViewById(R.id.keyD6);
        keyE6 = findViewById(R.id.keyE6);
        keyF6 = findViewById(R.id.keyF6);
        keyG6 = findViewById(R.id.keyG6);
        keyA6 = findViewById(R.id.keyA6);
        keyB6 = findViewById(R.id.keyB6);
        keyC7 = findViewById(R.id.keyC7);
        keyD7 = findViewById(R.id.keyD7);
        keyE7 = findViewById(R.id.keyE7);
        keyF7 = findViewById(R.id.keyF7);
        keyG7 = findViewById(R.id.keyG7);
        keyA7 = findViewById(R.id.keyA7);
        keyB7 = findViewById(R.id.keyB7);

        // Black keys
        keyCs3 = findViewById(R.id.keyCs3);
        keyDs3 = findViewById(R.id.keyDs3);
        keyFs3 = findViewById(R.id.keyFs3);
        keyGs3 = findViewById(R.id.keyGs3);
        keyAs3 = findViewById(R.id.keyAs3);
        keyCs4 = findViewById(R.id.keyCs4);
        keyDs4 = findViewById(R.id.keyDs4);
        keyFs4 = findViewById(R.id.keyFs4);
        keyGs4 = findViewById(R.id.keyGs4);
        keyAs4 = findViewById(R.id.keyAs4);
        keyCs5 = findViewById(R.id.keyCs5);
        keyDs5 = findViewById(R.id.keyDs5);
        keyFs5 = findViewById(R.id.keyFs5);
        keyGs5 = findViewById(R.id.keyGs5);
        keyAs5 = findViewById(R.id.keyAs5);
        keyCs6 = findViewById(R.id.keyCs6);
        keyDs6 = findViewById(R.id.keyDs6);
        keyFs6 = findViewById(R.id.keyFs6);
        keyGs6 = findViewById(R.id.keyGs6);
        keyAs6 = findViewById(R.id.keyAs6);
        keyCs7 = findViewById(R.id.keyCs7);
        keyDs7 = findViewById(R.id.keyDs7);
        keyFs7 = findViewById(R.id.keyFs7);
        keyGs7 = findViewById(R.id.keyGs7);
        keyAs7 = findViewById(R.id.keyAs7);
    }

    private void initializeTextViewLabelsOnPianoKeys() {
        labelC3 = findViewById(R.id.labelC3);
        labelD3 = findViewById(R.id.labelD3);
        labelE3 = findViewById(R.id.labelE3);
        labelF3 = findViewById(R.id.labelF3);
        labelG3 = findViewById(R.id.labelG3);
        labelA3 = findViewById(R.id.labelA3);
        labelB3 = findViewById(R.id.labelB3);
        labelC4 = findViewById(R.id.labelC4);
        labelD4 = findViewById(R.id.labelD4);
        labelE4 = findViewById(R.id.labelE4);
        labelF4 = findViewById(R.id.labelF4);
        labelG4 = findViewById(R.id.labelG4);
        labelA4 = findViewById(R.id.labelA4);
        labelB4 = findViewById(R.id.labelB4);
        labelC5 = findViewById(R.id.labelC5);
        labelD5 = findViewById(R.id.labelD5);
        labelE5 = findViewById(R.id.labelE5);
        labelF5 = findViewById(R.id.labelF5);
        labelG5 = findViewById(R.id.labelG5);
        labelA5 = findViewById(R.id.labelA5);
        labelB5 = findViewById(R.id.labelB5);
        labelC6 = findViewById(R.id.labelC6);
        labelD6 = findViewById(R.id.labelD6);
        labelE6 = findViewById(R.id.labelE6);
        labelF6 = findViewById(R.id.labelF6);
        labelG6 = findViewById(R.id.labelG6);
        labelA6 = findViewById(R.id.labelA6);
        labelB6 = findViewById(R.id.labelB6);
        labelC7 = findViewById(R.id.labelC7);
        labelD7 = findViewById(R.id.labelD7);
        labelE7 = findViewById(R.id.labelE7);
        labelF7 = findViewById(R.id.labelF7);
        labelG7 = findViewById(R.id.labelG7);
        labelA7 = findViewById(R.id.labelA7);
        labelB7 = findViewById(R.id.labelB7);
    }
}
