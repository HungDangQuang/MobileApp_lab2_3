package uit.app.calculator;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;
import org.mariuszgromada.math.mxparser.*;
import androidx.appcompat.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

//import uit.app.calculator.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.input);
        display.setShowSoftInputOnFocus(false);

        display.setSelection(display.getText().length());
    }


    private void updateText(String strToAdd){
        String oldStr = display.getText().toString();

        int cursorPos = display.getSelectionStart();

        String leftStr = oldStr.substring(0,cursorPos);

        String rightStr = oldStr.substring(cursorPos);

        if(getString(R.string.display).equals(display.getText().toString())){
            display.setText(strToAdd);
        }
        else{
            display.setText(String.format("%s%s%s", leftStr,strToAdd,rightStr));
        }
        display.setSelection(cursorPos + 1);

    }


    public void onClick(View v){
        Button b = (Button)v;
        String buttonText = b.getText().toString();
        updateText(buttonText);
    }

    public void deleteAll(View v){
        display.setText("");
    }

    public void deleteOne(View v){
        int cursorPos = display.getSelectionStart();
        int textLen = display.getText().length();

        if(cursorPos != 0 && textLen != 0){
            SpannableStringBuilder selection = (SpannableStringBuilder)display.getText();
            selection.replace(cursorPos -1, cursorPos,"");
            display.setText(selection);
            display.setSelection(cursorPos - 1);
        }
    }

    public void par(View v){
        int cursorPos = display.getSelectionStart();
        int openPar = 0;
        int closedPar = 0;
        int textLen = display.getText().length();

        for(int i = 0;i < cursorPos; i++){
            if(display.getText().toString().substring(i,i+1).equals("(")){
                openPar += 1;
            }
            if(display.getText().toString().substring(i,i+1).equals(")")){
                closedPar += 1;
            }
        }

        if(openPar == closedPar || display.getText().toString().substring(textLen-1, textLen).equals("(")){
            updateText("(");
        }

        else if(openPar > closedPar && !display.getText().toString().substring(textLen-1, textLen).equals("(")){
            updateText(")");
        }
        display.setSelection(cursorPos + 1);
    }
    public void showResult(View v){
        String input = display.getText().toString();
        input = input.replaceAll("÷", "/");
        input = input.replaceAll("×", "*");

        Expression exp = new Expression(input);

        String result = String.valueOf(exp.calculate());

        display.setText(result);
        display.setSelection(result.length());
    }
}