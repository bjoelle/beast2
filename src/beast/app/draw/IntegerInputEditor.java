package beast.app.draw;

public class IntegerInputEditor extends InputEditor.Base {
    private static final long serialVersionUID = 1L;

    public IntegerInputEditor() {
        super();
    }

    @Override
    public Class<?> type() {
        return Integer.class;
    }

} // class IntegerInputEditor
