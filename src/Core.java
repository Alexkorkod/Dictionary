import java.io.IOException;

//TODO allow custom directories
//TODO make word wrap
public class Core {
    private static Master master = new Master();

    public static void main(String[] args) throws IOException {
        InputUi Gui = new InputUi();
        Gui.setVisible(true);
    }

    static Master getMaster(){
        return master;
    }
}
