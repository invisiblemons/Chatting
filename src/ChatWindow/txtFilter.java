package ChatWindow;

import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.*;

public class txtFilter extends FileFilter {

    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        String extension = Utils.getExtension(f);
        if (extension != null) {
            if (extension.equals(Utils.txt)) {
                return true;
            }
        }
        return false;
    }

    //The description of this filter
    public String getDescription() {
        return "Just txt";
    }
}
