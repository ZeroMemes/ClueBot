package cluebot.util;

import javax.swing.table.DefaultTableModel;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author Brady
 * @since 12/24/2017 10:09 PM
 */
public final class TableHelper {

    private TableHelper() {}

    public static void removeAllRows(DefaultTableModel tableModel) {
        while (tableModel.getRowCount() > 0) {
            tableModel.removeRow(0);
        }
    }

    public static <T> void addAllRows(DefaultTableModel tableModel, List<T> data, Function<T, Object[]> toArrayFunction) {
        data.forEach(o -> tableModel.addRow(toArrayFunction.apply(o)));
    }

    public static void addAllRows(DefaultTableModel tableModel, List<?> data) {
        data.forEach(o -> tableModel.addRow(new Object[] { o }));
    }

    public static <T> void addAllRows(DefaultTableModel tableModel, T[] data, Function<T, Object[]> toArrayFunction) {
        Arrays.stream(data).forEach(o -> tableModel.addRow(toArrayFunction.apply(o)));
    }

    public static void addAllRows(DefaultTableModel tableModel, Object[] data) {
        Arrays.stream(data).forEach(o -> tableModel.addRow(new Object[] { o }));
    }

    public static void addAllRows(DefaultTableModel tableModel, Map<?, ?> data) {
        data.forEach((k, v) -> tableModel.addRow(new Object[] { k, v }));
    }
}
