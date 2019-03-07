package org.weasis.dicom.google.api.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class StudiesTable extends JPanel {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudiesTable.class);

    private ImageIcon loadIcon = new ImageIcon(this.getClass().getResource("/loading.gif"));

    private static final Object[] COLUMN_NAMES = {
            "Status","Patient name", "Patient ID", "ACC.#", "Study date", "Study time",
            "Desc", "REF.PHD", "REQ.PHD", "LOCATION", "BIRTH DATE"
    };

    private final DefaultTableModel tableModel;

    private final List<StudyView> studies = new ArrayList<>();
    private final GoogleExplorer explorer;
    private final JTable table;
    
    public StudiesTable(GoogleExplorer explorer) {
        this.explorer = explorer;
        tableModel = new DefaultTableModel(COLUMN_NAMES, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            @Override
            public Class<?> getColumnClass(int columnIndex) {
            	if (columnIndex == 0) {
            		return ImageIcon.class;
            	}
            	return super.getColumnClass(columnIndex);
            }
        };

        table = new JTable(tableModel);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        table.setFont(new Font("Sans-serif", Font.PLAIN, 14));

        BorderLayout layout = new BorderLayout();
        setLayout(layout);
        add(scrollPane, BorderLayout.CENTER);

        table.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table = (JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 2
                        && row >= 0) {
                    StudyView study = studies.get(row);
                    explorer.fireStudySelected(study.getStudyId());
                }
            }
        });
    }
    
    private void setImageObserver(JTable table) {
        TableModel model = table.getModel();
        int rowCount = model.getRowCount();
        int col = 0;
        if (ImageIcon.class == model.getColumnClass(col)) {
            for (int row = 0; row < rowCount; row++) {
                Object obj = model.getValueAt(row, col);
                ImageIcon icon = null;
                if (obj instanceof ImageIcon) {
                    icon = (ImageIcon) model.getValueAt(row, col);
                }
                if (icon != null && icon.getImageObserver() == null) {
               		icon.setImageObserver(new CellImageObserver(table, row, col));
                }
            }
        }
    }

    class CellImageObserver implements ImageObserver {
        JTable table;
        int row;
        int col;

        CellImageObserver(JTable table, int row, int col) {
            this.table = table;
            this.row = row;
            this.col = col;
        }

        public boolean imageUpdate(Image img, int flags, int x, int y, int w, int h) {
            if ((flags & (FRAMEBITS | ALLBITS)) != 0) {
                table.repaint();
            }
            return (flags & (ALLBITS | ABORT)) == 0;
        }
    }
    
    public void showLoadIcon(String studyId) {
    	for (int i=0;i < studies.size();i++) {
    		if (studies.get(i).getStudyId() == studyId) {
		    	table.setValueAt(loadIcon, i, 0);
		    	break;
    		}
    	}
        setImageObserver(table);
    }

    public void hideLoadIcon(String studyId) {
    	for (int i=0;i < studies.size();i++) {
    		if (studies.get(i).getStudyId() == studyId) {
		    	table.setValueAt(null, i, 0);
		    	break;
    		}
    	}
        setImageObserver(table);
    }

    public void addStudy(StudyView study) {
        LOGGER.info("Adding record");
        Vector<Object> values = new Vector<>();
        values.add(null);
        values.add(study.getPatientName());
        values.add(study.getPatientId());
        values.add(study.getAccountNumber());
        values.add(study.getStudyDate());
        values.add(study.getStudyTime());
        values.add(study.getDescription());
        values.add(study.getRefPhd());
        values.add(study.getReqPhd());
        values.add(study.getLocation());
        values.add(study.getBirthDate());
        tableModel.addRow(values);
        studies.add(study);
    }

    public void clearTable() {
        LOGGER.info("Removing " + tableModel.getRowCount() + " records");
        for (int i = tableModel.getRowCount() - 1; i >= 0; i--) {
            tableModel.removeRow(i);
        }
        studies.clear();
    }
}
