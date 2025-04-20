/**
 * This class defines the Test activity for the MelodyKeys application.
 *
 * It extends AppCompatActivity and sets up the layout defined in test.xml.
 * The activity enables edge-to-edge display using EdgeToEdge.enable(this) to allow the UI
 * to draw behind system bars, providing a more immersive experience on modern Android devices.
 *
 * It also applies window insets to the root view with ViewCompat.setOnApplyWindowInsetsListener,
 * ensuring that the content is padded correctly to avoid being overlapped by system bars
 * (such as the status bar and navigation bar), according to Android's edge-to-edge guidelines.
 *
 * This setup is standard for activities that want to provide a modern, full-screen user experience
 * while maintaining proper layout and usability across different devices and Android versions.
 */

package es.ifp.melodykeys;

import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Test extends AppCompatActivity {

    Button button, button2, button3, button4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.test); // Now correctly references test.xml


        button =(Button)findViewById(R.id.button);
        button2 = (Button)findViewById(R.id.button2);
        button3 =(Button) findViewById(R.id.button3);
        button4 =(Button) findViewById(R.id.button4);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
